DROP PROCEDURE p_statistic_basic;
CREATE PROCEDURE p_statistic_basic(stockCode varchar(10), startDate varchar(10))
BEGIN
    DECLARE maxDt VARCHAR(10);
    DECLARE maxHigh VARCHAR(10);
    DECLARE minDt VARCHAR(10);
    DECLARE minHigh VARCHAR(10);
    DECLARE upWidth VARCHAR(10);
    DECLARE upCurWidth VARCHAR(10);
    DECLARE stockName VARCHAR(30);
    DECLARE curDt VARCHAR(10);
    DECLARE curPrice VARCHAR(10);
    DECLARE maxPrice VARCHAR(10);
    DECLARE minPrice VARCHAR(10);
    DECLARE upTotal INT;
    DECLARE downTotal INT;
    DECLARE dividendDate VARCHAR(10);
    DECLARE sendScale VARCHAR(10);
    DECLARE turnScale VARCHAR(10);
    DECLARE cashScale VARCHAR(10);

    if isnull(startDate) then
        SELECT DATE_SUB( DATE_SUB( CURDATE( ) , INTERVAL DAYOFMONTH( CURDATE( ) ) -1 DAY ) , INTERVAL 2 MONTH )
        INTO startDate;
    end if;

    SELECT max(dt)
    INTO curDt
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt BETWEEN startDate AND CURDATE()
        AND cur_open != '--';

    if isnull(curDt) then
        DELETE FROM tot_stock_basic
        WHERE valid = '1'
        AND stock_code = stockCode
        AND dt = startDate;
    else
        SELECT count(1)
        INTO upTotal
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND f_cast(change_amount) > 0
            AND dt BETWEEN startDate AND curDt
            AND cur_open != '--';
        SELECT count(1)
        INTO downTotal
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND f_cast(change_amount) < 0
            AND dt BETWEEN startDate AND curDt
            AND cur_open != '--';

        SELECT max(dividend_date)
        INTO dividendDate
           FROM stk_high_dividend
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dividend_date BETWEEN startDate AND curDt;
        SELECT send_scale,turn_scale,cash_scale
        INTO sendScale,turnScale,cashScale
            FROM stk_high_dividend
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dividend_date = dividendDate;
        if (dividendDate is not null and dividendDate > startDate) then
            SELECT max(if(dt < dividendDate,f_calc_dividend(high,sendScale,turnScale,cashScale),high)+0)
            ,min(if(dt < dividendDate,f_calc_dividend(low,sendScale,turnScale,cashScale),low)+0)
            ,max(if(dt < dividendDate,f_calc_dividend(price,sendScale,turnScale,cashScale),price)+0)
            ,min(if(dt < dividendDate,f_calc_dividend(price,sendScale,turnScale,cashScale),price)+0)
            INTO maxHigh,minHigh,maxPrice,minPrice
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt BETWEEN startDate AND curDt
                AND cur_open != '--';
            SELECT max(dt)
            INTO maxDt
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND if(dt < dividendDate,f_calc_dividend(high,sendScale,turnScale,cashScale),high) = maxHigh+0;
            SELECT max(dt)
            INTO minDt
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND if(dt < dividendDate,f_calc_dividend(low,sendScale,turnScale,cashScale),low) = minHigh+0;
        else
            SELECT max(high+0),min(low+0),max(price+0),min(price+0)
            INTO maxHigh,minHigh,maxPrice,minPrice
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt BETWEEN startDate AND curDt
                AND cur_open != '--';
            SELECT max(dt)
            INTO maxDt
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND high = maxHigh+0;
            SELECT max(dt)
            INTO minDt
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND low = minHigh+0;
        end if;

        SELECT stock_name,price
        INTO stockName,curPrice
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt = curDt;

        if (maxDt < minDt) then
            set upWidth = f_calc_width(maxPrice,minPrice,null);
            set upCurWidth = f_calc_width(minHigh,curPrice,null);
        elseif (maxDt > minDt) then
            set upWidth = f_calc_width(minPrice,maxPrice,null);
            set upCurWidth = f_calc_width(maxHigh,curPrice,null);
        end if;

        REPLACE INTO tot_stock_basic(
            start_dt,
            stock_code,
            stock_name,
            max_dt,
            max_High,
            min_dt,
            min_High,
            up_width,
            up_cur_width,
            max_price,
            min_price,
            price,
            up_total,
            down_total,
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
            upCurWidth,
            maxprice,
            minprice,
            curPrice,
            upTotal,
            downTotal,
            current_date);
    end if;

END
