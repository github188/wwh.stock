/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : stock

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2015-10-20 17:07:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hsa_plate_stock
-- ----------------------------
DROP TABLE IF EXISTS `hsa_plate_stock`;
CREATE TABLE `hsa_plate_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `concept_id` varchar(10) DEFAULT NULL COMMENT '板块代号',
  `code` varchar(10) DEFAULT NULL COMMENT '股票代码',
  `net_flag` char(1) DEFAULT '1' COMMENT '网站标志',
  `industry_type` char(1) DEFAULT '1' COMMENT '板块类别',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  `order_by` int(11) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27256 DEFAULT CHARSET=utf8 COMMENT='板块代码关联';
