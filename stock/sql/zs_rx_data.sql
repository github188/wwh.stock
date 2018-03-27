-- ----------------------------
-- Table structure for zs_rx_data
-- ----------------------------
DROP TABLE IF EXISTS `zs_rx_data`;
CREATE TABLE `zs_rx_data` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `stock_code` varchar(6) NOT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `dt` varchar(10) DEFAULT NULL COMMENT '日期',
  `cur_open` varchar(10) DEFAULT NULL COMMENT '开盘',
  `high` varchar(10) DEFAULT NULL COMMENT '最高',
  `low` varchar(10) DEFAULT NULL COMMENT '最低',
  `cur_close` varchar(10) DEFAULT NULL COMMENT '收盘',
  `volume` varchar(10) DEFAULT NULL COMMENT '成交量',
  `ma1` varchar(10) DEFAULT NULL COMMENT '5日线',
  `ma2` varchar(10) DEFAULT NULL COMMENT '10日线',
  `ma3` varchar(10) DEFAULT NULL COMMENT '20日线',
  `ma4` varchar(10) DEFAULT NULL COMMENT '60日线',
  `mavol1` varchar(10) DEFAULT NULL COMMENT '5日量',
  `mavol2` varchar(10) DEFAULT NULL COMMENT '10日量',
  `k` varchar(10) DEFAULT NULL COMMENT 'KDJ.K',
  `d` varchar(10) DEFAULT NULL COMMENT 'KDJ.D',
  `j` varchar(10) DEFAULT NULL COMMENT 'KDJ.J',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt_index` (`stock_code`,`dt`)
) ENGINE=InnoDB AUTO_INCREMENT=2779 DEFAULT CHARSET=utf8 COMMENT='沪深A股日线';
