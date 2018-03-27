/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : stock

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2015-10-20 17:07:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hsa_history_detail
-- ----------------------------
DROP TABLE IF EXISTS `hsa_history_detail`;
CREATE TABLE `hsa_history_detail` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(6) DEFAULT NULL COMMENT '代码',
  `name` varchar(30) DEFAULT NULL COMMENT '简称',
  `current_price` float(10,3) DEFAULT NULL COMMENT '当前价',
  `forecast_price` float(10,3) DEFAULT NULL COMMENT '预测价',
  `initial_price` float(10,3) DEFAULT NULL COMMENT '起算价',
  `pressure_price` float(10,3) DEFAULT NULL COMMENT '压力位',
  `support_price` float(10,3) DEFAULT NULL COMMENT '支撑位',
  `highest_price` float(10,3) DEFAULT NULL COMMENT '最高价',
  `lowest_price` float(10,3) DEFAULT NULL COMMENT '最低价',
  `five_line` float(10,3) DEFAULT NULL COMMENT '5日线',
  `ten_line` float(10,3) DEFAULT NULL COMMENT '10日线',
  `thirty_line` float(10,3) DEFAULT NULL COMMENT '30日线',
  `dynamic_line` float(10,3) DEFAULT NULL COMMENT '动态线',
  `three_width` float(10,3) DEFAULT NULL COMMENT '3日涨幅',
  `five_width` float(10,3) DEFAULT NULL COMMENT '5日涨幅',
  `ten_width` float(10,3) DEFAULT NULL COMMENT '10日涨幅',
  `twenty_width` float(10,3) DEFAULT NULL COMMENT '20日涨幅',
  `dynamic_width` float(10,3) DEFAULT NULL COMMENT '动态涨幅',
  `exdividend_date` varchar(10) DEFAULT NULL COMMENT '除权除息日',
  `resume_date` varchar(10) DEFAULT NULL COMMENT '复牌日',
  `suspend_date` varchar(10) DEFAULT NULL COMMENT '停牌日',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15098 DEFAULT CHARSET=utf8 COMMENT='沪深A股行情明细';
