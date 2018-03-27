/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : bdtg-manager

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-05-11 16:42:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(100) NOT NULL,
  `value` varchar(100) NOT NULL,
  `content` varchar(100) NOT NULL,
  `order_by` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator` int(20) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `updator` int(20) DEFAULT NULL,
  `valid` char(1) NOT NULL DEFAULT '1',
  `memo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', 'app_type', '1', 'android', null, null, null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('2', 'app_type', '2', 'ios', null, null, null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('3', 'bool_type', '0', '否', null, null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('4', 'bool_type', '1', '是', null, null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('5', 'template_type', '0', '菜单', null, null, null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('6', 'template_type', '1', '功能', null, null, null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('7', 'template_type', '2', '内容', null, null, null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('8', 'onLinePlaform', '202', '后台管理', null, null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('9', 'auth_status', '-1', '请选择', '0', '2016-04-19 17:00:56', null, '2016-04-19 17:01:00', null, '1', null);
INSERT INTO `sys_dict` VALUES ('10', 'auth_status', '0', '未审核', '1', '2016-04-18 15:01:13', null, '2016-04-18 15:01:20', null, '1', null);
INSERT INTO `sys_dict` VALUES ('11', 'auth_status', '1', '审核通过', '2', '2016-04-18 15:01:36', null, '2016-04-18 15:01:40', null, '1', null);
INSERT INTO `sys_dict` VALUES ('12', 'auth_status', '2', '审核驳回', '3', '2016-04-18 15:01:59', null, '2016-04-18 15:02:01', null, '1', null);
INSERT INTO `sys_dict` VALUES ('13', 'sex', '-1', '保密', '0', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('14', 'sex', '0', '男', '1', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('15', 'sex', '1', '女', '2', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('16', 'sect', '0', '不公开', '1', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('17', 'sect', '1', '公开', '2', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('18', 'task_product_type', '1', '煤化气', null, '2016-04-22 14:33:05', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('19', 'task_product_type', '2', '合成氨', null, '2016-04-22 14:33:17', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('20', 'task_product_type', '3', '尿素', null, '2016-04-22 14:33:28', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('21', 'task_product_type', '4', '低温甲醇洗', null, '2016-04-22 14:33:43', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('22', 'task_product_type', '5', '甲醇合成', null, '2016-04-22 14:33:55', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('23', 'task_product_type', '5', '锅炉', null, '2016-04-22 14:34:06', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('24', 'task_product_type', '6', '罐区', null, '2016-04-22 14:34:19', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('25', 'task_product_type', '7', '脱硫脱硝', null, '2016-04-22 14:34:31', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('26', 'task_product_type', '8', '空分', null, '2016-04-22 14:34:44', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('27', 'task_zhuanye_type', '9', '化工工艺', null, '2016-04-22 14:35:54', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('28', 'task_zhuanye_type', '2', '自控', null, '2016-04-22 14:36:25', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('29', 'task_zhuanye_type', '3', '设备', null, '2016-04-22 14:36:37', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('30', 'task_zhuanye_type', '4', '机械', null, '2016-04-22 14:36:50', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('31', 'task_zhuanye_type', '5', '给排水', null, '2016-04-22 14:37:01', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('32', 'task_zhuanye_type', '6', '热工', null, '2016-04-22 14:37:24', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('33', 'task_zhuanye_type', '7', '电气', null, '2016-04-22 14:37:36', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('34', 'task_zhuanye_type', '8', '管道', null, '2016-04-22 14:37:50', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('35', 'task_zhuanye_type', '9', '总图', null, '2016-04-22 14:38:02', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('36', 'task_gerenyaoqi_type', '1', '工程师', null, '2016-04-22 14:39:21', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('37', 'task_gerenyaoqi_type', '2', '高级工程师及以上', null, '2016-04-22 14:39:35', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('38', 'task_gerenyaoqi_type', '3', '国家注册专业工程师', null, '2016-04-22 14:39:51', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('39', 'task_rwhf_type', '10', '10天', null, '2016-04-22 14:44:19', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('40', 'task_rwhf_type', '20', '20天', null, '2016-04-22 14:44:24', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('41', 'task_rwhf_type', '30', '30天', null, '2016-04-22 14:44:32', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('42', 'task_rwhf_type', '60', '2个月', null, '2016-04-22 14:44:43', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('43', 'task_rwhf_type', '90', '3个月', null, '2016-04-22 14:44:48', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('44', 'task_rwhf_type', '180', '半年', null, '2016-04-22 14:44:57', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('45', 'task_rwhf_type', '365', '1年', null, '2016-04-22 14:45:11', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('46', 'task_rwhf_type', '730', '2年', null, '2016-04-22 14:45:16', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('47', 'type', '1', '服务商', '1', '2016-04-26 15:44:57', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('48', 'type', '2', '业主', '2', '2016-04-26 15:45:21', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('49', 'type', '3', '制造商', '3', '2016-04-26 15:45:45', null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('50', 'job_type', '1', '业主消息', '1', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('51', 'job_type', '2', '团队消息', '2', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('52', 'job_type', '3', '财务消息', '3', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('53', 'induspid', '1', '工程设计', '1', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('54', 'induspid', '5', '前期咨询', '2', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('55', 'induspid', '11', '生产运营', '3', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('56', 'induspid', '17', '项目建设管理', null, null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('57', 'induspid', '29', '技术服务', '4', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('58', 'induspid', '32', '设备材料采购', '5', null, null, null, null, '1', null);
INSERT INTO `sys_dict` VALUES ('59', 'orderType', 'offline_charge', '线下充值', null, '2016-04-29 16:04:35', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('60', 'fina_action', 'buy_service', '购买商品', null, '2016-04-29 16:38:46', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('61', 'fina_action', 'buy_gy', '雇佣服务', null, '2016-04-29 16:39:12', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('62', 'fina_action', 'pub_task', '发布任务', null, '2016-04-29 16:39:25', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('63', 'fina_action', 'hosted_reward', '托管赏金', null, '2016-04-29 16:39:37', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('64', 'fina_action', 'withdraw', '提现', null, '2016-04-29 16:39:50', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('65', 'fina_action', 'task_delay', '任务延期', null, '2016-04-29 16:40:03', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('66', 'fina_action', 'ext_cash', '额外奖励', null, '2016-04-29 16:40:20', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('67', 'fina_action', 'offline_charge', '线下充值', null, '2016-04-29 16:40:32', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('68', 'fina_action', 'task_bid', '任务中标', null, '2016-04-29 16:40:50', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('69', 'fina_action', 'task_fail', '任务失败退款', null, '2016-04-29 16:41:04', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('70', 'fina_action', 'task_prom_fail', '任务推广失败退款', null, '2016-04-29 16:41:17', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('71', 'fina_action', 'sale_service', '卖服务费用', null, '2016-04-29 16:41:29', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('72', 'fina_action', 'sale_gy', '出售雇佣服务', null, '2016-04-29 16:41:41', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('73', 'fina_action', 'admin_recharge', '管理员充值', null, '2016-04-29 16:42:01', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('74', 'fina_action', 'withdraw_fail', '提现失败返还', null, '2016-04-29 16:42:14', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('75', 'fina_action', 'report', '仲裁处理', null, '2016-04-29 16:42:25', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('76', 'fina_action', 'payitem', '增值服务', null, '2016-04-29 16:42:35', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('77', 'fina_action', 'prom_reg', '推广注册', null, '2016-04-29 16:42:57', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('78', 'fina_action', 'task_fj', '任务反金', null, '2016-04-29 16:43:08', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('79', 'fina_action', 'rights_return', '维权返还', null, '2016-04-29 16:43:19', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('80', 'fina_action', 'task_auto_return', '自动选稿余款', null, '2016-04-29 16:43:31', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('81', 'fina_action', 'order_cancel', '订单终止返款', null, '2016-04-29 16:43:43', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('82', 'fina_action', 'online_charge', '在线余额充值', null, '2016-04-29 16:43:53', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('83', 'fina_action', 'order_charge', '订单充值', null, '2016-04-29 16:44:15', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('84', 'fina_action', 'prom_pub_task', '推广发布任务', null, '2016-04-29 16:44:31', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('85', 'fina_action', 'user_charge', '用户手动充值', null, '2016-04-29 16:44:42', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('86', 'fina_action', 'prom_bid_task', '推广承接任务', null, '2016-04-29 16:44:53', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('87', 'fina_action', 'prom_service', '推广购买服务', null, '2016-04-29 16:45:04', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('88', 'fina_action', 'prom_bank_auth', '推广注册+银行认证', null, '2016-04-29 16:45:19', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('89', 'fina_action', 'prom_realname_auth', '推广注册+实名认证', null, '2016-04-29 16:45:33', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('90', 'fina_action', 'prom_email_auth', '推广注册+邮箱认证', null, '2016-04-29 16:46:19', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('91', 'fina_action', 'prom_mobile_auth', '推广注册+手机认证', null, '2016-04-29 16:46:31', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('92', 'fina_action', 'prom_enterprise_auth', '推广注册+企业认证', null, '2016-04-29 16:47:49', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('93', 'fina_action', 'enterprise_auth', '企业认证', null, '2016-04-29 16:48:04', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('94', 'fina_action', 'task_remain_return', '任务余款返还', null, '2016-04-29 16:48:18', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('95', 'fina_action', 'task_hosted_return', '托管余款返还', null, '2016-04-29 16:48:28', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('96', 'fina_action', 'admin_charge', '网站手动充值', null, '2016-04-29 16:48:39', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('97', 'fina_action', 'host_deposit', '托管诚意金', null, '2016-04-29 16:48:49', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('98', 'fina_action', 'deposit_return', '诚意金退还', null, '2016-04-29 16:49:01', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('99', 'fina_action', 'host_return', '托管佣金返还', null, '2016-04-29 16:49:16', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('100', 'fina_action', 'cancel_bid', '撤销中标', null, '2016-04-29 16:49:26', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('101', 'fina_action', 'host_split', '托管佣金分配', null, '2016-04-29 16:49:37', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('102', 'fina_action', 'workhide', '稿件隐藏退款', null, '2016-04-29 16:49:48', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('103', 'fina_action', 'seohide', '屏蔽搜索引擎退款', null, '2016-04-29 16:49:57', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('104', 'fina_action', 'tasktop', '任务置顶退款', null, '2016-04-29 16:50:07', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('105', 'fina_action', 'goodstop', '服务置顶退款', null, '2016-04-29 16:50:17', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('106', 'fina_action', 'urgent', '任务加急退款', null, '2016-04-29 16:50:28', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('107', 'auth_bank_status', '0', '认证中', null, '2016-05-04 10:12:11', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('108', 'auth_bank_status', '1', '已通过', null, '2016-05-04 10:12:22', null, null, null, '1', '');
INSERT INTO `sys_dict` VALUES ('109', 'job_sts', '0', '任务删除', null, '2015-09-21 17:33:30', null, '2015-09-21 17:33:30', null, '1', '任务状态');
INSERT INTO `sys_dict` VALUES ('110', 'job_sts', '1', '任务暂停', null, '2015-09-21 17:32:57', null, '2015-09-21 17:32:57', null, '1', '任务状态');
INSERT INTO `sys_dict` VALUES ('111', 'job_sts', '2', '任务恢复', null, '2015-09-21 17:33:19', null, '2015-09-21 17:33:19', null, '1', '任务状态');

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `value` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator` int(20) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `updator` int(20) DEFAULT NULL,
  `valid` char(1) NOT NULL DEFAULT '1',
  `memo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES ('1', 'testa', '1', '安安', '2016-03-10 13:55:43', null, '2016-03-10 13:57:42', null, '0', null);
INSERT INTO `sys_param` VALUES ('2', 'test2', '2', '22', '2016-03-10 13:57:26', null, '2016-03-10 13:57:37', null, '0', null);
INSERT INTO `sys_param` VALUES ('3', 'ab', 'a', 'a', '2016-03-10 14:01:49', null, '2016-03-10 14:01:54', null, '1', null);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `type` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator` int(20) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `updator` int(20) DEFAULT NULL,
  `valid` char(1) NOT NULL DEFAULT '1',
  `url` varchar(200) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `pid` int(20) DEFAULT NULL,
  `tree_path` varchar(100) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', 'config', '系统管理', '', null, null, null, null, '1', '', null, '0', '0.1', null);
INSERT INTO `sys_permission` VALUES ('2', 'SYS_USER', '用户管理', '', null, null, '2016-03-03 23:52:19', null, '1', '/sys/user', null, '20', '0.1.20.2', null);
INSERT INTO `sys_permission` VALUES ('3', 'SYS_ROLE', '角色管理', '', null, null, null, null, '1', '/sys/role', null, '20', '0.1.20.3', null);
INSERT INTO `sys_permission` VALUES ('4', 'SYS_PERMISSSION', '菜单管理', '', null, null, null, null, '1', '/sys/permission', null, '20', '0.1.20.4', null);
INSERT INTO `sys_permission` VALUES ('5', 'SYS_DICT', '数据字典管理', '', null, null, null, null, '1', '/sys/dict', null, '20', '0.1.20.5', null);
INSERT INTO `sys_permission` VALUES ('6', 'user', '会员管理', '', null, null, null, null, '1', '', null, '0', '0.6', null);
INSERT INTO `sys_permission` VALUES ('8', 'content', '资讯管理', '', null, null, null, null, '1', '', null, '0', '0.8', null);
INSERT INTO `sys_permission` VALUES ('10', '', '会员管理', '', null, null, null, null, '1', '', null, '6', '0.6.10', null);
INSERT INTO `sys_permission` VALUES ('11', 'CM_CATALOG', '栏目管理', '', null, null, '2016-04-19 11:06:13', null, '1', '/manage/category-manage', null, '24', '0.8.24.11', null);
INSERT INTO `sys_permission` VALUES ('12', 'CM_CONTENT', '内容管理', '', null, null, '2016-04-19 11:06:32', null, '1', '/manage/content-publish', null, '24', '0.8.24.12', null);
INSERT INTO `sys_permission` VALUES ('13', 'APP_VERSION', '版本管理', '', null, null, null, null, '1', '/app/version', null, '7', '0.7.13', null);
INSERT INTO `sys_permission` VALUES ('14', 'APP_TEMPLATE', '排版设置', '', null, null, null, null, '1', '/app/template', null, '7', '0.7.14', null);
INSERT INTO `sys_permission` VALUES ('15', 'APP_FUNCTION', '功能设置', '', null, null, null, null, '1', '/app/function', null, '7', '0.7.15', null);
INSERT INTO `sys_permission` VALUES ('16', '', '认证审核', '', null, null, '2016-04-19 11:11:22', null, '1', '', null, '6', '0.6.16', null);
INSERT INTO `sys_permission` VALUES ('17', 'APP_FBK', '意见反馈', '', null, null, null, null, '1', '', null, '7', '0.7.17', null);
INSERT INTO `sys_permission` VALUES ('18', 'task', '任务管理', null, null, null, null, null, '1', null, null, '0', '0.18', null);
INSERT INTO `sys_permission` VALUES ('19', 'finance', '财务管理', null, null, null, null, null, '1', null, null, '0', '0.19', null);
INSERT INTO `sys_permission` VALUES ('20', null, '系统管理', null, null, null, null, null, '1', null, null, '1', '0.1.20', null);
INSERT INTO `sys_permission` VALUES ('21', null, '会员管理', null, null, null, null, null, '1', '/member/auth-space', null, '10', null, null);
INSERT INTO `sys_permission` VALUES ('22', '', '实名认证审核', '', null, null, '2016-04-19 11:10:44', null, '1', '/member/realname', null, '16', '0.6.16.22', null);
INSERT INTO `sys_permission` VALUES ('23', null, '资质审核', null, null, null, null, null, '1', '/welcome.html', null, '10', null, null);
INSERT INTO `sys_permission` VALUES ('24', null, '资讯管理', null, null, null, null, null, '1', null, null, '8', '0.8.24', null);
INSERT INTO `sys_permission` VALUES ('25', null, '任务管理', null, null, null, null, null, '1', null, null, '18', null, null);
INSERT INTO `sys_permission` VALUES ('26', null, '任务管理', null, null, null, null, null, '1', null, null, '25', null, null);
INSERT INTO `sys_permission` VALUES ('27', null, '任务配置', null, null, null, null, null, '1', null, null, '25', null, null);
INSERT INTO `sys_permission` VALUES ('28', null, '任务审核', null, null, null, null, null, '1', null, null, '25', null, null);
INSERT INTO `sys_permission` VALUES ('29', null, '财务模块', null, null, null, null, null, '1', null, null, '19', null, null);
INSERT INTO `sys_permission` VALUES ('30', null, '财务概况', null, null, null, null, null, '1', null, null, '29', null, null);
INSERT INTO `sys_permission` VALUES ('31', '', '企业认证审核', '', null, null, '2016-04-19 11:11:00', null, '1', '/member/company', null, '16', '0.6.16.31', null);
INSERT INTO `sys_permission` VALUES ('32', '', '资质审核', '', '2016-04-19 11:10:25', null, '2016-04-19 11:11:29', null, '1', '', null, '6', '0.6.32', null);
INSERT INTO `sys_permission` VALUES ('33', '', '网站收支', '', '2016-04-19 11:25:57', null, '2016-04-19 11:25:58', null, '1', '', null, '29', 'null.33', null);
INSERT INTO `sys_permission` VALUES ('34', '', '用户流水', '', '2016-04-19 11:26:10', null, '2016-04-19 11:26:10', null, '1', '/financial/finance', null, '29', '0.19.29.34', null);
INSERT INTO `sys_permission` VALUES ('35', '', '充值清单', '', '2016-04-19 11:26:25', null, '2016-04-29 15:57:27', null, '1', '/financial/order-charge', null, '29', 'null.35', null);
INSERT INTO `sys_permission` VALUES ('36', '', '提现清单', '', '2016-04-19 11:26:36', null, '2016-04-29 15:57:49', null, '1', '/financial/withdraw', null, '29', 'null.36', null);
INSERT INTO `sys_permission` VALUES ('37', '', '票据管理', '', '2016-04-19 11:26:47', null, '2016-04-19 11:26:47', null, '1', '', null, '29', 'null.37', null);
INSERT INTO `sys_permission` VALUES ('38', '', '对账管理', '', '2016-04-19 11:27:12', null, '2016-04-19 11:27:12', null, '1', '', null, '29', 'null.38', null);
INSERT INTO `sys_permission` VALUES ('39', null, '成员', null, '2016-04-26 15:33:33', null, '2016-04-26 15:33:36', null, '1', '', null, '18', null, null);
INSERT INTO `sys_permission` VALUES ('40', null, '服务商', null, '2016-04-26 15:34:24', null, '2016-04-26 15:34:27', null, '1', '/job/member?type=1', null, '39', null, null);
INSERT INTO `sys_permission` VALUES ('41', null, '业主', null, '2016-04-26 15:34:51', null, '2016-04-26 15:34:53', null, '1', '/job/member?type=2', null, '39', null, null);
INSERT INTO `sys_permission` VALUES ('42', null, '制造商', null, '2016-04-26 15:35:17', null, '2016-04-26 15:35:19', null, '1', '/job/member?type=3', null, '39', null, null);
INSERT INTO `sys_permission` VALUES ('43', null, '消息', null, null, null, null, null, '1', null, null, '18', null, null);
INSERT INTO `sys_permission` VALUES ('44', null, '通知', null, null, null, null, null, '1', '/job/msg/informindex?type=1', null, '43', null, null);
INSERT INTO `sys_permission` VALUES ('45', '', '动态', null, null, null, null, null, '1', '/job/msg/informindex?type=2', null, '43', null, null);
INSERT INTO `sys_permission` VALUES ('46', null, '在线作业', null, null, null, null, null, '1', '/job/msg/onlineindex', null, '43', null, null);
INSERT INTO `sys_permission` VALUES ('47', null, '地区管理', null, null, null, null, null, '1', '/config/district', null, '20', '0.1.20.47', null);
INSERT INTO `sys_permission` VALUES ('48', null, '银行认证', null, null, null, null, null, '1', '/financial/auth-bank', null, '16', '0.6.16.48', null);

-- ----------------------------
-- Table structure for sys_r_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_r_role_permission`;
CREATE TABLE `sys_r_role_permission` (
  `role_id` int(20) NOT NULL AUTO_INCREMENT,
  `permission_id` int(20) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator` int(20) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `updator` int(20) DEFAULT NULL,
  `valid` char(1) NOT NULL DEFAULT '1',
  KEY `user_id` (`role_id`),
  KEY `role_id` (`permission_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `fk_role_permission_pid` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`),
  CONSTRAINT `fk_role_permission_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_r_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_r_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_r_user_role`;
CREATE TABLE `sys_r_user_role` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(100) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator` int(20) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `updator` int(20) DEFAULT NULL,
  `valid` char(1) NOT NULL DEFAULT '1',
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_r_user_role
-- ----------------------------
INSERT INTO `sys_r_user_role` VALUES ('1', '1', '2016-04-26 15:51:32', '1', null, null, '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `note` varchar(100) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator` int(20) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `updator` int(20) DEFAULT NULL,
  `valid` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '管理员', '管理员', null, null, null, null, '1');
INSERT INTO `sys_role` VALUES ('2', 'cs', '测试', '就是测试', '2016-04-27 10:44:32', null, null, null, '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `salt` varchar(100) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `creator` int(20) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `updator` int(20) DEFAULT NULL,
  `valid` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'admin', 'dfd92043bb351cea1fba13d5e374c5b785885008', 'dacae72af9fe8aa6', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('2', 'jd111', '测试1', '60f127744b67e6d2b3476737eaba90521eb666c9', 'd', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('3', 'jd112', '测试2', '60f127744b67e6d2b3476737eaba90521eb666c9', 'd', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('4', 'jd113', '测试3', '60f127744b67e6d2b3476737eaba90521eb666c9', 'd', null, null, null, null, '1');
INSERT INTO `sys_user` VALUES ('5', 'jd123', 'jd123', '60f127744b67e6d2b3476737eaba90521eb666c9', '3e448ffd20c9f7e9', '2016-04-28 10:03:44', null, '2016-04-28 10:12:00', null, '1');
INSERT INTO `sys_user` VALUES ('6', 'jd1111', 'jd1111', '60f127744b67e6d2b3476737eaba90521eb666c9', '0ded3fec5acb67ff', '2016-04-28 10:26:52', null, null, null, '1');
INSERT INTO `sys_user` VALUES ('7', 'jd121', 'jd121', '60f127744b67e6d2b3476737eaba90521eb666c9', '3cfeb8b372e49a81', '2016-04-28 10:29:12', null, null, null, '1');
INSERT INTO `sys_user` VALUES ('8', 'jd124', 'jd124', '60f127744b67e6d2b3476737eaba90521eb666c9', '87e8312e0f5cfb01', '2016-04-28 10:31:13', null, '2016-04-28 11:32:00', null, '1');
INSERT INTO `sys_user` VALUES ('9', 'jd1241', 'jd1241', '60f127744b67e6d2b3476737eaba90521eb666c9', 'b7b30d0dfee29d62', '2016-04-28 11:13:25', null, null, null, '1');
INSERT INTO `sys_user` VALUES ('10', 'zhangjf', 'zhangjf', '7aec36be54884514af1835d0d06192e609bea9e1', '2a1fb1b3f72a6b35', '2016-04-29 10:09:34', null, null, null, '1');
INSERT INTO `sys_user` VALUES ('11', 'jdad', 'jdad', '60f127744b67e6d2b3476737eaba90521eb666c9', '4dab9af4ef35c9ae', '2016-04-29 10:16:13', null, null, null, '1');

-- ----------------------------
-- Table structure for sys_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_schedule_job`;
CREATE TABLE `sys_schedule_job` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `job_id` varchar(10) DEFAULT NULL COMMENT '任务id',
  `job_name` varchar(30) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(20) DEFAULT NULL COMMENT '任务分组',
  `job_status` varchar(1) DEFAULT NULL COMMENT '任务状态',
  `cron_expression` varchar(30) DEFAULT NULL COMMENT 'cron表达式',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `bean_class` varchar(50) DEFAULT NULL COMMENT '包名+类名',
  `is_concurrent` varchar(1) DEFAULT NULL COMMENT '任务是否有状态',
  `spring_id` varchar(10) DEFAULT NULL COMMENT 'spring bean',
  `method_name` varchar(20) DEFAULT NULL COMMENT '任务调用的方法名',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='计划任务信息';

-- ----------------------------
-- Records of sys_schedule_job
-- ----------------------------
INSERT INTO `sys_schedule_job` VALUES ('1', '1', '任务-1', 'default_group', '', '0 20 12,15 ? * 2-6', '', 'cn.hzstk.securities.common.service.StockInfoQtz', '', '', 'execute', null, null, '2016-04-01 16:59:47', null, '2016-04-01 16:59:47', '1');
INSERT INTO `sys_schedule_job` VALUES ('2', '2', '任务-2', 'default_group', '', '0 0 16 ? * 2-6', '', 'cn.hzstk.securities.common.service.SpringQtz', '', '', 'execute', null, null, '2016-04-01 16:59:55', null, '2016-04-01 16:59:55', '1');
INSERT INTO `sys_schedule_job` VALUES ('3', '3', '任务-3', 'default_group', '', '1 25/5 9 ? * 2-6', '', 'cn.hzstk.securities.common.service.StockIndexQtz', '', '', 'execute', null, null, '2015-10-20 09:26:32', null, '2015-10-20 09:26:32', '1');
INSERT INTO `sys_schedule_job` VALUES ('4', '4', '任务-4', 'default_group', '', '0 0/5 10,13-14 ? * 2-6', '', 'cn.hzstk.securities.common.service.StockIndexQtz', '', '', 'execute', null, null, '2015-09-22 10:21:05', null, '2015-09-22 10:21:05', '1');
INSERT INTO `sys_schedule_job` VALUES ('5', '5', '任务-5', 'default_group', '', '0 0-30/5 11 ? * 2-6', '', 'cn.hzstk.securities.common.service.StockIndexQtz', '', '', 'execute', null, null, '2015-09-22 10:21:13', null, '2015-09-22 10:21:13', '1');
INSERT INTO `sys_schedule_job` VALUES ('6', '6', '任务-6', 'default_group', '', '0 1 15 ? * 2-6', '', 'cn.hzstk.securities.common.service.StockIndexQtz', '', '', 'execute', null, null, '2015-12-09 21:58:50', null, '2015-12-09 21:58:50', '1');
INSERT INTO `sys_schedule_job` VALUES ('7', '7', '任务-7', 'default_group', '', '0 3/30 9-15 ? * 2-6', '', 'cn.hzstk.securities.common.service.StockSummaryQtz', '', '', 'execute', null, null, '2015-11-13 10:02:42', null, '2015-11-13 10:02:42', '1');
INSERT INTO `sys_schedule_job` VALUES ('8', '8', '任务-8', 'default_group', '', '0 10 15 ? * 2-6', '', 'cn.hzstk.securities.common.service.StockPlateQtz', '', '', 'execute', null, null, '2015-11-17 15:09:05', null, '2015-11-17 15:09:05', '1');

-- ----------------------------
-- Table structure for sys_holiday
-- ----------------------------
DROP TABLE IF EXISTS `sys_holiday`;
CREATE TABLE `sys_holiday` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `dt` varchar(10) DEFAULT NULL COMMENT '日期',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `order_by` int(10) DEFAULT NULL COMMENT '顺序',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updator` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `valid` char(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='股市假日信息';
