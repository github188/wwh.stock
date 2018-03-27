DROP PROCEDURE p_all_period;
CREATE PROCEDURE p_all_period()
BEGIN
    DECLARE stockCode VARCHAR(10);
    DECLARE no_more_info INT DEFAULT 0;
    DECLARE statTable VARCHAR(100);
    DECLARE minDays INT;
    DECLARE startDt VARCHAR(10);
    DECLARE endDt VARCHAR(10);
    DECLARE maxDt VARCHAR(10);
    DECLARE startClose VARCHAR(10);
    DECLARE endClose VARCHAR(10);

    DECLARE cur_info CURSOR FOR 
        SELECT code
        FROM stk_stocks_info
        WHERE valid = '1'
            AND stype =if(statTable='stk_index_data','z', 'a')
            AND status < 2;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_info = 1;

    SELECT IFNULL((SELECT value
            FROM sys_param
        WHERE valid = '1'
            AND name = 'stat_period_table'),'stk_hq_details')
                , IFNULL((SELECT value
            FROM sys_param
        WHERE valid = '1'
            AND name = 'min_period_days'),30)-1
        INTO statTable,minDays;

    OPEN cur_info;
    FETCH cur_info INTO stockCode;
    WHILE no_more_info != 1 DO
        SELECT max(start_dt)
        INTO maxDt
        FROM tot_stock_period
        WHERE valid = '1'
            AND stock_code = stockCode;
        if (statTable = 'stk_hq_details') then
            SELECT min(dt),max(dt)
            INTO startDt,endDt
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode;
        end if;
        if (statTable = 'stk_rx_data') then
            SELECT min(dt),max(dt)
            INTO startDt,endDt
                FROM stk_rx_data
            WHERE valid = '1'
                AND stock_code = stockCode;
        end if;
        if (statTable = 'stk_index_data') then
            SELECT min(dt),max(dt)
            INTO startDt,endDt
                FROM stk_index_data
            WHERE valid = '1'
                AND code = stockCode;
        end if;
        if (isnull(endDt) = 0) then
            if (isnull(maxDt) = 0) then
                set startDt = maxDt;
            end if;
            call p_per_period(stockCode,startDt,endDt,statTable,minDays);

            SELECT MAX(start_dt),MAX(start_close),MIN(end_close)
            INTO maxDt,startClose,endClose
                FROM tot_stock_period
            WHERE valid = '1'
                AND stock_code = stockCode;
            UPDATE tot_stock_period
            SET start_price=startClose
                    ,end_price=endClose
                    ,up_width=f_calc_width(endClose,end_close,null)
            WHERE valid = '1'
                AND stock_code = stockCode
                AND start_dt = maxDt;
        end if;

        FETCH cur_info INTO stockCode;
    END WHILE;
    CLOSE cur_info;
END