DROP PROCEDURE if exists p_all_limit;
CREATE PROCEDURE p_all_limit()
BEGIN
    DECLARE stockCode VARCHAR(6);
    DECLARE acscNum INT;
    DECLARE no_more_info INT DEFAULT 0;

    DECLARE cur_info CURSOR FOR 
        SELECT code
        FROM stk_stocks_info
        WHERE valid = '1'
            AND stype = 'a'
            AND status < 2;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_info = 1;
    
    OPEN cur_info;
    FETCH cur_info INTO stockCode;
    WHILE no_more_info != 1 DO
        call p_per_limit(stockCode);
        FETCH cur_info INTO stockCode;
    END WHILE;
    CLOSE cur_info;

END