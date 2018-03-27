-- ----------------------------
-- Table structure for tot_stock_period
-- ----------------------------
DROP TABLE IF EXISTS `tot_stock_period`;
CREATE TABLE `tot_stock_period` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `stock_code` varchar(10) NOT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `start_dt` varchar(10) DEFAULT NULL COMMENT '开始日期',
  `end_dt` varchar(10) DEFAULT NULL COMMENT '结束日期',
  `start_price` varchar(10) DEFAULT NULL COMMENT '开始价格',
  `end_price` varchar(10) DEFAULT NULL COMMENT '结束价格',
  `up_width` varchar(10) DEFAULT NULL COMMENT '涨跌幅',
  `start_close` varchar(10) DEFAULT NULL COMMENT '开始收盘价',
  `end_close` varchar(10) DEFAULT NULL COMMENT '结束收盘价',
  `close_width` varchar(10) DEFAULT NULL COMMENT '收盘价涨跌幅',
  `sum_total` int(10) DEFAULT NULL COMMENT '总交易天数',
  `up_total` int(10) DEFAULT NULL COMMENT '上涨天数',
  `down_total` int(10) DEFAULT NULL COMMENT '下跌天数',
  `balance_total` int(10) DEFAULT NULL COMMENT '平盘天数',
  `up_flag` int(1) DEFAULT 0 COMMENT '涨跌标志',

  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt_index` (`stock_code`,`start_dt`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深A股阶段行情明细';
