/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : stock

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2015-10-20 17:06:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hsa_history_data
-- ----------------------------
DROP TABLE IF EXISTS `hsa_history_data`;
CREATE TABLE `hsa_history_data` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(6) DEFAULT NULL COMMENT '代码',
  `dt` varchar(10) DEFAULT NULL COMMENT '日期',
  `closing_price` float(10,3) DEFAULT NULL COMMENT '收盘价(元)',
  `ud_width` varchar(10) DEFAULT NULL COMMENT '涨跌幅(%)',
  `turnover_rate` varchar(10) DEFAULT NULL COMMENT '换手率(%)',
  `volume_ratio` float(10,3) DEFAULT NULL COMMENT '量比',
  `highest_price` float(10,3) DEFAULT NULL COMMENT '最高价',
  `lowest_price` float(10,3) DEFAULT NULL COMMENT '最低价',
  `amplitude` varchar(10) DEFAULT NULL COMMENT '振幅',
  `volume` float(10,3) DEFAULT NULL COMMENT '成交量(万股)',
  `turn_volume` float(10,3) DEFAULT NULL COMMENT '成交额(亿)',
  `inflow_fund` float(10,3) DEFAULT NULL COMMENT '流入资金(亿)',
  `trade_balance` float(10,3) DEFAULT NULL COMMENT '买卖差额(万元)',
  `fund_diff` float(10,3) DEFAULT NULL COMMENT '资金差(亿)',
  `net_inflow_rate` varchar(10) DEFAULT NULL COMMENT '净流入率(%)',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dt_index` (`code`,`dt`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1366468 DEFAULT CHARSET=utf8 COMMENT='沪深A股历史行情';
