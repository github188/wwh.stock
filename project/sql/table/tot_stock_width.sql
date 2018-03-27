-- ----------------------------
-- Table structure for tot_stock_width
-- ----------------------------
DROP TABLE IF EXISTS `tot_stock_width`;
CREATE TABLE `tot_stock_width` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `stock_code` varchar(6) NOT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `start_dt` varchar(10) DEFAULT NULL COMMENT '开始日期',
  `cur_price` varchar(10) DEFAULT NULL COMMENT '当前价格',
  `max_price` varchar(10) DEFAULT NULL COMMENT '最高价格',
  `max_width` varchar(10) DEFAULT NULL COMMENT '最高涨跌幅',
  `min_price` varchar(10) DEFAULT NULL COMMENT '最低价格',
  `min_width` varchar(10) DEFAULT NULL COMMENT '最低涨跌幅',
  `up_width` varchar(10) DEFAULT NULL COMMENT '当天涨跌幅',
  `up_width1` varchar(10) DEFAULT NULL COMMENT '上一天涨跌幅',
  `up_width2` varchar(10) DEFAULT NULL COMMENT '上二天涨跌幅',
  `up_width3` varchar(10) DEFAULT NULL COMMENT '上三天涨跌幅',
  `down_total` int(10) DEFAULT NULL COMMENT '下跌天数',
  `up_total` int(10) DEFAULT NULL COMMENT '上涨天数',
  `up3_total` int(10) DEFAULT NULL COMMENT '上涨天数3',
  `up9_total` int(10) DEFAULT NULL COMMENT '上涨天数9',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt_index` (`stock_code`,`start_dt`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深A股涨幅统计信息';
