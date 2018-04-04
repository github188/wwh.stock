/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50629
Source Host           : localhost:3306
Source Database       : koubeiblog

Target Server Type    : MYSQL
Target Server Version : 50629
File Encoding         : 65001

Date: 2016-11-28 17:42:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(255) DEFAULT 'System',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(255) DEFAULT 'System',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `cate_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `tag_ids` varchar(255) DEFAULT NULL,
  `alias` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `intro` text,
  `content` longtext NOT NULL,
  `tag_num` int(11) DEFAULT '0',
  `comment_num` int(11) DEFAULT '0',
  `view_num` int(11) DEFAULT '0',
  `template` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('3', 'System', '2016-09-28 16:14:54', 'System', '2016-10-04 13:57:12', '0', '111', '2', null, null, '123中文1243', null, '范德萨', '0', '0', '0', null);
INSERT INTO `article` VALUES ('5', 'System', '2016-09-29 16:49:54', 'System', '2016-09-29 16:49:54', '0', '111', '2', null, null, '2', null, '2', '0', '0', '0', null);
INSERT INTO `article` VALUES ('6', 'System', '2016-09-29 16:56:03', 'System', '2016-09-29 16:56:03', '0', '111', '2', null, null, '4', null, '4', '0', '0', '0', null);
INSERT INTO `article` VALUES ('7', 'System', '2016-09-29 16:57:19', 'System', '2016-09-29 16:57:19', '0', '111', '2', null, null, '5', null, '5', '0', '0', '0', null);
INSERT INTO `article` VALUES ('8', 'System', '2016-09-29 16:58:03', 'System', '2016-09-29 16:58:03', '0', '111', '2', null, null, '4', null, '4', '0', '0', '0', null);
INSERT INTO `article` VALUES ('10', 'System', '2016-09-30 13:58:50', 'System', '2016-09-30 13:59:57', '1', '1', '2', null, '8飞', '8fdgsd反攻倒算', '8第四个', '8的分公司', '0', '0', '0', 'index');
INSERT INTO `article` VALUES ('11', 'System', '2016-10-04 13:17:36', 'System', '2016-10-04 13:57:30', '1', '1', '2', null, '1', '10000', '1', '1', '0', '0', '0', 'index');

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(255) DEFAULT 'System',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(255) DEFAULT 'System',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `name` varchar(255) NOT NULL,
  `sort` int(11) DEFAULT '1',
  `count` int(11) DEFAULT '0',
  `alias` varchar(255) DEFAULT NULL,
  `intro` text,
  `parent` int(11) DEFAULT '0',
  `template` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('2', 'System', '2016-09-24 03:58:33', 'System', '2016-09-24 04:25:23', '0', '11dsa', '111', '0', '11', null, '0', null);
INSERT INTO `category` VALUES ('9', 'System', '2016-09-28 00:08:47', 'System', '2016-09-28 00:08:47', '0', '1', '0', '0', null, null, '0', null);
INSERT INTO `category` VALUES ('11', 'System', '2016-10-04 16:20:22', 'System', '2016-10-04 16:20:22', '1', '1', '1', '0', '1', null, '0', null);

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(255) DEFAULT 'System',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(255) DEFAULT 'System',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `article_id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT '0',
  `user_id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('2', 'System', '2016-09-28 17:16:51', 'System', '2016-09-28 17:16:51', '1', '321', '0', '2341', '测试评论', '评论body', null, null);

-- ----------------------------
-- Table structure for `config`
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(255) DEFAULT 'System',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(255) DEFAULT 'System',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `name` varchar(255) NOT NULL,
  `content` longtext NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES ('1', 'System', '2016-10-09 14:30:27', 'System', '2016-11-28 17:27:54', '1', 'currentVersion	0.0.3\r\nsettingDTO	{\"allowUplaodPerFileSize\":0,\"deskPageSize\":0,\"systemPageSize\":0}\r\ncurrentVersion', '0.8.0');
INSERT INTO `config` VALUES ('2', 'System', '2016-10-09 14:30:42', 'System', '2016-11-28 17:27:37', '1', 'settingDTO', '{\"allowUplaodPerFileSize\":11,\"allowUploadFileType\":\"jpg|gif|png|jpeg|bmp|psd|wmf|ico|rpm|deb|tar|gz|sit|7z|bz2|zip|rar|xml|xsl|svg|svgz|doc|docx|ppt|pptx|xls|xlsx|wps|chm|txt|pdf|mp3|avi|mpg|rm|ra|rmvb|mov|wmv|wma|swf|fla|torrent|apk|zba\",\"blogSubTitle\":\"Good Luck To You!\",\"blogTitle\":\"JavaEar的博客\",\"copyRight\":\"Copyright © 2016-2016 <strong><a href=\\\"http://www.javaear.com\\\">JavaEar</a></strong> \\u00A0 All Rights Reserved. 备案号：沪ICP备15014457号-1\",\"deskPageSize\":10,\"domain\":\"zblog4java.javaear.com\",\"enableGzip\":\"true\",\"systemPageSize\":3}');

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `description` text,
  `creator` varchar(255) DEFAULT 'System',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(255) DEFAULT 'System',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('10000', '﻿登录', 'login', null, 'aooer', '2016-09-22 14:38:24', 'aooer', '2016-10-19 18:51:27', '0');
INSERT INTO `permission` VALUES ('10001', '登出', 'logout', null, 'aooer', '2016-09-22 14:38:24', 'aooer', '2016-10-19 18:51:32', '0');
INSERT INTO `permission` VALUES ('10002', '后台首页访问', 'index', null, 'aooer', '2016-09-22 14:38:24', 'aooer', '2016-10-19 18:51:37', '0');
INSERT INTO `permission` VALUES ('10003', '网站基础设置', 'setting', null, 'aooer', '2016-09-22 14:38:24', 'aooer', '2016-10-19 18:51:44', '0');
INSERT INTO `permission` VALUES ('20000', '分类查询', 'category-management', null, 'System', '2016-09-24 01:14:36', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('20001', '分类添加', 'category-add', null, 'System', '2016-09-24 01:14:48', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('20002', '分类修改', 'category-edit', null, 'System', '2016-09-24 01:14:57', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('20003', '分类删除', 'category-remove', null, 'System', '2016-09-24 01:15:10', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('30000', '标签查询', 'tag-management', null, 'System', '2016-09-24 18:17:52', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('30001', '标签添加', 'tag-add', null, 'System', '2016-09-24 18:18:15', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('30002', '标签修改', 'tag-edit', null, 'System', '2016-09-24 18:18:27', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('30003', '标签删除', 'tag-remove', null, 'System', '2016-09-24 18:18:39', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('40000', '用户查询', 'user-management', null, 'System', '2016-09-24 22:12:18', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('40001', '用户添加', 'user-add', null, 'System', '2016-09-24 22:12:37', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('40002', '用户修改', 'user-edit', null, 'System', '2016-09-24 22:12:43', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('40003', '用户删除', 'user-remove', null, 'System', '2016-09-24 22:12:55', 'System', '2016-10-07 01:53:46', '0');
INSERT INTO `permission` VALUES ('50000', '文章查询', 'article-management', null, 'System', '2016-09-27 17:28:28', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('50001', '文章添加', 'article-add', null, 'System', '2016-09-27 17:28:38', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('50002', '文章修改', 'article-edit', null, 'System', '2016-09-27 17:28:44', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('50003', '文章删除', 'article-remove', null, 'System', '2016-09-27 17:28:51', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('60000', '评论查询', 'comment-management', null, 'System', '2016-09-28 17:23:03', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('60001', '评论添加', 'comment-add', null, 'System', '2016-09-28 17:23:17', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('60002', '评论修改', 'comment-edit', null, 'System', '2016-09-28 17:23:32', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('60003', '评论删除', 'comment-remove', null, 'System', '2016-09-28 17:23:42', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('70000', '上传附件查询', 'upload-management', null, 'System', '2016-09-29 10:25:24', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('70001', '上传附件添加', 'upload-add', null, 'System', '2016-09-29 10:25:34', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('70002', '上传附件修改', 'upload-edit', null, 'System', '2016-09-29 10:25:43', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('70003', '上传附件删除', 'upload-remove', null, 'System', '2016-09-29 10:25:57', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('80000', '模板查询', 'template-management', null, 'System', '2016-09-29 15:53:42', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('80001', '模板添加', 'template-add', null, 'System', '2016-09-29 15:53:49', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('80002', '模板修改', 'template-edit', null, 'System', '2016-09-29 15:53:55', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('80003', '模板删除', 'template-remove', null, 'System', '2016-09-29 15:54:04', 'System', '2016-10-07 01:53:47', '0');
INSERT INTO `permission` VALUES ('90000', '权限查询', 'authority-management', null, 'System', '2016-10-20 14:22:20', 'System', '2016-10-20 14:22:20', '0');
INSERT INTO `permission` VALUES ('90002', '权限修改', 'authority-edit', null, 'System', '2016-10-20 15:47:55', 'System', '2016-10-20 15:47:55', '0');
INSERT INTO `permission` VALUES ('100000', '清空缓存', 'cache-reload', null, 'System', '2016-10-21 16:15:16', 'System', '2016-10-21 16:15:16', '0');
INSERT INTO `permission` VALUES ('110000', 'ueditor配置', 'ueditor-config', null, 'System', '2016-10-24 00:29:25', 'System', '2016-10-24 00:29:25', '0');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `permissions` text NOT NULL,
  `description` text,
  `creator` varchar(255) DEFAULT 'System',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(255) DEFAULT 'System',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('0', '管理员', '[10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,10057,10058,10059,10060,10061,20000,20001,20002,20003,30000,30001,30002,30003,40000,40001,40002,4003,50000,50001,50002,50003,60000,60001,60002,60003,70000,70001,70002,70003,80000,80001,80002,80003]', null, 'aooer', '2016-09-22 20:51:25', 'aooer', '2016-09-29 15:54:35', '0');
INSERT INTO `role` VALUES ('1', '网站编辑', '[10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,10057,10058,10059,10060,10061]', null, 'aooer', '2016-09-22 20:51:25', 'aooer', '2016-09-22 20:51:25', '0');
INSERT INTO `role` VALUES ('2', '作者', '[10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,10057,10058,10059,10060,10061]', null, 'aooer', '2016-09-22 20:51:25', 'aooer', '2016-09-22 20:51:25', '0');
INSERT INTO `role` VALUES ('3', '协作者', '[10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,10057,10058,10059,10060,10061]', null, 'aooer', '2016-09-22 20:51:25', 'aooer', '2016-09-22 20:51:25', '0');
INSERT INTO `role` VALUES ('4', '评论者', '[10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,10057,10058,10059,10060,10061]', null, 'aooer', '2016-09-22 20:51:25', 'aooer', '2016-09-22 20:51:25', '0');
INSERT INTO `role` VALUES ('5', '游客', '[10000,10001,10002,10003,10004,10005,10006,10007,10008,10009,10010,10011,10012,10013,10014,10015,10016,10017,10018,10019,10020,10021,10022,10023,10024,10025,10026,10027,10028,10029,10030,10031,10032,10033,10034,10035,10036,10037,10038,10039,10040,10041,10042,10043,10044,10045,10046,10047,10048,10049,10050,10051,10052,10053,10054,10055,10056,10057,10058,10059,10060,10061]', null, 'aooer', '2016-09-22 20:51:25', 'aooer', '2016-09-22 20:51:25', '0');

-- ----------------------------
-- Table structure for `tag`
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(255) DEFAULT 'System',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(255) DEFAULT 'System',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `name` varchar(255) NOT NULL,
  `sort` int(11) DEFAULT '1',
  `count` int(11) DEFAULT '0',
  `alias` varchar(255) DEFAULT NULL,
  `intro` text,
  `template` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('1', 'System', '2016-09-24 18:37:39', 'System', '2016-09-24 18:37:39', '0', 'testTag', '0', '0', 'tagalias', 'yes', null);
INSERT INTO `tag` VALUES ('4', 'System', '2016-09-30 14:25:56', 'System', '2016-09-30 14:26:17', '1', '3', '1', '0', '2', '2', null);

-- ----------------------------
-- Table structure for `upload`
-- ----------------------------
DROP TABLE IF EXISTS `upload`;
CREATE TABLE `upload` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` varchar(255) DEFAULT 'System',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(255) DEFAULT 'System',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  `user_id` int(11) DEFAULT '0',
  `size` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `source` varchar(255) NOT NULL,
  `mime_type` varchar(255) NOT NULL,
  `down_nums` int(11) DEFAULT '0',
  `article_id` int(11) DEFAULT '0',
  `intro` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of upload
-- ----------------------------
INSERT INTO `upload` VALUES ('6', 'System', '2016-09-29 13:20:34', 'System', '2016-09-29 13:20:34', '1', '0', '4652', 'uploadFile', 'F:\\develop\\workspace\\web\\app\\static\\upload\\dgfp_40_hp.zip', 'zip', '0', '0', null);
INSERT INTO `upload` VALUES ('7', 'System', '2016-10-04 15:29:53', 'System', '2016-10-04 15:29:53', '1', '0', '411', 'uploadFile', 'D:\\develop\\zblog4java\\uploadfile\\1.png', 'png', '0', '0', null);
INSERT INTO `upload` VALUES ('8', 'System', '2016-10-04 15:34:23', 'System', '2016-10-04 15:34:23', '1', '0', '411', 'uploadFile', 'D:\\develop\\zblog4java\\uploadfile\\1.png', 'png', '0', '0', null);
INSERT INTO `upload` VALUES ('9', 'System', '2016-10-04 16:48:46', 'System', '2016-10-04 16:48:46', '1', '0', '31', 'uploadFile', 'D:\\develop\\zblog4java\\uploadfile\\111.png', 'png', '0', '0', null);

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` int(2) DEFAULT '1',
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `alias` varchar(50) DEFAULT NULL,
  `intro` text,
  `articles` int(11) DEFAULT '0',
  `pages` int(11) DEFAULT '0',
  `comments` int(11) DEFAULT '0',
  `uploads` int(11) DEFAULT '0',
  `sign` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT 'System',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modifier` varchar(255) DEFAULT 'System',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `name` (`name`) USING BTREE,
  KEY `alias` (`alias`) USING BTREE,
  KEY `role` (`role`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('3', '0', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'angryid@163.com1123', '127.0.0.1', '123', '1123', '0', '0', '0', '0', null, 'System', '2016-09-22 21:58:26', 'System', '2016-11-28 17:33:21', '0');
INSERT INTO `user` VALUES ('4', '1', 'test', '202cb962ac59075b964b07152d234b70', 'test@1.com', null, 't', 'sda', '0', '0', '0', '0', null, 'System', '2016-09-24 22:33:57', 'System', '2016-09-24 22:33:57', '1');
INSERT INTO `user` VALUES ('5', '0', 'a', '0cc175b9c0f1b6a831c399e269772661', 'a', null, 'a', 'a', '0', '0', '0', '0', null, 'System', '2016-09-30 11:26:41', 'System', '2016-09-30 11:26:41', '1');
INSERT INTO `user` VALUES ('6', '1', '1', 'c4ca4238a0b923820dcc509a6f75849b', '1', null, null, null, '0', '0', '0', '0', null, 'System', '2016-10-04 15:45:14', 'System', '2016-10-04 15:45:14', '1');
