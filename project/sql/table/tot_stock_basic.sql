-- ----------------------------
-- Table structure for tot_stock_basic
-- ----------------------------
DROP TABLE IF EXISTS `tot_stock_basic`;
CREATE TABLE `tot_stock_basic` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `start_dt` varchar(10) DEFAULT NULL COMMENT '统计开始日期',
  `stock_code` varchar(6) NOT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `max_dt` varchar(10) DEFAULT NULL COMMENT '最高价日期',
  `max_High` varchar(10) DEFAULT NULL COMMENT '最高价',
  `min_dt` varchar(10) DEFAULT NULL COMMENT '最低价日期',
  `min_High` varchar(10) DEFAULT NULL COMMENT '最低价',
  `up_width` varchar(10) DEFAULT NULL COMMENT '涨跌幅',
  `up_cur_width` varchar(10) DEFAULT NULL COMMENT '当前涨跌幅',
  `max_price` varchar(10) DEFAULT NULL COMMENT '收盘最高价',
  `min_price` varchar(10) DEFAULT NULL COMMENT '收盘最低价',
  `price` varchar(10) DEFAULT NULL COMMENT '现价',
  `up_total` int(10) DEFAULT NULL COMMENT '上涨天数',
  `down_total` int(10) DEFAULT NULL COMMENT '下跌天数',

  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt_index` (`stock_code`,`start_dt`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深A股行情统计';
