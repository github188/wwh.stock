/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : stock

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2015-10-20 17:07:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hsa_stock_basic
-- ----------------------------
DROP TABLE IF EXISTS `hsa_stock_basic`;
CREATE TABLE `hsa_stock_basic` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(6) DEFAULT NULL COMMENT '代码',
  `name` varchar(30) DEFAULT NULL COMMENT '简称',
  `circulation_equity` float(12,3) DEFAULT NULL COMMENT '流通股本',
  `total_equity` float(12,3) DEFAULT NULL COMMENT '总股本',
  `per_profit` float(10,3) DEFAULT NULL COMMENT '每股收益',
  `net_assets` float(10,3) DEFAULT NULL COMMENT '每股净资产',
  `ndistrib_profit` float(10,3) DEFAULT NULL COMMENT '每股未分配利润',
  `capital_fund` float(10,3) DEFAULT NULL COMMENT '每股资本公积金',
  `assets_yield` float(10,3) DEFAULT NULL COMMENT '净资产收益率',
  `main_revenue` float(15,3) DEFAULT NULL COMMENT '主营业务收入',
  `net_profit` float(12,3) DEFAULT NULL COMMENT '净利润',
  `profit_describe` varchar(100) DEFAULT NULL COMMENT '净利润描述',
  `main_business` varchar(100) DEFAULT NULL COMMENT '主营业务',
  `the_plate` varchar(100) DEFAULT NULL COMMENT '所属板块',
  `open_date` varchar(10) DEFAULT NULL COMMENT '上市日期',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2779 DEFAULT CHARSET=utf8 COMMENT='沪深A股基本信息';
