-- ----------------------------
-- Table structure for net_profit_publish
-- ----------------------------
DROP TABLE IF EXISTS `net_profit_publish`;
CREATE TABLE `net_profit_publish` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `dt` varchar(10) DEFAULT NULL COMMENT '报告期',
  `stock_code` varchar(6) DEFAULT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '简称',
  `order_date` varchar(10) DEFAULT NULL COMMENT '预约日期',
  `once_change_date` varchar(10) DEFAULT NULL COMMENT '一次变更日期',
  `second_change_date` varchar(10) DEFAULT NULL COMMENT '二次变更日期',
  `third_change_date` varchar(10) DEFAULT NULL COMMENT '三次变更日期',
  `report_date` varchar(10) DEFAULT NULL COMMENT '公告日期',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深A股业绩披露时间';
