DROP FUNCTION f_calc_dividend;
CREATE FUNCTION f_calc_dividend(price varchar(10), sendScale varchar(10), turnScale varchar(10), cashScale varchar(10))
RETURNS VARCHAR(10)
BEGIN
	DECLARE iPrice decimal(10,2);
	DECLARE iSendScale decimal(10,5);
	DECLARE iTurnScale decimal(10,5);
	DECLARE iCashScale decimal(10,5);
	DECLARE calcPrice decimal(10,2);
	DECLARE ret VARCHAR(10) DEFAULT '';

    set iPrice = f_cast(price);
	set iSendScale = f_cast(sendScale);
	set iTurnScale = f_cast(turnScale);
	set iCashScale = f_cast(cashScale);

    set calcPrice = round((iPrice - iCashScale/10)*10/(10 + iSendScale + iTurnScale),2);

	set ret = cast(calcPrice as char(10));

	RETURN (ret);
END;