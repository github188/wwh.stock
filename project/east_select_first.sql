/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : securities-manager

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2017-03-30 21:04:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for east_select_first
-- ----------------------------
DROP TABLE IF EXISTS `east_select_first`;
CREATE TABLE `east_select_first` (
  `name` varchar(10) DEFAULT NULL COMMENT '指标名称',
  `focus` varchar(10) DEFAULT NULL COMMENT '选中指标',
  `items` tinyint(1) DEFAULT NULL COMMENT '指标条目',
  `list_cont` varchar(10) DEFAULT NULL COMMENT '指标列表内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选股器指标一层';

-- ----------------------------
-- Records of east_select_first
-- ----------------------------
INSERT INTO `east_select_first` VALUES ('常用指标', 'c', '1', '1');
INSERT INTO `east_select_first` VALUES ('股票范围', 'false', '1', '2');
INSERT INTO `east_select_first` VALUES ('财务指标', 'false', '0', '3');
INSERT INTO `east_select_first` VALUES ('估值指标', 'false', '1', '4');
INSERT INTO `east_select_first` VALUES ('行情指标', 'false', '1', '5');
INSERT INTO `east_select_first` VALUES ('股东股本', 'false', '0', '6');
INSERT INTO `east_select_first` VALUES ('公告选股', 'false', '1', '7');
