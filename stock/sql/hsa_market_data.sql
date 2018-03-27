/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : stock

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2015-10-20 17:07:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hsa_market_data
-- ----------------------------
DROP TABLE IF EXISTS `hsa_market_data`;
CREATE TABLE `hsa_market_data` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(6) NOT NULL COMMENT '代码',
  `name` varchar(30) DEFAULT NULL COMMENT '简称',
  `latest_price` float(10,2) DEFAULT NULL COMMENT '最新价',
  `ud_width` float(10,2) DEFAULT NULL COMMENT '涨跌幅',
  `ud_amount` float(10,2) DEFAULT NULL COMMENT '涨跌额',
  `five_width` varchar(10) DEFAULT NULL COMMENT '5分钟涨幅',
  `volume` float(12,2) DEFAULT NULL COMMENT '成交量(手)',
  `turn_volume` float(10,2) DEFAULT NULL COMMENT '成交额(万元)',
  `turnover_rate` varchar(10) DEFAULT NULL COMMENT '换手率',
  `amplitude` varchar(10) DEFAULT NULL COMMENT '振幅',
  `volume_ratio` float(10,2) DEFAULT NULL COMMENT '量比',
  `committee` float(10,2) DEFAULT NULL COMMENT '委比',
  `pe_ratio` float(10,2) DEFAULT NULL COMMENT '市盈率',
  `net_flag` char(1) DEFAULT '1' COMMENT '网站标志',
  `yesterday_price` float(10,3) DEFAULT NULL COMMENT '昨收',
  `today_price` float(10,3) DEFAULT NULL COMMENT '今开',
  `highest_price` float(10,3) DEFAULT NULL COMMENT '最高',
  `lowest_price` float(10,3) DEFAULT NULL COMMENT '最低',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_ind` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2779 DEFAULT CHARSET=utf8 COMMENT='沪深A股行情';
