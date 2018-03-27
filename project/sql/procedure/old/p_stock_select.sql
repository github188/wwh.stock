DROP PROCEDURE if exists p_stock_select;
CREATE PROCEDURE p_stock_select(stockCode varchar(10))
top:BEGIN
    DECLARE startDt VARCHAR(10) default '2017-06-01';
    DECLARE minDt VARCHAR(10);
    DECLARE stockName VARCHAR(30);
    DECLARE curPrice VARCHAR(10);
    DECLARE minPrice VARCHAR(10);
    DECLARE curOpen VARCHAR(10);
    DECLARE curClose VARCHAR(10);
    DECLARE up0 INT default 0;
    DECLARE up3 INT;
    DECLARE up5 INT;
    DECLARE up9 INT;
    DECLARE upWidth VARCHAR(10);
    DECLARE upWidth1 VARCHAR(10);
    DECLARE upWidth2 VARCHAR(10);
    DECLARE upWidth3 VARCHAR(10);
    DECLARE no_more_record INT DEFAULT 0;

    DECLARE cur_record CURSOR FOR 
        SELECT cur_open,cur_close
        FROM stk_rx_data
        WHERE valid = '1'
            AND stock_code = stockCode
            AND dt >= startDt
            ORDER BY dt desc;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_record = 1;

    OPEN cur_record;
    FETCH cur_record INTO curOpen,curClose;
    WHILE (no_more_record != 1 and up0 < 3) DO
        if (up0 = 0) then
            set curPrice = curClose;
            set upWidth1 = f_calc_width(curOpen,curClose,null);
        end if;
        if (up0 = 1) then
            set upWidth2 = f_calc_width(curOpen,curClose,null);
        end if;
        if (up0 = 2) then
            set upWidth3 = f_calc_width(curOpen,curClose,null);
        end if;
        set up0 = up0 + 1;
        FETCH cur_record INTO curOpen,curClose;
    END WHILE;
    CLOSE cur_record;

    SELECT max(stock_name),min(low+0)
    INTO stockName,minPrice
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt >= startDt
        AND cur_open != '--';
    if (isnull(stockName)) then
        leave top;
    end if;
    SELECT min(dt)
    INTO minDt
        FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt >= startDt
        AND low = minPrice+0;
    set upWidth = f_calc_width(minPrice,curPrice,null);

    SELECT COUNT(*)
    INTO up0
    FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt >= startDt
        AND f_cast(change_width) <= -2
        AND cur_open != '--';
    SELECT COUNT(*)
    INTO up3
    FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt >= startDt
        AND f_cast(change_width) >= 0
        AND cur_open != '--';
    SELECT COUNT(*)
    INTO up5
    FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt >= startDt
        AND f_cast(change_width) >= 2
        AND cur_open != '--';
    SELECT COUNT(*)
    INTO up9
    FROM stk_hq_details
    WHERE valid = '1'
        AND stock_code = stockCode
        AND dt >= startDt
        AND f_cast(change_width) >= 5
        AND cur_open != '--';

    INSERT INTO tot_stock_select(
        stock_code,
        stock_name,
        start_dt,
        cur_price,
        min_price,
        up_width,
        up_width1,
        up_width2,
        up_width3,
        up0,
        up3,
        up5,
        up9,
        create_date)
    VALUES(
        stockCode,
        stockName,
        minDt,
        curPrice,
        minPrice,
        upWidth,
        upWidth1,
        upWidth2,
        upWidth3,
        up0,
        up3,
        up5,
        up9,
        current_date);

END
