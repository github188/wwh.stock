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
-- Table structure for hsa_stock_new
-- ----------------------------
DROP TABLE IF EXISTS `hsa_stock_new`;
CREATE TABLE `hsa_stock_new` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(6) NOT NULL COMMENT '代码',
  `name` varchar(30) DEFAULT NULL COMMENT '简称',
  `latest_price` varchar(15) DEFAULT NULL COMMENT '最新价',
  `average_price` varchar(15) DEFAULT NULL COMMENT '平均价',
  `ud_width` varchar(15) DEFAULT NULL COMMENT '涨跌幅',
  `ud_amount` varchar(15) DEFAULT NULL COMMENT '涨跌额',
  `volume` varchar(15) DEFAULT NULL COMMENT '成交量(万手)',
  `turn_volume` varchar(15) DEFAULT NULL COMMENT '成交额(亿元)',
  `turnover_rate` varchar(10) DEFAULT NULL COMMENT '换手率',
  `volume_ratio` varchar(15) DEFAULT NULL COMMENT '量比',
  `amplitude` varchar(10) DEFAULT NULL COMMENT '振幅',
  `committee` varchar(15) DEFAULT NULL COMMENT '委比',
  `pe_ratio` varchar(15) DEFAULT NULL COMMENT '市盈率',
  `five_width` varchar(10) DEFAULT NULL COMMENT '5分钟涨幅',
  `net_flag` char(1) DEFAULT '1' COMMENT '网站标志',
  `highest_price` varchar(15) DEFAULT NULL COMMENT '最高',
  `lowest_price` varchar(15) DEFAULT NULL COMMENT '最低',
  `today_price` varchar(15) DEFAULT NULL COMMENT '今开',
  `yesterday_price` varchar(15) DEFAULT NULL COMMENT '昨收',
  `max_price` varchar(15) DEFAULT NULL COMMENT '涨停',
  `min_price` varchar(15) DEFAULT NULL COMMENT '跌停',
  `outside_dish` varchar(10) DEFAULT NULL COMMENT '外盘',
  `inside_dish` varchar(10) DEFAULT NULL COMMENT '内盘',
  `inflow_fund` varchar(15) DEFAULT NULL COMMENT '主力净流入(万元)',
  `inflow_large` varchar(15) DEFAULT NULL COMMENT '超大单流入(万元)',
  `inflow_big` varchar(15) DEFAULT NULL COMMENT '大单流入(万元)',
  `top_flag` int(10) DEFAULT NULL COMMENT '龙头标志',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_ind` (`code`, `net_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深A股自选行情';
