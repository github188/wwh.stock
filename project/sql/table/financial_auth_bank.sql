/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : bdtg-manager

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-09-18 16:46:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for financial_auth_bank
-- ----------------------------
DROP TABLE IF EXISTS `financial_auth_bank`;
CREATE TABLE `financial_auth_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '银行认证编号',
  `uid` int(11) DEFAULT NULL COMMENT '用户编号',
  `username` varchar(35) DEFAULT NULL COMMENT '用户名',
  `bank_account` varchar(50) DEFAULT NULL COMMENT '线下支付账号',
  `bank_name` varchar(50) DEFAULT NULL COMMENT '银行名称',
  `bank_id` int(11) DEFAULT NULL COMMENT '银行编号',
  `deposit_area` varchar(100) DEFAULT NULL COMMENT '开户行所在地',
  `deposit_name` varchar(100) DEFAULT NULL COMMENT '开户行名称',
  `pay_to_user_cash` decimal(10,2) DEFAULT '0.00' COMMENT '打给用户的金额',
  `user_get_cash` decimal(10,2) DEFAULT '0.00' COMMENT '收款金额',
  `pay_time` datetime DEFAULT NULL COMMENT '打款时间',
  `cash` decimal(10,2) DEFAULT NULL COMMENT '支付费用',
  `start_time` datetime DEFAULT NULL COMMENT '认证开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '认证结束时间',
  `auth_status` int(11) DEFAULT NULL COMMENT '认证状态',
  `bank_sname` varchar(250) DEFAULT NULL COMMENT '开户行名称',
  `create_date` datetime DEFAULT NULL,
  `creator` int(20) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `updator` int(20) DEFAULT NULL,
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `index_2` (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
