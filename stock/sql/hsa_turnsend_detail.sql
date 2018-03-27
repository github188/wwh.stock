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
-- Table structure for hsa_turnsend_detail
-- ----------------------------
DROP TABLE IF EXISTS `hsa_turnsend_detail`;
CREATE TABLE `hsa_turnsend_detail` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(6) DEFAULT NULL COMMENT '代码',
  `name` varchar(30) DEFAULT NULL COMMENT '简称',
  `plan_date` varchar(10) DEFAULT NULL COMMENT '预案公布日',
  `send_scale` int(10) DEFAULT NULL COMMENT '送股比例(10送X)',
  `turn_scale` int(10) DEFAULT NULL COMMENT '转增比例(10转X)',
  `cash_scale` float(10,2) DEFAULT NULL COMMENT '派现比例(10派X)',
  `pass_date` varchar(10) DEFAULT NULL COMMENT '股东大会通过日',
  `register_date` varchar(10) DEFAULT NULL COMMENT '股权登记日',
  `dividend_date` varchar(10) DEFAULT NULL COMMENT '除权除息日',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='沪深A股分红送转';
