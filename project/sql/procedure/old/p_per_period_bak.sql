DROP PROCEDURE if exists p_per_period;
CREATE PROCEDURE p_per_period(stockCode varchar(10), startDate varchar(10), endDate varchar(10), statTable VARCHAR(100),minDays INT)
top:BEGIN
    DECLARE startDt VARCHAR(10);
    DECLARE endDt VARCHAR(10);
    DECLARE tmpDt VARCHAR(10);
    DECLARE orderBy INT;
    DECLARE no_more_record INT DEFAULT 0;

    DECLARE cur_record CURSOR FOR 
        SELECT start_dt,end_dt
        FROM tot_stock_period
        WHERE valid = '1'
            AND stock_code = stockCode
            AND order_by = orderBy;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_record = 1;

    SELECT max(order_by)
    INTO orderBy
    FROM tot_stock_period
    WHERE valid = '1'
        AND stock_code = stockCode;
    if (isnull(orderBy)) then
        set orderBy = 1;
        if (statTable = 'stk_hq_details') then
            SELECT min(dt),max(dt)
            INTO startDt,endDt
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt >= startDate;
            SELECT if(dt > endDate, endDate, dt)
            INTO tmpDt
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt >= startDt
                ORDER BY dt
                limit minDays,1;
        end if;
        if (statTable = 'stk_rx_data') then
            SELECT min(dt),max(dt)
            INTO startDt,endDt
                FROM stk_rx_data
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt >= startDate;
            SELECT if(dt > endDate, endDate, dt)
            INTO tmpDt
                FROM stk_rx_data
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt >= startDt
                ORDER BY dt
                limit minDays,1;
        end if;
        if (statTable = 'stk_index_data') then
            SELECT min(dt),max(dt)
            INTO startDt,endDt
                FROM stk_index_data
            WHERE valid = '1'
                AND code = stockCode
                AND dt >= startDate;
            SELECT if(dt > endDate, endDate, dt)
            INTO tmpDt
                FROM stk_index_data
            WHERE valid = '1'
                AND code = stockCode
                AND dt >= startDt
                ORDER BY dt
                limit minDays,1;
        end if;
        if isnull(endDt) then
            leave top;
        end if;
        if isnull(tmpDt) then
        else if (tmpDt < endDt) then
            set endDt = tmpDt;
        end if;
        call p_stat_period(stockCode,startDt,endDt,statTable,minDays,orderBy+1);
    end if;

    SET @@max_sp_recursion_depth = 50;

    OPEN cur_record;
    FETCH cur_record INTO startDt,endDt;
    WHILE no_more_record != 1 DO
        call p_stat_period(stockCode,startDt,endDt,statTable,minDays,orderBy+1);
        FETCH cur_record INTO startDt,endDt;
    END WHILE;
    CLOSE cur_record;

    SELECT min(start_dt)
    INTO startDt
    FROM tot_stock_period
    WHERE valid = '1'
        AND stock_code = stockCode
        AND order_by = orderBy+1;

    if (isnull(startDt)) then
    else
        call p_per_period(stockCode,startDt,statTable,minDays);
    end if;

END