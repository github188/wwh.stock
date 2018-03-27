/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : stock

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2015-10-20 17:08:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for net_performance_data
-- ----------------------------
DROP TABLE IF EXISTS `net_performance_data`;
CREATE TABLE `net_performance_data` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `dt` varchar(10) DEFAULT NULL COMMENT '业绩日期',
  `stock_code` varchar(6) DEFAULT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '简称',
  `relative_info` varchar(100) DEFAULT NULL COMMENT '相关资料',
  `per_profit` varchar(10) DEFAULT NULL COMMENT '每股收益',
  `main_revenue` varchar(15) DEFAULT NULL COMMENT '营业收入',
  `main_growth` varchar(10) DEFAULT NULL COMMENT '营业收入同比增长',
  `main_quarter` varchar(10) DEFAULT NULL COMMENT '营业收入季度环比',
  `net_profit` varchar(15) DEFAULT NULL COMMENT '净利润',
  `net_growth` varchar(10) DEFAULT NULL COMMENT '净利润同比增长',
  `net_quarter` varchar(10) DEFAULT NULL COMMENT '净利润季度环比',
  `net_assets` varchar(10) DEFAULT NULL COMMENT '每股净资产',
  `assets_yield` varchar(10) DEFAULT NULL COMMENT '净资产收益率',
  `ope_cashflows` varchar(10) DEFAULT NULL COMMENT '每股经营现金流量',
  `sale_grossprofit` varchar(10) DEFAULT NULL COMMENT '销售毛利率',
  `report_dt` varchar(10) DEFAULT NULL COMMENT '公告日期',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='沪深A股业绩报表';
