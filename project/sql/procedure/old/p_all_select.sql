DROP PROCEDURE p_all_select;
CREATE PROCEDURE p_all_select()
BEGIN
    DECLARE stockCode VARCHAR(6);
    DECLARE no_more_info INT DEFAULT 0;

    DECLARE cur_info CURSOR FOR 
        SELECT code
        FROM stk_stocks_info
        WHERE valid = '1'
            AND stype = 'a'
            AND status < 2;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_info = 1;
    truncate table tot_stock_select;

    OPEN cur_info;
    FETCH cur_info INTO stockCode;
    WHILE no_more_info != 1 DO
        call p_stock_select(stockCode);
        FETCH cur_info INTO stockCode;
    END WHILE;
    CLOSE cur_info;

    insert into stk_self_select(
        stock_code,
        stock_name,
        dt,
        order_by)
    select stock_code,stock_name,date_format(now(),'%Y-%m-%d'),1
    from tot_stock_select WHERE up_width3 BETWEEN 0 AND 1 AND up_width2 BETWEEN 1 AND 2 AND up_width2>up_width3
    AND up_width1 BETWEEN -2 AND 0 AND up_width1+up_width2+up_width3>0.5 AND up_width<10 ORDER BY up_width+0 DESC;
    insert into stk_self_select(
        stock_code,
        stock_name,
        dt,
        order_by)
    select stock_code,stock_name,date_format(now(),'%Y-%m-%d'),2
    from tot_stock_select WHERE up_width2 BETWEEN 0 AND 1 AND up_width1 BETWEEN 1 AND 2 AND up_width1>up_width2
    AND up_width3 <0 AND up_width<10 ORDER BY up_width+0 desc;

END