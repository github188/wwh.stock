DROP FUNCTION f_cast;
CREATE FUNCTION f_cast(price varchar(10))
RETURNS decimal(10,5)
BEGIN
	declare ret decimal(10,5);

    if isnull(price) then
        return 0;
    end if;
	set ret = cast(price as decimal(10,5));

	RETURN (ret);
END;