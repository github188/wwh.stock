/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : stock

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2015-10-20 17:07:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for stk_index_data
-- ----------------------------
DROP TABLE IF EXISTS `stk_index_data`;
CREATE TABLE `stk_index_data` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL COMMENT '代码',
  `name` varchar(30) DEFAULT NULL COMMENT '简称',
  `dt` varchar(10) DEFAULT NULL COMMENT '日期',
  `cur_open` varchar(10) DEFAULT NULL COMMENT '今开',
  `pre_close` varchar(10) DEFAULT NULL COMMENT '昨收',
  `price` varchar(10) DEFAULT NULL COMMENT '当前点数',
  `change_amount` varchar(10) DEFAULT NULL COMMENT '涨跌',
  `change_width` varchar(10) DEFAULT NULL COMMENT '涨幅',
  `high` varchar(10) DEFAULT NULL COMMENT '最高',
  `low` varchar(10) DEFAULT NULL COMMENT '最低',
  `volume` varchar(10) DEFAULT NULL COMMENT '成交量(手)',
  `amount` varchar(10) DEFAULT NULL COMMENT '成交额(万元)',
  `rise_cnt` varchar(20) DEFAULT NULL COMMENT '上涨家数',
  `flat_cnt` varchar(20) DEFAULT NULL COMMENT '平盘家数',
  `fall_cnt` varchar(20) DEFAULT NULL COMMENT '下跌家数',
  `stop_cnt` varchar(20) DEFAULT NULL COMMENT '停牌家数',
  `change_rate` varchar(10) DEFAULT NULL COMMENT '涨速',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt_index` (`code`,`dt`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深A股指数';
