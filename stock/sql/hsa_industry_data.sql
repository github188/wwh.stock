/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : stock

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2015-10-20 17:07:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hsa_industry_data
-- ----------------------------
DROP TABLE IF EXISTS `hsa_industry_data`;
CREATE TABLE `hsa_industry_data` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `industry_id` varchar(6) DEFAULT NULL COMMENT '板块编码',
  `industry_name` varchar(100) DEFAULT NULL COMMENT '板块名称',
  `stock_num` int(10) DEFAULT NULL COMMENT '股票数量',
  `ud_width` varchar(10) DEFAULT NULL COMMENT '加权涨跌幅',
  `up_num` int(10) DEFAULT NULL COMMENT '上涨家数',
  `up_width` varchar(10) DEFAULT NULL COMMENT '涨幅',
  `down_num` int(10) DEFAULT NULL COMMENT '下跌家数',
  `down_width` varchar(10) DEFAULT NULL COMMENT '跌幅',
  `volume` float(15,3) DEFAULT NULL COMMENT '成交量(手)',
  `turn_volume` float(15,3) DEFAULT NULL COMMENT '成交额(万元)',
  `circu_value` float(15,3) DEFAULT NULL COMMENT '总流通市值',
  `net_address` varchar(100) DEFAULT NULL COMMENT '网址',
  `net_flag` char(1) DEFAULT '1' COMMENT '网站标志',
  `industry_type` char(1) DEFAULT '1' COMMENT '板块类别',
  `fund_address` varchar(100) DEFAULT NULL COMMENT '相关资讯',
  `turnover_rate` varchar(10) DEFAULT NULL COMMENT '换手率(%)',
  `stock_name` varchar(20) DEFAULT NULL COMMENT '领涨股票',
  `stock_address` varchar(100) DEFAULT NULL COMMENT '股票网址',
  `stock_width` varchar(10) DEFAULT NULL COMMENT '涨跌幅',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=416 DEFAULT CHARSET=utf8 COMMENT='沪深A股板块行情';
