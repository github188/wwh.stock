CREATE PROCEDURE processorders()
            BEGIN
            -- 声明局部变量
            DECLARE done BOOLEAN DEFAULT 0;
            DECLARE o INT;
 
            DECLAREordernumbers CURSOR
            FOR
            SELECT order_num FROM orders ;
            -- 当SQLSTATE为02000时设置done值为1
            DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done=1;
            --打开游标
            Open ordernumbers ;
            -- 开始循环
            REPEAT
            -- 把当前行的值赋给声明的局部变量o中
            FETCH ordernumbers INTO o;
            -- 当done为真时停止循环
            UNTIL done END REPEAT;
            --关闭游标
            Close ordernumbers ;  //CLOSE释放游标使用的所有内部内存和资源，因此，每个游标不需要时都应该关闭
            END;