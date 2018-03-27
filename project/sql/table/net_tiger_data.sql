-- ----------------------------
-- Table structure for net_tiger_data
-- ----------------------------
DROP TABLE IF EXISTS `net_tiger_data`;
CREATE TABLE `net_tiger_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stock_code` varchar(6) NOT NULL COMMENT '股票代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `price` varchar(10) DEFAULT NULL COMMENT '现价',
  `change_rate` varchar(10) DEFAULT NULL COMMENT '涨跌幅',
  `turn_rate` varchar(10) DEFAULT NULL COMMENT '换手率',
  `current_value` varchar(12) DEFAULT NULL COMMENT '流通值',
  `up_count_1M` varchar(4) DEFAULT NULL COMMENT '近1月上榜',
  `up_count_3M` varchar(4) DEFAULT NULL COMMENT '近3月上榜',
  `up_count_6M` varchar(4) DEFAULT NULL COMMENT '近6月上榜',
  `change_width_1M` varchar(10) DEFAULT NULL COMMENT '近一个月涨幅',
  `change_width_3M` varchar(10) DEFAULT NULL COMMENT '近三个月涨幅',
  `change_width_6M` varchar(10) DEFAULT NULL COMMENT '近六个月涨幅',
  `change_width_1Y` varchar(10) DEFAULT NULL COMMENT '近一年涨幅',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `order_by` int(11) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tiger_index` (`stock_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深股票龙虎榜数据';
