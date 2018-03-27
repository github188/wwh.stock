DROP PROCEDURE if exists p_statistic_period;
CREATE PROCEDURE p_statistic_period(stockCode varchar(10), startDate varchar(10), endDate varchar(10), orderBy int)
BEGIN
    DECLARE startDt VARCHAR(10);
    DECLARE startPrice VARCHAR(10);
    DECLARE endDt VARCHAR(10);
    DECLARE endPrice VARCHAR(10);
    DECLARE stockName VARCHAR(30);
    DECLARE maxPrice VARCHAR(10);
    DECLARE minPrice VARCHAR(10);
    DECLARE dividendDate VARCHAR(10);
    DECLARE sendScale VARCHAR(10);
    DECLARE turnScale VARCHAR(10);
    DECLARE cashScale VARCHAR(10);

    SELECT stock_name
    INTO stockName
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt = endDate;

    SELECT max(dividend_date)
    INTO dividendDate
       FROM stk_high_dividend
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dividend_date BETWEEN startDate AND endDate;
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
        INTO startPrice,endPrice,maxPrice,minPrice
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDate AND endDate
            AND cur_open != '--';
        SELECT max(dt)
        INTO startDt
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND if(dt < dividendDate,f_calc_dividend(high,sendScale,turnScale,cashScale),high) = startPrice+0;
        SELECT max(dt)
        INTO endDt
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND if(dt < dividendDate,f_calc_dividend(low,sendScale,turnScale,cashScale),low) = endPrice+0;
    else
        SELECT max(high+0),min(low+0),max(price+0),min(price+0)
        INTO startPrice,endPrice,maxPrice,minPrice
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDate AND endDate
            AND cur_open != '--';
        SELECT max(dt)
        INTO startDt
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND high = startPrice+0;
        SELECT max(dt)
        INTO endDt
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND low = endPrice+0;
    end if;

    if (startDt < endDt) then
        if (startDate != startDt) then
            call p_update_period(stockCode, stockName, startDate, null, startDt, startPrice, null, null, dividendDate, sendScale, turnScale, cashScale, 1, orderBy);
        end if;
        call p_update_period(stockCode, stockName, startDt, startPrice, endDt, endPrice, maxPrice, minPrice, dividendDate, sendScale, turnScale, cashScale, 0, orderBy);
        if (endDate != endDt) then
            call p_update_period(stockCode, stockName, endDt, endPrice, endDate, null, null, null, dividendDate, sendScale, turnScale, cashScale, 1, orderBy);
        end if;
    elseif (startDt > endDt) then
        if (startDate != startDt) then
            call p_update_period(stockCode, stockName, startDate, null, endDt, endPrice, null, null, dividendDate, sendScale, turnScale, cashScale, 0, orderBy);
        end if;
        call p_update_period(stockCode, stockName, endDt, endPrice, startDt, startPrice, maxPrice, minPrice, dividendDate, sendScale, turnScale, cashScale, 1, orderBy);
        if (endDate != endDt) then
            call p_update_period(stockCode, stockName, startDt, startPrice, endDate, null, null, null, dividendDate, sendScale, turnScale, cashScale, 0, orderBy);
        end if;
    end if;

END
