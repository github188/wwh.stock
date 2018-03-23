CREATE TABLE `attachment` (
  `attachment_id` int(11) NOT NULL auto_increment,
  `post_id` int(11) NOT NULL,
  `name` varchar(100) default NULL COMMENT '名称',
  `description` varchar(255) default NULL COMMENT '描述',
  `file_path` varchar(100) default NULL COMMENT '路径',
  `file_name` varchar(100) default NULL COMMENT '文件名称',
  `file_size` int(11) default NULL COMMENT '大小',
  `is_pictrue` tinyint(1) default '0' COMMENT '是否是图片',
  `create_time` datetime default NULL COMMENT '创建时间',
  PRIMARY KEY  (`attachment_id`),
  KEY `FK_attachment_post` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

INSERT INTO `attachment` (`attachment_id`,`post_id`,`name`,`description`,`file_path`,`file_name`,`file_size`,`is_pictrue`,`create_time`) VALUES (19,72,'mv1.jpg',NULL,'/u/cms/www/201501/29112330edq7.jpg','mv1.jpg',22429,1,'2015-01-29 11:23:30');
INSERT INTO `attachment` (`attachment_id`,`post_id`,`name`,`description`,`file_path`,`file_name`,`file_size`,`is_pictrue`,`create_time`) VALUES (20,73,'DSC04065.JPG',NULL,'/u/cms/www/201501/291145406tyx.jpg','DSC04065.JPG',2007771,1,'2015-01-29 11:45:40');
INSERT INTO `attachment` (`attachment_id`,`post_id`,`name`,`description`,`file_path`,`file_name`,`file_size`,`is_pictrue`,`create_time`) VALUES (21,79,'1217需求.docx',NULL,'/u/cms/www/201502/03150559cj89.docx','1217需求.docx',15148,0,'2015-02-03 15:05:59');
INSERT INTO `attachment` (`attachment_id`,`post_id`,`name`,`description`,`file_path`,`file_name`,`file_size`,`is_pictrue`,`create_time`) VALUES (26,79,'Z-24建站方案展示文件.pdf',NULL,'/u/cms/www/201502/031600086k8e.pdf','Z-24建站方案展示文件.pdf',1593888,0,'2015-02-03 16:00:08');
INSERT INTO `attachment` (`attachment_id`,`post_id`,`name`,`description`,`file_path`,`file_name`,`file_size`,`is_pictrue`,`create_time`) VALUES (29,79,'mv2.jpg',NULL,'/u/cms/www/201502/03160327vews.jpg','mv2.jpg',24879,1,'2015-02-03 16:03:27');
INSERT INTO `attachment` (`attachment_id`,`post_id`,`name`,`description`,`file_path`,`file_name`,`file_size`,`is_pictrue`,`create_time`) VALUES (30,79,'CSJMRO交易平台需求文档.docx',NULL,'/u/cms/www/201502/03160720ds5h.docx','CSJMRO交易平台需求文档.docx',22761,0,'2015-02-03 16:07:20');
INSERT INTO `attachment` (`attachment_id`,`post_id`,`name`,`description`,`file_path`,`file_name`,`file_size`,`is_pictrue`,`create_time`) VALUES (31,79,'ehs商城.docx',NULL,'/u/cms/www/201502/03163305ml9y.docx','ehs商城.docx',13951,0,'2015-02-03 16:33:05');
INSERT INTO `attachment` (`attachment_id`,`post_id`,`name`,`description`,`file_path`,`file_name`,`file_size`,`is_pictrue`,`create_time`) VALUES (32,79,'整理后需求报表.zip',NULL,'/u/cms/www/201502/03163305gymr.zip','整理后需求报表.zip',755650,0,'2015-02-03 16:33:05');
CREATE TABLE `bbs_accredit` (
  `accredit_id` int(11) NOT NULL auto_increment,
  `corporation_name` varchar(100) NOT NULL default '',
  `telphone` varchar(20) NOT NULL default '',
  `website_name` varchar(50) NOT NULL default '',
  `realm_name` varchar(50) NOT NULL default '',
  `buy_time` varchar(255) NOT NULL default '',
  `stop_time` varchar(255) NOT NULL default '',
  `user_id` int(11) NOT NULL default '0',
  PRIMARY KEY  (`accredit_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授权表';

CREATE TABLE `bbs_category` (
  `CATEGORY_ID` int(11) NOT NULL auto_increment,
  `SITE_ID` int(11) NOT NULL,
  `PATH` varchar(20) NOT NULL COMMENT '访问路径',
  `TITLE` varchar(100) NOT NULL COMMENT '标题',
  `PRIORITY` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `FORUM_COLS` int(11) NOT NULL default '1' COMMENT '板块列数',
  `moderators` varchar(100) default NULL,
  PRIMARY KEY  (`CATEGORY_ID`),
  KEY `FK_BBS_CTG_SITE` (`SITE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='论坛分区';

INSERT INTO `bbs_category` (`CATEGORY_ID`,`SITE_ID`,`PATH`,`TITLE`,`PRIORITY`,`FORUM_COLS`,`moderators`) VALUES (1,1,'jl','技术交流',0,1,'admin');
CREATE TABLE `bbs_category_user` (
  `CATEGORY_ID` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`CATEGORY_ID`,`user_id`),
  KEY `FK_BBS_CATEGORY_TO_USER` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分区版主';

CREATE TABLE `bbs_common_magic` (
  `magicid` smallint(6) NOT NULL auto_increment COMMENT '道具id',
  `available` tinyint(1) NOT NULL default '0' COMMENT '是否可用',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `identifier` varchar(40) NOT NULL COMMENT '唯一标识',
  `description` varchar(255) NOT NULL COMMENT '描述',
  `displayorder` tinyint(3) NOT NULL default '0' COMMENT '顺序',
  `credit` tinyint(1) NOT NULL default '0' COMMENT '使用的积分',
  `price` mediumint(8) unsigned NOT NULL default '0' COMMENT '价格',
  `num` smallint(6) unsigned NOT NULL default '0' COMMENT '数量',
  `salevolume` smallint(6) unsigned NOT NULL default '0' COMMENT '销售量',
  `supplytype` tinyint(1) NOT NULL default '0' COMMENT '自动补货类型',
  `supplynum` smallint(6) unsigned NOT NULL default '0' COMMENT '自动补货数量',
  `useperoid` tinyint(1) NOT NULL default '0' COMMENT '使用周期',
  `usenum` smallint(6) unsigned NOT NULL default '0' COMMENT '周期使用数量',
  `weight` tinyint(3) unsigned NOT NULL default '1' COMMENT '重量',
  `useevent` tinyint(1) NOT NULL default '0' COMMENT '0:只在特定环境使用 1:可以在道具中心使用',
  PRIMARY KEY  (`magicid`),
  UNIQUE KEY `identifier` (`identifier`),
  KEY `displayorder` (`available`,`displayorder`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='道具数据表';

INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (1,1,'喧嚣卡','open','可以将主题开启，可以回复',0,1,10,0,0,2,0,2,0,10,0);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (2,1,'悔悟卡','repent','可以删除自己的帖子',0,2,10,2,0,1,0,1,0,10,0);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (3,1,'照妖镜','namepost','可以查看一次匿名用户的真实身份。',0,1,10,1,0,1,0,1,0,10,0);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (4,1,'金钱卡','money','可以随机获得一些金币',0,2,10,44,0,1,0,1,0,10,1);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (5,1,'千斤顶','jack','可以将主题顶起一段时间，重复使用可延长帖子被顶起的时间',0,1,10,0,0,0,0,0,0,10,0);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (6,1,'窥视卡','showip','可以查看指定用户的 IP',0,1,10,1,0,1,0,1,0,10,1);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (7,1,'抢沙发','sofa','可以抢夺指定主题的沙发',0,1,10,0,0,0,0,0,0,10,0);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (8,1,'置顶卡','stick','可以将主题置顶',0,1,10,0,0,0,0,0,0,10,0);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (9,1,'变色卡','highlight','可以将帖子或日志的标题高亮，变更颜色',0,1,10,2,0,0,0,0,0,10,0);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (10,1,'雷达卡','checkonline','查看某个用户是否在线',0,1,10,1,0,1,0,1,0,10,1);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (11,1,'沉默卡','close','可以将主题关闭，禁止回复',0,1,10,2,100,1,0,1,2,10,0);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (12,1,'提升卡','bump','可以提升某个主题',0,1,10,0,0,1,0,1,0,10,0);
INSERT INTO `bbs_common_magic` (`magicid`,`available`,`name`,`identifier`,`description`,`displayorder`,`credit`,`price`,`num`,`salevolume`,`supplytype`,`supplynum`,`useperoid`,`usenum`,`weight`,`useevent`) VALUES (13,1,'匿名卡','anonymouspost','在指定的地方，让自己的名字显示为匿名。',0,1,10,0,0,0,0,0,0,10,0);
CREATE TABLE `bbs_config` (
  `CONFIG_ID` bigint(20) NOT NULL,
  `DEF_AVATAR` varchar(100) NOT NULL default '' COMMENT '默认会员头像',
  `AVATAR_WIDTH` int(11) NOT NULL default '160' COMMENT '头像最大宽度',
  `AVATAR_HEIGHT` int(11) NOT NULL default '160' COMMENT '头像最大高度',
  `TOPIC_COUNT_PER_PAGE` int(11) NOT NULL default '20' COMMENT '每页主题数',
  `POST_COUNT_PER_PAGE` int(11) NOT NULL default '10' COMMENT '每页帖子数',
  `KEYWORDS` varchar(255) NOT NULL default '' COMMENT '首页关键字',
  `DESCRIPTION` varchar(255) NOT NULL default '' COMMENT '首页描述',
  `REGISTER_STATUS` smallint(6) NOT NULL default '1' COMMENT '注册状态（0：关闭，1：开放，2：邀请）',
  `REGISTER_GROUP_ID` int(11) NOT NULL default '1' COMMENT '注册会员组',
  `REGISTER_RULE` longtext COMMENT '注册协议',
  `CACHE_POST_TODAY` int(11) NOT NULL default '0' COMMENT '今日贴数',
  `CACHE_POST_YESTERDAY` int(11) NOT NULL default '0' COMMENT '昨日帖数',
  `CACHE_POST_MAX` int(11) NOT NULL default '0' COMMENT '最高帖数',
  `CACHE_POST_MAX_DATE` date NOT NULL COMMENT '最高帖数日',
  `CACHE_TOPIC_TOTAL` int(11) NOT NULL default '0' COMMENT '总主题',
  `CACHE_POST_TOTAL` int(11) NOT NULL default '0' COMMENT '总帖数',
  `CACHE_USER_TOTAL` int(11) NOT NULL default '0' COMMENT '总会员',
  `last_user_id` int(11) default NULL COMMENT '最新会员',
  `site_id` int(11) NOT NULL,
  `DEFAULT_GROUP_ID` bigint(20) NOT NULL default '1' COMMENT '默认会员组',
  `TOPIC_HOT_COUNT` int(11) NOT NULL default '10' COMMENT '热帖回复数量',
  `AUTO_REGISTER` tinyint(1) default '1' COMMENT '是否自动注册',
  `EMAIL_VALIDATE` tinyint(1) default '0' COMMENT '开启邮箱验证',
  PRIMARY KEY  (`CONFIG_ID`),
  KEY `FK_BBS_CONFIG_SITE` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛配置';

INSERT INTO `bbs_config` (`CONFIG_ID`,`DEF_AVATAR`,`AVATAR_WIDTH`,`AVATAR_HEIGHT`,`TOPIC_COUNT_PER_PAGE`,`POST_COUNT_PER_PAGE`,`KEYWORDS`,`DESCRIPTION`,`REGISTER_STATUS`,`REGISTER_GROUP_ID`,`REGISTER_RULE`,`CACHE_POST_TODAY`,`CACHE_POST_YESTERDAY`,`CACHE_POST_MAX`,`CACHE_POST_MAX_DATE`,`CACHE_TOPIC_TOTAL`,`CACHE_POST_TOTAL`,`CACHE_USER_TOTAL`,`last_user_id`,`site_id`,`DEFAULT_GROUP_ID`,`TOPIC_HOT_COUNT`,`AUTO_REGISTER`,`EMAIL_VALIDATE`) VALUES (1,'1',160,160,20,10,'JEEBBS','JEEBBS',1,1,'',10,0,10,'2015-03-19',1,10,3,5,1,1,0,1,0);
CREATE TABLE `bbs_config_attr` (
  `config_id` bigint(20) NOT NULL default '0',
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_attr_config` (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BBS配置属性表';

INSERT INTO `bbs_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'keepMinute','15');
CREATE TABLE `bbs_credit_exchange` (
  `eid` int(11) NOT NULL default '0',
  `expoint` int(11) NOT NULL default '0' COMMENT '兑换比率积分基数',
  `exprestige` int(11) NOT NULL default '0' COMMENT '兑换比率威望基数',
  `pointoutavailable` tinyint(1) NOT NULL default '0' COMMENT '积分是否可以兑出',
  `pointinavailable` tinyint(1) NOT NULL default '0' COMMENT '积分是否允许兑入',
  `prestigeoutavailable` tinyint(3) NOT NULL default '0' COMMENT '威望是否允许兑出',
  `prestigeinavailable` tinyint(1) NOT NULL default '0' COMMENT '威望是否允许兑入',
  `exchangetax` float(2,1) NOT NULL default '0.0' COMMENT '兑换交易税',
  `mini_balance` int(11) NOT NULL default '0' COMMENT '兑换最低余额',
  PRIMARY KEY  (`eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分交易规则';

INSERT INTO `bbs_credit_exchange` (`eid`,`expoint`,`exprestige`,`pointoutavailable`,`pointinavailable`,`prestigeoutavailable`,`prestigeinavailable`,`exchangetax`,`mini_balance`) VALUES (1,1,10,1,1,1,1,0.2,0);
CREATE TABLE `bbs_credit_rule` (
  `rid` int(11) NOT NULL auto_increment COMMENT '规则ID',
  `rulename` varchar(20) NOT NULL default '' COMMENT '规则名称',
  `action` varchar(50) NOT NULL default '' COMMENT '规则action唯一KEY',
  `cycletype` tinyint(1) NOT NULL default '0' COMMENT '奖励周期0:一次;1:每天;2:整点;3:间隔分钟;4:不限;',
  `cycletime` int(10) NOT NULL default '0' COMMENT '间隔时间',
  `rewardnum` tinyint(2) NOT NULL default '1' COMMENT '奖励次数',
  `extcredits1` int(10) NOT NULL default '0' COMMENT '扩展1',
  `extcredits2` int(10) NOT NULL default '0' COMMENT '扩展2',
  `extcredits3` int(10) NOT NULL default '0' COMMENT '扩展3',
  `extcredits4` int(10) NOT NULL default '0' COMMENT '扩展4',
  `ext1name` varchar(20) default NULL COMMENT '扩展1别名',
  `ext2name` varchar(20) default NULL COMMENT '扩展2别名',
  `ext3name` varchar(20) default NULL COMMENT '扩展3别名',
  `ext4name` varchar(20) default NULL COMMENT '扩展4别名',
  `ext1avai` tinyint(1) NOT NULL default '0' COMMENT '是否启用\n(0:不启用 1:启用但不显示 2:启用并显示)',
  `ext2avai` tinyint(1) NOT NULL default '0' COMMENT '是否启用\n(0:不启用 1:启用但不显示 2:启用并显示)',
  `ext3avai` tinyint(1) NOT NULL default '0' COMMENT '是否启用\n(0:不启用 1:启用但不显示 2:启用并显示)',
  `ext4avai` tinyint(1) NOT NULL default '0' COMMENT '是否启用\n(0:不启用 1:启用但不显示 2:启用并显示)',
  `ext1exchangeout` tinyint(1) default '0' COMMENT '积分兑出',
  `ext2exchangeout` tinyint(1) default '0' COMMENT '积分兑出',
  `ext3exchangeout` tinyint(1) default '0' COMMENT '积分兑出',
  `ext4exchangeout` tinyint(1) default '0' COMMENT '积分兑出',
  `ext1exchangein` tinyint(1) default '0' COMMENT '积分兑入',
  `ext2exchangein` tinyint(1) default '0' COMMENT '积分兑入',
  `ext3exchangein` tinyint(1) default '0' COMMENT '积分兑入',
  `ext4exchangein` tinyint(1) default '0' COMMENT '积分兑入',
  `credittax` tinyint(2) default NULL COMMENT '积分交易税',
  `minibalance` int(10) default NULL COMMENT '兑换最低余额',
  PRIMARY KEY  (`rid`),
  UNIQUE KEY `action` (`action`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='积分规则表';

CREATE TABLE `bbs_forum` (
  `FORUM_ID` int(11) NOT NULL auto_increment,
  `CATEGORY_ID` int(11) NOT NULL COMMENT '分区ID',
  `SITE_ID` int(11) NOT NULL COMMENT '站点ID',
  `POST_ID` int(11) default NULL COMMENT '最后回帖',
  `replyer_id` int(11) default NULL COMMENT '最后回帖会员',
  `PATH` varchar(20) NOT NULL COMMENT '访问路径',
  `TITLE` varchar(150) NOT NULL COMMENT '标题',
  `DESCRIPTION` varchar(255) default NULL COMMENT '描述',
  `KEYWORDS` varchar(255) default NULL COMMENT 'meta-keywords',
  `FORUM_RULE` varchar(255) default NULL COMMENT '版规',
  `PRIORITY` int(11) NOT NULL default '10' COMMENT '排列顺序',
  `TOPIC_TOTAL` int(11) NOT NULL default '0' COMMENT '主题总数',
  `POST_TOTAL` int(11) NOT NULL default '0' COMMENT '帖子总数',
  `POST_TODAY` int(11) NOT NULL default '0' COMMENT '今日新帖',
  `OUTER_URL` varchar(255) default NULL COMMENT '外部链接',
  `POINT_TOPIC` int(11) NOT NULL default '0' COMMENT '发贴加分',
  `POINT_REPLY` int(11) NOT NULL default '0' COMMENT '回帖加分',
  `POINT_PRIME` int(11) NOT NULL default '0' COMMENT '精华加分',
  `LAST_TIME` datetime default NULL COMMENT '最后发贴时间',
  `TOPIC_LOCK_LIMIT` int(11) NOT NULL default '0' COMMENT '锁定主题（天）',
  `moderators` varchar(100) default NULL COMMENT '版主',
  `group_views` varchar(100) default NULL COMMENT '访问会员组',
  `group_topics` varchar(100) default NULL COMMENT '发帖会员组',
  `group_replies` varchar(100) default NULL COMMENT '回复会员组',
  `POINT_AVAILABLE` tinyint(1) default NULL COMMENT '积分是否启用',
  `PRESTIGE_AVAILABLE` tinyint(1) default NULL COMMENT '威望是否启用',
  `PRESTIGE_TOPIC` int(11) default '0' COMMENT '发帖加威望',
  `PRESTIGE_REPLY` int(11) default '0' COMMENT '回帖加威望',
  `PRESTIGE_PRIME1` int(11) default '0' COMMENT '精华1加威望',
  `PRESTIGE_PRIME2` int(11) default '0' COMMENT '精华2加威望',
  `PRESTIGE_PRIME3` int(11) default '0' COMMENT '精华3加威望',
  `PRESTIGE_PRIME0` int(11) default '0' COMMENT '解除精华扣除威望',
  PRIMARY KEY  (`FORUM_ID`),
  KEY `FK_BBS_FORUM_CTG` (`CATEGORY_ID`),
  KEY `FK_BBS_FORUM_USER` (`replyer_id`),
  KEY `FK_BBS_FORUM_POST` (`POST_ID`),
  KEY `FK_BBS_FORUM_WEBSITE` (`SITE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='论坛板块';

INSERT INTO `bbs_forum` (`FORUM_ID`,`CATEGORY_ID`,`SITE_ID`,`POST_ID`,`replyer_id`,`PATH`,`TITLE`,`DESCRIPTION`,`KEYWORDS`,`FORUM_RULE`,`PRIORITY`,`TOPIC_TOTAL`,`POST_TOTAL`,`POST_TODAY`,`OUTER_URL`,`POINT_TOPIC`,`POINT_REPLY`,`POINT_PRIME`,`LAST_TIME`,`TOPIC_LOCK_LIMIT`,`moderators`,`group_views`,`group_topics`,`group_replies`,`POINT_AVAILABLE`,`PRESTIGE_AVAILABLE`,`PRESTIGE_TOPIC`,`PRESTIGE_REPLY`,`PRESTIGE_PRIME1`,`PRESTIGE_PRIME2`,`PRESTIGE_PRIME3`,`PRESTIGE_PRIME0`) VALUES (1,1,1,186,5,'jsjl','技术交流','','技术交流','',10,50,122,1,'',5,0,100,'2015-03-29 10:52:40',0,'jiasudu',',14,1,2,3,4,5,6,7,8,9,10,11,12,',',14,1,2,3,4,5,6,7,8,9,10,11,12,',',14,1,2,3,4,5,6,7,8,9,10,11,12,',1,1,0,0,0,0,0,0);
INSERT INTO `bbs_forum` (`FORUM_ID`,`CATEGORY_ID`,`SITE_ID`,`POST_ID`,`replyer_id`,`PATH`,`TITLE`,`DESCRIPTION`,`KEYWORDS`,`FORUM_RULE`,`PRIORITY`,`TOPIC_TOTAL`,`POST_TOTAL`,`POST_TODAY`,`OUTER_URL`,`POINT_TOPIC`,`POINT_REPLY`,`POINT_PRIME`,`LAST_TIME`,`TOPIC_LOCK_LIMIT`,`moderators`,`group_views`,`group_topics`,`group_replies`,`POINT_AVAILABLE`,`PRESTIGE_AVAILABLE`,`PRESTIGE_TOPIC`,`PRESTIGE_REPLY`,`PRESTIGE_PRIME1`,`PRESTIGE_PRIME2`,`PRESTIGE_PRIME3`,`PRESTIGE_PRIME0`) VALUES (2,1,1,153,27,'yanjiu','java研究','java研究','java研究','java研究',10,5,31,0,'',5,0,100,'2015-03-20 10:30:57',0,'jiasudu',',14,1,2,3,4,5,6,7,8,9,10,11,12,',',14,1,2,3,4,5,6,7,8,9,10,11,12,',',14,1,2,3,4,5,6,7,8,9,10,11,12,',1,1,1,0,1,2,3,0);
CREATE TABLE `bbs_forum_group_reply` (
  `FORUM_ID` int(11) NOT NULL,
  `GROUP_ID` int(11) NOT NULL,
  PRIMARY KEY  (`FORUM_ID`,`GROUP_ID`),
  KEY `FK_BBS_FORUM_GROUP_REPLY` (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='回复权限';

CREATE TABLE `bbs_forum_group_topic` (
  `FORUM_ID` int(11) NOT NULL,
  `GROUP_ID` int(11) NOT NULL,
  PRIMARY KEY  (`FORUM_ID`,`GROUP_ID`),
  KEY `FK_BBS_FORUM_GROUP_TOPIC` (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发贴权限';

CREATE TABLE `bbs_forum_group_view` (
  `GROUP_ID` int(11) NOT NULL,
  `FORUM_ID` int(11) NOT NULL,
  PRIMARY KEY  (`GROUP_ID`,`FORUM_ID`),
  KEY `FK_BBS_GROUP_FORUM_VIEW` (`FORUM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='浏览权限';

CREATE TABLE `bbs_forum_user` (
  `FORUM_ID` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY  (`FORUM_ID`,`user_id`),
  KEY `FK_BBS_FORUM_TO_USER` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='版块版主';

CREATE TABLE `bbs_grade` (
  `GRADE_ID` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL,
  `POST_ID` int(11) NOT NULL,
  `SCORE` int(11) default NULL,
  `REASON` varchar(100) default NULL,
  `GRADE_TIME` datetime default NULL,
  PRIMARY KEY  (`GRADE_ID`),
  KEY `FK_MEMBER_GRADE` (`user_id`),
  KEY `FK_POST_GRADE` (`POST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `bbs_group_type` (
  `GROUP_ID` int(11) NOT NULL default '0',
  `TYPE_ID` int(11) NOT NULL default '0',
  PRIMARY KEY  (`TYPE_ID`,`GROUP_ID`),
  KEY `FK_BBS_GROUP_TYPE_GROUP` (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员组投票分类关联表';

CREATE TABLE `bbs_limit` (
  `limit_id` int(11) NOT NULL auto_increment,
  `ip` varchar(50) default '' COMMENT '限制ip',
  `user_id` int(11) default NULL COMMENT '限制用户ID',
  PRIMARY KEY  (`limit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='限制发帖回帖';

CREATE TABLE `bbs_login_log` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default NULL COMMENT '登录用户',
  `login_time` datetime default NULL COMMENT '登录时间',
  `logout_time` datetime default NULL COMMENT '退出时间',
  `ip` varchar(255) character set gbk default NULL COMMENT '登录ip',
  PRIMARY KEY  (`id`),
  KEY `fk_bbs_login_log_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=866 DEFAULT CHARSET=utf8 COMMENT='登录日志';

CREATE TABLE `bbs_magic_config` (
  `id` int(11) NOT NULL default '1' COMMENT '主键id',
  `magic_switch` tinyint(1) NOT NULL default '0' COMMENT '是否开启道具',
  `magic_discount` int(3) default NULL COMMENT '道具回收折扣',
  `magic_sofa_lines` varchar(255) character set gbk default NULL COMMENT '抢沙发台词',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='道具配置表';

INSERT INTO `bbs_magic_config` (`id`,`magic_switch`,`magic_discount`,`magic_sofa_lines`) VALUES (1,1,80,'O(∩_∩)O哈哈~，沙发是我的啦O(∩_∩)O');
CREATE TABLE `bbs_magic_log` (
  `log_id` int(11) NOT NULL auto_increment,
  `magic_id` smallint(5) NOT NULL default '0',
  `user_id` int(11) NOT NULL default '0',
  `log_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `operator` tinyint(2) default NULL COMMENT '操作(0出售道具1使用道具 2丢弃道具 3购买道具,4赠送)',
  `price` int(11) default NULL COMMENT '购买价格',
  `num` int(11) default NULL COMMENT '购买数量或者赠送数量',
  `targetuid` int(11) default '0' COMMENT '赠送目标用户',
  PRIMARY KEY  (`log_id`),
  KEY `fk_magic_log_magic` (`magic_id`),
  KEY `fk_magic_log_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='道具记录表';

INSERT INTO `bbs_magic_log` (`log_id`,`magic_id`,`user_id`,`log_time`,`operator`,`price`,`num`,`targetuid`) VALUES (1,6,5,'2015-02-04 14:58:21',3,10,1,NULL);
INSERT INTO `bbs_magic_log` (`log_id`,`magic_id`,`user_id`,`log_time`,`operator`,`price`,`num`,`targetuid`) VALUES (2,10,5,'2015-02-04 14:58:27',3,10,1,NULL);
CREATE TABLE `bbs_magic_usergroup` (
  `magicid` smallint(6) NOT NULL default '0',
  `groupid` int(11) NOT NULL default '0' COMMENT '有权限使用该道具的用户组id',
  PRIMARY KEY  (`magicid`,`groupid`),
  KEY `fk_bbs_magic_usergroup_group` (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='道具组权限';

CREATE TABLE `bbs_magic_usergroup_to` (
  `magicid` smallint(6) NOT NULL default '0',
  `groupid` int(11) NOT NULL default '0' COMMENT '允许被使用的用户组id',
  PRIMARY KEY  (`magicid`,`groupid`),
  KEY `fk_bbs_magic_usergroup_group` (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='允许被使用的用户组';

CREATE TABLE `bbs_member_magic` (
  `id` int(11) NOT NULL auto_increment,
  `uid` int(11) NOT NULL default '0' COMMENT '用户id',
  `magicid` smallint(6) NOT NULL default '0' COMMENT '道具id',
  `num` int(11) NOT NULL default '0' COMMENT '道具数量',
  PRIMARY KEY  (`id`),
  KEY `fk_bbs_member_magic_user` (`uid`),
  KEY `fk_bbs_member_magic_magic` (`magicid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户道具表';

INSERT INTO `bbs_member_magic` (`id`,`uid`,`magicid`,`num`) VALUES (1,5,6,1);
INSERT INTO `bbs_member_magic` (`id`,`uid`,`magicid`,`num`) VALUES (2,5,10,1);
CREATE TABLE `bbs_operation` (
  `OPERATOR_ID` int(11) NOT NULL auto_increment,
  `SITE_ID` int(11) NOT NULL,
  `operater_id` int(11) NOT NULL COMMENT '操作会员',
  `REF_TYPE` char(4) NOT NULL COMMENT '引用类型',
  `REF_ID` int(11) NOT NULL default '0' COMMENT '引用ID',
  `OPT_NAME` varchar(100) NOT NULL COMMENT '操作名称',
  `OPT_REASON` varchar(255) default NULL COMMENT '操作原因',
  `OPT_TIME` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY  (`OPERATOR_ID`),
  KEY `FK_BBS_OPEATTION` (`SITE_ID`),
  KEY `FK_BBS_OPERATION_USER` (`operater_id`)
) ENGINE=InnoDB AUTO_INCREMENT=330 DEFAULT CHARSET=utf8 COMMENT='主题、帖子操作记录';
CREATE TABLE `bbs_permission` (
  `GROUP_ID` int(11) NOT NULL,
  `PERM_KEY` varchar(20) NOT NULL COMMENT '权限key',
  `PERM_VALUE` varchar(255) default NULL COMMENT '权限value',
  KEY `FK_BBS_PERMISSION_GROUP` (`GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛权限';

INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'allow_attach','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'allow_reply','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'allow_topic','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'attach_max_size','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'attach_per_day','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'attach_type','');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'edit_limit_minute','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'favorite_count','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'msg_count','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'msg_interval','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'msg_per_day','100');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'post_interval','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (1,'post_per_day','100');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'allow_attach','false');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'allow_reply','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'allow_topic','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'attach_max_size','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'attach_per_day','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'attach_type','');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'edit_limit_minute','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'favorite_count','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'msg_count','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'msg_interval','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'msg_per_day','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'post_interval','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (2,'post_per_day','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'allow_attach','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'allow_reply','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'allow_topic','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'attach_max_size','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'attach_per_day','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'attach_type','');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'edit_limit_minute','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'favorite_count','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'member_prohibit','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'msg_count','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'msg_interval','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'msg_per_day','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'post_interval','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'post_limit','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'post_per_day','0');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'super_moderator','false');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'topic_delete','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'topic_edit','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'topic_manage','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'topic_shield','true');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'topic_top','3');
INSERT INTO `bbs_permission` (`GROUP_ID`,`PERM_KEY`,`PERM_VALUE`) VALUES (12,'view_ip','true');
CREATE TABLE `bbs_post` (
  `POST_ID` int(11) NOT NULL auto_increment,
  `TOPIC_ID` int(11) NOT NULL COMMENT '主题',
  `SITE_ID` int(11) NOT NULL COMMENT '站点',
  `CONFIG_ID` int(11) NOT NULL,
  `EDITER_ID` int(11) default NULL COMMENT '编辑器会员',
  `CREATER_ID` int(11) NOT NULL COMMENT '发贴会员',
  `CREATE_TIME` datetime NOT NULL COMMENT '发贴时间',
  `POSTER_IP` varchar(20) NOT NULL default '' COMMENT '发贴IP',
  `EDIT_TIME` datetime default NULL COMMENT '编辑时间',
  `EDITER_IP` varchar(20) default '' COMMENT '编辑者IP',
  `EDIT_COUNT` int(11) NOT NULL default '0' COMMENT '编辑次数',
  `INDEX_COUNT` int(11) NOT NULL default '1' COMMENT '第几楼',
  `STATUS` smallint(6) NOT NULL default '0' COMMENT '状态',
  `IS_AFFIX` tinyint(1) NOT NULL default '0' COMMENT '是否上传附件',
  `IS_HIDDEN` tinyint(1) default '0' COMMENT '是否有隐藏内容',
  `TYPE_ID` int(11) NOT NULL COMMENT '帖子分类id',
  `ANONYMOUS` tinyint(1) default NULL COMMENT '是否匿名',
  PRIMARY KEY  (`POST_ID`),
  KEY `FK_BBS_POST_CREATER` (`CREATER_ID`),
  KEY `FK_BBS_POST_EDITOR` (`EDITER_ID`),
  KEY `FK_BBS_POST_TOPIC` (`TOPIC_ID`),
  KEY `FK_BBS_POST_WEBSITE` (`SITE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=utf8 COMMENT='论坛帖子';

INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (70,42,1,1,NULL,5,'2015-01-21 10:02:39','0:0:0:0:0:0:0:1',NULL,NULL,0,1,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (71,43,1,1,NULL,5,'2015-01-29 10:25:55','0:0:0:0:0:0:0:1',NULL,NULL,0,1,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (72,44,1,1,NULL,5,'2015-01-29 11:23:30','0:0:0:0:0:0:0:1',NULL,NULL,0,1,0,1,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (73,44,1,1,NULL,5,'2015-01-29 11:45:40','0:0:0:0:0:0:0:1',NULL,NULL,0,2,0,1,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (74,45,1,1,NULL,5,'2015-02-02 14:41:43','0:0:0:0:0:0:0:1',NULL,NULL,0,1,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (75,46,1,1,NULL,5,'2015-02-02 14:42:23','0:0:0:0:0:0:0:1',NULL,NULL,0,1,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (76,47,1,1,NULL,5,'2015-02-02 14:43:04','0:0:0:0:0:0:0:1',NULL,NULL,0,1,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (77,48,1,1,5,5,'2015-02-02 15:45:09','0:0:0:0:0:0:0:1','2015-02-02 16:12:12','0:0:0:0:0:0:0:1',34,1,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (78,49,1,1,5,5,'2015-02-02 16:15:16','0:0:0:0:0:0:0:1','2015-02-03 14:55:30','0:0:0:0:0:0:0:1',61,1,0,0,1,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (79,50,1,1,5,5,'2015-02-03 14:57:09','0:0:0:0:0:0:0:1','2015-02-04 11:23:21','0:0:0:0:0:0:0:1',21,1,0,1,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (80,50,1,1,NULL,5,'2015-02-04 11:23:27','0:0:0:0:0:0:0:1',NULL,NULL,0,2,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (81,49,1,1,NULL,5,'2015-02-25 15:33:26','0:0:0:0:0:0:0:1',NULL,NULL,0,2,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (82,50,1,1,NULL,5,'2015-02-25 15:33:36','0:0:0:0:0:0:0:1',NULL,NULL,0,3,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (83,48,1,1,NULL,5,'2015-02-25 15:33:43','0:0:0:0:0:0:0:1',NULL,NULL,0,2,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (96,47,1,1,NULL,5,'2015-03-07 10:47:55','127.0.0.1',NULL,NULL,0,2,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (105,54,1,1,NULL,5,'2015-03-13 08:55:30','0:0:0:0:0:0:0:1',NULL,NULL,0,1,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (106,54,1,1,NULL,5,'2015-03-13 08:55:39','0:0:0:0:0:0:0:1',NULL,NULL,0,2,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (108,54,1,1,NULL,5,'2015-03-18 13:49:21','127.0.0.1',NULL,NULL,0,3,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (109,54,1,1,NULL,5,'2015-03-18 13:49:29','127.0.0.1',NULL,NULL,0,4,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (110,54,1,1,NULL,5,'2015-03-18 13:49:37','127.0.0.1',NULL,NULL,0,5,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (112,54,1,1,NULL,26,'2015-03-18 15:31:23','127.0.0.1',NULL,NULL,0,6,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (114,54,1,1,NULL,5,'2015-03-19 09:51:42','127.0.0.1',NULL,NULL,0,7,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (115,54,1,1,NULL,5,'2015-03-19 09:55:37','127.0.0.1',NULL,NULL,0,8,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (116,54,1,1,NULL,5,'2015-03-19 10:05:32','127.0.0.1',NULL,NULL,0,9,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (117,54,1,1,NULL,5,'2015-03-19 10:07:48','127.0.0.1',NULL,NULL,0,10,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (118,54,1,1,NULL,5,'2015-03-19 10:08:03','127.0.0.1',NULL,NULL,0,11,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (119,54,1,1,NULL,5,'2015-03-19 10:08:44','127.0.0.1',NULL,NULL,0,12,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (120,54,1,1,NULL,5,'2015-03-19 10:15:27','127.0.0.1',NULL,NULL,0,13,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (121,54,1,1,NULL,5,'2015-03-19 10:15:44','127.0.0.1',NULL,NULL,0,14,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (122,54,1,1,NULL,5,'2015-03-19 10:42:32','127.0.0.1',NULL,NULL,0,15,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (123,54,1,1,NULL,5,'2015-03-19 11:07:11','127.0.0.1',NULL,NULL,0,16,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (124,54,1,1,NULL,5,'2015-03-19 11:23:49','127.0.0.1',NULL,NULL,0,17,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (125,54,1,1,NULL,5,'2015-03-19 11:52:15','127.0.0.1',NULL,NULL,0,18,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (126,54,1,1,NULL,5,'2015-03-19 11:56:23','127.0.0.1',NULL,NULL,0,19,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (127,54,1,1,NULL,5,'2015-03-19 13:20:56','127.0.0.1',NULL,NULL,0,20,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (128,49,1,1,NULL,5,'2015-03-19 13:21:27','127.0.0.1',NULL,NULL,0,3,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (129,60,1,1,NULL,27,'2015-03-19 13:22:34','127.0.0.1',NULL,NULL,0,1,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (130,54,1,1,NULL,27,'2015-03-19 13:23:00','127.0.0.1',NULL,NULL,0,21,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (131,54,1,1,NULL,27,'2015-03-19 13:23:00','127.0.0.1',NULL,NULL,0,22,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (132,50,1,1,NULL,27,'2015-03-19 13:23:17','127.0.0.1',NULL,NULL,0,4,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (133,50,1,1,NULL,27,'2015-03-19 13:23:19','127.0.0.1',NULL,NULL,0,5,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (134,54,1,1,NULL,27,'2015-03-19 13:24:35','127.0.0.1',NULL,NULL,0,23,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (135,60,1,1,NULL,27,'2015-03-19 13:26:00','127.0.0.1',NULL,NULL,0,2,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (136,60,1,1,NULL,27,'2015-03-19 13:26:07','127.0.0.1',NULL,NULL,0,3,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (137,60,1,1,NULL,5,'2015-03-19 15:11:47','127.0.0.1',NULL,NULL,0,4,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (138,60,1,1,NULL,5,'2015-03-19 15:11:47','127.0.0.1',NULL,NULL,0,5,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (139,60,1,1,NULL,5,'2015-03-19 15:11:56','127.0.0.1',NULL,NULL,0,6,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (140,60,1,1,NULL,5,'2015-03-19 15:11:56','127.0.0.1',NULL,NULL,0,7,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (141,60,1,1,NULL,5,'2015-03-19 15:12:11','127.0.0.1',NULL,NULL,0,8,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (142,60,1,1,NULL,5,'2015-03-19 15:12:18','127.0.0.1',NULL,NULL,0,9,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (143,54,1,1,NULL,5,'2015-03-19 17:23:13','127.0.0.1',NULL,NULL,0,24,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (144,60,1,1,NULL,5,'2015-03-19 17:23:51','127.0.0.1',NULL,NULL,0,10,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (145,60,1,1,NULL,5,'2015-03-19 17:26:47','127.0.0.1',NULL,NULL,0,11,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (146,54,1,1,NULL,5,'2015-03-19 17:27:00','127.0.0.1',NULL,NULL,0,25,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (147,60,1,1,NULL,5,'2015-03-20 08:52:07','127.0.0.1',NULL,NULL,0,12,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (148,60,1,1,NULL,5,'2015-03-20 08:52:07','127.0.0.1',NULL,NULL,0,13,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (149,60,1,1,NULL,5,'2015-03-20 10:25:44','127.0.0.1',NULL,NULL,0,14,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (150,60,1,1,NULL,5,'2015-03-20 10:25:46','127.0.0.1',NULL,NULL,0,15,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (151,60,1,1,NULL,5,'2015-03-20 10:25:47','127.0.0.1',NULL,NULL,0,16,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (152,60,1,1,NULL,5,'2015-03-20 10:25:59','127.0.0.1',NULL,NULL,0,17,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (153,54,1,1,NULL,27,'2015-03-20 10:30:57','127.0.0.1',NULL,NULL,0,26,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (154,60,1,1,NULL,27,'2015-03-20 10:51:57','127.0.0.1',NULL,NULL,0,18,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (155,60,1,1,NULL,27,'2015-03-20 10:52:14','127.0.0.1',NULL,NULL,0,19,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (156,61,1,1,NULL,27,'2015-03-20 14:44:04','127.0.0.1',NULL,NULL,0,1,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (157,61,1,1,NULL,5,'2015-03-20 14:44:38','127.0.0.1',NULL,NULL,0,2,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (158,60,1,1,NULL,5,'2015-03-20 14:55:31','127.0.0.1',NULL,NULL,0,20,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (159,60,1,1,NULL,5,'2015-03-20 14:55:33','127.0.0.1',NULL,NULL,0,21,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (160,60,1,1,NULL,5,'2015-03-20 14:55:33','127.0.0.1',NULL,NULL,0,22,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (161,60,1,1,NULL,5,'2015-03-20 14:55:34','127.0.0.1',NULL,NULL,0,23,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (162,60,1,1,NULL,5,'2015-03-20 14:55:34','127.0.0.1',NULL,NULL,0,24,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (163,60,1,1,NULL,5,'2015-03-20 14:55:34','127.0.0.1',NULL,NULL,0,25,0,0,0,2,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (164,61,1,1,NULL,5,'2015-03-20 15:15:07','127.0.0.1',NULL,NULL,0,3,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (165,61,1,1,NULL,5,'2015-03-20 15:15:18','127.0.0.1',NULL,NULL,0,4,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (166,61,1,1,NULL,5,'2015-03-20 15:15:53','127.0.0.1',NULL,NULL,0,5,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (167,61,1,1,NULL,27,'2015-03-20 15:18:10','127.0.0.1',NULL,NULL,0,6,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (168,62,1,1,NULL,5,'2015-03-20 16:08:58','127.0.0.1',NULL,NULL,0,1,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (169,62,1,1,NULL,27,'2015-03-20 16:09:33','127.0.0.1',NULL,NULL,0,2,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (170,62,1,1,NULL,5,'2015-03-20 16:10:07','127.0.0.1',NULL,NULL,0,3,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (171,62,1,1,NULL,5,'2015-03-20 16:12:08','127.0.0.1',NULL,NULL,0,4,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (172,62,1,1,NULL,5,'2015-03-20 16:13:55','127.0.0.1',NULL,NULL,0,5,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (173,61,1,1,NULL,5,'2015-03-20 16:15:11','127.0.0.1',NULL,NULL,0,7,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (174,62,1,1,NULL,5,'2015-03-20 16:23:27','127.0.0.1',NULL,NULL,0,6,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (175,62,1,1,NULL,5,'2015-03-20 16:33:08','127.0.0.1',NULL,NULL,0,7,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (176,62,1,1,NULL,5,'2015-03-20 16:33:08','127.0.0.1',NULL,NULL,0,8,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (177,62,1,1,NULL,5,'2015-03-20 16:37:26','127.0.0.1',NULL,NULL,0,9,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (178,63,1,1,NULL,28,'2015-03-20 16:45:55','192.168.0.167',NULL,NULL,0,1,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (179,63,1,1,NULL,5,'2015-03-20 16:41:52','127.0.0.1',NULL,NULL,0,2,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (180,63,1,1,NULL,27,'2015-03-20 16:42:56','127.0.0.1',NULL,NULL,0,3,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (181,63,1,1,NULL,5,'2015-03-20 16:43:51','127.0.0.1',NULL,NULL,0,4,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (182,64,1,1,NULL,5,'2015-03-20 16:50:48','127.0.0.1',NULL,NULL,0,1,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (183,64,1,1,NULL,27,'2015-03-20 16:51:27','127.0.0.1',NULL,NULL,0,2,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (184,64,1,1,NULL,28,'2015-03-20 16:51:55','192.168.0.167',NULL,NULL,0,3,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (185,64,1,1,NULL,5,'2015-03-20 16:52:39','127.0.0.1',NULL,NULL,0,4,0,0,0,1,0);
INSERT INTO `bbs_post` (`POST_ID`,`TOPIC_ID`,`SITE_ID`,`CONFIG_ID`,`EDITER_ID`,`CREATER_ID`,`CREATE_TIME`,`POSTER_IP`,`EDIT_TIME`,`EDITER_IP`,`EDIT_COUNT`,`INDEX_COUNT`,`STATUS`,`IS_AFFIX`,`IS_HIDDEN`,`TYPE_ID`,`ANONYMOUS`) VALUES (186,63,1,1,NULL,5,'2015-03-29 10:52:40','0:0:0:0:0:0:0:1',NULL,NULL,0,5,0,0,0,1,0);
CREATE TABLE `bbs_post_text` (
  `POST_ID` bigint(20) NOT NULL,
  `POST_TITLE` varchar(100) default NULL COMMENT '帖子标题',
  `POST_CONTENT` longtext COMMENT '帖子内容',
  PRIMARY KEY  (`POST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛帖子内容';

INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (70,'1111111111','1');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (71,'1111','1111111111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (72,'111111111','1[attachment]19[/attachment]');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (73,'222222222222222222222','[attachment]20[/attachment]');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (74,'啊啊啊啊',' 啊啊啊啊');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (75,'啊啊啊啊 啊啊啊啊',' 啊啊啊啊 啊啊啊啊 啊啊啊啊 啊啊啊啊');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (76,'啊啊啊啊 啊啊啊啊 啊啊啊啊 啊啊啊啊',' 啊啊啊啊 啊啊啊啊 啊啊啊啊 啊啊啊啊');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (77,'长春岳阳街','1月31日，长春岳阳街与至善路交会往东，马路边有一处自来水管漏水，水量非常大，已经快流到树勋街口，路边有多辆车都被冻上了，不少私家车的车主用铁锨、撬棍营救“冻车”\r\n[font=黑体]1月31日，长春岳阳街与至善路交会往东，马路边有一处自来水管漏水，水量非常大，已经快流到树勋街口，路边有多辆车都被冻上了，不少私家车的车主用铁锨、撬棍营救“冻车”[/font]');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (78,'长春岳阳街与至善路交会往东','1月31日，长春岳阳街与至善路交会往东，马路边有一处自来水管漏水，水量非常大，已经快流到树勋街口，路边有多辆车都被冻上了，不少私家车的车主用铁锨、撬棍营救“冻车\"\r\n[float=right][hide]1月31日，长春岳阳街与至善路交[/hide][/float]\r\n[ollist]\r\n11111\r\n[/ollist]\r\n[ollist]\r\n2222222\r\n[/ollist]\r\n222222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (79,'121212121','[font=微软雅黑]1月31日，长春岳阳街与至善路交会往东，[quote]马路边有一处自来[/quote]水管漏水，水量非常大，已经快流到树勋街口，路边有多辆车都被冻上了，不少私家车的车主用铁锨、撬棍营救“冻车\" [/font]\r\n\r\n[attachment]21[/attachment]\r\n[attachment]26[/attachment]\r\n[attachment]30[/attachment]\r\n[attachment]31[/attachment]\r\n[attachment]32[/attachment]\r\n[attachment]29[/attachment]\r\n1111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (80,'','222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (81,'','1111111111111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (82,'','2222222222222222222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (83,'','222222222222222222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (96,'','dddddddddddddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (105,'12','22222222222222222222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (106,'','qweq');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (108,'','test');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (109,'','ddddddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (110,'','dddddddddddddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (112,'','ddddddddddddddddddddddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (114,NULL,'测试');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (115,NULL,'中国');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (116,NULL,'dddddddddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (117,NULL,'ddddddddddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (118,NULL,'qqqqqqq');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (119,NULL,'ddddddddddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (120,NULL,'测试测试');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (121,NULL,'测试测试');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (122,NULL,'男足 ');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (123,NULL,'测测没磊磊磊在在磊在在大大 大大');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (124,NULL,'测试工  磊');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (125,NULL,'工工工工工');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (126,NULL,'11111111111111111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (127,NULL,'sssssssssssssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (128,NULL,'ssssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (129,'ddddddddddddddddddddddddddddddddd','dddddddddddddddddddddddddddddddddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (130,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=5\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>admin</a>ssssssssssssssssssssssssssssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (131,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=5\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>admin</a>ssssssssssssssssssssssssssssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (132,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=5\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>admin</a>sssssssssssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (133,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=5\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>admin</a>sssssssssssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (134,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=5\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>admin</a>111111111111111111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (135,NULL,'qqqqqqqqqqqqqqqqqqqqqqqqqqqq');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (136,NULL,'qqqqqqqqqqqqqqqqqqqqqqqqqqqqq');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (137,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>人人人人人人人');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (138,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>人人人人人人人');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (139,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>大大大大磊');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (140,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>大大大大磊');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (141,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>要');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (142,NULL,'大大大大');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (143,NULL,'11111111111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (144,NULL,'1111111111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (145,NULL,'11111111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (146,NULL,'22222222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (147,NULL,'ssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (148,NULL,'ssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (149,NULL,'发现');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (150,NULL,'发现');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (151,NULL,'发现');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (152,NULL,'发现');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (153,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=5\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>admin</a>dddddddddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (154,NULL,'1111111111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (155,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=5\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>admin</a>wwwwwwwwwwwwwwwwwwwww');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (156,'dddddddddd','ddddddddddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (157,NULL,'111111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (158,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>ssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (159,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>ssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (160,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>ssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (161,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>ssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (162,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>ssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (163,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>ssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (164,NULL,'ssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (165,NULL,'222222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (166,NULL,'22222222222222222222222222222222222222222222222222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (167,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=5\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>admin</a>sssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (168,'ddddd','ddddd');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (169,NULL,'111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (170,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>2222222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (171,NULL,'1111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (172,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>22222222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (173,NULL,'2222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (174,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>wwwwwwwwwwwwww');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (175,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>11111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (176,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>11111111111');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (177,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>ssssssssssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (178,'这只是测试同志们好','这只是测试同志们好');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (179,NULL,'ssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (180,NULL,'木木木木木木');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (181,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>sssssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (182,'test','test');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (183,NULL,'木木木木木木要');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (184,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>ssssssssssssssssss');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (185,NULL,' 回复 <a href=\'../app/mytopic.jspx?userId=27\' rel=\'external\' style=\'color:#01546f;text-decoration:none;\'>test12</a>22222222222');
INSERT INTO `bbs_post_text` (`POST_ID`,`POST_TITLE`,`POST_CONTENT`) VALUES (186,'saf','asdf');
CREATE TABLE `bbs_post_type` (
  `type_id` int(11) NOT NULL auto_increment,
  `type_name` varchar(255) character set gbk default NULL COMMENT '帖子分类名称',
  `priority` int(11) default NULL COMMENT '排序',
  `site_id` int(11) default NULL COMMENT '站点id',
  `forum_id` int(11) NOT NULL default '0' COMMENT '板块',
  `parent_id` int(11) default NULL COMMENT '父类id',
  PRIMARY KEY  (`type_id`),
  KEY `fk_bbs_post_type_site` (`site_id`),
  KEY `fk_bbs_post_type_parent` (`parent_id`),
  KEY `fk_bbs_type_forum` (`forum_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

INSERT INTO `bbs_post_type` (`type_id`,`type_name`,`priority`,`site_id`,`forum_id`,`parent_id`) VALUES (1,'测试',1,1,1,NULL);
INSERT INTO `bbs_post_type` (`type_id`,`type_name`,`priority`,`site_id`,`forum_id`,`parent_id`) VALUES (2,'嘻嘻',2,1,1,1);
INSERT INTO `bbs_post_type` (`type_id`,`type_name`,`priority`,`site_id`,`forum_id`,`parent_id`) VALUES (3,'哈哈',3,1,1,1);
INSERT INTO `bbs_post_type` (`type_id`,`type_name`,`priority`,`site_id`,`forum_id`,`parent_id`) VALUES (4,'呜呜',4,1,1,1);
CREATE TABLE `bbs_private_msg` (
  `PRIVMSG_ID` bigint(20) NOT NULL,
  `TO_USER` bigint(20) NOT NULL COMMENT '收信人',
  `FROM_USER` bigint(20) NOT NULL COMMENT '发信人',
  `MSG_TYPE` smallint(6) NOT NULL default '1' COMMENT '类型（2：已发，1：已阅，0：未阅）',
  `MSG_SUBJECT` varchar(255) default NULL COMMENT '主题',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `MSG_IP` varchar(20) NOT NULL default '' COMMENT 'IP地址',
  PRIMARY KEY  (`PRIVMSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人短消息';

CREATE TABLE `bbs_private_msg_text` (
  `PRIVMSG_ID` bigint(20) NOT NULL,
  `MSG_TEXT` longtext COMMENT '个人信息内容',
  PRIMARY KEY  (`PRIVMSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人消息内容';

CREATE TABLE `bbs_report` (
  `id` int(11) NOT NULL auto_increment,
  `report_url` varchar(255) character set gbk NOT NULL default '' COMMENT '举报地址',
  `process_user` int(11) default NULL COMMENT '处理人',
  `process_time` datetime default NULL COMMENT '处理时间',
  `process_result` varchar(255) character set gbk default NULL COMMENT '处理结果',
  `status` tinyint(1) default NULL COMMENT '处理状态0未处理。1已经处理',
  `report_time` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '举报时间',
  PRIMARY KEY  (`id`),
  KEY `fk_bbs_report_process_user` (`process_user`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='举报记录';

INSERT INTO `bbs_report` (`id`,`report_url`,`process_user`,`process_time`,`process_result`,`status`,`report_time`) VALUES (1,'http：//localhost：8080/jsjl/33.jhtml＃pidnull',NULL,NULL,NULL,0,'2014-11-20 15:29:36');
CREATE TABLE `bbs_report_ext` (
  `id` int(11) NOT NULL auto_increment COMMENT '举报id',
  `report_id` int(11) NOT NULL default '0' COMMENT 'reportid',
  `report_user` int(11) NOT NULL default '0' COMMENT '举报人',
  `report_time` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '举报时间',
  `report_reason` varchar(255) character set gbk default NULL COMMENT '举报理由',
  PRIMARY KEY  (`id`),
  KEY `fk_bbs_report_ext_report_user` (`report_user`),
  KEY `fk_bbs_report_ext_report` (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='举报扩展';

INSERT INTO `bbs_report_ext` (`id`,`report_id`,`report_user`,`report_time`,`report_reason`) VALUES (1,1,5,'2014-11-20 15:29:36','<script＞alert(1)</script＞');
CREATE TABLE `bbs_session` (
  `sid` bigint(20) NOT NULL auto_increment,
  `session_id` varchar(100) NOT NULL default '' COMMENT '会话sessionID',
  `user_id` int(11) default NULL COMMENT '会员用户ID',
  `ip` varchar(50) NOT NULL default '' COMMENT 'IP地址',
  `last_activetime` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '最后活动时间',
  `first_activetime` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '开始活动时间',
  PRIMARY KEY  (`sid`),
  KEY `fk_session_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1225 DEFAULT CHARSET=utf8 COMMENT='论坛会话';

INSERT INTO `bbs_session` (`sid`,`session_id`,`user_id`,`ip`,`last_activetime`,`first_activetime`) VALUES (1224,'2F33B9128F5C8A3AAACF3AD7B7E8F5FE',5,'0:0:0:0:0:0:0:1','2015-03-26 18:55:32','2015-03-26 18:55:32');
INSERT INTO `bbs_session` (`sid`,`session_id`,`user_id`,`ip`,`last_activetime`,`first_activetime`) VALUES (1225,'1615B8882307F3ACE86C5831D1C4BEB0',5,'0:0:0:0:0:0:0:1','2015-03-29 10:52:20','2015-03-29 10:52:20');
INSERT INTO `bbs_session` (`sid`,`session_id`,`user_id`,`ip`,`last_activetime`,`first_activetime`) VALUES (1226,'B96F6E3F293D895F8F797CCA7F0B4E91',5,'0:0:0:0:0:0:0:1','2015-03-29 10:52:57','2015-03-29 10:52:57');
INSERT INTO `bbs_session` (`sid`,`session_id`,`user_id`,`ip`,`last_activetime`,`first_activetime`) VALUES (1227,'E8B1281117DE170925869AA1F3167CC4',NULL,'0:0:0:0:0:0:0:1','2015-03-29 10:57:53','2015-03-29 10:57:53');
INSERT INTO `bbs_session` (`sid`,`session_id`,`user_id`,`ip`,`last_activetime`,`first_activetime`) VALUES (1228,'40BA8D579237E10037C8B248EB6DA581',NULL,'0:0:0:0:0:0:0:1','2015-03-29 10:57:56','2015-03-29 10:57:56');
CREATE TABLE `bbs_third_account` (
  `account_id` bigint(20) NOT NULL auto_increment,
  `account_key` varchar(255) NOT NULL default '' COMMENT '第三方账号key',
  `username` varchar(100) NOT NULL default '0' COMMENT '关联用户名',
  `source` varchar(10) NOT NULL default '' COMMENT '第三方账号平台(QQ、新浪微博等)',
  PRIMARY KEY  (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方登录平台账号';

CREATE TABLE `bbs_topic` (
  `TOPIC_ID` int(11) NOT NULL auto_increment,
  `FORUM_ID` int(11) NOT NULL COMMENT '板块',
  `LAST_POST_ID` int(11) default NULL COMMENT '最后帖',
  `FIRST_POST_ID` int(11) default NULL COMMENT '主题帖',
  `SITE_ID` int(11) NOT NULL COMMENT '站点',
  `CREATER_ID` int(11) NOT NULL COMMENT '发帖会员',
  `REPLYER_ID` int(11) NOT NULL COMMENT '最后回帖会员',
  `TITLE` varchar(100) NOT NULL COMMENT '标题',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `LAST_TIME` datetime NOT NULL COMMENT '最后回帖时间',
  `SORT_TIME` datetime NOT NULL COMMENT '用于排序',
  `VIEW_COUNT` bigint(20) NOT NULL default '0' COMMENT '浏览次数',
  `REPLY_COUNT` int(11) NOT NULL default '0' COMMENT '恢复次数',
  `TOP_LEVEL` smallint(6) NOT NULL default '0' COMMENT '固定级别',
  `PRIME_LEVEL` smallint(6) NOT NULL default '0' COMMENT '精华级别',
  `STATUS` smallint(6) NOT NULL default '0' COMMENT '状态',
  `OUTER_URL` varchar(255) default NULL COMMENT '外部链接',
  `STYLE_BOLD` tinyint(1) NOT NULL default '0' COMMENT '粗体',
  `STYLE_ITALIC` tinyint(1) NOT NULL default '0' COMMENT '斜体',
  `STYLE_COLOR` char(6) default NULL COMMENT '颜色',
  `STYLE_TIME` datetime default NULL COMMENT '样式有效时间',
  `IS_AFFIX` tinyint(1) NOT NULL default '0' COMMENT '是否上传附件',
  `HAVA_REPLY` longtext COMMENT '回复列表',
  `moderator_reply` tinyint(1) default '0' COMMENT '版主是否回复',
  `TYPE_ID` int(11) NOT NULL COMMENT '主题分类id',
  `ALLAY_REPLY` tinyint(1) default NULL COMMENT '主题是否允许回复',
  `HAS_SOFAED` tinyint(1) default NULL COMMENT '主题是否已经被抢走沙发',
  `CATEGORY` tinyint(1) default NULL COMMENT '帖子类型(0:普通帖;1:投票贴)',
  `TOTAL_COUNT` int(11) default NULL COMMENT '总票数',
  `views_day` int(11) NOT NULL default '0' COMMENT '日访问量',
  `views_week` int(11) NOT NULL default '0' COMMENT '周访问量',
  `views_month` int(11) NOT NULL default '0' COMMENT '月访问量',
  `replycount_day` int(11) NOT NULL default '0' COMMENT '日回复量',
  PRIMARY KEY  (`TOPIC_ID`),
  KEY `BBS_SORT_TIME` (`SORT_TIME`),
  KEY `FK_BBS_FIRST_POST` (`FIRST_POST_ID`),
  KEY `FK_BBS_LAST_POST` (`LAST_POST_ID`),
  KEY `FK_BBS_TOPIC_FORUM` (`FORUM_ID`),
  KEY `FK_BBS_TOPIC_USER_CREATE` (`CREATER_ID`),
  KEY `FK_BBS_TOPIC_USER_LAST` (`REPLYER_ID`),
  KEY `FK_BBS_TOPIC_SITE` (`SITE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='论坛主题';

INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (42,1,NULL,70,1,5,5,'1111111111','2015-01-21 10:02:39','2015-01-21 10:02:39','2015-01-21 10:02:39',2,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0,2,1,0,0,NULL,0,0,0,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (43,2,NULL,71,1,5,5,'1111','2015-01-29 10:25:55','2015-01-29 10:25:55','2015-01-29 10:25:55',4,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0,1,1,0,0,NULL,1,1,1,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (44,1,73,72,1,5,5,'111111111','2015-01-29 11:23:30','2015-01-29 11:45:40','2015-01-29 11:45:40',44,1,0,0,0,NULL,0,0,NULL,NULL,1,',5,',0,2,1,0,0,NULL,1,1,1,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (45,2,NULL,74,1,5,5,'啊啊啊啊','2015-02-02 14:41:43','2015-02-02 14:41:43','2015-02-02 14:41:43',1,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0,1,1,0,0,NULL,0,0,0,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (46,2,NULL,75,1,5,5,'啊啊啊啊 啊啊啊啊','2015-02-02 14:42:23','2015-02-02 14:42:23','2015-02-02 14:42:23',10,0,0,0,0,NULL,0,0,NULL,NULL,0,',',0,1,1,0,0,NULL,9,9,9,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (47,2,96,76,1,5,5,'啊啊啊啊 啊啊啊啊 啊啊啊啊 啊啊啊啊','2015-02-02 14:43:04','2015-03-07 10:47:55','2015-03-07 10:47:55',21,1,0,0,0,NULL,0,0,NULL,NULL,0,',5,',0,1,1,0,0,NULL,15,12,15,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (48,1,83,77,1,5,5,'长春岳阳街','2015-02-02 15:45:09','2015-02-25 15:33:43','2015-02-25 15:33:43',32,1,0,0,0,NULL,0,0,NULL,NULL,0,',5,',0,2,1,0,0,NULL,0,0,0,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (49,1,128,78,1,5,5,'长春岳阳街与至善路交会往东','2015-02-02 16:15:16','2015-03-19 13:21:27','2015-03-19 13:21:27',149,2,0,0,0,NULL,0,0,NULL,NULL,0,',5,',0,2,1,0,0,NULL,33,29,33,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (50,1,133,79,1,5,27,'121212121','2015-02-03 14:57:09','2015-03-19 13:23:19','2015-03-19 13:23:19',36,4,0,0,0,NULL,0,0,NULL,NULL,0,',5,27,',0,2,1,0,0,NULL,0,1,2,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (54,2,153,105,1,5,27,'12','2015-03-13 08:55:30','2015-03-20 10:30:57','2015-03-20 10:30:57',2,25,0,0,0,NULL,0,0,NULL,NULL,0,',5,26,27,',0,1,1,0,0,NULL,0,2,2,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (60,1,163,129,1,27,5,'ddddddddddddddddddddddddddddddddd','2015-03-19 13:22:34','2015-03-20 14:55:34','2015-03-20 14:55:34',6,24,0,0,0,NULL,0,0,NULL,NULL,0,',27,5,',0,2,1,0,0,NULL,6,6,6,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (61,1,173,156,1,27,5,'dddddddddd','2015-03-20 14:44:04','2015-03-20 16:15:11','2015-03-20 16:15:11',1,6,0,0,0,NULL,0,0,NULL,NULL,1,',5,27,',0,1,1,0,0,NULL,1,1,1,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (62,1,177,168,1,5,5,'ddddd','2015-03-20 16:08:58','2015-03-20 16:37:26','2015-03-20 16:37:26',22,8,0,0,0,NULL,0,0,NULL,NULL,1,',27,5,',0,1,1,0,0,NULL,21,21,21,0);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (63,1,186,178,1,28,5,'这只是测试同志们好','2015-03-20 16:45:55','2015-03-29 10:52:40','2015-03-29 10:52:40',27,4,3,0,0,NULL,0,0,NULL,NULL,1,',5,27,',0,1,1,0,0,NULL,22,22,22,1);
INSERT INTO `bbs_topic` (`TOPIC_ID`,`FORUM_ID`,`LAST_POST_ID`,`FIRST_POST_ID`,`SITE_ID`,`CREATER_ID`,`REPLYER_ID`,`TITLE`,`CREATE_TIME`,`LAST_TIME`,`SORT_TIME`,`VIEW_COUNT`,`REPLY_COUNT`,`TOP_LEVEL`,`PRIME_LEVEL`,`STATUS`,`OUTER_URL`,`STYLE_BOLD`,`STYLE_ITALIC`,`STYLE_COLOR`,`STYLE_TIME`,`IS_AFFIX`,`HAVA_REPLY`,`moderator_reply`,`TYPE_ID`,`ALLAY_REPLY`,`HAS_SOFAED`,`CATEGORY`,`TOTAL_COUNT`,`views_day`,`views_week`,`views_month`,`replycount_day`) VALUES (64,1,185,182,1,5,5,'test','2015-03-20 16:50:48','2015-03-20 16:52:39','2015-03-20 16:52:39',15,3,0,0,0,NULL,0,0,NULL,NULL,1,',27,28,5,',0,1,1,0,0,NULL,13,13,13,0);
CREATE TABLE `bbs_topic_text` (
  `topic_id` int(11) NOT NULL,
  `title` varchar(100) default NULL COMMENT '主题标题',
  PRIMARY KEY  (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛主题内容';

INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (42,'1111111111');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (43,'1111');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (44,'111111111');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (45,'啊啊啊啊');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (46,'啊啊啊啊 啊啊啊啊');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (47,'啊啊啊啊 啊啊啊啊 啊啊啊啊 啊啊啊啊');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (48,'长春岳阳街');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (49,'长春岳阳街与至善路交会往东');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (50,'121212121');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (54,'12');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (60,'ddddddddddddddddddddddddddddddddd');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (61,'dddddddddd');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (62,'ddddd');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (63,'这只是测试同志们好');
INSERT INTO `bbs_topic_text` (`topic_id`,`title`) VALUES (64,'test');
CREATE TABLE `bbs_user_active_level` (
  `level_id` int(11) NOT NULL auto_increment,
  `level_name` varchar(255) NOT NULL default '' COMMENT '等级名称',
  `required_hour` bigint(20) NOT NULL default '0' COMMENT '等级需要时间(小时)',
  `level_img` varchar(255) default NULL COMMENT '等级头像',
  PRIMARY KEY  (`level_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户活跃等级';

INSERT INTO `bbs_user_active_level` (`level_id`,`level_name`,`required_hour`,`level_img`) VALUES (1,'1',0,'/r/cms/www/blue/bbs_forum/img/Lv_1.png');
INSERT INTO `bbs_user_active_level` (`level_id`,`level_name`,`required_hour`,`level_img`) VALUES (2,'2',20,'/r/cms/www/blue/bbs_forum/img/Lv_2.png');
INSERT INTO `bbs_user_active_level` (`level_id`,`level_name`,`required_hour`,`level_img`) VALUES (3,'3',50,'/r/cms/www/blue/bbs_forum/img/Lv_3.png');
CREATE TABLE `bbs_user_group` (
  `GROUP_ID` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `NAME` varchar(20) NOT NULL COMMENT '头衔',
  `GROUP_TYPE` smallint(6) NOT NULL default '0' COMMENT '组类别',
  `GROUP_IMG` varchar(100) default NULL COMMENT '图片',
  `GROUP_POINT` int(11) NOT NULL default '0' COMMENT '升级积分',
  `IS_DEFAULT` tinyint(1) NOT NULL default '0' COMMENT '是否默认组',
  `GRADE_NUM` int(11) default '0' COMMENT '评分',
  `magic_packet_size` int(11) default NULL COMMENT '用户组道具包容量',
  PRIMARY KEY  (`GROUP_ID`),
  KEY `FK_BBS_GROUP_SITE` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='论坛会员组';

INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (1,1,'白丁',1,'1',0,1,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (2,1,'童生',1,'2',100,0,0,100);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (3,1,'秀才',1,'3',500,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (4,1,'举人',1,'4',1000,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (5,1,'解元',1,'5',2000,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (6,1,'贡士',1,'6',4000,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (7,1,'会元',1,'7',8000,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (8,1,'进士',1,'8',16000,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (9,1,'探花',1,'9',32000,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (10,1,'榜眼',1,'10',64000,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (11,1,'状元',1,'11',128000,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (12,1,'版主',2,'21',0,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (13,1,'VIP会员',3,'10',0,0,0,0);
INSERT INTO `bbs_user_group` (`GROUP_ID`,`site_id`,`NAME`,`GROUP_TYPE`,`GROUP_IMG`,`GROUP_POINT`,`IS_DEFAULT`,`GRADE_NUM`,`magic_packet_size`) VALUES (14,1,'游客',0,'1',0,0,0,0);
CREATE TABLE `bbs_user_online` (
  `user_id` int(11) NOT NULL auto_increment,
  `online_latest` double(10,2) default NULL COMMENT '最后登录时长',
  `online_day` double(10,2) default NULL COMMENT '今日在线时长',
  `online_week` double(10,2) default NULL COMMENT '本周在线',
  `online_month` double(10,2) default NULL COMMENT '本月在线',
  `online_year` double(10,2) default NULL COMMENT '本年在线',
  `online_total` double(10,2) default NULL COMMENT '总在线时长',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=gbk COMMENT='用户在线时长统计';

INSERT INTO `bbs_user_online` (`user_id`,`online_latest`,`online_day`,`online_week`,`online_month`,`online_year`,`online_total`) VALUES (5,0,0,0,379.15,379.15,379.15);
INSERT INTO `bbs_user_online` (`user_id`,`online_latest`,`online_day`,`online_week`,`online_month`,`online_year`,`online_total`) VALUES (6,0,0,0,0,0,30);
INSERT INTO `bbs_user_online` (`user_id`,`online_latest`,`online_day`,`online_week`,`online_month`,`online_year`,`online_total`) VALUES (8,0,0,0,0.37,0.38,0.42);
INSERT INTO `bbs_user_online` (`user_id`,`online_latest`,`online_day`,`online_week`,`online_month`,`online_year`,`online_total`) VALUES (25,0,0,0,0,0,0);
INSERT INTO `bbs_user_online` (`user_id`,`online_latest`,`online_day`,`online_week`,`online_month`,`online_year`,`online_total`) VALUES (26,0,0,0,0.02,0.02,0.02);
INSERT INTO `bbs_user_online` (`user_id`,`online_latest`,`online_day`,`online_week`,`online_month`,`online_year`,`online_total`) VALUES (27,0,0,0,0,0,0);
INSERT INTO `bbs_user_online` (`user_id`,`online_latest`,`online_day`,`online_week`,`online_month`,`online_year`,`online_total`) VALUES (28,0,0,0,0,0,0);
CREATE TABLE `bbs_vote_item` (
  `item_id` int(11) NOT NULL auto_increment,
  `topic_id` int(11) default NULL,
  `name` varchar(255) default NULL,
  `vote_count` int(11) NOT NULL default '0' COMMENT '票数',
  PRIMARY KEY  (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `bbs_vote_record` (
  `record_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default NULL,
  `topic_id` int(11) default NULL,
  `vote_time` datetime default NULL COMMENT '投票时间',
  PRIMARY KEY  (`record_id`),
  KEY `fk_vote_record_user` (`user_id`),
  KEY `fk_vote_record_topic` (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `jb_friendship` (
  `friendship_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) NOT NULL default '0',
  `friend_id` int(11) NOT NULL default '0',
  `status` tinyint(1) NOT NULL default '0' COMMENT '好友状态(0:申请中;1:接受;2:拒绝)',
  PRIMARY KEY  (`friendship_id`),
  KEY `fk_jb_friendship_friend` (`friend_id`),
  KEY `fk_jb_friendship_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

INSERT INTO `jb_friendship` (`friendship_id`,`user_id`,`friend_id`,`status`) VALUES (1,5,6,1);
INSERT INTO `jb_friendship` (`friendship_id`,`user_id`,`friend_id`,`status`) VALUES (2,6,5,1);
INSERT INTO `jb_friendship` (`friendship_id`,`user_id`,`friend_id`,`status`) VALUES (3,5,8,1);
INSERT INTO `jb_friendship` (`friendship_id`,`user_id`,`friend_id`,`status`) VALUES (4,8,5,1);
INSERT INTO `jb_friendship` (`friendship_id`,`user_id`,`friend_id`,`status`) VALUES (5,5,15,0);
INSERT INTO `jb_friendship` (`friendship_id`,`user_id`,`friend_id`,`status`) VALUES (6,27,5,1);
INSERT INTO `jb_friendship` (`friendship_id`,`user_id`,`friend_id`,`status`) VALUES (7,5,27,1);
CREATE TABLE `jb_message` (
  `msg_id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default '0',
  `sender` int(11) default NULL COMMENT '发送人',
  `receiver` int(11) default '0' COMMENT '接收人',
  `content` longtext character set gbk NOT NULL COMMENT '内容',
  `create_time` datetime default NULL COMMENT '发送时间',
  `is_sys` tinyint(1) NOT NULL default '0' COMMENT '是否为系统消息(0:不是;1:是)',
  `msg_type` int(2) NOT NULL default '1' COMMENT '1消息，2留言,3打招呼',
  `is_read` tinyint(1) default '0' COMMENT '信息状态 0未读 1已读',
  PRIMARY KEY  (`msg_id`),
  KEY `fk_jb_message_user` (`user_id`),
  KEY `fk_jb_message_receiver` (`receiver`),
  KEY `fk_jb_message_sender` (`sender`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `jb_message_reply` (
  `reply_id` int(11) NOT NULL auto_increment,
  `msg_id` int(11) NOT NULL default '0',
  `sender` int(11) default NULL,
  `receiver` int(11) NOT NULL default '0',
  `content` longtext NOT NULL,
  `create_time` datetime default NULL,
  `is_read` tinyint(3) NOT NULL default '0',
  PRIMARY KEY  (`reply_id`),
  KEY `fk_jb_reply_sender` (`sender`),
  KEY `fk_jb_reply_receiver` (`receiver`),
  KEY `fk_jb_reply_msg` (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `jb_user` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '邮箱',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '注册IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '最后登录IP',
  `login_count` int(11) NOT NULL default '0' COMMENT '登录次数',
  `upload_total` bigint(20) NOT NULL default '0' COMMENT '上传总大小',
  `upload_size` int(11) NOT NULL default '0' COMMENT '上传大小',
  `upload_date` date default NULL COMMENT '上传日期',
  `is_admin` tinyint(1) NOT NULL default '0' COMMENT '是否管理员',
  `is_disabled` tinyint(1) NOT NULL default '0' COMMENT '是否禁用',
  `PROHIBIT_POST` smallint(6) NOT NULL default '0' COMMENT '禁言(0:不禁言;1:永久禁言;2:定期禁言)',
  `PROHIBIT_TIME` datetime default NULL COMMENT '解禁时间',
  `GRADE_TODAY` int(11) default '0' COMMENT '今日评分',
  `UPLOAD_TODAY` int(11) default '0' COMMENT '今日上传',
  `POINT` bigint(20) default '0' COMMENT '积分',
  `INTRODUCTION` varchar(255) default NULL COMMENT '个人介绍',
  `SIGNED` varchar(255) default NULL COMMENT '个性签名',
  `AVATAR` varchar(100) default NULL COMMENT '个人头像',
  `AVATAR_TYPE` smallint(6) default '0' COMMENT '头像类型',
  `TOPIC_COUNT` int(11) default '0' COMMENT '主题总数',
  `REPLY_COUNT` int(11) default '0' COMMENT '回复总数',
  `PRIME_COUNT` int(11) default '0' COMMENT '精华总数',
  `POST_TODAY` int(11) default '0' COMMENT '今日发帖',
  `LAST_POST_TIME` datetime default NULL COMMENT '最后回帖时间',
  `PRESTIGE` bigint(20) default '0' COMMENT '威望',
  `magic_packet_size` int(11) default NULL COMMENT '用户道具包现有容量',
  `session_id` varchar(255) default NULL,
  `active_level_id` int(11) default NULL,
  `is_official` int(11) default NULL,
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `ak_username` (`username`),
  KEY `FK_BBS_MEMBER_MEMBERGROUP` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BBS用户表';

INSERT INTO `jb_user` (`user_id`,`group_id`,`username`,`email`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`upload_total`,`upload_size`,`upload_date`,`is_admin`,`is_disabled`,`PROHIBIT_POST`,`PROHIBIT_TIME`,`GRADE_TODAY`,`UPLOAD_TODAY`,`POINT`,`INTRODUCTION`,`SIGNED`,`AVATAR`,`AVATAR_TYPE`,`TOPIC_COUNT`,`REPLY_COUNT`,`PRIME_COUNT`,`POST_TODAY`,`LAST_POST_TIME`,`PRESTIGE`,`magic_packet_size`,`session_id`,`active_level_id`,`is_official`) VALUES (5,2,'admin',NULL,'2011-03-17 12:02:04','127.0.0.1','2015-03-29 10:53:10','0:0:0:0:0:0:0:1',934,0,0,'2011-03-17',1,0,0,NULL,NULL,0,297,NULL,'简介2222','/jeebbs4/user/images/201503/18160147tlda.jpg',0,61,115,0,176,NULL,7,0,'B96F6E3F293D895F8F797CCA7F0B4E91',3,0);
INSERT INTO `jb_user` (`user_id`,`group_id`,`username`,`email`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`upload_total`,`upload_size`,`upload_date`,`is_admin`,`is_disabled`,`PROHIBIT_POST`,`PROHIBIT_TIME`,`GRADE_TODAY`,`UPLOAD_TODAY`,`POINT`,`INTRODUCTION`,`SIGNED`,`AVATAR`,`AVATAR_TYPE`,`TOPIC_COUNT`,`REPLY_COUNT`,`PRIME_COUNT`,`POST_TODAY`,`LAST_POST_TIME`,`PRESTIGE`,`magic_packet_size`,`session_id`,`active_level_id`,`is_official`) VALUES (6,2,'test','test@tt.com','2015-01-28 13:35:51','0:0:0:0:0:0:0:1','2015-02-02 09:33:23','0:0:0:0:0:0:0:1',4,0,0,'2015-01-28',0,1,0,NULL,NULL,0,0,NULL,'珍惜每一个人，每一份情','/jeebbs4/user/images/201503/13103948ew0p.jpg',0,0,0,0,0,NULL,0,0,NULL,2,0);
INSERT INTO `jb_user` (`user_id`,`group_id`,`username`,`email`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`upload_total`,`upload_size`,`upload_date`,`is_admin`,`is_disabled`,`PROHIBIT_POST`,`PROHIBIT_TIME`,`GRADE_TODAY`,`UPLOAD_TODAY`,`POINT`,`INTRODUCTION`,`SIGNED`,`AVATAR`,`AVATAR_TYPE`,`TOPIC_COUNT`,`REPLY_COUNT`,`PRIME_COUNT`,`POST_TODAY`,`LAST_POST_TIME`,`PRESTIGE`,`magic_packet_size`,`session_id`,`active_level_id`,`is_official`) VALUES (8,12,'jiasudu','test@tt.com','2015-02-02 14:37:25','0:0:0:0:0:0:0:1','2015-03-25 18:16:55','0:0:0:0:0:0:0:1',54,0,0,'2015-02-02',0,0,0,NULL,NULL,0,0,NULL,'心累了就休息','/jeebbs4/user/images/201503/13103557msay.jpg',0,0,0,0,0,NULL,0,0,'4B8F27F707470C577DFA262AB79E6048',1,0);
INSERT INTO `jb_user` (`user_id`,`group_id`,`username`,`email`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`upload_total`,`upload_size`,`upload_date`,`is_admin`,`is_disabled`,`PROHIBIT_POST`,`PROHIBIT_TIME`,`GRADE_TODAY`,`UPLOAD_TODAY`,`POINT`,`INTRODUCTION`,`SIGNED`,`AVATAR`,`AVATAR_TYPE`,`TOPIC_COUNT`,`REPLY_COUNT`,`PRIME_COUNT`,`POST_TODAY`,`LAST_POST_TIME`,`PRESTIGE`,`magic_packet_size`,`session_id`,`active_level_id`,`is_official`) VALUES (15,1,'test1','test@tt.com','2015-02-28 10:03:07','127.0.0.1','2015-03-13 17:27:17','192.168.0.6',9,0,0,'2015-02-28',0,0,0,NULL,NULL,0,0,NULL,NULL,'/jeebbs4/user/images/201503/09085239dcda.gif',0,0,0,0,0,NULL,0,0,'D3887426200D768EED75989AD5BD3808',1,0);
INSERT INTO `jb_user` (`user_id`,`group_id`,`username`,`email`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`upload_total`,`upload_size`,`upload_date`,`is_admin`,`is_disabled`,`PROHIBIT_POST`,`PROHIBIT_TIME`,`GRADE_TODAY`,`UPLOAD_TODAY`,`POINT`,`INTRODUCTION`,`SIGNED`,`AVATAR`,`AVATAR_TYPE`,`TOPIC_COUNT`,`REPLY_COUNT`,`PRIME_COUNT`,`POST_TODAY`,`LAST_POST_TIME`,`PRESTIGE`,`magic_packet_size`,`session_id`,`active_level_id`,`is_official`) VALUES (25,12,'jeebbs','','2015-03-17 09:28:24','0:0:0:0:0:0:0:1','2015-03-20 13:30:00','127.0.0.1',7,0,0,'2015-03-17',0,0,0,NULL,NULL,0,0,NULL,NULL,NULL,0,0,0,0,0,NULL,0,0,'486C2BE32EF949CBC66FA846908772DD',1,1);
INSERT INTO `jb_user` (`user_id`,`group_id`,`username`,`email`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`upload_total`,`upload_size`,`upload_date`,`is_admin`,`is_disabled`,`PROHIBIT_POST`,`PROHIBIT_TIME`,`GRADE_TODAY`,`UPLOAD_TODAY`,`POINT`,`INTRODUCTION`,`SIGNED`,`AVATAR`,`AVATAR_TYPE`,`TOPIC_COUNT`,`REPLY_COUNT`,`PRIME_COUNT`,`POST_TODAY`,`LAST_POST_TIME`,`PRESTIGE`,`magic_packet_size`,`session_id`,`active_level_id`,`is_official`) VALUES (26,1,'test11','caoweiwei18@163.com','2015-03-18 15:30:44','127.0.0.1','2015-03-18 15:30:44','127.0.0.1',0,0,0,'2015-03-18',0,0,0,NULL,NULL,0,5,NULL,NULL,NULL,0,1,1,0,2,NULL,0,0,NULL,1,0);
INSERT INTO `jb_user` (`user_id`,`group_id`,`username`,`email`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`upload_total`,`upload_size`,`upload_date`,`is_admin`,`is_disabled`,`PROHIBIT_POST`,`PROHIBIT_TIME`,`GRADE_TODAY`,`UPLOAD_TODAY`,`POINT`,`INTRODUCTION`,`SIGNED`,`AVATAR`,`AVATAR_TYPE`,`TOPIC_COUNT`,`REPLY_COUNT`,`PRIME_COUNT`,`POST_TODAY`,`LAST_POST_TIME`,`PRESTIGE`,`magic_packet_size`,`session_id`,`active_level_id`,`is_official`) VALUES (27,1,'test12','22@22.com','2015-03-19 13:22:17','127.0.0.1','2015-03-20 14:43:22','127.0.0.1',2,0,0,'2015-03-19',0,0,0,NULL,NULL,0,10,NULL,NULL,NULL,0,2,14,0,16,NULL,0,0,'1502A0B90D1AD86BBFA71E06087893C3',1,0);
INSERT INTO `jb_user` (`user_id`,`group_id`,`username`,`email`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`upload_total`,`upload_size`,`upload_date`,`is_admin`,`is_disabled`,`PROHIBIT_POST`,`PROHIBIT_TIME`,`GRADE_TODAY`,`UPLOAD_TODAY`,`POINT`,`INTRODUCTION`,`SIGNED`,`AVATAR`,`AVATAR_TYPE`,`TOPIC_COUNT`,`REPLY_COUNT`,`PRIME_COUNT`,`POST_TODAY`,`LAST_POST_TIME`,`PRESTIGE`,`magic_packet_size`,`session_id`,`active_level_id`,`is_official`) VALUES (28,1,'test13','test@test.com','2015-03-20 16:44:50','192.168.0.167','2015-03-20 16:49:39','192.168.0.167',1,0,0,'2015-03-20',0,0,0,NULL,NULL,0,5,NULL,NULL,NULL,0,1,1,0,2,NULL,0,0,'0C470C572B07A4D7E5C23AE23C81BDA1',1,0);
CREATE TABLE `jb_user_attr` (
  `user_id` int(11) NOT NULL,
  `attr_name` varchar(255) NOT NULL,
  `attr_value` varchar(255) default NULL,
  KEY `pk_jb_attr_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户属性表';

INSERT INTO `jb_user_attr` (`user_id`,`attr_name`,`attr_value`) VALUES (5,'tel','');
INSERT INTO `jb_user_attr` (`user_id`,`attr_name`,`attr_value`) VALUES (15,'tel','');
INSERT INTO `jb_user_attr` (`user_id`,`attr_name`,`attr_value`) VALUES (8,'tel','');
CREATE TABLE `jb_user_ext` (
  `user_id` int(11) NOT NULL,
  `realname` varchar(100) default NULL COMMENT '真实姓名',
  `gender` tinyint(1) default NULL COMMENT '性别',
  `avatar` varchar(100) default NULL COMMENT '用户头像',
  `birthday` datetime default NULL COMMENT '出生日期',
  `intro` varchar(255) default NULL COMMENT '个人介绍',
  `comefrom` varchar(150) default NULL COMMENT '来自',
  `qq` varchar(100) default NULL COMMENT 'QQ',
  `msn` varchar(100) default NULL COMMENT 'MSN',
  `phone` varchar(50) default NULL COMMENT '电话',
  `moble` varchar(50) default NULL COMMENT '手机',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BBS用户扩展信息表';

INSERT INTO `jb_user_ext` (`user_id`,`realname`,`gender`,`avatar`,`birthday`,`intro`,`comefrom`,`qq`,`msn`,`phone`,`moble`) VALUES (5,'abc',NULL,NULL,NULL,'简介2222',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` (`user_id`,`realname`,`gender`,`avatar`,`birthday`,`intro`,`comefrom`,`qq`,`msn`,`phone`,`moble`) VALUES (6,NULL,NULL,NULL,NULL,NULL,',',NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` (`user_id`,`realname`,`gender`,`avatar`,`birthday`,`intro`,`comefrom`,`qq`,`msn`,`phone`,`moble`) VALUES (8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` (`user_id`,`realname`,`gender`,`avatar`,`birthday`,`intro`,`comefrom`,`qq`,`msn`,`phone`,`moble`) VALUES (15,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` (`user_id`,`realname`,`gender`,`avatar`,`birthday`,`intro`,`comefrom`,`qq`,`msn`,`phone`,`moble`) VALUES (25,'JEEBBS团队',NULL,NULL,NULL,NULL,',',NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` (`user_id`,`realname`,`gender`,`avatar`,`birthday`,`intro`,`comefrom`,`qq`,`msn`,`phone`,`moble`) VALUES (26,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` (`user_id`,`realname`,`gender`,`avatar`,`birthday`,`intro`,`comefrom`,`qq`,`msn`,`phone`,`moble`) VALUES (27,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
INSERT INTO `jb_user_ext` (`user_id`,`realname`,`gender`,`avatar`,`birthday`,`intro`,`comefrom`,`qq`,`msn`,`phone`,`moble`) VALUES (28,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
CREATE TABLE `jc_config` (
  `config_id` int(11) NOT NULL,
  `context_path` varchar(20) default '/JeeCms' COMMENT '部署路径',
  `servlet_point` varchar(20) default NULL COMMENT 'Servlet挂载点',
  `port` int(11) default NULL COMMENT '端口',
  `db_file_uri` varchar(50) NOT NULL default '/dbfile.svl?n=' COMMENT '数据库附件访问地址',
  `is_upload_to_db` tinyint(1) NOT NULL default '0' COMMENT '上传附件至数据库',
  `def_img` varchar(255) NOT NULL default '/JeeCms/r/cms/www/default/no_picture.gif' COMMENT '图片不存在时默认图片',
  `login_url` varchar(255) NOT NULL default '/login.jspx' COMMENT '登录地址',
  `process_url` varchar(255) default NULL COMMENT '登录后处理地址',
  `mark_on` tinyint(1) NOT NULL default '1' COMMENT '开启图片水印',
  `mark_width` int(11) NOT NULL default '120' COMMENT '图片最小宽度',
  `mark_height` int(11) NOT NULL default '120' COMMENT '图片最小高度',
  `mark_image` varchar(100) default '/r/cms/www/watermark.png' COMMENT '图片水印',
  `mark_content` varchar(100) NOT NULL default 'www.jeecms.com' COMMENT '文字水印内容',
  `mark_size` int(11) NOT NULL default '20' COMMENT '文字水印大小',
  `mark_color` varchar(10) NOT NULL default '#FF0000' COMMENT '文字水印颜色',
  `mark_alpha` int(11) NOT NULL default '50' COMMENT '水印透明度（0-100）',
  `mark_position` int(11) NOT NULL default '1' COMMENT '水印位置(0-5)',
  `mark_offset_x` int(11) NOT NULL default '0' COMMENT 'x坐标偏移量',
  `mark_offset_y` int(11) NOT NULL default '0' COMMENT 'y坐标偏移量',
  `count_clear_time` date NOT NULL default '0000-00-00' COMMENT '用户活跃清除时间',
  `count_copy_time` datetime NOT NULL COMMENT '计数器拷贝时间',
  `download_code` varchar(32) NOT NULL default 'jeecms' COMMENT '下载防盗链md5混淆码',
  `download_time` int(11) NOT NULL default '12' COMMENT '下载有效时间（小时）',
  `email_host` varchar(50) default NULL COMMENT '邮件发送服务器',
  `email_encoding` varchar(20) default NULL COMMENT '邮件发送编码',
  `email_username` varchar(100) default NULL COMMENT '邮箱用户名',
  `email_password` varchar(100) default NULL COMMENT '邮箱密码',
  `email_personal` varchar(100) default NULL COMMENT '邮箱发件人',
  `allow_suffix` varchar(255) default NULL COMMENT '允许上传文件后缀',
  PRIMARY KEY  (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS配置表';

INSERT INTO `jc_config` (`config_id`,`context_path`,`servlet_point`,`port`,`db_file_uri`,`is_upload_to_db`,`def_img`,`login_url`,`process_url`,`mark_on`,`mark_width`,`mark_height`,`mark_image`,`mark_content`,`mark_size`,`mark_color`,`mark_alpha`,`mark_position`,`mark_offset_x`,`mark_offset_y`,`count_clear_time`,`count_copy_time`,`download_code`,`download_time`,`email_host`,`email_encoding`,`email_username`,`email_password`,`email_personal`,`allow_suffix`) VALUES (1,'',NULL,8080,'/dbfile.svl?n=',0,'/r/cms/www/no_picture.gif','/login.jspx',NULL,1,120,120,'/r/cms/www/watermark.png','www.jeecms.com',20,'#FF0000',50,1,0,0,'2015-03-26','2011-12-26 13:32:26','jeecms',12,NULL,NULL,NULL,NULL,NULL,'7z,aiff,asf,avi,bmp,csv,doc,docx,fla,flv,gif,gz,gzip,jpeg,jpg,mid,mov,mp3,mp4,mpc,mpeg,mpg,ods,odt,pdf,png,ppt,pxd,qt,ram,rar,rm,rmi,rmvb,rtf,sdc,sitd,swf,sxc,sxw,tar,tgz,tif,tiff,vsd,wav,wma,wmv,xls,xlsx,txt,xml,zip');
CREATE TABLE `jc_config_attr` (
  `config_id` bigint(20) NOT NULL default '0',
  `attr_name` varchar(30) NOT NULL COMMENT '名称',
  `attr_value` varchar(255) default NULL COMMENT '值',
  KEY `fk_attr_config` (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='BBS配置属性表';

INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'keepMinute','1');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'qqEnable','true');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'sinaEnable','false');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'qqWeboEnable','false');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'qqWeboKey','');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'sinaKey','');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'qqID','101194204');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'qqKey','d4e1583fabe2a2db6e44bbcc8a2c24e8');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'sinaID','');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'qqWeboID','');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'useronlinetopnum','146');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'useronlinetopday','2015-3-21');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'changeGroup','10');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'autoChangeGroup','false');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'serviceExpirationEmailNotice','true');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'expirationEmailNoticeCount','3');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'sso_2','http://cms.cmscms.com:81/sso/authenticate.jspx');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'ssoEnable','false');
INSERT INTO `jc_config_attr` (`config_id`,`attr_name`,`attr_value`) VALUES (1,'defaultActiveLevel','2');
CREATE TABLE `jc_config_item` (
  `modelitem_id` int(11) NOT NULL auto_increment,
  `config_id` int(11) NOT NULL,
  `field` varchar(50) NOT NULL COMMENT '字段',
  `item_label` varchar(100) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '70' COMMENT '排列顺序',
  `def_value` varchar(255) default NULL COMMENT '默认值',
  `opt_value` varchar(255) default NULL COMMENT '可选项',
  `text_size` varchar(20) default NULL COMMENT '长度',
  `area_rows` varchar(3) default NULL COMMENT '文本行数',
  `area_cols` varchar(3) default NULL COMMENT '文本列数',
  `help` varchar(255) default NULL COMMENT '帮助信息',
  `help_position` varchar(1) default NULL COMMENT '帮助位置',
  `data_type` int(11) NOT NULL default '1' COMMENT '数据类型 "1":"字符串文本","2":"整型文本","3":"浮点型文本","4":"文本区","5":"日期","6":"下拉列表","7":"复选框","8":"单选框"',
  `is_required` tinyint(1) NOT NULL default '0' COMMENT '是否必填',
  `category` int(11) NOT NULL default '10' COMMENT '模型项目所属分类（10用户模型）',
  PRIMARY KEY  (`modelitem_id`),
  KEY `pk_jc_item_config` (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='CMS配置模型项表';

INSERT INTO `jc_config_item` (`modelitem_id`,`config_id`,`field`,`item_label`,`priority`,`def_value`,`opt_value`,`text_size`,`area_rows`,`area_cols`,`help`,`help_position`,`data_type`,`is_required`,`category`) VALUES (6,1,'tel','手机号码',1,NULL,NULL,NULL,'3','30',NULL,NULL,1,0,10);
CREATE TABLE `jc_friendlink` (
  `friendlink_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `friendlinkctg_id` int(11) NOT NULL,
  `site_name` varchar(150) NOT NULL COMMENT '网站名称',
  `domain` varchar(255) NOT NULL COMMENT '网站地址',
  `logo` varchar(150) default NULL COMMENT '图标',
  `email` varchar(100) default NULL COMMENT '站长邮箱',
  `description` varchar(255) default NULL COMMENT '描述',
  `views` int(11) NOT NULL default '0' COMMENT '点击次数',
  `is_enabled` char(1) NOT NULL default '1' COMMENT '是否显示',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  PRIMARY KEY  (`friendlink_id`),
  KEY `fk_jc_ctg_friendlink` (`friendlinkctg_id`),
  KEY `fk_jc_friendlink_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS友情链接';

CREATE TABLE `jc_friendlink_ctg` (
  `friendlinkctg_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) NOT NULL,
  `friendlinkctg_name` varchar(50) NOT NULL COMMENT '名称',
  `priority` int(11) NOT NULL default '10' COMMENT '排列顺序',
  PRIMARY KEY  (`friendlinkctg_id`),
  KEY `fk_jc_friendlinkctg_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS友情链接类别';

CREATE TABLE `jc_sensitivity` (
  `sensitivity_id` int(11) NOT NULL auto_increment,
  `site_id` int(11) default NULL,
  `search` varchar(255) NOT NULL COMMENT '敏感词',
  `replacement` varchar(255) NOT NULL COMMENT '替换词',
  PRIMARY KEY  (`sensitivity_id`),
  KEY `fk_sensitivity_site` (`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CMS敏感词表';

CREATE TABLE `jc_site` (
  `site_id` int(11) NOT NULL auto_increment,
  `config_id` int(11) NOT NULL COMMENT '配置ID',
  `ftp_upload_id` int(11) default NULL COMMENT '上传ftp',
  `domain` varchar(50) NOT NULL COMMENT '域名',
  `site_path` varchar(20) NOT NULL COMMENT '路径',
  `site_name` varchar(100) NOT NULL COMMENT '网站名称',
  `short_name` varchar(100) NOT NULL COMMENT '简短名称',
  `protocol` varchar(20) NOT NULL default 'http://' COMMENT '协议',
  `dynamic_suffix` varchar(10) NOT NULL default '.jhtml' COMMENT '动态页后缀',
  `static_suffix` varchar(10) NOT NULL default '.html' COMMENT '静态页后缀',
  `static_dir` varchar(50) default NULL COMMENT '静态页存放目录',
  `is_index_to_root` char(1) NOT NULL default '0' COMMENT '是否使用将首页放在根目录下',
  `is_static_index` char(1) NOT NULL default '0' COMMENT '是否静态化首页',
  `locale_admin` varchar(10) NOT NULL default 'zh_CN' COMMENT '后台本地化',
  `locale_front` varchar(10) NOT NULL default 'zh_CN' COMMENT '前台本地化',
  `tpl_solution` varchar(50) NOT NULL default 'default' COMMENT '模板方案',
  `tpl_mobile_solution` varchar(50) NOT NULL default '' COMMENT '手机访问模板方案',
  `final_step` tinyint(4) NOT NULL default '2' COMMENT '终审级别',
  `after_check` tinyint(4) NOT NULL default '2' COMMENT '审核后(1:不能修改删除;2:修改后退回;3:修改后不变)',
  `is_relative_path` char(1) NOT NULL default '1' COMMENT '是否使用相对路径',
  `is_recycle_on` char(1) NOT NULL default '1' COMMENT '是否开启回收站',
  `domain_alias` varchar(255) default NULL COMMENT '域名别名',
  `domain_redirect` varchar(255) default NULL COMMENT '域名重定向',
  `creditex_id` int(11) default '1' COMMENT '积分交易规则id',
  PRIMARY KEY  (`site_id`),
  UNIQUE KEY `ak_domain` (`domain`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='站点表';

INSERT INTO `jc_site` (`site_id`,`config_id`,`ftp_upload_id`,`domain`,`site_path`,`site_name`,`short_name`,`protocol`,`dynamic_suffix`,`static_suffix`,`static_dir`,`is_index_to_root`,`is_static_index`,`locale_admin`,`locale_front`,`tpl_solution`,`tpl_mobile_solution`,`final_step`,`after_check`,`is_relative_path`,`is_recycle_on`,`domain_alias`,`domain_redirect`,`creditex_id`) VALUES (1,1,NULL,'localhost','www','JEEBBS论坛','jeebbs','http://','.jhtml','.html',NULL,'0','0','zh_CN','zh_CN','blue','mobile',2,2,'1','1','192.168.0.2,192.168.0.7,192.168.0.144,localhost','',1);
CREATE TABLE `jo_authentication` (
  `authentication_id` char(32) NOT NULL COMMENT '认证ID',
  `userid` int(11) NOT NULL COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '邮箱',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `login_ip` varchar(50) NOT NULL COMMENT '登录ip',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY  (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='认证信息表';

CREATE TABLE `jo_config` (
  `cfg_key` varchar(50) NOT NULL COMMENT '配置KEY',
  `cfg_value` varchar(255) default NULL COMMENT '配置VALUE',
  PRIMARY KEY  (`cfg_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置表';

INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('email_encoding','utf-8');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('email_host','smtp.qq.com');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('email_password','212');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('email_personal','jeecms');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('email_port',NULL);
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('email_username','11212@qq.com');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('login_error_interval','30');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('login_error_times','3');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('message_forgotpassword_subject','JEECMS会员密码找回信息');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('message_forgotpassword_text','感谢您使用JEECMS系统会员密码找回功能，请记住以下找回信息：\r\n用户ID：${uid}\r\n用户名：${username}\r\n您的新密码为：${resetPwd}\r\n请访问如下链接新密码才能生效：\r\nhttp://localhost:8080/jeebbs4/member/password_reset.jspx?uid=${uid}&key=${resetKey}\r\n');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('message_register_subject','JEECMS会员注册信息');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('message_register_text','${username}您好：\r\n欢迎您注册JEECMS系统会员\r\n请点击以下链接进行激活\r\nhttp://localhost:8080/jeebbs4/active.jspx?username=${username}&key=${activationCode}\r\n');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('message_serviceexpiration_subject','JEECMS商业服务到期');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('message_serviceexpiration_text','感谢您使用JEECMS系列产品，您的服务已到期，如需得到我司服务，您可以到电话联系售前人员购买支持服务，联系方式在官网可以查询：http://www.jeecms.com');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('message_subject','JEECMS会员密码找回信息');
INSERT INTO `jo_config` (`cfg_key`,`cfg_value`) VALUES ('message_text','感谢您使用JEECMS系统会员密码找回功能，请记住以下找回信息：\r\n用户ID：${uid}\r\n用户名：${username}\r\n您的新密码为：${resetPwd}\r\n请访问如下链接新密码才能生效：\r\nhttp://localhost/member/password_reset.jspx?uid=${uid}&key=${resetKey}\r\n');
CREATE TABLE `jo_ftp` (
  `ftp_id` int(11) NOT NULL auto_increment,
  `ftp_name` varchar(100) NOT NULL COMMENT '名称',
  `ip` varchar(50) NOT NULL COMMENT 'IP',
  `port` int(11) NOT NULL default '21' COMMENT '端口号',
  `username` varchar(100) default NULL COMMENT '登录名',
  `password` varchar(100) default NULL COMMENT '登陆密码',
  `encoding` varchar(20) NOT NULL default 'UTF-8' COMMENT '编码',
  `timeout` int(11) default NULL COMMENT '超时时间',
  `ftp_path` varchar(255) default NULL COMMENT '路径',
  `url` varchar(255) NOT NULL COMMENT '访问URL',
  PRIMARY KEY  (`ftp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FTP表';

CREATE TABLE `jo_template` (
  `tpl_name` varchar(150) NOT NULL COMMENT '模板名称',
  `tpl_source` longtext COMMENT '模板内容',
  `last_modified` bigint(20) NOT NULL COMMENT '最后修改时间',
  `is_directory` tinyint(1) NOT NULL default '0' COMMENT '是否目录',
  PRIMARY KEY  (`tpl_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模板表';

CREATE TABLE `jo_upload` (
  `filename` varchar(150) NOT NULL COMMENT '文件名',
  `length` int(11) NOT NULL COMMENT '文件大小(字节)',
  `last_modified` bigint(20) NOT NULL COMMENT '最后修改时间',
  `content` longblob NOT NULL COMMENT '内容',
  PRIMARY KEY  (`filename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上传附件表';

CREATE TABLE `jo_user` (
  `user_id` int(11) NOT NULL auto_increment COMMENT '用户ID',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `email` varchar(100) default NULL COMMENT '电子邮箱',
  `password` char(32) NOT NULL COMMENT '密码',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `register_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '注册IP',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) NOT NULL default '127.0.0.1' COMMENT '最后登录IP',
  `login_count` int(11) NOT NULL default '0' COMMENT '登录次数',
  `reset_key` char(32) default NULL COMMENT '重置密码KEY',
  `reset_pwd` varchar(10) default NULL COMMENT '重置密码VALUE',
  `activation` tinyint(1) NOT NULL default '0' COMMENT '激活状态',
  `activation_code` char(32) default NULL COMMENT '激活码',
  `error_time` datetime default NULL COMMENT '登录错误时间',
  `error_count` int(11) NOT NULL default '0' COMMENT '登录错误次数',
  `error_ip` varchar(50) default NULL COMMENT '登录错误IP',
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `ak_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO `jo_user` (`user_id`,`username`,`email`,`password`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`reset_key`,`reset_pwd`,`activation`,`activation_code`,`error_time`,`error_count`,`error_ip`) VALUES (5,'admin',NULL,'5f4dcc3b5aa765d61d8327deb882cf99','2011-03-17 12:02:04','127.0.0.1','2015-03-29 10:53:10','0:0:0:0:0:0:0:1',1684,NULL,NULL,1,NULL,NULL,0,NULL);
INSERT INTO `jo_user` (`user_id`,`username`,`email`,`password`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`reset_key`,`reset_pwd`,`activation`,`activation_code`,`error_time`,`error_count`,`error_ip`) VALUES (6,'test',NULL,'5f4dcc3b5aa765d61d8327deb882cf99','2015-01-28 13:35:51','0:0:0:0:0:0:0:1','2015-02-02 09:33:23','0:0:0:0:0:0:0:1',4,NULL,NULL,1,NULL,'2015-03-02 16:50:37',1,'127.0.0.1');
INSERT INTO `jo_user` (`user_id`,`username`,`email`,`password`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`reset_key`,`reset_pwd`,`activation`,`activation_code`,`error_time`,`error_count`,`error_ip`) VALUES (8,'jiasudu','test@tt.com','1a1dc91c907325c69271ddf0c944bc72','2015-02-02 14:37:25','0:0:0:0:0:0:0:1','2015-03-25 18:16:55','0:0:0:0:0:0:0:1',55,NULL,NULL,1,NULL,NULL,0,NULL);
INSERT INTO `jo_user` (`user_id`,`username`,`email`,`password`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`reset_key`,`reset_pwd`,`activation`,`activation_code`,`error_time`,`error_count`,`error_ip`) VALUES (15,'test1','test@tt.com','1a1dc91c907325c69271ddf0c944bc72','2015-02-28 10:03:07','127.0.0.1','2015-03-13 17:27:17','192.168.0.6',9,NULL,NULL,1,'6f6f95fa8a7d42a99a4999e53b419920',NULL,0,NULL);
INSERT INTO `jo_user` (`user_id`,`username`,`email`,`password`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`reset_key`,`reset_pwd`,`activation`,`activation_code`,`error_time`,`error_count`,`error_ip`) VALUES (25,'jeebbs','','1a1dc91c907325c69271ddf0c944bc72','2015-03-17 09:28:24','0:0:0:0:0:0:0:1','2015-03-20 13:30:01','127.0.0.1',7,NULL,NULL,1,NULL,NULL,0,NULL);
INSERT INTO `jo_user` (`user_id`,`username`,`email`,`password`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`reset_key`,`reset_pwd`,`activation`,`activation_code`,`error_time`,`error_count`,`error_ip`) VALUES (26,'test11','caoweiwei18@163.com','e10adc3949ba59abbe56e057f20f883e','2015-03-18 15:30:44','127.0.0.1','2015-03-18 15:30:44','127.0.0.1',0,NULL,NULL,1,NULL,NULL,0,NULL);
INSERT INTO `jo_user` (`user_id`,`username`,`email`,`password`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`reset_key`,`reset_pwd`,`activation`,`activation_code`,`error_time`,`error_count`,`error_ip`) VALUES (27,'test12','22@22.com','e10adc3949ba59abbe56e057f20f883e','2015-03-19 13:22:17','127.0.0.1','2015-03-20 14:43:22','127.0.0.1',2,NULL,NULL,1,NULL,NULL,0,NULL);
INSERT INTO `jo_user` (`user_id`,`username`,`email`,`password`,`register_time`,`register_ip`,`last_login_time`,`last_login_ip`,`login_count`,`reset_key`,`reset_pwd`,`activation`,`activation_code`,`error_time`,`error_count`,`error_ip`) VALUES (28,'test13','test@test.com','e10adc3949ba59abbe56e057f20f883e','2015-03-20 16:44:50','192.168.0.167','2015-03-20 16:49:39','192.168.0.167',1,NULL,NULL,1,NULL,NULL,0,NULL);

ALTER TABLE `attachment`
ADD CONSTRAINT `FK_attachment_post` FOREIGN KEY (`post_id`) REFERENCES `bbs_post` (`POST_ID`);

ALTER TABLE `bbs_accredit`
ADD CONSTRAINT `bbs_accredit_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `bbs_category`
ADD CONSTRAINT `FK_BBS_CTG_SITE` FOREIGN KEY (`SITE_ID`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `bbs_category_user`
ADD CONSTRAINT `FK_BBS_CATEGORY_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`),
ADD CONSTRAINT `FK_BBS_USER_TO_CATEGORY` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `bbs_category` (`CATEGORY_ID`);

ALTER TABLE `bbs_config`
ADD CONSTRAINT `FK_BBS_CONFIG_SITE` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `bbs_forum`
ADD CONSTRAINT `FK_BBS_FORUM_CTG` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `bbs_category` (`CATEGORY_ID`),
ADD CONSTRAINT `FK_BBS_FORUM_POST` FOREIGN KEY (`POST_ID`) REFERENCES `bbs_post` (`POST_ID`),
ADD CONSTRAINT `FK_BBS_FORUM_USER` FOREIGN KEY (`replyer_id`) REFERENCES `jb_user` (`user_id`),
ADD CONSTRAINT `FK_BBS_FORUM_WEBSITE` FOREIGN KEY (`SITE_ID`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `bbs_forum_group_reply`
ADD CONSTRAINT `FK_BBS_FORUM_GROUP_REPLY` FOREIGN KEY (`GROUP_ID`) REFERENCES `bbs_user_group` (`GROUP_ID`),
ADD CONSTRAINT `FK_BBS_GROUP_FORUM_REPLY` FOREIGN KEY (`FORUM_ID`) REFERENCES `bbs_forum` (`FORUM_ID`);

ALTER TABLE `bbs_forum_group_topic`
ADD CONSTRAINT `FK_BBS_FORUM_GROUP_TOPIC` FOREIGN KEY (`GROUP_ID`) REFERENCES `bbs_user_group` (`GROUP_ID`),
ADD CONSTRAINT `FK_BBS_GROUP_FORUM_TOPIC` FOREIGN KEY (`FORUM_ID`) REFERENCES `bbs_forum` (`FORUM_ID`);

ALTER TABLE `bbs_forum_group_view`
ADD CONSTRAINT `FK_BBS_FORUM_GROUP_VIEW` FOREIGN KEY (`GROUP_ID`) REFERENCES `bbs_user_group` (`GROUP_ID`),
ADD CONSTRAINT `FK_BBS_GROUP_FORUM_VIEW` FOREIGN KEY (`FORUM_ID`) REFERENCES `bbs_forum` (`FORUM_ID`);

ALTER TABLE `bbs_forum_user`
ADD CONSTRAINT `FK_BBS_FORUM_TO_USER` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`),
ADD CONSTRAINT `FK_BBS_USER_TO_FORUM` FOREIGN KEY (`FORUM_ID`) REFERENCES `bbs_forum` (`FORUM_ID`);

ALTER TABLE `bbs_grade`
ADD CONSTRAINT `FK_MEMBER_GRADE` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`),
ADD CONSTRAINT `FK_POST_GRADE` FOREIGN KEY (`POST_ID`) REFERENCES `bbs_post` (`POST_ID`);

ALTER TABLE `bbs_group_type`
ADD CONSTRAINT `FK_BBS_GROUP_TYPE_GROUP` FOREIGN KEY (`GROUP_ID`) REFERENCES `bbs_user_group` (`GROUP_ID`),
ADD CONSTRAINT `FK_BBS_GROUP_TYPE_TYPE` FOREIGN KEY (`TYPE_ID`) REFERENCES `bbs_post_type` (`type_id`);

ALTER TABLE `bbs_login_log`
ADD CONSTRAINT `fk_bbs_login_log_user` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `bbs_magic_log`
ADD CONSTRAINT `fk_magic_log_magic` FOREIGN KEY (`magic_id`) REFERENCES `bbs_common_magic` (`magicid`),
ADD CONSTRAINT `fk_magic_log_user` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `bbs_operation`
ADD CONSTRAINT `FK_BBS_OPEATTION` FOREIGN KEY (`SITE_ID`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `FK_BBS_OPERATION_USER` FOREIGN KEY (`operater_id`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `bbs_permission`
ADD CONSTRAINT `FK_BBS_PERMISSION_GROUP` FOREIGN KEY (`GROUP_ID`) REFERENCES `bbs_user_group` (`GROUP_ID`);

ALTER TABLE `bbs_post`
ADD CONSTRAINT `FK_BBS_POST_CREATER` FOREIGN KEY (`CREATER_ID`) REFERENCES `jb_user` (`user_id`),
ADD CONSTRAINT `FK_BBS_POST_EDITOR` FOREIGN KEY (`EDITER_ID`) REFERENCES `jb_user` (`user_id`),
ADD CONSTRAINT `FK_BBS_POST_TOPIC` FOREIGN KEY (`TOPIC_ID`) REFERENCES `bbs_topic` (`TOPIC_ID`),
ADD CONSTRAINT `FK_BBS_POST_WEBSITE` FOREIGN KEY (`SITE_ID`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `bbs_post_type`
ADD CONSTRAINT `fk_bbs_post_type_parent` FOREIGN KEY (`parent_id`) REFERENCES `bbs_post_type` (`type_id`),
ADD CONSTRAINT `fk_bbs_post_type_site` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `fk_bbs_type_forum` FOREIGN KEY (`forum_id`) REFERENCES `bbs_forum` (`FORUM_ID`);

ALTER TABLE `bbs_report`
ADD CONSTRAINT `fk_bbs_report_process_user` FOREIGN KEY (`process_user`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `bbs_report_ext`
ADD CONSTRAINT `fk_bbs_report_ext_report` FOREIGN KEY (`report_id`) REFERENCES `bbs_report` (`id`),
ADD CONSTRAINT `fk_bbs_report_ext_report_user` FOREIGN KEY (`report_user`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `bbs_session`
ADD CONSTRAINT `fk_session_user` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `bbs_topic`
ADD CONSTRAINT `FK_BBS_FIRST_POST` FOREIGN KEY (`FIRST_POST_ID`) REFERENCES `bbs_post` (`POST_ID`),
ADD CONSTRAINT `FK_BBS_LAST_POST` FOREIGN KEY (`LAST_POST_ID`) REFERENCES `bbs_post` (`POST_ID`),
ADD CONSTRAINT `FK_BBS_TOPIC_FORUM` FOREIGN KEY (`FORUM_ID`) REFERENCES `bbs_forum` (`FORUM_ID`),
ADD CONSTRAINT `FK_BBS_TOPIC_SITE` FOREIGN KEY (`SITE_ID`) REFERENCES `jc_site` (`site_id`),
ADD CONSTRAINT `FK_BBS_TOPIC_USER_CREATE` FOREIGN KEY (`CREATER_ID`) REFERENCES `jb_user` (`user_id`),
ADD CONSTRAINT `FK_BBS_TOPIC_USER_LAST` FOREIGN KEY (`REPLYER_ID`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `bbs_user_group`
ADD CONSTRAINT `FK_BBS_GROUP_SITE` FOREIGN KEY (`site_id`) REFERENCES `jc_site` (`site_id`);

ALTER TABLE `bbs_user_online`
ADD CONSTRAINT `fk_bbs_user_online_user` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `jb_friendship`
ADD CONSTRAINT `jb_friendship_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`),
ADD CONSTRAINT `jb_friendship_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `jb_user`
ADD CONSTRAINT `FK_BBS_MEMBER_MEMBERGROUP` FOREIGN KEY (`group_id`) REFERENCES `bbs_user_group` (`GROUP_ID`);

ALTER TABLE `jb_user_attr`
ADD CONSTRAINT `fk_jc_attr_user` FOREIGN KEY (`user_id`) REFERENCES `jb_user` (`user_id`);

ALTER TABLE `jc_config_item`
ADD CONSTRAINT `fk_jb_item_config` FOREIGN KEY (`config_id`) REFERENCES `jc_config` (`config_id`);
