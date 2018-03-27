DROP FUNCTION f_calc_width;
CREATE FUNCTION f_calc_width(startPrice varchar(10), endPrice varchar(10), divPrice varchar(10))
RETURNS VARCHAR(10)
BEGIN
	DECLARE iPrice decimal(10,2);
	DECLARE ePrice decimal(10,2);
	DECLARE dPrice decimal(10,2);
	DECLARE width decimal(10,2);
	DECLARE ret VARCHAR(10) DEFAULT '';

    set dPrice = f_cast(divPrice);
	set iPrice = f_cast(startPrice);
	set ePrice = f_cast(endPrice);

    if (isnull(dPrice) || dPrice = 0) then
        set dPrice = iPrice;
    end if;
    set iPrice = ePrice - iPrice;

	set width = round(iPrice/dPrice,4)*100;
	set ret = cast(width as char(10));

	RETURN (ret);
END;