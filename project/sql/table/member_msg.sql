/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : bdtg-manager

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-09-17 13:45:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for member_msg
-- ----------------------------
DROP TABLE IF EXISTS `member_msg`;
CREATE TABLE `member_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '短信编号',
  `pid` int(11) DEFAULT '0' COMMENT '短信父编号',
  `uid` int(11) DEFAULT '0' COMMENT '用户编号',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `to_uid` int(11) DEFAULT NULL COMMENT '收件人编号',
  `to_username` varchar(50) DEFAULT NULL COMMENT '收件人名',
  `msg_status` tinyint(4) DEFAULT '0' COMMENT '删除状态(1=>发送方删，2=>接收方删，3=>在状态为2时发放删除)',
  `view_status` tinyint(4) DEFAULT '0' COMMENT '查看状态',
  `title` varchar(100) DEFAULT NULL COMMENT '短信标题',
  `content` text COMMENT '短信内容',
  `on_time` int(11) DEFAULT '0' COMMENT '发布时间',
  `tid` int(20) DEFAULT NULL COMMENT '项目ID',
  `job_type` int(1) DEFAULT '0' COMMENT '(0:默认值；1:业主消息；2:团队消息；3:财务消息;4:已发消息)',
  `type` int(11) DEFAULT NULL COMMENT '消息类型（1系统通知2交易动态 3在线作业）',
  `valid` int(1) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `updator` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `msg_pid` (`pid`),
  KEY `on_time` (`on_time`),
  KEY `recive_uid` (`to_uid`),
  KEY `uid` (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=407 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member_msg
-- ----------------------------
INSERT INTO `member_msg` VALUES ('396', null, '1', 'admin', '7', 'jd6061@aliyun.com', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:26:13', null, null, null);
INSERT INTO `member_msg` VALUES ('397', null, '1', 'admin', '7', 'jd6061@aliyun.com', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:26:50', null, null, null);
INSERT INTO `member_msg` VALUES ('398', null, '1', 'admin', '6', 'jd1111', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:26:50', null, null, null);
INSERT INTO `member_msg` VALUES ('399', null, '1', 'admin', '1', 'admin', '0', '2', '', '', null, '99', '2', '3', '1', '2016-05-11 11:26:50', null, '2016-05-17 09:27:50', null);
INSERT INTO `member_msg` VALUES ('400', null, '1', 'admin', '1', 'admin', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:28:27', null, null, null);
INSERT INTO `member_msg` VALUES ('401', null, '1', 'admin', '6', 'jd1111', '0', '1', 'asdf', 'asdf', null, '99', '2', '3', '1', '2016-05-11 11:28:40', null, null, null);
INSERT INTO `member_msg` VALUES ('402', null, '1', 'admin', '1', 'admin', '0', '1', 'asdf', 'asdf', null, '99', '2', '3', '1', '2016-05-11 11:28:40', null, null, null);
INSERT INTO `member_msg` VALUES ('403', null, '1', 'admin', '7', 'jd6061@aliyun.com', '0', '1', 'asdf', 'asdf', null, '99', '2', '3', '1', '2016-05-11 11:28:40', null, null, null);
INSERT INTO `member_msg` VALUES ('382', null, '1', 'admin', '1', 'admin', '0', '2', '', '', null, '99', '2', '3', '1', '2016-05-11 11:00:23', null, '2016-05-11 11:17:30', null);
INSERT INTO `member_msg` VALUES ('383', null, '1', 'admin', '6', 'jd1111', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:00:23', null, null, null);
INSERT INTO `member_msg` VALUES ('384', null, '1', 'admin', '7', 'jd6061@aliyun.com', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:00:23', null, null, null);
INSERT INTO `member_msg` VALUES ('385', null, '1', 'admin', '19', 'wangc', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:00:23', null, null, null);
INSERT INTO `member_msg` VALUES ('386', null, '1', 'admin', '6', 'jd1111', '0', '1', '大标题', '内尔动手；是否', null, '99', '2', '3', '1', '2016-05-11 11:01:07', null, null, null);
INSERT INTO `member_msg` VALUES ('387', null, '1', 'admin', '7', 'jd6061@aliyun.com', '0', '1', '大标题', '内尔动手；是否', null, '99', '2', '3', '1', '2016-05-11 11:01:07', null, null, null);
INSERT INTO `member_msg` VALUES ('388', null, '1', 'admin', '19', 'wangc', '0', '1', '大标题', '内尔动手；是否', null, '99', '2', '3', '1', '2016-05-11 11:01:07', null, null, null);
INSERT INTO `member_msg` VALUES ('389', null, '1', 'admin', '1', 'admin', '0', '2', '大标题', '内尔动手；是否', null, '99', '2', '3', '1', '2016-05-11 11:01:07', null, '2016-05-11 11:01:15', null);
INSERT INTO `member_msg` VALUES ('390', null, '1', 'admin', '1', 'admin', '0', '2', '是打发', '撒旦法\r\n撒旦法\r\n撒旦法\r\n撒旦法\r\n设定', null, '99', '2', '3', '1', '2016-05-11 11:08:55', null, '2016-05-17 09:27:40', null);
INSERT INTO `member_msg` VALUES ('391', null, '1', 'admin', '1', 'admin', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:25:28', null, null, null);
INSERT INTO `member_msg` VALUES ('392', null, '1', 'admin', '6', 'jd1111', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:25:28', null, null, null);
INSERT INTO `member_msg` VALUES ('393', null, '1', 'admin', '7', 'jd6061@aliyun.com', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:25:29', null, null, null);
INSERT INTO `member_msg` VALUES ('394', null, '1', 'admin', '1', 'admin', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:26:13', null, null, null);
INSERT INTO `member_msg` VALUES ('395', null, '1', 'admin', '48', 'jd3302', '0', '1', '', '', null, '99', '2', '3', '1', '2016-05-11 11:26:13', null, null, null);
INSERT INTO `member_msg` VALUES ('404', null, '1', 'admin', '48', 'jd3302', '0', '1', '小标题一个', '没有内容', null, '99', '2', '3', '1', '2016-05-17 09:28:16', null, null, null);
INSERT INTO `member_msg` VALUES ('405', null, '1', 'admin', '48', 'jd3302', '0', '1', '小标题一个', '没有内容', null, '99', '2', '3', '1', '2016-05-17 09:28:16', null, null, null);
INSERT INTO `member_msg` VALUES ('406', null, '1', 'admin', '48', 'jd3302', '0', '1', '小标题一个', '没有内容', null, '99', '2', '3', '1', '2016-05-17 09:28:16', null, null, null);
