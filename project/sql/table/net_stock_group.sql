-- ----------------------------
-- Table structure for net_stock_group
-- ----------------------------
DROP TABLE IF EXISTS `net_stock_group`;
CREATE TABLE `net_stock_group` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `group_code` varchar(10) NOT NULL COMMENT '组合编码',
  `group_name` varchar(10) NOT NULL COMMENT '组合名称',
  `group_flag` char(1) DEFAULT '1' COMMENT '组合标志',
  --`stock_num` varchar(5) DEFAULT '0' COMMENT '股票数量',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_index` (`group_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深证券股票组合';
