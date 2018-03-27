DROP FUNCTION f_calc_continue;
CREATE FUNCTION f_calc_continue(stockCode varchar(10), maxDate varchar(10))
RETURNS INT
BEGIN
    DECLARE num INT DEFAULT 0;
    DECLARE flag INT DEFAULT 0;
    DECLARE dChangeAmount decimal(10,2);
    DECLARE no_more_record INT DEFAULT 0;

    DECLARE cur_continue CURSOR FOR 
        SELECT f_cast(change_amount)
        FROM stk_hq_details
        WHERE valid = '1'
        AND stock_code = stockCode
        AND dt <= maxDate
        ORDER BY dt DESC;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_record = 1;
    
    OPEN cur_continue;
    FETCH cur_continue INTO dChangeAmount;
    WHILE (no_more_record != 1 && flag = 0) DO
        if (dChangeAmount > 0 && num >= 0) then
            set num = num + 1;
        elseif (dChangeAmount < 0 && num <= 0) then
            set num = num - 1;
        else
            set flag = 1;
        end if;
        FETCH cur_continue INTO dChangeAmount;
    END WHILE;
    CLOSE cur_continue;

	RETURN (num);
END;