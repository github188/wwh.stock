DROP PROCEDURE if exists p_update_period;
CREATE PROCEDURE p_update_period(stockCode varchar(10), stockName VARCHAR(30), startDt VARCHAR(10), startPrice VARCHAR(10)
    , endDt VARCHAR(10), endPrice VARCHAR(10), statTable VARCHAR(100), flag INT)
BEGIN
    DECLARE startClose VARCHAR(10);
    DECLARE endClose VARCHAR(10);
    DECLARE upWidth VARCHAR(10);
    DECLARE closeWidth VARCHAR(10);
    DECLARE sumTotal INT;
    DECLARE upTotal INT;
    DECLARE downTotal INT;
    DECLARE balanceTotal INT;
    DECLARE cnt INT DEFAULT 0;

    if (statTable = 'stk_hq_details') then
        SELECT if(flag=1,min(price+0),max(price+0)),if(flag=1,max(price+0),min(price+0))
        INTO startClose,endClose
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
        set closeWidth = f_calc_width(startClose,endClose,null);
        if isnull(startPrice) then
            SELECT if(flag=1,min(low+0),max(high+0))
            INTO startPrice
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt = startDt;
        end if;
        if isnull(endPrice) then
            SELECT if(flag=1,max(high+0),min(low+0))
            INTO endPrice
                FROM stk_hq_details
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt = endDt;
        end if;
        set upWidth = f_calc_width(startPrice,endPrice,null);

        SELECT count(1)
        INTO upTotal
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND price > pre_close
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
        SELECT count(1)
        INTO downTotal
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND price < pre_close
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
        SELECT count(1)
        INTO balanceTotal
            FROM stk_hq_details
        WHERE valid = '1'
            AND stock_code = stockCode
            AND price = pre_close
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
    end if;
    if (statTable = 'stk_rx_data') then
        SELECT if(flag=1,min(cur_close+0),max(cur_close+0)),if(flag=1,max(cur_close+0),min(cur_close+0))
        INTO startClose,endClose
            FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
        set closeWidth = f_calc_width(startClose,endClose,null);
        if isnull(startPrice) then
            SELECT if(flag=1,min(low+0),max(high+0))
            INTO startPrice
                FROM stk_rx_data
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt BETWEEN startDt AND endDt
                AND dt != endDt;
        end if;
        if isnull(endPrice) then
            SELECT if(flag=1,max(high+0),min(low+0))
            INTO endPrice
                FROM stk_rx_data
            WHERE valid = '1'
                AND stock_code = stockCode
                AND dt BETWEEN startDt AND endDt
                AND dt != startDt;
        end if;
        set upWidth = f_calc_width(startPrice,endPrice,null);

        SELECT count(1)
        INTO upTotal
            FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND cur_close > cur_open
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
        SELECT count(1)
        INTO downTotal
            FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND cur_close < cur_open
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
        SELECT count(1)
        INTO balanceTotal
            FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND cur_close = cur_open
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
    end if;
    if (statTable = 'stk_index_data') then
        SELECT if(flag=1,min(price+0),max(price+0)),if(flag=1,max(price+0),min(price+0))
        INTO startClose,endClose
            FROM stk_index_data
        WHERE valid = '1'
            AND code = stockCode
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
        set closeWidth = f_calc_width(startClose,endClose,null);
        if isnull(startPrice) then
            SELECT if(flag=1,min(low+0),max(high+0))
            INTO startPrice
                FROM stk_index_data
            WHERE valid = '1'
                AND code = stockCode
                AND dt BETWEEN startDt AND endDt
                AND dt != endDt;
        end if;
        if isnull(endPrice) then
            SELECT if(flag=1,max(high+0),min(low+0))
            INTO endPrice
                FROM stk_index_data
            WHERE valid = '1'
                AND code = stockCode
                AND dt BETWEEN startDt AND endDt
                AND dt != startDt;
        end if;
        set upWidth = f_calc_width(startPrice,endPrice,null);

        SELECT count(1)
        INTO upTotal
            FROM stk_index_data
        WHERE valid = '1'
            AND code = stockCode
            AND price > cur_open
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
        SELECT count(1)
        INTO downTotal
            FROM stk_index_data
        WHERE valid = '1'
            AND code = stockCode
            AND price < cur_open
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
        SELECT count(1)
        INTO balanceTotal
            FROM stk_index_data
        WHERE valid = '1'
            AND code = stockCode
            AND price = cur_open
            AND dt BETWEEN startDt AND endDt
            AND cur_open != '--';
    end if;
    set sumTotal = ifnull(upTotal,0) + ifnull(downTotal,0) + ifnull(balanceTotal,0);

    SELECT count(1)
    INTO cnt
    FROM tot_stock_period
    WHERE valid = '1'
        AND stock_code = stockCode
        AND start_dt = startDt;
    if (cnt = 0) then
        INSERT INTO tot_stock_period(
            stock_code,
            stock_name,
            start_dt,
            end_dt,
            start_price,
            end_price,
            up_width,
            start_close,
            end_close,
            close_width,
            sum_total,
            up_total,
            down_total,
            balance_total,
            up_flag,
            create_date)
        VALUES(
            stockCode,
            stockName,
            startDt,
            endDt,
            startPrice,
            endPrice,
            upWidth,
            startClose,
            endClose,
            closeWidth,
            sumTotal,
            upTotal,
            downTotal,
            balanceTotal,
            flag,
            current_date);
    else
        UPDATE tot_stock_period
        SET stock_name = stockName
            ,end_dt = endDt
            ,start_price = startPrice
            ,end_price = endPrice
            ,up_width = upWidth
            ,start_close = startClose
            ,end_close = endClose
            ,close_width = closeWidth
            ,sum_total = sumTotal
            ,up_total = upTotal
            ,down_total = downTotal
            ,balance_total = balanceTotal
            ,up_flag = flag
            ,update_date = current_date
        WHERE valid = '1'
            AND stock_code = stockCode
            AND start_dt = startDt;
    end if;
END;