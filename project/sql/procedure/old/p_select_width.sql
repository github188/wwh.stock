DROP PROCEDURE IF EXISTS `p_select_width`;
CREATE PROCEDURE `p_select_width`(IN `industryid` char,IN `udwidth` float,IN `num` int)
BEGIN
	INSERT INTO hsa_plate_stock (
		concept_id,
		CODE,
		net_flag,
		industry_type,
		CREATE_DATE,
		CREATOR,
		UPDATE_DATE,
		UPDATOR,
		VALID
	) SELECT
		RIGHT (
			REPLACE (LEFT(SYSDATE(), 10), '-', ''),
			6
		),
		CODE,
		net_flag,
		industryid,
		CREATE_DATE,
		CREATOR,
		UPDATE_DATE,
		UPDATOR,
		VALID
	FROM
		hsa_market_data
	WHERE
		net_flag = '2'
	AND valid = '1'
	AND ud_width <= udwidth
	ORDER BY
		REPLACE (five_width, '%', '') DESC
	LIMIT num;
END;
