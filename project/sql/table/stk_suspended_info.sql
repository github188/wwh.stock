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
-- Table structure for stk_suspended_info
-- ----------------------------
DROP TABLE IF EXISTS `stk_suspended_info`;
CREATE TABLE `stk_suspended_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `stock_code` varchar(6) DEFAULT NULL COMMENT '代码',
  `stock_name` varchar(30) DEFAULT NULL COMMENT '简称',
  `relative_info` varchar(100) DEFAULT NULL COMMENT '相关资料',
  `start_date` varchar(20) DEFAULT NULL COMMENT '停牌时间',
  `end_date` varchar(20) DEFAULT NULL COMMENT '停牌截止时间',
  `resume_date` varchar(20) DEFAULT NULL COMMENT '预计复牌时间',
  `suspension_period` varchar(10) DEFAULT NULL COMMENT '停牌期限',
  `suspension_reason` varchar(10) DEFAULT NULL COMMENT '停牌原因',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='沪深A股停复牌提示';
