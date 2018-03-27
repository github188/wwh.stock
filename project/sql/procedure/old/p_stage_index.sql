DROP PROCEDURE if exists p_stage_index;
CREATE PROCEDURE p_stage_index(stockCode varchar(10), startDate varchar(10), endDate varchar(10), upFlag int, orderBy int)
BEGIN
    DECLARE startDt VARCHAR(10);
    DECLARE endDt VARCHAR(10);
    DECLARE stockName VARCHAR(30);
    DECLARE maxPrice VARCHAR(10);
    DECLARE minPrice VARCHAR(10);
    DECLARE tmpDt VARCHAR(10);
    DECLARE minDays INT;
    DECLARE statWidth VARCHAR(10);
    DECLARE statTable VARCHAR(100);

    SELECT IFNULL((SELECT value
            FROM sys_param
        WHERE valid = '1'
            AND name = 'min_period_days'),30)-1
            ,IFNULL((SELECT value
            FROM sys_param
        WHERE valid = '1'
            AND name = 'stat_period_width'),2)
            ,IFNULL((SELECT value
            FROM sys_param
        WHERE valid = '1'
            AND name = 'stat_period_table'),'stk_hq_details')
        INTO minDays,statWidth,statTable;
    if (upFlag = 0) then
        set tmpDt = endDate;
    else
        if (statTable = 'stk_hq_details') then
            SELECT if(dt > endDate, endDate, dt)
            INTO tmpDt
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt >= startDate
                ORDER BY dt
                limit minDays,1;
        else
            SELECT if(dt > endDate, endDate, dt)
            INTO tmpDt
                FROM stk_rx_data
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt >= startDate
                ORDER BY dt
                limit minDays,1;
        end if;
    end if;

    if (statTable = 'stk_hq_details') then
        SELECT min(stock_name),max(high+0),min(low+0)
        INTO stockName,maxPrice,minPrice
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND cur_open != '--';
        SELECT min(dt)
        INTO startDt
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND high = maxPrice+0;
        SELECT min(dt)
        INTO endDt
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND low = minPrice+0;
    else
        SELECT min(stock_name),max(high+0)
        INTO stockName,maxPrice
            FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND cur_open != '--';
        SELECT min(dt)
        INTO startDt
            FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND high = maxPrice+0;
        SELECT min(low+0)
        INTO minPrice
            FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND dt != startDt
            AND cur_open != '--';
        SELECT min(dt)
        INTO endDt
            FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND low = minPrice+0;
    end if;

    if (startDt < endDt) then
        if (startDate != startDt) then
            call p_update_period(stockCode, stockName, startDate, null, startDt, maxPrice, minDays+1, statWidth, statTable, 1, orderBy);
        end if;
        call p_update_period(stockCode, stockName, startDt, maxPrice, endDt, minPrice, minDays+1, statWidth, statTable, -1, orderBy);
        if (endDate != endDt) then
            call p_update_period(stockCode, stockName, endDt, minPrice, endDate, null, minDays+1, statWidth, statTable, 1, orderBy);
        end if;
    elseif (startDt > endDt) then
        if (startDate != endDt) then
            call p_update_period(stockCode, stockName, startDate, null, endDt, minPrice, minDays+1, statWidth, statTable, -1, orderBy);
        end if;
        call p_update_period(stockCode, stockName, endDt, minPrice, startDt, maxPrice, minDays+1, statWidth, statTable, 1, orderBy);
        if (endDate != startDt) then
            call p_update_period(stockCode, stockName, startDt, maxPrice, endDate, null, minDays+1, statWidth, statTable, -1, orderBy);
        end if;
    end if;

END
