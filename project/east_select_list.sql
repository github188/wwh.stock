/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : securities-manager

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2017-03-30 21:03:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for east_select_list
-- ----------------------------
DROP TABLE IF EXISTS `east_select_list`;
CREATE TABLE `east_select_list` (
  `list_cont` varchar(10) DEFAULT NULL COMMENT '上层指标',
  `pre_tit` varchar(50) DEFAULT NULL COMMENT '指标标题',
  `name` varchar(50) DEFAULT NULL COMMENT '指标名称',
  `colsable` tinyint(1) DEFAULT NULL COMMENT '指标折叠标识',
  `ul` varchar(10) DEFAULT NULL COMMENT '指标列表内容',
  `selectorcache` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选股器指标二层';

-- ----------------------------
-- Records of east_select_list
-- ----------------------------
INSERT INTO `east_select_list` VALUES ('1', '', '', null, '1', 'true');
INSERT INTO `east_select_list` VALUES ('2', '', '', '0', '21', '');
INSERT INTO `east_select_list` VALUES ('2', '', '', '0', '22', '');
INSERT INTO `east_select_list` VALUES ('3', 'ylnl', '盈利能力', null, '32', 'true');
INSERT INTO `east_select_list` VALUES ('3', 'cznl', '成长能力', null, '33', 'true');
INSERT INTO `east_select_list` VALUES ('3', 'zbjgcz', '资本结构与偿债', null, '34', 'true');
INSERT INTO `east_select_list` VALUES ('3', 'gz', '每股指标', null, '31', 'true');
INSERT INTO `east_select_list` VALUES ('4', '', '', null, '4', 'true');
INSERT INTO `east_select_list` VALUES ('5', '', '', null, '5', 'true');
INSERT INTO `east_select_list` VALUES ('6', 'gbzb', '股本指标', null, '61', 'true');
INSERT INTO `east_select_list` VALUES ('6', 'gdhs', '股东户数', null, '62', 'true');
INSERT INTO `east_select_list` VALUES ('6', 'jgcg', '机构持股家数', '0', '63', 'true');
INSERT INTO `east_select_list` VALUES ('7', '', '', '0', '7', 'true');
