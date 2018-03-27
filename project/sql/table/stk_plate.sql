-- ----------------------------
-- Table structure for stk_plate
-- ----------------------------
DROP TABLE IF EXISTS `stk_plate`;
CREATE TABLE `stk_plate` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `dt` varchar(10) DEFAULT NULL COMMENT '日期',
  `plate_code` varchar(6) NOT NULL COMMENT '代码',
  `plate_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `change_width` varchar(10) DEFAULT NULL COMMENT '涨幅',
  `price` varchar(10) DEFAULT NULL COMMENT '现价',
  `change_amount` varchar(10) DEFAULT NULL COMMENT '涨跌',
  `change_rate` varchar(10) DEFAULT NULL COMMENT '涨速',
  `volume_ratio` varchar(10) DEFAULT NULL COMMENT '量比',
  `change_num` varchar(10) DEFAULT NULL COMMENT '涨跌数',
  `even_up` varchar(10) DEFAULT NULL COMMENT '连涨天',
  `three_change_width` varchar(10) DEFAULT NULL COMMENT '3日涨幅',
  `turn_over` varchar(10) DEFAULT NULL COMMENT '换手',
  `amount` varchar(10) DEFAULT NULL COMMENT '净流入',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `plate_index` (`plate_code`,`dt`)
) ENGINE=InnoDB AUTO_INCREMENT=2779 DEFAULT CHARSET=utf8 COMMENT='沪深A股板块行情';
