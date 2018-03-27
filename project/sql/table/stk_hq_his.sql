-- ----------------------------
-- Table structure for stk_hq_his
-- ----------------------------
DROP TABLE IF EXISTS `stk_hq_his`;
CREATE TABLE `stk_hq_his` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `dt` varchar(10) DEFAULT NULL COMMENT '日期',
  `stock_code` varchar(6) NOT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '名称',
  `change_width` varchar(10) DEFAULT NULL COMMENT '涨幅',
  `price` varchar(10) DEFAULT NULL COMMENT '现价',
  `change_amount` varchar(10) DEFAULT NULL COMMENT '涨跌',
  `buy_price` varchar(10) DEFAULT NULL COMMENT '买价',
  `sale_price` varchar(10) DEFAULT NULL COMMENT '卖价',
  `total_volume` varchar(10) DEFAULT NULL COMMENT '总量',
  `volume` varchar(10) DEFAULT NULL COMMENT '现量',
  `change_rate` varchar(10) DEFAULT NULL COMMENT '涨速',
  `turn_over` varchar(10) DEFAULT NULL COMMENT '换手',
  `cur_open` varchar(10) DEFAULT NULL COMMENT '今开',
  `high` varchar(10) DEFAULT NULL COMMENT '最高',
  `low` varchar(10) DEFAULT NULL COMMENT '最低',
  `pre_close` varchar(10) DEFAULT NULL COMMENT '昨收',
  `pe` varchar(10) DEFAULT NULL COMMENT '市盈(动)',
  `amount` varchar(12) DEFAULT NULL COMMENT '总金额',
  `volume_ratio` varchar(10) DEFAULT NULL COMMENT '量比',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt_index` (`stock_code`,`dt`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深A股行情历史';
