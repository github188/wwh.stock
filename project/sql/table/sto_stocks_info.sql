-- ----------------------------
-- Table structure for sto_stocks_info
-- ----------------------------
DROP TABLE IF EXISTS `sto_stocks_info`;
CREATE TABLE `sto_stocks_info` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '自动增长、ID、主键',
  `name` varchar(15) NOT NULL COMMENT '股票名称  股票中文名称',
  `code` varchar(6) NOT NULL COMMENT '股票代码  股票数字代码',
  `abbreviation` varchar(4) NOT NULL COMMENT '股票简称  股票简易代码',
  `place` varchar(2) NOT NULL COMMENT '交易场所  上交所：sh 深交所：sz',
  `stype` char(1) NOT NULL DEFAULT '' COMMENT '股票类型  指数:z A股:a B股:b',
  `maxcategory` varchar(128) NOT NULL DEFAULT '' COMMENT '行业大类',
  `mincategory` varchar(128) NOT NULL DEFAULT '' COMMENT '行业小类',
  `sfccategory` varchar(128) NOT NULL DEFAULT '' COMMENT '证监会行业分类',
  `area` varchar(30) NOT NULL DEFAULT '' COMMENT '所在地区  省级地区',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态  交易中:0 停牌:1 下市:2',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON INSERT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `idx_stostocks_name` (`name`),
  KEY `idx_stostocks_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='股票信息表';
