DROP PROCEDURE if exists p_backup_data;
CREATE PROCEDURE p_backup_data
BEGIN
    INSERT INTO stk_rx_his
    SELECT * from stk_rx_data
    WHERE valid = '1'
        AND dt < concat(date_format(CURDATE(),'%Y-'),'01-01');
    DELETE from stk_rx_data
    WHERE dt < concat(date_format(CURDATE(),'%Y-'),'01-01');
#concat(date_format(DATE_SUB(CURDATE(),INTERVAL 1 YEAR),'%Y-'),'01-01')
#concat(date_format(DATE_SUB(CURDATE(),INTERVAL 1 MONTH),'%Y-%m-'),'01')
    INSERT INTO stk_hq_his
    SELECT * from stk_hq_details
    WHERE valid = '1'
        AND dt < concat(date_format(CURDATE(),'%Y-'),'01-01');
    DELETE from stk_hq_details
    WHERE dt < concat(date_format(CURDATE(),'%Y-'),'01-01');
END

SELECT
  COUNT(*)
FROM stk_hq_details shd
WHERE shd.dt = DATE_FORMAT(CURDATE(), '%Y-%m-%d')
  AND ROUND(shd.pre_close*1.1,2) = shd.price
  AND CAST(shd.change_width AS decimal(10, 2)) > 3