-- ----------------------------
-- Table structure for east_select_first
-- ----------------------------
DROP TABLE IF EXISTS `east_select_first`;
CREATE TABLE `east_select_first` (
  `name` varchar(10) DEFAULT NULL COMMENT '指标名称',
  `focus` varchar(10) DEFAULT NULL COMMENT '选中指标',
  `items` tinyint DEFAULT NULL COMMENT '指标条目',
  `list_cont` varchar(10) DEFAULT NULL COMMENT '指标列表内容'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='选股器指标一层';

DROP TABLE IF EXISTS `east_select_list`;
CREATE TABLE `east_select_list` (
  `list_cont` varchar(10) DEFAULT NULL COMMENT '上层指标',
  `pre_tit` varchar(50) DEFAULT NULL COMMENT '指标标题',
  `name` varchar(50) DEFAULT NULL COMMENT '指标名称',
  `colsable` tinyint DEFAULT NULL COMMENT '指标折叠标识',
  `ul` varchar(10) DEFAULT NULL COMMENT '指标列表内容',
  `selectorcache` varchar(10) DEFAULT NULL COMMENT '子指标列表内容'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='选股器指标列表';

DROP TABLE IF EXISTS `east_select_detail`;
CREATE TABLE `east_select_detail` (
  `ul` varchar(20) DEFAULT NULL COMMENT '上层指标标识',
  `id` varchar(20) DEFAULT NULL COMMENT '指标标识',
  `ck` varchar(50) DEFAULT NULL COMMENT '指标条件',
  `text` varchar(50) DEFAULT NULL COMMENT '指标内容',
  `unit` varchar(10) DEFAULT NULL COMMENT '指标单位',
  `clf` varchar(10) DEFAULT NULL COMMENT '指标类别'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='选股器指标明细';
