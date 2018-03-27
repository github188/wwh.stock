DROP PROCEDURE p_all_summary;
CREATE PROCEDURE p_all_summary(startDate varchar(10))
BEGIN
    DECLARE stockCode VARCHAR(6);
    DECLARE acscNum INT;
    DECLARE no_more_record INT DEFAULT 0;

    DECLARE cur_record CURSOR FOR 
        SELECT distinct(stock_code)
        FROM stk_hq_details
        WHERE valid = '1';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_record = 1;
    
    OPEN cur_record;
    FETCH cur_record INTO stockCode;
    WHILE no_more_record != 1 DO
        call p_select_summary(stockCode,startDate);
        FETCH cur_record INTO stockCode;
    END WHILE;
    CLOSE cur_record;

END