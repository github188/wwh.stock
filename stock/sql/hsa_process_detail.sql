CREATE PROCEDURE processorders()
            BEGIN
            -- �����ֲ�����
            DECLARE done BOOLEAN DEFAULT 0;
            DECLARE o INT;
 
            DECLAREordernumbers CURSOR
            FOR
            SELECT order_num FROM orders ;
            -- ��SQLSTATEΪ02000ʱ����doneֵΪ1
            DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done=1;
            --���α�
            Open ordernumbers ;
            -- ��ʼѭ��
            REPEAT
            -- �ѵ�ǰ�е�ֵ���������ľֲ�����o��
            FETCH ordernumbers INTO o;
            -- ��doneΪ��ʱֹͣѭ��
            UNTIL done END REPEAT;
            --�ر��α�
            Close ordernumbers ;  //CLOSE�ͷ��α�ʹ�õ������ڲ��ڴ����Դ����ˣ�ÿ���α겻��Ҫʱ��Ӧ�ùر�
            END;