DROP PROCEDURE p_self_basic;
CREATE PROCEDURE p_self_basic(startDate varchar(10))
BEGIN
    DECLARE stockCode VARCHAR(6);
    DECLARE acscNum INT;
    DECLARE no_more_record INT DEFAULT 0;

    DECLARE cur_record CURSOR FOR 
        SELECT distinct(stock_code)
        FROM stk_self_select
        WHERE valid = '1';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_record = 1;

    if isnull(startDate) then
        SELECT DATE_SUB( DATE_SUB( CURDATE( ) , INTERVAL DAYOFMONTH( CURDATE( ) ) -1 DAY ) , INTERVAL 2 MONTH )
        INTO startDate;
    end if;
    
    OPEN cur_record;
    FETCH cur_record INTO stockCode;
    WHILE no_more_record != 1 DO
        call p_statistic_basic(stockCode,startDate);
        FETCH cur_record INTO stockCode;
    END WHILE;
    CLOSE cur_record;

END