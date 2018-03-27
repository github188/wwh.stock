-- ----------------------------
-- Table structure for tot_stock_limit
-- ----------------------------
DROP TABLE IF EXISTS `tot_stock_limit`;
CREATE TABLE `tot_stock_limit` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `start_dt` varchar(10) DEFAULT NULL COMMENT '统计开始日期',
  `stock_code` varchar(6) NOT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `max_dt` varchar(10) DEFAULT NULL COMMENT '最高价日期',
  `max_price` varchar(10) DEFAULT NULL COMMENT '最高价',
  `min_dt` varchar(10) DEFAULT NULL COMMENT '最低价日期',
  `min_price` varchar(10) DEFAULT NULL COMMENT '最低价',
  `up_width` varchar(10) DEFAULT NULL COMMENT '涨跌幅',
  `price` varchar(10) DEFAULT NULL COMMENT '现价',
  `total_amount` varchar(10) DEFAULT NULL COMMENT '总市值',
  `max_close` varchar(10) DEFAULT NULL COMMENT '最高收盘价',
  `min_close` varchar(10) DEFAULT NULL COMMENT '最低收盘价',
  `close_width` varchar(10) DEFAULT NULL COMMENT '收盘价涨跌幅',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt_index` (`stock_code`,`start_dt`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深A股行情涨跌统计';
