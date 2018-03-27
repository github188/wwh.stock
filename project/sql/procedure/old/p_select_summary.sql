DROP PROCEDURE p_select_summary;
CREATE PROCEDURE p_select_summary(stockCode varchar(10), startDate varchar(10))
BEGIN
    DECLARE maxDt VARCHAR(10);
    DECLARE maxHigh VARCHAR(10);
    DECLARE minDt VARCHAR(10);
    DECLARE minHigh VARCHAR(10);
    DECLARE upWidth VARCHAR(10);
    DECLARE downWidth VARCHAR(10);
    DECLARE stockName VARCHAR(30);
    DECLARE curDt VARCHAR(10);
    DECLARE curPrice VARCHAR(10);
    DECLARE totalEquity VARCHAR(10);
    DECLARE totalAmount VARCHAR(10);
    DECLARE upCurWidth VARCHAR(10);
    DECLARE downCurWidth VARCHAR(10);
    DECLARE openDt VARCHAR(10);
    DECLARE upTotal INT;
    DECLARE downTotal INT;
    DECLARE continueTotal INT;
    DECLARE changeTotal INT;

    if isnull(startDate) then
        set startDate = '2016-06-01';
    end if;
    SELECT count(1)
    INTO upTotal
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND f_cast(change_amount) > 0
        AND dt BETWEEN startDate AND date_format(now(),'%Y-%m-%d')
        AND cur_open != '--';
    SELECT count(1)
    INTO downTotal
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND f_cast(change_amount) < 0
        AND dt BETWEEN startDate AND date_format(now(),'%Y-%m-%d')
        AND cur_open != '--';
    SELECT count(1)
    INTO changeTotal
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND f_calc_width(high,low,price) > 2
        AND dt BETWEEN startDate AND date_format(now(),'%Y-%m-%d')
        AND cur_open != '--';

    SELECT max(dt),max(high+0),min(low+0)
    INTO curDt,maxHigh,minHigh
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt BETWEEN startDate AND date_format(now(),'%Y-%m-%d')
        AND cur_open != '--';
    set continueTotal = f_calc_continue(stockCode, curDt);

    SELECT open_date,replace(total_equity,'вк','')
    INTO openDt,totalEquity
        FROM stk_basic_info
    WHERE valid = '1'
        AND stock_code = stockCode;
    if isnull(openDt) then
        SELECT min(dt)
        INTO openDt
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode;
    end if;

    SELECT stock_name,price,round(price*totalEquity,2)
    INTO stockName,curPrice,totalAmount
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt = curDt;
    SELECT max(dt)
    INTO maxDt
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND high = maxHigh;
    SELECT max(dt)
    INTO minDt
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND low = minHigh;

    if (maxDt < minDt) then
        set downWidth = f_calc_width(maxHigh,minHigh,null);
        if (minDt < curDt) then
            set upCurWidth = f_calc_width(minHigh,curPrice,null);
        end if;
    elseif (maxDt > minDt) then
        set upWidth = f_calc_width(minHigh,maxHigh,null);
        if (maxDt < curDt) then
            set downCurWidth = f_calc_width(curPrice,minHigh,null);
        end if;
    end if;

    REPLACE INTO tot_stock_summary(
        start_dt,
        stock_code,
        stock_name,
        max_dt,
        max_price,
        min_dt,
        min_price,
        up_width,
        down_width,
        price,
        total_amount,
        up_cur_width,
        down_cur_width,
        open_dt,
        up_total,
        down_total,
        continue_total,
        change_total,
        create_date)
    VALUES(
        startDate,
        stockCode,
        stockName,
        maxDt,
        maxHigh,
        minDt,
        minHigh,
        upWidth,
        downWidth,
        curPrice,
        totalAmount,
        upCurWidth,
        downCurWidth,
        openDt,
        upTotal,
        downTotal,
        continueTotal,
        changeTotal,
        current_date);

END
