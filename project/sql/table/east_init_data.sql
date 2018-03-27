/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : securities-manager

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2017-03-26 11:33:12
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
INSERT INTO `east_select_list` VALUES ('3', 'gz', '每股指标', null, '31', 'true');
INSERT INTO `east_select_list` VALUES ('3', 'ylnl', '盈利能力', null, '32', 'true');
INSERT INTO `east_select_list` VALUES ('3', 'cznl', '成长能力', null, '33', 'true');
INSERT INTO `east_select_list` VALUES ('3', 'zbjgcz', '资本结构与偿债', null, '34', 'true');
INSERT INTO `east_select_list` VALUES ('4', '', '', null, '4', 'true');
INSERT INTO `east_select_list` VALUES ('5', '', '', null, '5', 'true');
INSERT INTO `east_select_list` VALUES ('6', 'gbzb', '股本指标', null, '61', 'true');
INSERT INTO `east_select_list` VALUES ('6', 'gdhs', '股东户数', null, '62', 'true');
INSERT INTO `east_select_list` VALUES ('6', 'jgcg', '机构持股家数', '0', '63', 'true');
INSERT INTO `east_select_list` VALUES ('7', '', '', '0', '7', 'true');

-- ----------------------------
-- Table structure for east_select_detail
-- ----------------------------
DROP TABLE IF EXISTS `east_select_detail`;
CREATE TABLE `east_select_detail` (
  `ul` varchar(30) DEFAULT NULL COMMENT '上层指标标识',
  `id` varchar(20) DEFAULT NULL COMMENT '指标标识',
  `ck` varchar(50) DEFAULT NULL COMMENT '指标条件',
  `text` varchar(50) DEFAULT NULL COMMENT '指标内容',
  `unit` varchar(10) DEFAULT NULL COMMENT '指标单位',
  `clf` varchar(10) DEFAULT NULL COMMENT '指标类别'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选股器指标明细';

-- ----------------------------
-- Records of east_select_detail
-- ----------------------------
INSERT INTO `east_select_detail` VALUES ('1', 'cz_gz01', 'showChildConditions(\'cz_gz\',\'01\')', '每股收益', '元', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_gz02', 'showChildConditions(\'cz_gz\',\'02\')', '每股净资产', '元', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_gz03', 'showChildConditions(\'cz_gz\',\'03\')', '每股未分配利润', '元', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_gz04', 'showChildConditions(\'cz_gz\',\'04\')', '每股资本公积', '元', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_gz05', 'showChildConditions(\'cz_gz\',\'05\')', '每股经营活动产生的现金流量净额', '元', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_ylnl01', 'showChildConditions(\'cz_ylnl\',\'01\')', '净资产收益率', '%', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_cznl06', 'showChildConditions(\'cz_cznl\',\'06\')', '归属母公司净利润3年复合增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_ylnl04', 'showChildConditions(\'cz_ylnl\',\'04\')', '毛利率', '%', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_gbzb01', 'showChildConditions(\'cz_gbzb\',\'01\')', '总股本', '万', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_jgcg01', 'addCondition(\'cz_jgcg\',\'01\')', '机构持股家数合计', '家', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_jgcg02', 'addCondition(\'cz_jgcg\',\'02\')', '基金持股家数', '家', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_jgcg09', 'addCondition(\'cz_jgcg\',\'09\')', '社保基金持股家数', '家', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_hqzb03', 'addCondition(\'cz_hqzb\',\'03\')', '今日创历史新高', '-', 'hqzb_jrdg');
INSERT INTO `east_select_detail` VALUES ('1', 'cz_hqzb02', 'addCondition(\'cz_hqzb\',\'02\')', '破净股票', '-', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_hqzb01', 'addCondition(\'cz_hqzb\',\'01\')', '破发股票', '-', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz19', 'showChildConditions(\'cz\',\'19\')', '总市值', '万元', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz20', 'showChildConditions(\'cz\',\'20\')', '流通市值', '万元', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_hqzb07', 'showChildConditions(\'cz_hqzb\',\'07\')', '连涨天数', '天', 'hqzb_zdts');
INSERT INTO `east_select_detail` VALUES ('1', 'cz_hqzb08', 'showChildConditions(\'cz_hqzb\',\'08\')', '连跌天数', '天', 'hqzb_zdts');
INSERT INTO `east_select_detail` VALUES ('1', 'cz_gzzb01', 'showChildConditions(\'cz_gzzb\',\'01\')', '市盈率', '倍', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_gzzb02', 'showChildConditions(\'cz_gzzb\',\'02\')', '市净率', '倍', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_ggxg01', 'addCondition(\'cz_ggxg\',\'01\')', '最近一个月有增发相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_ggxg02', 'addCondition(\'cz_ggxg\',\'02\')', '最近一个月有重组相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_ggxg07', 'addCondition(\'cz_ggxg\',\'07\')', '最近一个月有重大合同相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_gdhs04', 'showChildConditions(\'cz_gdhs\',\'04\')', '户均持股数季度增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_zbjgcz01', 'showChildConditions(\'cz_zbjgcz\',\'01\')', '资产负债率', '%', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_zbjgcz04', 'showChildConditions(\'cz_zbjgcz\',\'04\')', '流动比率', '倍', null);
INSERT INTO `east_select_detail` VALUES ('1', 'cz_zbjgcz02', 'showChildConditions(\'cz_zbjgcz\',\'02\')', '产权比例', '倍', null);
INSERT INTO `east_select_detail` VALUES ('cz_gz01_childcache', 'cz_gz01_def', 'clearType(\'cz_gz01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz01_childcache', 'cz_gz01(2|0)', 'addCondition(\'cz_gz01\',\'(2|0)\')', '小于0元', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz01_childcache', 'cz_gz01(1|0)', 'addCondition(\'cz_gz01\',\'(1|0)\')', '0元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz01_childcache', 'cz_gz01(1|0.3)', 'addCondition(\'cz_gz01\',\'(1|0.3)\')', '0.3元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz01_childcache', 'cz_gz01(1|0.5)', 'addCondition(\'cz_gz01\',\'(1|0.5)\')', '0.5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz01_childcache', 'cz_gz01(1|1)', 'addCondition(\'cz_gz01\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz02_childcache', 'cz_gz02_def', 'clearType(\'cz_gz02\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz02_childcache', 'cz_gz02(2|0)', 'addCondition(\'cz_gz02\',\'(2|0)\')', '小于0元', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz02_childcache', 'cz_gz02(1|0)', 'addCondition(\'cz_gz02\',\'(1|0)\')', '0元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz02_childcache', 'cz_gz02(1|1)', 'addCondition(\'cz_gz02\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz02_childcache', 'cz_gz02(1|3)', 'addCondition(\'cz_gz02\',\'(1|3)\')', '3元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz02_childcache', 'cz_gz02(1|5)', 'addCondition(\'cz_gz02\',\'(1|5)\')', '5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz03_childcache', 'cz_gz03_def', 'clearType(\'cz_gz03\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz03_childcache', 'cz_gz03(2|0.1)', 'addCondition(\'cz_gz03\',\'(2|0.1)\')', '0.1元以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz03_childcache', 'cz_gz03(1|0.5)', 'addCondition(\'cz_gz03\',\'(1|0.5)\')', '0.5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz03_childcache', 'cz_gz03(1|1)', 'addCondition(\'cz_gz03\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz03_childcache', 'cz_gz03(1|5)', 'addCondition(\'cz_gz03\',\'(1|5)\')', '5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz03_childcache', 'cz_gz03(1|10)', 'addCondition(\'cz_gz03\',\'(1|10)\')', '10元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz04_childcache', 'cz_gz04_def', 'clearType(\'cz_gz04\')', '不限', 'true', null);
INSERT INTO `east_select_detail` VALUES ('cz_gz04_childcache', 'cz_gz04(2|0.1)', 'addCondition(\'cz_gz04\',\'(2|0.1)\')', '0.1元以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz04_childcache', 'cz_gz04(1|0.5)', 'addCondition(\'cz_gz04\',\'(1|0.5)\')', '0.5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz04_childcache', 'cz_gz04(1|1)', 'addCondition(\'cz_gz04\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz04_childcache', 'cz_gz04(1|5)', 'addCondition(\'cz_gz04\',\'(1|5)\')', '5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz04_childcache', 'cz_gz04(1|10)', 'addCondition(\'cz_gz04\',\'(1|10)\')', '10元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz05_childcache', 'cz_gz05_def', 'clearType(\'cz_gz05\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz05_childcache', 'cz_gz05(2|0.1)', 'addCondition(\'cz_gz05\',\'(2|0.1)\')', '0.1元以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz05_childcache', 'cz_gz05(1|0.5)', 'addCondition(\'cz_gz05\',\'(1|0.5)\')', '0.5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz05_childcache', 'cz_gz05(1|1)', 'addCondition(\'cz_gz05\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz05_childcache', 'cz_gz05(1|2)', 'addCondition(\'cz_gz05\',\'(1|2)\')', '2元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gz05_childcache', 'cz_gz05(1|5)', 'addCondition(\'cz_gz05\',\'(1|5)\')', '5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl01_childcache', 'cz_ylnl01_def', 'clearType(\'cz_ylnl01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl01_childcache', 'cz_ylnl01(2|0)', 'addCondition(\'cz_ylnl01\',\'(2|0)\')', '小于0', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl01_childcache', 'cz_ylnl01(1|0.05)', 'addCondition(\'cz_ylnl01\',\'(1|0.05)\')', '5%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl01_childcache', 'cz_ylnl01(1|0.15)', 'addCondition(\'cz_ylnl01\',\'(1|0.15)\')', '15%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl01_childcache', 'cz_ylnl01(1|0.2)', 'addCondition(\'cz_ylnl01\',\'(1|0.2)\')', '20%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl01_childcache', 'cz_ylnl01(1|0.3)', 'addCondition(\'cz_ylnl01\',\'(1|0.3)\')', '30%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_cznl06_childcache', 'cz_cznl06_def', 'clearType(\'cz_cznl06\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_cznl06_childcache', 'cz_cznl06(2|0.05)', 'addCondition(\'cz_cznl06\',\'(2|0.05)\')', '5%以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_cznl06_childcache', 'cz_cznl06(2|0.1)', 'addCondition(\'cz_cznl06\',\'(2|0.1)\')', '10%以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_cznl06_childcache', 'cz_cznl06(1|0.1)', 'addCondition(\'cz_cznl06\',\'(1|0.1)\')', '10%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_cznl06_childcache', 'cz_cznl06(1|0.15)', 'addCondition(\'cz_cznl06\',\'(1|0.15)\')', '15%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_cznl06_childcache', 'cz_cznl06(1|0.25)', 'addCondition(\'cz_cznl06\',\'(1|0.25)\')', '25%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl04_childcache', 'cz_ylnl04_def', 'clearType(\'cz_ylnl04\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl04_childcache', 'cz_ylnl04(2|0.1)', 'addCondition(\'cz_ylnl04\',\'(2|0.1)\')', '10%以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl04_childcache', 'cz_ylnl04(2|0.3)', 'addCondition(\'cz_ylnl04\',\'(2|0.3)\')', '30%以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl04_childcache', 'cz_ylnl04(1|0.3)', 'addCondition(\'cz_ylnl04\',\'(1|0.3)\')', '30%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl04_childcache', 'cz_ylnl04(1|0.5)', 'addCondition(\'cz_ylnl04\',\'(1|0.5)\')', '50%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_ylnl04_childcache', 'cz_ylnl04(1|0.7)', 'addCondition(\'cz_ylnl04\',\'(1|0.7)\')', '70%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gbzb01_childcache', 'cz_gbzb01_def', 'clearType(\'cz_gbzb01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gbzb01_childcache', 'cz_gbzb01(2|5000w)', 'addCondition(\'cz_gbzb01\',\'(2|5000w)\')', '5000万以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gbzb01_childcache', 'cz_gbzb01(2|1y)', 'addCondition(\'cz_gbzb01\',\'(2|1y)\')', '1亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gbzb01_childcache', 'cz_gbzb01(2|2y)', 'addCondition(\'cz_gbzb01\',\'(2|2y)\')', '2亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gbzb01_childcache', 'cz_gbzb01(2|5y)', 'addCondition(\'cz_gbzb01\',\'(2|5y)\')', '5亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gbzb01_childcache', 'cz_gbzb01(2|10y)', 'addCondition(\'cz_gbzb01\',\'(2|10y)\')', '10亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gbzb01_childcache', 'cz_gbzb01(2|50y)', 'addCondition(\'cz_gbzb01\',\'(2|50y)\')', '50亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz19_childcache', 'cz19_def', 'clearType(\'cz19\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz19_childcache', 'cz19(2|20y)', 'addCondition(\'cz19\',\'(2|20y)\')', '20亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz19_childcache', 'cz19(2|50y)', 'addCondition(\'cz19\',\'(2|50y)\')', '50亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz19_childcache', 'cz19(2|100y)', 'addCondition(\'cz19\',\'(2|100y)\')', '100亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz19_childcache', 'cz19(1|100y)', 'addCondition(\'cz19\',\'(1|100y)\')', '100亿以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz19_childcache', 'cz19(1|500y)', 'addCondition(\'cz19\',\'(1|500y)\')', '500亿以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz20_childcache', 'cz20_def', 'clearType(\'cz20\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz20_childcache', 'cz20(2|20y)', 'addCondition(\'cz20\',\'(2|20y)\')', '20亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz20_childcache', 'cz20(2|50y)', 'addCondition(\'cz20\',\'(2|50y)\')', '50亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz20_childcache', 'cz20(2|100y)', 'addCondition(\'cz20\',\'(2|100y)\')', '100亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz20_childcache', 'cz20(1|100y)', 'addCondition(\'cz20\',\'(1|100y)\')', '100亿以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz20_childcache', 'cz20(1|500y)', 'addCondition(\'cz20\',\'(1|500y)\')', '500亿以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_hqzb07_childcache', 'cz_hqzb07_def', 'clearType(\'cz_hqzb07\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_hqzb07_childcache', 'cz_hqzb07(4|3)', 'addCondition(\'cz_hqzb07\',\'(4|3)\')', '3天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_hqzb07_childcache', 'cz_hqzb07(4|5)', 'addCondition(\'cz_hqzb07\',\'(4|5)\')', '5天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_hqzb07_childcache', 'cz_hqzb07(4|10)', 'addCondition(\'cz_hqzb07\',\'(4|10)\')', '10天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_hqzb07_childcache', 'cz_hqzb07(4|20)', 'addCondition(\'cz_hqzb07\',\'(4|20)\')', '20天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_hqzb08_childcache', 'cz_hqzb08_def', 'clearType(\'cz_hqzb08\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_hqzb08_childcache', 'cz_hqzb08(4|3)', 'addCondition(\'cz_hqzb08\',\'(4|3)\')', '3天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_hqzb08_childcache', 'cz_hqzb08(4|5)', 'addCondition(\'cz_hqzb08\',\'(4|5)\')', '5天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_hqzb08_childcache', 'cz_hqzb08(4|10)', 'addCondition(\'cz_hqzb08\',\'(4|10)\')', '10天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_hqzb08_childcache', 'cz_hqzb08(4|20)', 'addCondition(\'cz_hqzb08\',\'(4|20)\')', '20天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb01_childcache', 'cz_gzzb01_def', 'clearType(\'cz_gzzb01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb01_childcache', 'cz_gzzb01(2|5)', 'addCondition(\'cz_gzzb01\',\'(2|5)\')', '5倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb01_childcache', 'cz_gzzb01(2|10)', 'addCondition(\'cz_gzzb01\',\'(2|10)\')', '10倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb01_childcache', 'cz_gzzb01(2|20)', 'addCondition(\'cz_gzzb01\',\'(2|20)\')', '20倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb01_childcache', 'cz_gzzb01(2|50)', 'addCondition(\'cz_gzzb01\',\'(2|50)\')', '50倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb01_childcache', 'cz_gzzb01(2|100)', 'addCondition(\'cz_gzzb01\',\'(2|100)\')', '100倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb02_childcache', 'cz_gzzb02_def', 'clearType(\'cz_gzzb02\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb02_childcache', 'cz_gzzb02(2|1)', 'addCondition(\'cz_gzzb02\',\'(2|1)\')', '1倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb02_childcache', 'cz_gzzb02(2|3)', 'addCondition(\'cz_gzzb02\',\'(2|3)\')', '3倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb02_childcache', 'cz_gzzb02(2|5)', 'addCondition(\'cz_gzzb02\',\'(2|5)\')', '5倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb02_childcache', 'cz_gzzb02(2|10)', 'addCondition(\'cz_gzzb02\',\'(2|10)\')', '10倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gzzb02_childcache', 'cz_gzzb02(2|20)', 'addCondition(\'cz_gzzb02\',\'(2|20)\')', '20倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gdhs04_childcache', 'cz_gdhs04_def', 'clearType(\'cz_gdhs04\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gdhs04_childcache', 'cz_gdhs04(4|0.1)', 'addCondition(\'cz_gdhs04\',\'(4|0.1)\')', '10%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gdhs04_childcache', 'cz_gdhs04(4|0.3)', 'addCondition(\'cz_gdhs04\',\'(4|0.3)\')', '30%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gdhs04_childcache', 'cz_gdhs04(4|0.5)', 'addCondition(\'cz_gdhs04\',\'(4|0.5)\')', '50%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_gdhs04_childcache', 'cz_gdhs04(4|1)', 'addCondition(\'cz_gdhs04\',\'(4|1)\')', '100%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz01_childcache', 'cz_zbjgcz01_def', 'clearType(\'cz_zbjgcz01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz01_childcache', 'cz_zbjgcz01(5|0.1)', 'addCondition(\'cz_zbjgcz01\',\'(5|0.1)\')', '10%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz01_childcache', 'cz_zbjgcz01(5|0.3)', 'addCondition(\'cz_zbjgcz01\',\'(5|0.3)\')', '30%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz01_childcache', 'cz_zbjgcz01(5|0.5)', 'addCondition(\'cz_zbjgcz01\',\'(5|0.5)\')', '50%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz01_childcache', 'cz_zbjgcz01(5|1)', 'addCondition(\'cz_zbjgcz01\',\'(5|1)\')', '100%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz02_childcache', 'cz_zbjgcz02_def', 'clearType(\'cz_zbjgcz02\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz02_childcache', 'cz_zbjgcz02(5|0.5)', 'addCondition(\'cz_zbjgcz02\',\'(5|0.5)\')', '0.5倍及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz02_childcache', 'cz_zbjgcz02(5|1)', 'addCondition(\'cz_zbjgcz02\',\'(5|1)\')', '1倍及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz02_childcache', 'cz_zbjgcz02(4|1)', 'addCondition(\'cz_zbjgcz02\',\'(4|1)\')', '1倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz02_childcache', 'cz_zbjgcz02(4|2)', 'addCondition(\'cz_zbjgcz02\',\'(4|2)\')', '2倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz02_childcache', 'cz_zbjgcz02(4|5)', 'addCondition(\'cz_zbjgcz02\',\'(4|5)\')', '5倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz04_childcache', 'cz_zbjgcz04_def', 'clearType(\'cz_zbjgcz04\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz04_childcache', 'cz_zbjgcz04(2|0.5)', 'addCondition(\'cz_zbjgcz04\',\'(2|0.5)\')', '50%以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz04_childcache', 'cz_zbjgcz04(1|1)', 'addCondition(\'cz_zbjgcz04\',\'(1|1)\')', '1倍以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz04_childcache', 'cz_zbjgcz04(1|2)', 'addCondition(\'cz_zbjgcz04\',\'(1|2)\')', '2倍以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cz_zbjgcz04_childcache', 'cz_zbjgcz04(1|3)', 'addCondition(\'cz_zbjgcz04\',\'(1|3)\')', '3倍以上', null, null);
INSERT INTO `east_select_detail` VALUES ('21', 'gf_def', 'clearType(\'gf\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('21', 'gf1', 'addCondition(\'gf\',\'1\')', '沪A', '-', null);
INSERT INTO `east_select_detail` VALUES ('21', 'gf2', 'addCondition(\'gf\',\'2\')', '深A', '-', null);
INSERT INTO `east_select_detail` VALUES ('21', 'gf3', 'addCondition(\'gf\',\'3\')', '深主板A', '-', null);
INSERT INTO `east_select_detail` VALUES ('21', 'gf4', 'addCondition(\'gf\',\'4\')', '中小板', '-', null);
INSERT INTO `east_select_detail` VALUES ('21', 'gf5', 'addCondition(\'gf\',\'5\')', '创业板', '-', null);
INSERT INTO `east_select_detail` VALUES ('22', 'gfzs_def', 'clearType(\'gfzs\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('22', 'gfzs1', 'addCondition(\'gfzs\',\'1\')', '沪深300指数成分股', '-', null);
INSERT INTO `east_select_detail` VALUES ('22', 'gfzs2', 'addCondition(\'gfzs\',\'2\')', '上证180指数成分股', '-', null);
INSERT INTO `east_select_detail` VALUES ('22', 'gfzs3', 'addCondition(\'gfzs\',\'3\')', '上证50指数成分股', '-', null);
INSERT INTO `east_select_detail` VALUES ('22', 'gfzs4', 'addCondition(\'gfzs\',\'4\')', '中证100指数', '-', null);
INSERT INTO `east_select_detail` VALUES ('22', 'gfzs5', 'addCondition(\'gfzs\',\'5\')', '深证100指数', '-', null);
INSERT INTO `east_select_detail` VALUES ('22', 'gfzs6', 'showChildConditions(\'gfzs\',\'6\')', '行业板块', '-', null);
INSERT INTO `east_select_detail` VALUES ('22', 'gfzs7', 'showChildConditions(\'gfzs\',\'7\')', '概念板块', '-', null);
INSERT INTO `east_select_detail` VALUES ('22', 'gfzs8', 'showChildConditions(\'gfzs\',\'8\')', '地区板块', '-', null);
INSERT INTO `east_select_detail` VALUES ('31', 'gz_def', 'clearType(\'gz\')', '不限', '元', null);
INSERT INTO `east_select_detail` VALUES ('31', 'gz01', 'showChildConditions(\'gz\',\'01\')', '每股收益', '元', null);
INSERT INTO `east_select_detail` VALUES ('31', 'gz02', 'showChildConditions(\'gz\',\'02\')', '每股净资产', '元', null);
INSERT INTO `east_select_detail` VALUES ('31', 'gz03', 'showChildConditions(\'gz\',\'03\')', '每股未分配利润', '元', null);
INSERT INTO `east_select_detail` VALUES ('31', 'gz04', 'showChildConditions(\'gz\',\'04\')', '每股资本公积', '元', null);
INSERT INTO `east_select_detail` VALUES ('31', 'gz05', 'showChildConditions(\'gz\',\'05\')', '每股经营活动产生的现金流量净额', '元', null);
INSERT INTO `east_select_detail` VALUES ('31', 'gz06', 'showChildConditions(\'gz\',\'06\')', '每股盈余公积', '元', null);
INSERT INTO `east_select_detail` VALUES ('31', 'gz07', 'showChildConditions(\'gz\',\'07\')', '每股留存收益', '元', null);
INSERT INTO `east_select_detail` VALUES ('gz01_childcache', 'gz01_def', 'clearType(\'gz01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gz01_childcache', 'gz01(2|0)', 'addCondition(\'gz01\',\'(2|0)\')', '小于0元', null, null);
INSERT INTO `east_select_detail` VALUES ('gz01_childcache', 'gz01(1|0)', 'addCondition(\'gz01\',\'(1|0)\')', '0元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz01_childcache', 'gz01(1|0.3)', 'addCondition(\'gz01\',\'(1|0.3)\')', '0.3元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz01_childcache', 'gz01(1|0.5)', 'addCondition(\'gz01\',\'(1|0.5)\')', '0.5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz01_childcache', 'gz01(1|1)', 'addCondition(\'gz01\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz02_childcache', 'gz02_def', 'clearType(\'gz02\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gz02_childcache', 'gz02(2|0)', 'addCondition(\'gz02\',\'(2|0)\')', '0元以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gz02_childcache', 'gz02(1|0)', 'addCondition(\'gz02\',\'(1|0)\')', '0元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz02_childcache', 'gz02(1|1)', 'addCondition(\'gz02\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz02_childcache', 'gz02(1|3)', 'addCondition(\'gz02\',\'(1|3)\')', '3元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz02_childcache', 'gz02(1|5)', 'addCondition(\'gz02\',\'(1|5)\')', '5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz03_childcache', 'gz03_def', 'clearType(\'gz03\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gz03_childcache', 'gz03(2|0.1)', 'addCondition(\'gz03\',\'(2|0.1)\')', '0.1元以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gz03_childcache', 'gz03(1|0.5)', 'addCondition(\'gz03\',\'(1|0.5)\')', '0.5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz03_childcache', 'gz03(1|1)', 'addCondition(\'gz03\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz03_childcache', 'gz03(1|5)', 'addCondition(\'gz03\',\'(1|5)\')', '5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz03_childcache', 'gz03(1|10)', 'addCondition(\'gz03\',\'(1|10)\')', '10元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz04_childcache', 'gz04_def', 'clearType(\'gz04\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gz04_childcache', 'gz04(2|0.1)', 'addCondition(\'gz04\',\'(2|0.1)\')', '0.1元以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gz04_childcache', 'gz04(1|0.5)', 'addCondition(\'gz04\',\'(1|0.5)\')', '0.5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz04_childcache', 'gz04(1|1)', 'addCondition(\'gz04\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz04_childcache', 'gz04(1|5)', 'addCondition(\'gz04\',\'(1|5)\')', '5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz04_childcache', 'gz04(1|10)', 'addCondition(\'gz04\',\'(1|10)\')', '10元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz05_childcache', 'gz05_def', 'clearType(\'gz05\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gz05_childcache', 'gz05(2|0.1)', 'addCondition(\'gz05\',\'(2|0.1)\')', '0.1元以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gz05_childcache', 'gz05(1|0.5)', 'addCondition(\'gz05\',\'(1|0.5)\')', '0.5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz05_childcache', 'gz05(1|1)', 'addCondition(\'gz05\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz05_childcache', 'gz05(1|2)', 'addCondition(\'gz05\',\'(1|2)\')', '2元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz05_childcache', 'gz05(1|5)', 'addCondition(\'gz05\',\'(1|5)\')', '5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz06_childcache', 'gz06_def', 'clearType(\'gz06\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gz06_childcache', 'gz06(2|0.1)', 'addCondition(\'gz06\',\'(2|0.1)\')', '0.1元以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gz06_childcache', 'gz06(1|0.5)', 'addCondition(\'gz06\',\'(1|0.5)\')', '0.5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz06_childcache', 'gz06(1|1)', 'addCondition(\'gz06\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz06_childcache', 'gz06(1|5)', 'addCondition(\'gz06\',\'(1|5)\')', '5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz06_childcache', 'gz06(1|10)', 'addCondition(\'gz06\',\'(1|10)\')', '10元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz07_childcache', 'gz07_def', 'clearType(\'gz07\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gz07_childcache', 'gz07(2|0.1)', 'addCondition(\'gz07\',\'(2|0.1)\')', '0.1元以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gz07_childcache', 'gz07(1|0.5)', 'addCondition(\'gz07\',\'(1|0.5)\')', '0.5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz07_childcache', 'gz07(1|1)', 'addCondition(\'gz07\',\'(1|1)\')', '1元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz07_childcache', 'gz07(1|5)', 'addCondition(\'gz07\',\'(1|5)\')', '5元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gz07_childcache', 'gz07(1|10)', 'addCondition(\'gz07\',\'(1|10)\')', '10元以上', null, null);
INSERT INTO `east_select_detail` VALUES ('32', 'ylnl_def', 'clearType(\'ylnl\')', '不限', '元', null);
INSERT INTO `east_select_detail` VALUES ('32', 'ylnl01', 'showChildConditions(\'ylnl\',\'01\')', '净资产收益率', '%', null);
INSERT INTO `east_select_detail` VALUES ('32', 'ylnl02', 'showChildConditions(\'ylnl\',\'02\')', '总资产报酬率', '%', null);
INSERT INTO `east_select_detail` VALUES ('32', 'ylnl03', 'showChildConditions(\'ylnl\',\'03\')', '总资产净利率', '%', null);
INSERT INTO `east_select_detail` VALUES ('32', 'ylnl04', 'showChildConditions(\'ylnl\',\'04\')', '销售毛利率', '%', null);
INSERT INTO `east_select_detail` VALUES ('32', 'ylnl05', 'showChildConditions(\'ylnl\',\'05\')', '净利润/营业总收入', '%', null);
INSERT INTO `east_select_detail` VALUES ('ylnl01_childcache', 'ylnl01_def', 'clearType(\'ylnl01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl01_childcache', 'ylnl01(2|0)', 'addCondition(\'ylnl01\',\'(2|0)\')', '小于0', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl01_childcache', 'ylnl01(1|0.05)', 'addCondition(\'ylnl01\',\'(1|0.05)\')', '5%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl01_childcache', 'ylnl01(1|0.15)', 'addCondition(\'ylnl01\',\'(1|0.15)\')', '15%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl01_childcache', 'ylnl01(1|0.2)', 'addCondition(\'ylnl01\',\'(1|0.2)\')', '20%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl01_childcache', 'ylnl01(1|0.3)', 'addCondition(\'ylnl01\',\'(1|0.3)\')', '30%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl02_childcache', 'ylnl02_def', 'clearType(\'ylnl02\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl02_childcache', 'ylnl02(2|0)', 'addCondition(\'ylnl02\',\'(2|0)\')', '小于0', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl02_childcache', 'ylnl02(1|0.05)', 'addCondition(\'ylnl02\',\'(1|0.05)\')', '5%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl02_childcache', 'ylnl02(1|0.15)', 'addCondition(\'ylnl02\',\'(1|0.15)\')', '15%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl02_childcache', 'ylnl02(1|0.2)', 'addCondition(\'ylnl02\',\'(1|0.2)\')', '20%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl02_childcache', 'ylnl02(1|0.3)', 'addCondition(\'ylnl02\',\'(1|0.3)\')', '30%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl03_childcache', 'ylnl03_def', 'clearType(\'ylnl03\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl03_childcache', 'ylnl03(2|0)', 'addCondition(\'ylnl03\',\'(2|0)\')', '小于0', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl03_childcache', 'ylnl03(1|0.05)', 'addCondition(\'ylnl03\',\'(1|0.05)\')', '5%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl03_childcache', 'ylnl03(1|0.15)', 'addCondition(\'ylnl03\',\'(1|0.15)\')', '15%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl03_childcache', 'ylnl03(1|0.2)', 'addCondition(\'ylnl03\',\'(1|0.2)\')', '20%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl03_childcache', 'ylnl03(1|0.3)', 'addCondition(\'ylnl03\',\'(1|0.3)\')', '30%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl04_childcache', 'ylnl04_def', 'clearType(\'ylnl04\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl04_childcache', 'ylnl04(2|0.1)', 'addCondition(\'ylnl04\',\'(2|0.1)\')', '10%以下', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl04_childcache', 'ylnl04(2|0.3)', 'addCondition(\'ylnl04\',\'(2|0.3)\')', '30%以下', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl04_childcache', 'ylnl04(1|0.3)', 'addCondition(\'ylnl04\',\'(1|0.3)\')', '30%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl04_childcache', 'ylnl04(1|0.5)', 'addCondition(\'ylnl04\',\'(1|0.5)\')', '50%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl04_childcache', 'ylnl04(1|0.7)', 'addCondition(\'ylnl04\',\'(1|0.7)\')', '70%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl05_childcache', 'ylnl05_def', 'clearType(\'ylnl05\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl05_childcache', 'ylnl05(2|0)', 'addCondition(\'ylnl05\',\'(2|0)\')', '小于0', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl05_childcache', 'ylnl05(1|0.05)', 'addCondition(\'ylnl05\',\'(1|0.05)\')', '5%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl05_childcache', 'ylnl05(1|0.15)', 'addCondition(\'ylnl05\',\'(1|0.15)\')', '15%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl05_childcache', 'ylnl05(1|0.2)', 'addCondition(\'ylnl05\',\'(1|0.2)\')', '20%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('ylnl05_childcache', 'ylnl05(1|0.3)', 'addCondition(\'ylnl05\',\'(1|0.3)\')', '30%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('33', 'cznl_def', 'clearType(\'cznl\')', '不限', '元', null);
INSERT INTO `east_select_detail` VALUES ('33', 'cznl01', 'showChildConditions(\'cznl\',\'01\')', '每股收益同比增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('33', 'cznl02', 'showChildConditions(\'cznl\',\'02\')', '净利润同比增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('33', 'cznl03', 'showChildConditions(\'cznl\',\'03\')', '利润总额同比增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('33', 'cznl04', 'showChildConditions(\'cznl\',\'04\')', '营业收入同比增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('33', 'cznl05', 'showChildConditions(\'cznl\',\'05\')', '营业利润同比增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('33', 'cznl06', 'showChildConditions(\'cznl\',\'06\')', '归属母公司净利润3年复合增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('33', 'cznl07', 'showChildConditions(\'cznl\',\'07\')', '营业总收入3年复合增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('cznl01_childcache', 'cznl01_def', 'clearType(\'cznl01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl01_childcache', 'cznl01(4|0.05)', 'addCondition(\'cznl01\',\'(4|0.05)\')', '5%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl01_childcache', 'cznl01(4|0.1)', 'addCondition(\'cznl01\',\'(4|0.1)\')', '10%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl01_childcache', 'cznl01(4|0.3)', 'addCondition(\'cznl01\',\'(4|0.3)\')', '30%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl01_childcache', 'cznl01(4|0.5)', 'addCondition(\'cznl01\',\'(4|0.5)\')', '50%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl01_childcache', 'cznl01(4|1)', 'addCondition(\'cznl01\',\'(4|1)\')', '100%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl02_childcache', 'cznl02_def', 'clearType(\'cznl02\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl02_childcache', 'cznl02(4|0.05)', 'addCondition(\'cznl02\',\'(4|0.05)\')', '5%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl02_childcache', 'cznl02(4|0.1)', 'addCondition(\'cznl02\',\'(4|0.1)\')', '10%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl02_childcache', 'cznl02(4|0.3)', 'addCondition(\'cznl02\',\'(4|0.3)\')', '30%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl02_childcache', 'cznl02(4|0.5)', 'addCondition(\'cznl02\',\'(4|0.5)\')', '50%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl02_childcache', 'cznl02(4|1)', 'addCondition(\'cznl02\',\'(4|1)\')', '100%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl03_childcache', 'cznl03_def', 'clearType(\'cznl03\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl03_childcache', 'cznl03(4|0.05)', 'addCondition(\'cznl03\',\'(4|0.05)\')', '5%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl03_childcache', 'cznl03(4|0.1)', 'addCondition(\'cznl03\',\'(4|0.1)\')', '10%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl03_childcache', 'cznl03(4|0.3)', 'addCondition(\'cznl03\',\'(4|0.3)\')', '30%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl03_childcache', 'cznl03(4|0.5)', 'addCondition(\'cznl03\',\'(4|0.5)\')', '50%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl03_childcache', 'cznl03(4|1)', 'addCondition(\'cznl03\',\'(4|1)\')', '100%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl04_childcache', 'cznl04_def', 'clearType(\'cznl04\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl04_childcache', 'cznl04(4|0.05)', 'addCondition(\'cznl04\',\'(4|0.05)\')', '5%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl04_childcache', 'cznl04(4|0.1)', 'addCondition(\'cznl04\',\'(4|0.1)\')', '10%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl04_childcache', 'cznl04(4|0.3)', 'addCondition(\'cznl04\',\'(4|0.3)\')', '30%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl04_childcache', 'cznl04(4|0.5)', 'addCondition(\'cznl04\',\'(4|0.5)\')', '50%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl04_childcache', 'cznl04(4|1)', 'addCondition(\'cznl04\',\'(4|1)\')', '100%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl05_childcache', 'cznl05_def', 'clearType(\'cznl05\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl05_childcache', 'cznl05(4|0.05)', 'addCondition(\'cznl05\',\'(4|0.05)\')', '5%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl05_childcache', 'cznl05(4|0.1)', 'addCondition(\'cznl05\',\'(4|0.1)\')', '10%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl05_childcache', 'cznl05(4|0.3)', 'addCondition(\'cznl05\',\'(4|0.3)\')', '30%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl05_childcache', 'cznl05(4|0.5)', 'addCondition(\'cznl05\',\'(4|0.5)\')', '50%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl05_childcache', 'cznl05(4|1)', 'addCondition(\'cznl05\',\'(4|1)\')', '100%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl06_childcache', 'cznl06_def', 'clearType(\'cznl06\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl06_childcache', 'cznl06(2|0.05)', 'addCondition(\'cznl06\',\'(2|0.05)\')', '5%以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl06_childcache', 'cznl06(2|0.1)', 'addCondition(\'cznl06\',\'(2|0.1)\')', '10%以下', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl06_childcache', 'cznl06(1|0.1)', 'addCondition(\'cznl06\',\'(1|0.1)\')', '10%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl06_childcache', 'cznl06(1|0.15)', 'addCondition(\'cznl06\',\'(1|0.15)\')', '15%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl06_childcache', 'cznl06(1|0.25)', 'addCondition(\'cznl06\',\'(1|0.25)\')', '25%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl07_childcache', 'cznl07_def', 'clearType(\'cznl07\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl07_childcache', 'cznl07(4|0.05)', 'addCondition(\'cznl07\',\'(4|0.05)\')', '5%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl07_childcache', 'cznl07(4|0.1)', 'addCondition(\'cznl07\',\'(4|0.1)\')', '10%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl07_childcache', 'cznl07(4|0.3)', 'addCondition(\'cznl07\',\'(4|0.3)\')', '30%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl07_childcache', 'cznl07(4|0.5)', 'addCondition(\'cznl07\',\'(4|0.5)\')', '50%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('cznl07_childcache', 'cznl07(4|1)', 'addCondition(\'cznl07\',\'(4|1)\')', '100%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('34', 'zbjgcz_def', 'clearType(\'zbjgcz\')', '不限', '元', null);
INSERT INTO `east_select_detail` VALUES ('34', 'zbjgcz01', 'showChildConditions(\'zbjgcz\',\'01\')', '资产负债率', '%', null);
INSERT INTO `east_select_detail` VALUES ('34', 'zbjgcz02', 'showChildConditions(\'zbjgcz\',\'02\')', '产权比例', '倍', null);
INSERT INTO `east_select_detail` VALUES ('34', 'zbjgcz03', 'showChildConditions(\'zbjgcz\',\'03\')', '权益乘数', '倍', null);
INSERT INTO `east_select_detail` VALUES ('34', 'zbjgcz04', 'showChildConditions(\'zbjgcz\',\'04\')', '流动比率', '倍', null);
INSERT INTO `east_select_detail` VALUES ('34', 'zbjgcz05', 'showChildConditions(\'zbjgcz\',\'05\')', '速动比例', '倍', null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz01_childcache', 'zbjgcz01_def', 'clearType(\'zbjgcz01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz01_childcache', 'zbjgcz01(5|0.1)', 'addCondition(\'zbjgcz01\',\'(5|0.1)\')', '10%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz01_childcache', 'zbjgcz01(5|0.3)', 'addCondition(\'zbjgcz01\',\'(5|0.3)\')', '30%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz01_childcache', 'zbjgcz01(5|0.5)', 'addCondition(\'zbjgcz01\',\'(5|0.5)\')', '50%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz01_childcache', 'zbjgcz01(5|1)', 'addCondition(\'zbjgcz01\',\'(5|1)\')', '100%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz02_childcache', 'zbjgcz02_def', 'clearType(\'zbjgcz02\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz02_childcache', 'zbjgcz02(5|0.5)', 'addCondition(\'zbjgcz02\',\'(5|0.5)\')', '0.5倍及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz02_childcache', 'zbjgcz02(5|1)', 'addCondition(\'zbjgcz02\',\'(5|1)\')', '1倍及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz02_childcache', 'zbjgcz02(4|1)', 'addCondition(\'zbjgcz02\',\'(4|1)\')', '1倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz02_childcache', 'zbjgcz02(4|2)', 'addCondition(\'zbjgcz02\',\'(4|2)\')', '2倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz02_childcache', 'zbjgcz02(4|5)', 'addCondition(\'zbjgcz02\',\'(4|5)\')', '5倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz03_childcache', 'zbjgcz03_def', 'clearType(\'zbjgcz03\')', '不限', 'true', null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz03_childcache', 'zbjgcz03(5|1.2)', 'addCondition(\'zbjgcz03\',\'(5|1.2)\')', '1.2倍及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz03_childcache', 'zbjgcz03(5|1.5)', 'addCondition(\'zbjgcz03\',\'(5|1.5)\')', '1.5倍及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz03_childcache', 'zbjgcz03(4|2)', 'addCondition(\'zbjgcz03\',\'(4|2)\')', '2倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz03_childcache', 'zbjgcz03(4|5)', 'addCondition(\'zbjgcz03\',\'(4|5)\')', '5倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz03_childcache', 'zbjgcz03(4|10)', 'addCondition(\'zbjgcz03\',\'(4|10)\')', '10倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz04_childcache', 'zbjgcz04_def', 'clearType(\'zbjgcz04\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz04_childcache', 'zbjgcz04(1|0.5)', 'addCondition(\'zbjgcz04\',\'(1|0.5)\')', '50%以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz04_childcache', 'zbjgcz04(1|1)', 'addCondition(\'zbjgcz04\',\'(1|1)\')', '1倍以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz04_childcache', 'zbjgcz04(1|2)', 'addCondition(\'zbjgcz04\',\'(1|2)\')', '2倍以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz04_childcache', 'zbjgcz04(1|3)', 'addCondition(\'zbjgcz04\',\'(1|3)\')', '3倍以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz05_childcache', 'zbjgcz05_def', 'clearType(\'zbjgcz05\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz05_childcache', 'zbjgcz05(5|0.5)', 'addCondition(\'zbjgcz05\',\'(5|0.5)\')', '0.5倍及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz05_childcache', 'zbjgcz05(5|1)', 'addCondition(\'zbjgcz05\',\'(5|1)\')', '1倍及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz05_childcache', 'zbjgcz05(4|1)', 'addCondition(\'zbjgcz05\',\'(4|1)\')', '1倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz05_childcache', 'zbjgcz05(4|2)', 'addCondition(\'zbjgcz05\',\'(4|2)\')', '2倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('zbjgcz05_childcache', 'zbjgcz05(4|5)', 'addCondition(\'zbjgcz05\',\'(4|5)\')', '5倍及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('4', 'gzzb_def', 'clearType(\'gzzb\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('4', 'gzzb01', 'showChildConditions(\'gzzb\',\'01\')', '市盈率', '倍', null);
INSERT INTO `east_select_detail` VALUES ('4', 'gzzb02', 'showChildConditions(\'gzzb\',\'02\')', '市净率', '倍', null);
INSERT INTO `east_select_detail` VALUES ('4', 'gzzb04', 'showChildConditions(\'gzzb\',\'04\')', '市现率', '倍', null);
INSERT INTO `east_select_detail` VALUES ('4', 'gzzb05', 'showChildConditions(\'gzzb\',\'05\')', '市销率', '倍', null);
INSERT INTO `east_select_detail` VALUES ('gzzb01_childcache', 'gzzb01_def', 'clearType(\'gzzb01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb01_childcache', 'gzzb01(2|5)', 'addCondition(\'gzzb01\',\'(2|5)\')', '5倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb01_childcache', 'gzzb01(2|10)', 'addCondition(\'gzzb01\',\'(2|10)\')', '10倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb01_childcache', 'gzzb01(2|20)', 'addCondition(\'gzzb01\',\'(2|20)\')', '20倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb01_childcache', 'gzzb01(2|50)', 'addCondition(\'gzzb01\',\'(2|50)\')', '50倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb01_childcache', 'gzzb01(2|100)', 'addCondition(\'gzzb01\',\'(2|100)\')', '100倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb02_childcache', 'gzzb02_def', 'clearType(\'gzzb02\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb02_childcache', 'gzzb02(2|1)', 'addCondition(\'gzzb02\',\'(2|1)\')', '1倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb02_childcache', 'gzzb02(2|3)', 'addCondition(\'gzzb02\',\'(2|3)\')', '3倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb02_childcache', 'gzzb02(2|5)', 'addCondition(\'gzzb02\',\'(2|5)\')', '5倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb02_childcache', 'gzzb02(2|10)', 'addCondition(\'gzzb02\',\'(2|10)\')', '10倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb02_childcache', 'gzzb02(2|20)', 'addCondition(\'gzzb02\',\'(2|20)\')', '20倍以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb04_childcache', 'gzzb04_def', 'clearType(\'gzzb04\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb04_childcache', 'gzzb04(2|0)', 'addCondition(\'gzzb04\',\'(2|0)\')', '小于0倍', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb04_childcache', 'gzzb04(1|0)', 'addCondition(\'gzzb04\',\'(1|0)\')', '大于0倍', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb04_childcache', 'gzzb04(1|10)', 'addCondition(\'gzzb04\',\'(1|10)\')', '大于10倍', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb04_childcache', 'gzzb04(1|50)', 'addCondition(\'gzzb04\',\'(1|50)\')', '大于50倍', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb04_childcache', 'gzzb04(1|100)', 'addCondition(\'gzzb04\',\'(1|100)\')', '大于100倍', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb05_childcache', 'gzzb05_def', 'clearType(\'gzzb05\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb05_childcache', 'gzzb05(2|1)', 'addCondition(\'gzzb05\',\'(2|1)\')', '小于1倍', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb05_childcache', 'gzzb05(1|1)', 'addCondition(\'gzzb05\',\'(1|1)\')', '大于1倍', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb05_childcache', 'gzzb05(1|10)', 'addCondition(\'gzzb05\',\'(1|10)\')', '大于10倍', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb05_childcache', 'gzzb05(1|50)', 'addCondition(\'gzzb05\',\'(1|50)\')', '大于50倍', null, null);
INSERT INTO `east_select_detail` VALUES ('gzzb05_childcache', 'gzzb05(1|100)', 'addCondition(\'gzzb05\',\'(1|100)\')', '大于100倍', null, null);
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb_def', 'clearType(\'hqzb\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb01', 'addCondition(\'hqzb\',\'01\')', '破发股票', '-', null);
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb02', 'addCondition(\'hqzb\',\'02\')', '破净股票', '-', null);
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb03', 'addCondition(\'hqzb\',\'03\')', '今日创历史新高', '-', 'hqzb_jrdg');
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb04', 'addCondition(\'hqzb\',\'04\')', '今日创历史新低', '-', 'hqzb_jrdg');
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb05', 'showChildConditions(\'hqzb\',\'05\')', '近日创历史新高', '天', 'hqzb_jrdgs');
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb06', 'showChildConditions(\'hqzb\',\'06\')', '近日创历史新低', '天', 'hqzb_jrdgs');
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb07', 'showChildConditions(\'hqzb\',\'07\')', '连涨天数', '天', 'hqzb_zdts');
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb08', 'showChildConditions(\'hqzb\',\'08\')', '连跌天数', '天', 'hqzb_zdts');
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb11', 'showChildConditions(\'hqzb\',\'11\')', '阶段涨幅', '%', 'hqzb_jdzdf');
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb12', 'showChildConditions(\'hqzb\',\'12\')', '阶段跌幅', '%', 'hqzb_jdzdf');
INSERT INTO `east_select_detail` VALUES ('5', 'hqzb13', 'showChildConditions(\'hqzb\',\'13\')', '阶段换手率', '%', null);
INSERT INTO `east_select_detail` VALUES ('hqzb05_childcache', 'hqzb05_def', 'clearType(\'hqzb05\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb05_childcache', 'hqzb05(1|3)', 'addCondition(\'hqzb05\',\'(1|3)\')', '3日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb05_childcache', 'hqzb05(1|5)', 'addCondition(\'hqzb05\',\'(1|5)\')', '5日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb05_childcache', 'hqzb05(1|10)', 'addCondition(\'hqzb05\',\'(1|10)\')', '10日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb06_childcache', 'hqzb06_def', 'clearType(\'hqzb06\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb06_childcache', 'hqzb06(1|3)', 'addCondition(\'hqzb06\',\'(1|3)\')', '3日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb06_childcache', 'hqzb06(1|5)', 'addCondition(\'hqzb06\',\'(1|5)\')', '5日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb06_childcache', 'hqzb06(1|10)', 'addCondition(\'hqzb06\',\'(1|10)\')', '10日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb07_childcache', 'hqzb07_def', 'clearType(\'hqzb07\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb07_childcache', 'hqzb07(4|3)', 'addCondition(\'hqzb07\',\'(4|3)\')', '3天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb07_childcache', 'hqzb07(4|5)', 'addCondition(\'hqzb07\',\'(4|5)\')', '5天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb07_childcache', 'hqzb07(4|10)', 'addCondition(\'hqzb07\',\'(4|10)\')', '10天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb07_childcache', 'hqzb07(4|20)', 'addCondition(\'hqzb07\',\'(4|20)\')', '20天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb08_childcache', 'hqzb08_def', 'clearType(\'hqzb08\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb08_childcache', 'hqzb08(4|3)', 'addCondition(\'hqzb08\',\'(4|3)\')', '3天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb08_childcache', 'hqzb08(4|5)', 'addCondition(\'hqzb08\',\'(4|5)\')', '5天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb08_childcache', 'hqzb08(4|10)', 'addCondition(\'hqzb08\',\'(4|10)\')', '10天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb08_childcache', 'hqzb08(4|20)', 'addCondition(\'hqzb08\',\'(4|20)\')', '20天及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb11_childcache', 'hqzb11_def', 'clearType(\'hqzb11\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb11_childcache', 'hqzb11(1|3)', 'addCondition(\'hqzb11\',\'(1|3)\')', '3日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb11_childcache', 'hqzb11(1|5)', 'addCondition(\'hqzb11\',\'(1|5)\')', '5日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb11_childcache', 'hqzb11(1|10)', 'addCondition(\'hqzb11\',\'(1|10)\')', '10日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb11_childcache', 'hqzb11(1|20)', 'addCondition(\'hqzb11\',\'(1|20)\')', '20日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb11_childcache', 'hqzb11(1|120)', 'addCondition(\'hqzb11\',\'(1|120)\')', '120日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb11_childcache', 'hqzb11(1|240)', 'addCondition(\'hqzb11\',\'(1|240)\')', '240日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb11_childcache', 'hqzb11(1|720)', 'addCondition(\'hqzb11\',\'(1|720)\')', '720日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb12_childcache', 'hqzb12_def', 'clearType(\'hqzb12\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb12_childcache', 'hqzb12(1|3)', 'addCondition(\'hqzb12\',\'(1|3)\')', '3日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb12_childcache', 'hqzb12(1|5)', 'addCondition(\'hqzb12\',\'(1|5)\')', '5日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb12_childcache', 'hqzb12(1|10)', 'addCondition(\'hqzb12\',\'(1|10)\')', '10日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb13_childcache', 'hqzb13_def', 'clearType(\'hqzb13\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb13_childcache', 'hqzb13(1|3)', 'addCondition(\'hqzb13\',\'(1|3)\')', '3日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb13_childcache', 'hqzb13(1|5)', 'addCondition(\'hqzb13\',\'(1|5)\')', '5日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb13_childcache', 'hqzb13(1|10)', 'addCondition(\'hqzb13\',\'(1|10)\')', '10日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb13_childcache', 'hqzb13(1|20)', 'addCondition(\'hqzb13\',\'(1|20)\')', '20日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb13_childcache', 'hqzb13(1|120)', 'addCondition(\'hqzb13\',\'(1|120)\')', '120日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb13_childcache', 'hqzb13(1|240)', 'addCondition(\'hqzb13\',\'(1|240)\')', '240日', null, null);
INSERT INTO `east_select_detail` VALUES ('hqzb13_childcache', 'hqzb13(1|720)', 'addCondition(\'hqzb13\',\'(1|720)\')', '720日', null, null);
INSERT INTO `east_select_detail` VALUES ('61', 'gbzb_def', 'clearType(\'gbzb\')', '不限', '元', null);
INSERT INTO `east_select_detail` VALUES ('61', 'gbzb01', 'addCondition(\'gbzb\',\'01\')', '总股本', '万', null);
INSERT INTO `east_select_detail` VALUES ('61', 'gbzb02', 'addCondition(\'gbzb\',\'02\')', 'A股合计', '万元', null);
INSERT INTO `east_select_detail` VALUES ('61', 'gbzb03', 'addCondition(\'gbzb\',\'03\')', '流通A股', '万元', null);
INSERT INTO `east_select_detail` VALUES ('61', 'gbzb06', 'addCondition(\'gbzb\',\'06\')', '前十大股东持股比例合计', '%', null);
INSERT INTO `east_select_detail` VALUES ('61', 'gbzb08', 'addCondition(\'gbzb\',\'08\')', '前十大流通股东持股比例合计', '%', null);
INSERT INTO `east_select_detail` VALUES ('gbzb01_childcache', 'gbzb01_def', 'clearType(\'gbzb01\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb01_childcache', 'gbzb01(2|5000w)', 'addCondition(\'gbzb01\',\'(2|5000w)\')', '5000万以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb01_childcache', 'gbzb01(2|1y)', 'addCondition(\'gbzb01\',\'(2|1y)\')', '1亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb01_childcache', 'gbzb01(2|2y)', 'addCondition(\'gbzb01\',\'(2|2y)\')', '2亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb01_childcache', 'gbzb01(2|5y)', 'addCondition(\'gbzb01\',\'(2|5y)\')', '5亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb01_childcache', 'gbzb01(2|10y)', 'addCondition(\'gbzb01\',\'(2|10y)\')', '10亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb01_childcache', 'gbzb01(2|50y)', 'addCondition(\'gbzb01\',\'(2|50y)\')', '50亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb02_childcache', 'gbzb02_def', 'clearType(\'gbzb02\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb02_childcache', 'gbzb02(2|5000w)', 'addCondition(\'gbzb02\',\'(2|5000w)\')', '5000万以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb02_childcache', 'gbzb02(2|1y)', 'addCondition(\'gbzb02\',\'(2|1y)\')', '1亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb02_childcache', 'gbzb02(2|2y)', 'addCondition(\'gbzb02\',\'(2|2y)\')', '2亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb02_childcache', 'gbzb02(2|5y)', 'addCondition(\'gbzb02\',\'(2|5y)\')', '5亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb02_childcache', 'gbzb02(2|10y)', 'addCondition(\'gbzb02\',\'(2|10y)\')', '10亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb02_childcache', 'gbzb02(2|50y)', 'addCondition(\'gbzb02\',\'(2|50y)\')', '50亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb03_childcache', 'gbzb03_def', 'clearType(\'gbzb03\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb03_childcache', 'gbzb03(2|5000w)', 'addCondition(\'gbzb03\',\'(2|5000w)\')', '5000万以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb03_childcache', 'gbzb03(2|1y)', 'addCondition(\'gbzb03\',\'(2|1y)\')', '1亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb03_childcache', 'gbzb03(2|2y)', 'addCondition(\'gbzb03\',\'(2|2y)\')', '2亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb03_childcache', 'gbzb03(2|5y)', 'addCondition(\'gbzb03\',\'(2|5y)\')', '5亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb03_childcache', 'gbzb03(2|10y)', 'addCondition(\'gbzb03\',\'(2|10y)\')', '10亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb03_childcache', 'gbzb03(2|50y)', 'addCondition(\'gbzb03\',\'(2|50y)\')', '50亿以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb06_childcache', 'gbzb06_def', 'clearType(\'gbzb06\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb06_childcache', 'gbzb06(4|0.05)', 'addCondition(\'gbzb06\',\'(4|0.05)\')', '5%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb06_childcache', 'gbzb06(4|0.1)', 'addCondition(\'gbzb06\',\'(4|0.1)\')', '10%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb06_childcache', 'gbzb06(4|0.15)', 'addCondition(\'gbzb06\',\'(4|0.15)\')', '15%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb06_childcache', 'gbzb06(4|0.2)', 'addCondition(\'gbzb06\',\'(4|0.2)\')', '20%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb06_childcache', 'gbzb06(4|0.3)', 'addCondition(\'gbzb06\',\'(4|0.3)\')', '30%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb08_childcache', 'gbzb08_def', 'clearType(\'gbzb08\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb08_childcache', 'gbzb08(4|0.05)', 'addCondition(\'gbzb08\',\'(4|0.05)\')', '5%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb08_childcache', 'gbzb08(4|0.1)', 'addCondition(\'gbzb08\',\'(4|0.1)\')', '10%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb08_childcache', 'gbzb08(4|0.15)', 'addCondition(\'gbzb08\',\'(4|0.15)\')', '15%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb08_childcache', 'gbzb08(4|0.2)', 'addCondition(\'gbzb08\',\'(4|0.2)\')', '20%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gbzb08_childcache', 'gbzb08(4|0.3)', 'addCondition(\'gbzb08\',\'(4|0.3)\')', '30%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('62', 'gdhs_def', 'clearType(\'gdhs\')', '不限', '元', null);
INSERT INTO `east_select_detail` VALUES ('62', 'gdhs01', 'addCondition(\'gdhs\',\'01\')', '股东户数', '家', null);
INSERT INTO `east_select_detail` VALUES ('62', 'gdhs02', 'addCondition(\'gdhs\',\'02\')', '户均持股数量', '股', null);
INSERT INTO `east_select_detail` VALUES ('62', 'gdhs04', 'showChildConditions(\'gdhs\',\'04\')', '户均持股数季度增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('62', 'gdhs06', 'showChildConditions(\'gdhs\',\'06\')', '户均持股数半年增长率', '%', null);
INSERT INTO `east_select_detail` VALUES ('gdhs04_childcache', 'gdhs04_def', 'clearType(\'gdhs04\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gdhs04_childcache', 'gdhs04(4|0.1)', 'addCondition(\'gdhs04\',\'(4|0.1)\')', '10%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gdhs04_childcache', 'gdhs04(4|0.3)', 'addCondition(\'gdhs04\',\'(4|0.3)\')', '30%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gdhs04_childcache', 'gdhs04(4|0.5)', 'addCondition(\'gdhs04\',\'(4|0.5)\')', '50%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gdhs04_childcache', 'gdhs04(4|1)', 'addCondition(\'gdhs04\',\'(4|1)\')', '100%及以上', null, null);
INSERT INTO `east_select_detail` VALUES ('gdhs06_childcache', 'gdhs06_def', 'clearType(\'gdhs06\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('gdhs06_childcache', 'gdhs06(5|-0.2)', 'addCondition(\'gdhs06\',\'(5|-0.2)\')', '-20%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gdhs06_childcache', 'gdhs06(5|-0.1)', 'addCondition(\'gdhs06\',\'(5|-0.1)\')', '-10%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gdhs06_childcache', 'gdhs06(5|0)', 'addCondition(\'gdhs06\',\'(5|0)\')', '0及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gdhs06_childcache', 'gdhs06(5|0.1)', 'addCondition(\'gdhs06\',\'(5|0.1)\')', '10%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('gdhs06_childcache', 'gdhs06(5|0.2)', 'addCondition(\'gdhs06\',\'(5|0.2)\')', '20%及以下', null, null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg_def', 'clearType(\'jgcg\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg01', 'addCondition(\'jgcg\',\'01\')', '机构持股合计', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg02', 'addCondition(\'jgcg\',\'02\')', '基金', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg03', 'addCondition(\'jgcg\',\'03\')', '基金资产管理计划', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg04', 'addCondition(\'jgcg\',\'04\')', '券商', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg05', 'addCondition(\'jgcg\',\'05\')', '券商理财产品', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg06', 'addCondition(\'jgcg\',\'06\')', 'QFII', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg07', 'addCondition(\'jgcg\',\'07\')', '保险公司', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg08', 'addCondition(\'jgcg\',\'08\')', '保险产品', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg09', 'addCondition(\'jgcg\',\'09\')', '社保', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg10', 'addCondition(\'jgcg\',\'10\')', '企业年金', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg11', 'addCondition(\'jgcg\',\'11\')', '信托公司', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg12', 'addCondition(\'jgcg\',\'12\')', '信托计划', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg13', 'addCondition(\'jgcg\',\'13\')', '财务公司', '-', null);
INSERT INTO `east_select_detail` VALUES ('63', 'jgcg14', 'addCondition(\'jgcg\',\'14\')', '银行', '-', null);
INSERT INTO `east_select_detail` VALUES ('7', 'ggxg_def', 'clearType(\'ggxg\')', '不限', null, null);
INSERT INTO `east_select_detail` VALUES ('7', 'ggxg01', 'addCondition(\'ggxg\',\'01\')', '最近一个月有增发相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('7', 'ggxg02', 'addCondition(\'ggxg\',\'02\')', '最近一个月有重组相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('7', 'ggxg03', 'addCondition(\'ggxg\',\'03\')', '最近一个月有业绩预告相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('7', 'ggxg04', 'addCondition(\'ggxg\',\'04\')', '最近一个月有股权激励相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('7', 'ggxg05', 'addCondition(\'ggxg\',\'05\')', '最近一个月有政府补贴相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('7', 'ggxg06', 'addCondition(\'ggxg\',\'06\')', '最近一个月有分红派息相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('7', 'ggxg07', 'addCondition(\'ggxg\',\'07\')', '最近一个月有重大合同相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('7', 'ggxg08', 'addCondition(\'ggxg\',\'08\')', '最近一个月有项目投资相关公告', '-', null);
INSERT INTO `east_select_detail` VALUES ('7', 'ggxg09', 'addCondition(\'ggxg\',\'09\')', '最近一个月有高管变动相关公告', '-', null);
