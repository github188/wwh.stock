/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : bdtg-manager

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-09-16 21:30:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for member_auth_space
-- ----------------------------
DROP TABLE IF EXISTS `member_auth_space`;
CREATE TABLE `member_auth_space` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `company_sect` int(2) DEFAULT '0' COMMENT '公司是否公开',
  `company` varchar(255) DEFAULT NULL COMMENT '所属公司',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `name` varchar(50) DEFAULT NULL COMMENT '真实姓名或企业名称',
  `code` varchar(20) DEFAULT NULL COMMENT '身份证号或者营业执照编码',
  `idpic` varchar(200) DEFAULT NULL COMMENT '身份证复印件正面照',
  `idpic_down` varchar(200) DEFAULT NULL COMMENT '身份证复印件背面照',
  `mobile_status` int(2) DEFAULT NULL COMMENT '手机认证',
  `email_status` int(2) DEFAULT NULL COMMENT '邮箱认证',
  `auth_status` int(2) DEFAULT NULL COMMENT '认证状态(0:未认证1：认证通过2认证驳回)',
  `auth_stime` datetime DEFAULT NULL COMMENT '认证开始时间',
  `auth_etime` datetime DEFAULT NULL COMMENT '认证结束时间',
  `indus_pid` varchar(4) DEFAULT NULL COMMENT '所属行业',
  `indus_id` varchar(4) DEFAULT NULL COMMENT '所属子行业',
  `sex` int(2) DEFAULT NULL COMMENT '性别',
  `birthday` varchar(10) DEFAULT NULL COMMENT '出生日期',
  `legal` varchar(100) DEFAULT NULL COMMENT '法人代表',
  `staff_num` int(11) DEFAULT NULL COMMENT '员工人数',
  `run_years` int(11) DEFAULT NULL COMMENT '经营年数',
  `url` varchar(200) DEFAULT NULL COMMENT '公司网址',
  `address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `summary` text COMMENT '简介',
  `user_type` int(11) DEFAULT '0' COMMENT '用户类型(0:默认值,无类型；1：个人用户；2：企业用户)',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `sec_code` varchar(255) DEFAULT NULL COMMENT '支付密码',
  `user_pic` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `group_id` int(11) DEFAULT NULL COMMENT '用户组编号',
  `isvip` int(11) DEFAULT NULL COMMENT '是否是VIP',
  `status` int(11) DEFAULT '1' COMMENT '用户状态',
  `marry` char(10) DEFAULT NULL COMMENT '是否已婚',
  `hometown` char(10) DEFAULT NULL COMMENT '出生地',
  `residency` varchar(30) DEFAULT NULL COMMENT '所在地',
  `fax` varchar(20) DEFAULT NULL COMMENT '传真',
  `skill_ids` varchar(150) DEFAULT NULL COMMENT '技能编号',
  `experience` text COMMENT '经历',
  `reg_time` datetime DEFAULT NULL COMMENT '注册时间',
  `reg_ip` varchar(20) DEFAULT NULL COMMENT '注册IP',
  `domain` varchar(50) DEFAULT NULL COMMENT '域名',
  `credit` decimal(11,3) DEFAULT '0.000' COMMENT '积分',
  `balance` decimal(11,3) DEFAULT '0.000' COMMENT '账号余额',
  `balance_status` int(11) DEFAULT '0' COMMENT '账号状态',
  `pub_num` int(11) DEFAULT '0' COMMENT '发布数',
  `take_num` int(11) DEFAULT '0' COMMENT '承接数',
  `accepted_num` int(11) DEFAULT '0' COMMENT '接受数目',
  `vip_start_time` datetime DEFAULT NULL COMMENT 'vip开始时间',
  `vip_end_time` datetime DEFAULT NULL COMMENT 'vip结束时间',
  `pay_zfb` varchar(100) DEFAULT NULL COMMENT '支付宝',
  `pay_cft` varchar(100) DEFAULT NULL COMMENT '财付通',
  `pay_bank` text COMMENT '银行',
  `score` int(11) DEFAULT '0' COMMENT '积分',
  `buyer_credit` int(11) DEFAULT '0' COMMENT '买家信誉',
  `buyer_good_num` int(11) DEFAULT '0' COMMENT '买家好评数',
  `buyer_level` text COMMENT '买家等级',
  `buyer_total_num` int(11) DEFAULT '0' COMMENT '买家出售总数',
  `seller_credit` int(11) DEFAULT '0' COMMENT '卖家信誉',
  `seller_good_num` int(11) DEFAULT '0' COMMENT '卖家好评数',
  `seller_level` int(11) DEFAULT '0' COMMENT '卖家等级',
  `seller_total_num` int(11) DEFAULT '0' COMMENT '卖家出售总数',
  `studio_id` int(11) DEFAULT NULL COMMENT '工作室编号',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `client_status` int(11) DEFAULT '0' COMMENT '客户端状态',
  `recommend` tinyint(1) DEFAULT NULL COMMENT '推荐',
  `unions` tinyint(1) DEFAULT NULL COMMENT '联盟',
  `union_assoc` tinyint(1) DEFAULT NULL COMMENT '联盟对象',
  `union_rid` tinyint(1) DEFAULT NULL COMMENT '联盟RID',
  `union_user` tinyint(1) DEFAULT NULL COMMENT '联盟用户',
  `is_mail` int(2) DEFAULT '0' COMMENT '0表示没有发送，1表示已经发送',
  `is_perfect` int(2) DEFAULT '0' COMMENT '是否完善 1：完善',
  `email` varchar(40) DEFAULT NULL COMMENT '邮箱',
  `email_sect` int(2) DEFAULT NULL COMMENT '邮箱是否公开(1:不公开；2：公开)',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `mobile_sect` int(2) DEFAULT NULL COMMENT '手机是否公开(1:不公开；2：公开)',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ',
  `qq_sect` int(2) DEFAULT NULL COMMENT 'qq是否公开(1:不公开；2：公开)',
  `msn` varchar(20) DEFAULT NULL COMMENT '微信',
  `msn_sect` int(2) DEFAULT NULL COMMENT '微信是否公开(1:不公开；2：公开)',
  `phone` varchar(20) DEFAULT NULL COMMENT '固定电话',
  `phone_sect` int(2) DEFAULT NULL COMMENT '固定电话是否公开(1:不公开；2：公开)',
  `province` varchar(4) DEFAULT NULL COMMENT '所在省',
  `city` varchar(4) DEFAULT NULL COMMENT '所在城市',
  `area` varchar(4) DEFAULT NULL COMMENT '所在区域',
  `creator` bigint(10) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` bigint(10) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改日期',
  `valid` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_s_user_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=123672 DEFAULT CHARSET=utf8 COMMENT='用户信息表';
