DROP PROCEDURE if exists p_per_limit;
CREATE PROCEDURE p_per_limit(stockCode varchar(10))
BEGIN
    DECLARE startDt VARCHAR(10);
    DECLARE endDt VARCHAR(10);
    DECLARE maxDt VARCHAR(10);
    DECLARE maxHigh VARCHAR(10);
    DECLARE minDt VARCHAR(10);
    DECLARE minLow VARCHAR(10);
    DECLARE upWidth VARCHAR(10);
    DECLARE stockName VARCHAR(30);
    DECLARE curPrice VARCHAR(10);
    DECLARE maxClose VARCHAR(10);
    DECLARE minClose VARCHAR(10);
    DECLARE closeWidth VARCHAR(10);
    DECLARE cnt INT DEFAULT 0;

    SELECT min(dt),max(dt),max(stock_name),max(high+0),min(low+0),max(cur_close+0),min(cur_close+0)
    INTO startDt,endDt,stockName,maxHigh,minLow,maxClose,minClose
        FROM stk_rx_data
    WHERE valid = '1'
        AND stock_code = stockCode;
    SELECT cur_close
    INTO curPrice
        FROM stk_rx_data
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt = endDt;
    SELECT max(dt)
    INTO maxDt
        FROM stk_rx_data
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt BETWEEN startDt AND endDt
        AND high = maxHigh+0;
    SELECT max(dt)
    INTO minDt
        FROM stk_rx_data
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt BETWEEN startDt AND endDt
        AND low = minLow+0;

    if (maxDt <= minDt) then
        set upWidth = f_calc_width(maxHigh,minLow,null);
        set closeWidth = f_calc_width(minClose,curPrice,null);
    elseif (maxDt > minDt) then
        set upWidth = f_calc_width(minLow,maxHigh,null);
        set closeWidth = f_calc_width(maxClose,curPrice,null);
    end if;

    SELECT count(1)
    INTO cnt
    FROM tot_stock_limit
    WHERE valid = '1'
        AND stock_code = stockCode;
    if (cnt = 0) then
        INSERT INTO tot_stock_limit(
            start_dt,
            stock_code,
            stock_name,
            max_dt,
            max_price,
            min_dt,
            min_price,
            up_width,
            price,
            max_close,
            min_close,
            close_width,
            create_date)
        VALUES(
            startDt,
            stockCode,
            stockName,
            maxDt,
            maxHigh,
            minDt,
            minLow,
            upWidth,
            curPrice,
            maxClose,
            minClose,
            closeWidth,
            current_date);
    else
        UPDATE tot_stock_limit
        SET max_dt = maxDt
            ,max_price = maxHigh
            ,min_dt = minDt
            ,min_price = minLow
            ,up_width = upWidth
            ,price = curPrice
            ,max_close = maxClose
            ,min_close = minClose
            ,close_width = closeWidth
            ,update_date = current_date
        WHERE valid = '1'
            AND stock_code = stockCode
            AND start_dt = startDt;
    end if;

END
