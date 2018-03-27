-- ----------------------------
-- Table structure for net_plate
-- ----------------------------
DROP TABLE IF EXISTS `net_plate`;
CREATE TABLE `net_plate` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `dt` varchar(10) DEFAULT NULL COMMENT '日期',
  `plate_type` varchar(6) NOT NULL COMMENT '板块类型',
  `plate_code` varchar(6) NOT NULL COMMENT '板块代码',
  `plate_name` varchar(30) DEFAULT NULL COMMENT '板块名称',
  `change_width` varchar(10) DEFAULT NULL COMMENT '涨跌幅',
  `amount` varchar(10) DEFAULT NULL COMMENT '总市值',
  `turn_over` varchar(10) DEFAULT NULL COMMENT '换手率',
  `up_num` varchar(10) DEFAULT NULL COMMENT '上涨家数',
  `down_num` varchar(10) DEFAULT NULL COMMENT '下跌家数',
  `stock_code` varchar(6) NOT NULL COMMENT '领涨股票代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '领涨股票名称',
  `stock_change_width` varchar(10) DEFAULT NULL COMMENT '涨跌幅',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `plate_index` (`dt`,`plate_type`,`plate_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深证券板块行情';
