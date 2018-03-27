DROP PROCEDURE if exists p_per_period;
CREATE PROCEDURE p_per_period(stockCode varchar(10), startDate varchar(10), endDate varchar(10), statTable VARCHAR(100),minDays INT)
top:BEGIN
    DECLARE startDt VARCHAR(10);
    DECLARE endDt VARCHAR(10);
    DECLARE tmpDt VARCHAR(10);
    DECLARE stockName VARCHAR(30);
    DECLARE maxPrice VARCHAR(10);
    DECLARE minPrice VARCHAR(10);
    DECLARE flag INT DEFAULT 0;
    DECLARE cnt INT DEFAULT 0;

    if (statTable = 'stk_hq_details') then
        SELECT if(dt > endDate, endDate, dt)
        INTO tmpDt
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt >= startDate
            ORDER BY dt
            limit minDays,1;
    elseif (statTable = 'stk_rx_data') then
        SELECT if(dt > endDate, endDate, dt)
        INTO tmpDt
            FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt >= startDate
            ORDER BY dt
            limit minDays,1;
    elseif (statTable = 'stk_index_data') then
        SELECT if(dt > endDate, endDate, dt)
        INTO tmpDt
            FROM stk_index_data
        WHERE valid = '1'
            AND code = stockCode
            AND dt >= startDate
            ORDER BY dt
            limit minDays,1;
    end if;
    if (isnull(tmpDt)) then
        set tmpDt = endDate;
    end if;
    if (statTable = 'stk_hq_details') then
        SELECT min(stock_name),max(high+0),min(low+0),count(1)
        INTO stockName,maxPrice,minPrice,cnt
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
    elseif (statTable = 'stk_rx_data') then
       SELECT min(stock_name),max(high+0),min(low+0),count(1)
        INTO stockName,maxPrice,minPrice,cnt
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
        SELECT min(dt)
        INTO endDt
            FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND low = minPrice+0;
     elseif (statTable = 'stk_index_data') then
       SELECT min(name),max(high+0),min(low+0),count(1)
        INTO stockName,maxPrice,minPrice,cnt
            FROM stk_index_data
        WHERE valid = '1'
            AND code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND cur_open != '--';
        SELECT min(dt)
        INTO startDt
            FROM stk_index_data
        WHERE valid = '1'
            AND code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND high = maxPrice+0;
        SELECT min(dt)
        INTO endDt
            FROM stk_index_data
        WHERE valid = '1'
            AND code = stockCode
            AND dt BETWEEN startDate AND tmpDt
            AND low = minPrice+0;
    end if;
    if (endDate = startDt or endDate = endDt) and (cnt < minDays) then
        call p_update_period(stockCode, stockName, startDate, null, endDate, null, statTable, flag);
        leave top;
    end if;

    SET @@max_sp_recursion_depth = 50;

    SELECT IF(startDate=startDt,-1,0),IF(startDate=startDt,endDt,startDt)
    INTO flag,tmpDt;
    if (flag = 0) then
        SELECT IF(startDate=endDt,1,0),IF(startDate=endDt,startDt,endDt)
        INTO flag,tmpDt;
    end if;
    if (flag = 0) then
        SELECT IF(startDt<endDt,-1,1),IF(startDt<endDt,startDt,endDt)
        INTO flag,tmpDt;
    end if;
    call p_update_period(stockCode, stockName, startDate, null, tmpDt, null, statTable, flag);
    call p_per_period(stockCode,tmpDt,endDate,statTable,minDays);
END
