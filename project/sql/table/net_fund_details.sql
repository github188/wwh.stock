-- ----------------------------
-- Table structure for net_fund_details
-- ----------------------------
DROP TABLE IF EXISTS `net_fund_details`;
CREATE TABLE `net_fund_details` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `dt` varchar(10) DEFAULT NULL COMMENT '日期',
  `stock_code` varchar(6) NOT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `change_width` varchar(10) DEFAULT NULL COMMENT '涨幅',
  `price` varchar(10) DEFAULT NULL COMMENT '现价',
  `main_amount` varchar(10) DEFAULT NULL COMMENT '主力净流入',
  `main_duty` varchar(10) DEFAULT NULL COMMENT '主力净占比',
  `large_amount` varchar(10) DEFAULT NULL COMMENT '超大单净流入',
  `large_duty` varchar(10) DEFAULT NULL COMMENT '超大单净占比',
  `big_amount` varchar(10) DEFAULT NULL COMMENT '大单净流入',
  `big_duty` varchar(10) DEFAULT NULL COMMENT '大单净占比',
  `middle_amount` varchar(10) DEFAULT NULL COMMENT '中单净流入',
  `middle_duty` varchar(10) DEFAULT NULL COMMENT '中单净占比',
  `small_amount` varchar(10) DEFAULT NULL COMMENT '小单净流入',
  `small_duty` varchar(10) DEFAULT NULL COMMENT '小单净占比',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt_fund` (`dt`,`stock_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深A股资金流向';
