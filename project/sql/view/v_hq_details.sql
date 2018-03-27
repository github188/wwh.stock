CREATE or replace VIEW `v_hq_details` AS 
SELECT stock_code,stock_name,DATE_FORMAT(dt,'%Y-%m-%d') dt,cur_open,high,low,cur_close price,valid
    FROM stk_rx_data
WHERE valid = '1'
    AND cur_open != '--'
    AND stock_code = '002102'
UNION
SELECT stock_code,stock_name,dt,cur_open,high,low,price,valid
    FROM stk_hq_details
WHERE valid = '1'
    AND cur_open != '--'
    AND stock_code = '002102'
    ORDER BY stock_code,dt ;
