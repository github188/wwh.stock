-- ----------------------------
-- Table structure for stk_fund_details
-- ----------------------------
DROP TABLE IF EXISTS `stk_fund_details`;
CREATE TABLE `stk_fund_details` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `dt` varchar(10) DEFAULT NULL COMMENT '日期',
  `stock_code` varchar(6) NOT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `change_width` varchar(10) DEFAULT NULL COMMENT '涨幅',
  `price` varchar(10) DEFAULT NULL COMMENT '现价',
  `turn_over` varchar(10) DEFAULT NULL COMMENT '换手',
  `net_rate` varchar(10) DEFAULT NULL COMMENT '净买率',
  `net_amount` varchar(10) DEFAULT NULL COMMENT '净流入',
  `relative_flow` varchar(10) DEFAULT NULL COMMENT '相对流量',
  `large_amount` varchar(10) DEFAULT NULL COMMENT '大宗流入',
  `large_flow` varchar(10) DEFAULT NULL COMMENT '大宗流量',
  `change_width5` varchar(10) DEFAULT NULL COMMENT '5分钟涨幅',
  `turn_over5` varchar(10) DEFAULT NULL COMMENT '5分钟换手',
  `net_amount5` varchar(10) DEFAULT NULL COMMENT '5分钟净流入',
  `relative_flow5` varchar(10) DEFAULT NULL COMMENT '5分钟相对流量',
  `large_amount5` varchar(10) DEFAULT NULL COMMENT '5分钟大宗流入',
  `large_flow5` varchar(10) DEFAULT NULL COMMENT '5分钟大宗流量',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt_index` (`stock_code`,`dt`)
) ENGINE=InnoDB AUTO_INCREMENT=2779 DEFAULT CHARSET=utf8 COMMENT='沪深A股资金流向';
