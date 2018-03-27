/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : securities-manager

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-10-22 21:10:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for net_profit_report
-- ----------------------------
DROP TABLE IF EXISTS `net_profit_report`;
CREATE TABLE `net_profit_report` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `dt` varchar(10) DEFAULT NULL COMMENT '报告期',
  `stock_code` varchar(6) DEFAULT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '简称',
  `per_profit` varchar(10) DEFAULT NULL COMMENT '每股收益',
  `business_profit` varchar(15) DEFAULT NULL COMMENT '营业收入',
  `year_growth` varchar(10) DEFAULT NULL COMMENT '同比增长',
  `chain_growth` varchar(10) DEFAULT NULL COMMENT '季度环比增长',
  `net_profit` varchar(15) DEFAULT NULL COMMENT '净利润',
  `net_year_growth` varchar(24) DEFAULT NULL COMMENT '净利润同比增长',
  `net_chain_growth` varchar(10) DEFAULT NULL COMMENT '净利润季度环比增长',
  `net_assets` varchar(10) DEFAULT NULL COMMENT '每股净资产',
  `assets_yield` varchar(10) DEFAULT NULL COMMENT '净资产收益率',
  `cash_flow` varchar(10) DEFAULT NULL COMMENT '每股经营现金流量',
  `gross_profit` varchar(10) DEFAULT NULL COMMENT '销售毛利率',
  `report_date` varchar(10) DEFAULT NULL COMMENT '公告日期',
  `order_date` varchar(10) DEFAULT NULL COMMENT '预约日期',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='沪深A股业绩报表';
