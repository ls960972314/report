/*
MySQL Backup
Source Server Version: 5.6.29
Source Database: report
Date: 2016/10/25 17:02:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `rp_report_sql`
-- ----------------------------
DROP TABLE IF EXISTS `rp_report_sql`;
CREATE TABLE `rp_report_sql` (
  `sql_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `base_count_sql` text,
  `base_sql` text,
  `rptname` varchar(200) DEFAULT NULL,
  `procedure` varchar(200) DEFAULT NULL,
  `database_source` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sql_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `rptchart`
-- ----------------------------
DROP TABLE IF EXISTS `rptchart`;
CREATE TABLE `rptchart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `chartoption` varchar(4000) DEFAULT NULL COMMENT 'ͼ??option',
  `datavsle` varchar(2000) DEFAULT NULL COMMENT 'legend?????ݿ??ж?Ӧ??ϵ',
  `toolflag` varchar(255) DEFAULT NULL COMMENT '??????־',
  `order_num` tinyint(4) DEFAULT NULL COMMENT '˳?',
  `name` varchar(100) DEFAULT NULL COMMENT 'ͼ?????',
  `charttype` varchar(10) DEFAULT NULL COMMENT 'ͼ?????',
  `datavsx` varchar(50) DEFAULT NULL COMMENT 'X???????ݿ??ж?Ӧ??ϵ',
  `field1` varchar(255) DEFAULT NULL COMMENT 'Ԥ????1',
  `field2` varchar(255) DEFAULT NULL COMMENT 'Ԥ????2',
  `show_rownum` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rptchart_toolflag` (`toolflag`(191))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `rptcmcon`
-- ----------------------------
DROP TABLE IF EXISTS `rptcmcon`;
CREATE TABLE `rptcmcon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `toolflag` varchar(255) DEFAULT NULL COMMENT '??????־',
  `conflag` varchar(255) DEFAULT NULL COMMENT '??????־',
  `conwhere` varchar(255) DEFAULT NULL COMMENT 'ƥ??sql????',
  `convalue` varchar(255) DEFAULT NULL COMMENT '????ֵ',
  PRIMARY KEY (`id`),
  KEY `rptcomcon_flag` (`toolflag`(191),`conflag`(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `rptcon`
-- ----------------------------
DROP TABLE IF EXISTS `rptcon`;
CREATE TABLE `rptcon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `conmuti` varchar(4000) DEFAULT NULL COMMENT '??ѡ???ߵ?ѡʱ??ֵ(??Ϊsql)',
  `conname` varchar(2000) DEFAULT NULL COMMENT '???????',
  `conoption` varchar(255) DEFAULT NULL COMMENT '???????',
  `contype` varchar(255) DEFAULT NULL COMMENT '??ý??????(?ı?,????)',
  `conwhere` varchar(255) DEFAULT NULL COMMENT 'ƥ??sql????',
  `toolflag` varchar(255) DEFAULT NULL COMMENT '??????־',
  `order_num` tinyint(4) DEFAULT NULL COMMENT '˳?',
  `chartid` bigint(20) DEFAULT NULL COMMENT 'ͼ??ID',
  `default_value` varchar(500) DEFAULT NULL COMMENT 'Ĭ??ֵ,???????????ڵ?????ֵ??Сֵ',
  `row_num` tinyint(4) DEFAULT NULL COMMENT '????',
  `field1` varchar(255) DEFAULT NULL COMMENT 'Ԥ????1',
  `field2` varchar(255) DEFAULT NULL COMMENT 'Ԥ????2',
  `database_source` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rptcon_toolflag` (`toolflag`(191))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `rptlog`
-- ----------------------------
DROP TABLE IF EXISTS `rptlog`;
CREATE TABLE `rptlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `user_name` varchar(50) DEFAULT NULL COMMENT '?û???',
  `ope_action` varchar(50) DEFAULT NULL COMMENT '????????',
  `ope_id` varchar(200) DEFAULT NULL COMMENT 'ִ?е?SQLID????TOOLFLAG',
  `waste_time` varchar(10) DEFAULT NULL COMMENT '????ʱ?',
  `exception` varchar(1000) DEFAULT NULL COMMENT '?쳣??Ϣ',
  `create_time` datetime DEFAULT NULL COMMENT '????ʱ?',
  PRIMARY KEY (`id`),
  KEY `rptlog_action` (`ope_action`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `rptmodel`
-- ----------------------------
DROP TABLE IF EXISTS `rptmodel`;
CREATE TABLE `rptmodel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `modelname` varchar(500) DEFAULT NULL COMMENT 'ģ?????',
  `conname` varchar(255) DEFAULT NULL COMMENT 'ģ????????(???Ÿ???)',
  `send_usernames` varchar(4000) DEFAULT NULL COMMENT '?ʼ???????(???Ÿ???)',
  `create_time` datetime DEFAULT NULL COMMENT '????ʱ?',
  `modeltitle` varchar(1000) DEFAULT NULL,
  `save_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `rptpub`
-- ----------------------------
DROP TABLE IF EXISTS `rptpub`;
CREATE TABLE `rptpub` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `toolccolumn` varchar(2000) DEFAULT NULL COMMENT '????????????',
  `tooldsqlid` varchar(255) DEFAULT NULL COMMENT '??sql',
  `toolecolumn` varchar(2000) DEFAULT NULL COMMENT 'sql??ѯ????Ӣ?ı',
  `toolflag` varchar(255) DEFAULT NULL COMMENT '??????־',
  `toolhsqlid` varchar(255) DEFAULT NULL COMMENT 'ʱsql',
  `toolmsqlid` varchar(255) DEFAULT NULL COMMENT '??sql',
  `tooltitle` varchar(2000) DEFAULT NULL COMMENT '???????',
  `toolwsqlid` varchar(255) DEFAULT NULL COMMENT '??sql',
  `toolqsqlid` varchar(255) DEFAULT NULL COMMENT '??sql',
  `toolysqlid` varchar(255) DEFAULT NULL COMMENT '??sql',
  `gather_column` varchar(500) DEFAULT NULL COMMENT '?????',
  `format` varchar(2000) DEFAULT NULL COMMENT '??ʽ??',
  `field1` varchar(255) DEFAULT NULL COMMENT 'Ԥ????1',
  `field2` varchar(255) DEFAULT NULL COMMENT 'Ԥ????2',
  `static_rownum` tinyint(4) DEFAULT NULL,
  `static_ccolumn` varchar(500) DEFAULT NULL,
  `static_sql` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rptpub_toolflag` (`toolflag`(191))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `rptpublishconfig`
-- ----------------------------
DROP TABLE IF EXISTS `rptpublishconfig`;
CREATE TABLE `rptpublishconfig` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '????',
  `model_id` bigint(20) DEFAULT NULL COMMENT 'ģ??ID',
  `toolflag` varchar(255) DEFAULT NULL COMMENT '????FLAG',
  `rptcon_id` varchar(1000) DEFAULT NULL COMMENT '????????(rptcon)ID',
  `modelconname` varchar(1000) DEFAULT NULL COMMENT 'ģ????????',
  `default_value` varchar(255) DEFAULT NULL COMMENT '????Ĭ??ֵ(?޷???Ӧģ??????ʱ)',
  `sql_id` varchar(255) DEFAULT NULL COMMENT '????ԴSQLID',
  `rpt_comment` varchar(4000) DEFAULT NULL COMMENT '????????˵??',
  `chartshow` varchar(10) DEFAULT NULL COMMENT '?Ƿ???ʾͼ(Y??ʾ N????ʾ)',
  `tableshow` varchar(10) DEFAULT NULL COMMENT '?Ƿ???ʾ??(Y??ʾ N????ʾ)',
  `reportname` varchar(1000) DEFAULT NULL,
  `reporttime` varchar(1000) DEFAULT NULL,
  `title` varchar(1000) DEFAULT NULL,
  `chart_id` bigint(20) DEFAULT NULL,
  `chart_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `uc_group`
-- ----------------------------
DROP TABLE IF EXISTS `uc_group`;
CREATE TABLE `uc_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_code` varchar(24) DEFAULT NULL,
  `group_name` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `group_type` tinyint(4) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2001 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `uc_group_role`
-- ----------------------------
DROP TABLE IF EXISTS `uc_group_role`;
CREATE TABLE `uc_group_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_code` varchar(64) DEFAULT NULL,
  `role_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=504 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `uc_member`
-- ----------------------------
DROP TABLE IF EXISTS `uc_member`;
CREATE TABLE `uc_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acc_no` varchar(100) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `member_type` tinyint(4) DEFAULT NULL,
  `id_card` varchar(256) DEFAULT NULL,
  `birth` datetime DEFAULT NULL,
  `extend` varchar(100) DEFAULT NULL,
  `user_nick` varchar(30) DEFAULT NULL,
  `user_city` varchar(30) DEFAULT NULL,
  `bank_id` bigint(20) DEFAULT NULL,
  `bank_name` varchar(60) DEFAULT NULL,
  `card_no` varchar(19) DEFAULT NULL,
  `auth_last_time` datetime DEFAULT NULL,
  `qrcode_url` varchar(500) DEFAULT NULL,
  `partner` varchar(25) DEFAULT NULL,
  `sch_id` bigint(20) DEFAULT NULL,
  `change_card_last_time` datetime DEFAULT NULL,
  `qr_email_image_url` varchar(500) DEFAULT NULL,
  `cas_type` tinyint(4) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `password_u` varchar(32) DEFAULT NULL,
  `member_resource` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `uc_member_group`
-- ----------------------------
DROP TABLE IF EXISTS `uc_member_group`;
CREATE TABLE `uc_member_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `member_id` bigint(20) DEFAULT NULL,
  `group_code` varchar(64) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2086 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `uc_resource`
-- ----------------------------
DROP TABLE IF EXISTS `uc_resource`;
CREATE TABLE `uc_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `resource_action` varchar(255) DEFAULT NULL,
  `resource_type` varchar(10) DEFAULT NULL,
  `p_id` bigint(20) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `order_by` bigint(20) DEFAULT NULL,
  `sys_code` varchar(64) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `old_id` varchar(128) DEFAULT NULL,
  `old_pid` varchar(128) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  `resource_code` varchar(32) DEFAULT NULL,
  `temp_p_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2880 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `uc_role`
-- ----------------------------
DROP TABLE IF EXISTS `uc_role`;
CREATE TABLE `uc_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(64) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `sys_code` varchar(64) DEFAULT NULL,
  `old_id` varchar(64) DEFAULT NULL,
  `creater_id` bigint(20) DEFAULT NULL,
  `modifier_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7788 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `uc_role_res`
-- ----------------------------
DROP TABLE IF EXISTS `uc_role_res`;
CREATE TABLE `uc_role_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_code` varchar(64) DEFAULT NULL,
  `role_code` varchar(64) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=943 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `rp_report_sql` VALUES ('1','select count(1)   from (select date_format(create_time, \'%Y-%m-%d\') create_time,                id,                p_id,                name,                resource_action           from uc_resource          where create_time > str_to_date(:begintime, \'%Y-%m-%d\')            and create_time < str_to_date(:endtime, \'%Y-%m-%d\') + 1 ) vvv','select date_format(create_time, \'%Y-%m-%d %T\') create_time,        id,        p_id,        name,        resource_action   from uc_resource   where create_time > str_to_date(:begintime, \'%Y-%m-%d\')    and create_time < str_to_date(:endtime, \'%Y-%m-%d\') + 1   order by id desc','柱状图1SQL',NULL,'ods');
INSERT INTO `rptchart` VALUES ('1','{\"title\":{\"text\":\"图标标题\",\"subtext\":\"图标解释\"},\"tooltip\":{\"trigger\":\"axis\"},\"legend\":{\"data\":[\"ID\",\"父ID\"]},\"toolbox\":{\"show\":true,\"feature\":{\"mark\":{\"show\":true},\"dataView\":{\"show\":true,\"readOnly\":false},\"magicType\":{\"show\":true,\"type\":[\"line\",\"bar\"]},\"restore\":{\"show\":true},\"saveAsImage\":{\"show\":true}}},\"calculable\":true,\"xAxis\":[{\"type\":\"category\",\"data\":[\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\"]},{\"type\":\"category\",\"axisLine\":{\"show\":false},\"axisTick\":{\"show\":false},\"axisLabel\":{\"show\":false},\"splitArea\":{\"show\":false},\"splitLine\":{\"show\":false},\"data\":[\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\",\"2016-10-25\"]}],\"yAxis\":[{\"type\":\"value\"}],\"series\":[{\"name\":\"ID\",\"type\":\"bar\",\"data\":[2870,2871,2872,2873,2874,2875,2876,2877,2878,2879]},{\"name\":\"父ID\",\"type\":\"bar\",\"data\":[2869,46,2871,46,2873,46,2875,46,2877,2862]}]}','id:ID,p_id:父ID,name:资源名称,resource_action:资源URL','bar1','1','柱状图随意测试1','bar','create_time:创建时间',NULL,NULL,NULL);
INSERT INTO `rptcon` VALUES ('1','','开始时间','input','日期','begintime','bar1','1',NULL,'','1',NULL,NULL,'ods'), ('2','','结束时间','input','日期','endtime','bar1','2',NULL,'','1',NULL,NULL,'ods');
INSERT INTO `rptlog` VALUES ('1','','reportMakeQueryData','null','7074',NULL,'2016-10-25 16:21:41'), ('2','','reportMakeQueryData','null','20616',NULL,'2016-10-25 16:22:30'), ('3','','reportMakeQueryData','null','37773',NULL,'2016-10-25 16:23:24'), ('4','','reportMakeQueryData','null','58867',NULL,'2016-10-25 16:28:11'), ('5','','reportMakeQueryData','null','40',NULL,'2016-10-25 16:29:01'), ('6','','reportMakeQueryData','null','34',NULL,'2016-10-25 16:31:01'), ('7','','reportMakeQueryData','null','36',NULL,'2016-10-25 16:34:48'), ('8','','reportMakeQueryData','null','36',NULL,'2016-10-25 16:36:34'), ('9','','reportMakeQueryData','null','592',NULL,'2016-10-25 16:36:39'), ('10','','reportMakeQueryData','null','556',NULL,'2016-10-25 16:36:42'), ('11','','reportMakeQueryData','null','37',NULL,'2016-10-25 16:36:44'), ('12','','saveReport','bar1','189',NULL,'2016-10-25 16:37:55'), ('13','','reportShowQueryData','1','0','com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Every derived table must have its own alias','2016-10-25 16:38:06'), ('14','','reportShowQueryData','1','0','com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Every derived table must have its own alias','2016-10-25 16:44:56'), ('15','','updateReportSql','1','155',NULL,'2016-10-25 16:51:49'), ('16','','reportShowQueryData','1','0','com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'> str_to_date(\'2016-10-01\',\'%Y-%m-%d\')and create_time < str_to_date(\'2016-10-18\'\' at line 1','2016-10-25 16:51:59'), ('17','','updateReportSql','1','46',NULL,'2016-10-25 16:53:03'), ('18','','reportShowQueryData','1','0','java.math.BigInteger cannot be cast to java.math.BigDecimal','2016-10-25 16:53:04'), ('19','','reportShowQueryData','1','0','java.math.BigInteger cannot be cast to java.math.BigDecimal','2016-10-25 16:53:12'), ('20','','reportShowQueryData','1','336',NULL,'2016-10-25 16:55:45'), ('21','','updateReportSql','1','62',NULL,'2016-10-25 16:56:03'), ('22','','reportShowQueryData','1','143',NULL,'2016-10-25 16:56:06'), ('23','','reportShowQueryData','1','141',NULL,'2016-10-25 16:56:22'), ('24','','reportShowQueryData','1','133',NULL,'2016-10-25 16:56:25');
INSERT INTO `rptpub` VALUES ('1','创建时间,ID,父ID,资源名称,资源URL','1 ','create_time,id,p_id,name,resource_action','bar1',NULL,NULL,'柱状图1',NULL,NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL);
INSERT INTO `uc_group` VALUES ('2000','per_group','',NULL,'2016-10-18 12:55:47','1','1',NULL,'200000016',NULL);
INSERT INTO `uc_group_role` VALUES ('503','per_group','per_admin');
INSERT INTO `uc_member` VALUES ('100','perAdmin','E10ADC3949BA59ABBE56E057F20F883E',NULL,'',NULL,'1','2016-10-18 12:55:47','2016-10-18 12:55:47',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'1','200000016',NULL,NULL,NULL);
INSERT INTO `uc_member_group` VALUES ('2085','100','per_group','1',NULL,'2016-10-18 12:55:47');
INSERT INTO `uc_resource` VALUES ('46','本地资源管理',NULL,'module',NULL,NULL,'本地资源管理','2016-10-25 15:00:36','2016-10-25 15:00:36','1','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('49','资源管理','resource/resource.htm','menu','46',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','3','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('50','添加资源','add.htm','op','49',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36',NULL,'per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('81','角色管理','role/role.htm','menu','46',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','2','per','1',NULL,NULL,NULL,NULL,'role_codeasdsa',NULL), ('82','组别管理','group/group.htm','menu','46',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','4','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('83','人员管理','member/member.htm','menu','46',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','5','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('845','报表资源管理',NULL,'module',NULL,NULL,'报表资源管理',NULL,NULL,'2','omp','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2060','删除','role/del.htm','button','81',NULL,NULL,'2016-10-25 15:00:37','2016-10-25 15:00:37','3','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2080','新建编辑','role/addOrUpdate.htm','button','81',NULL,'又新增就有编辑权限','2016-10-25 15:00:37','2016-10-25 15:00:37','1','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2082','详情','role/showDetail.htm','button','81',NULL,NULL,'2016-10-25 15:00:37','2016-10-25 15:00:37','4','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2083','查看资源','resource/getPartialDistriResources.htm','button','81',NULL,NULL,'2016-10-25 15:00:37','2016-10-25 15:00:37','5','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2084','分配权限','role/saveRoleResources.htm','button','81',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','6','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2085','新建编辑','resource/addOrUpdate.htm','button','49',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','1','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2086','详情','resource/showDetail.htm','button','49',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','2','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2087','删除','resource/del.htm','button','49',NULL,NULL,'2016-10-25 15:00:37','2016-10-25 15:00:37','3','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2088','查看角色','role/getRoleCodeAndNameList.htm','button','82',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','6','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2089','删除','group/deleteGroup.htm','button','82',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','3','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2090','详情','group/showDetail.htm','button','82',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','4','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2091','新建编辑','group/addOrUpdateGroup.htm','button','82',NULL,'又新增就有编辑权限','2016-10-25 15:00:36','2016-10-25 15:00:36','1','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2092','重置密码','member/resetPassword.htm','button','83',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','6','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2093','删除','member/deleteMember.htm','button','83',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','3','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2094','详情','member/showDetail.htm','button','83',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','4','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2095','新建编辑','member/addOrUpdateMember.htm','button','83',NULL,'又新增就有编辑权限','2016-10-25 15:00:37','2016-10-25 15:00:37','1','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2220','分配临时权限','memberTempPriv/distriTempResource.htm','button','83',NULL,NULL,'2016-10-25 15:00:37','2016-10-25 15:00:37','5','per','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2860','代理商按地推排名','/activeopr','menu','680',NULL,NULL,'2016-10-25 15:00:36','2016-10-25 15:00:36','1','omp','1',NULL,NULL,NULL,NULL,NULL,NULL), ('2861','报表展示','','menu','845',NULL,'','2016-10-25 15:36:03','2016-10-25 16:08:31','1','omp','1',NULL,NULL,NULL,NULL,'',NULL), ('2862','柱状图菜单','','menu','2861',NULL,'','2016-10-25 16:04:08','2016-10-25 16:08:17','1','omp','1',NULL,NULL,NULL,NULL,'',NULL), ('2863','报表工具','','menu','845',NULL,'','2016-10-25 16:08:40','2016-10-25 16:08:40','1','omp','1',NULL,NULL,NULL,NULL,'',NULL), ('2864','创建报表','','menu','2863',NULL,'','2016-10-25 16:09:16','2016-10-25 16:09:16','1','omp','1',NULL,NULL,NULL,NULL,'',NULL), ('2865','创建报表工具','tpl/tool/smartReport.jsp','menu','2864',NULL,'','2016-10-25 16:09:33','2016-10-25 16:09:33','1','omp','1',NULL,NULL,NULL,NULL,'',NULL), ('2866','多报表组合工具','tpl/tool/mutiReportCreate.jsp','menu','2864',NULL,'','2016-10-25 16:10:11','2016-10-25 16:10:11','1','omp','1',NULL,NULL,NULL,NULL,'',NULL), ('2867','创建报告模板','tpl/tool/createModelSend.jsp','menu','2864',NULL,'','2016-10-25 16:10:38','2016-10-25 16:10:38','1','omp','1',NULL,NULL,NULL,NULL,'',NULL), ('2868','发送报告','tpl/tool/modelSend.jsp','menu','2864',NULL,'','2016-10-25 16:10:57','2016-10-25 16:10:57','1','omp','1',NULL,NULL,NULL,NULL,'',NULL), ('2869','报表条件管理','condition/condition.htm','menu','46',NULL,'','2016-10-25 16:12:00','2016-10-25 16:12:00','1','per','1',NULL,NULL,NULL,NULL,'',NULL), ('2870','编辑条件','condition/update.htm','menu','2869',NULL,'','2016-10-25 16:12:15','2016-10-25 16:12:15','1','per','1',NULL,NULL,NULL,NULL,'',NULL), ('2871','报表公共信息管理','public/public.htm','menu','46',NULL,'','2016-10-25 16:12:45','2016-10-25 16:12:45','1','per','1',NULL,NULL,NULL,NULL,'',NULL), ('2872','编辑公共信息','public/update.htm','menu','2871',NULL,'','2016-10-25 16:12:59','2016-10-25 16:12:59','1','per','1',NULL,NULL,NULL,NULL,'',NULL), ('2873','报表SQL管理','reportSql/reportSql.htm','menu','46',NULL,'','2016-10-25 16:13:27','2016-10-25 16:13:27','1','per','1',NULL,NULL,NULL,NULL,'',NULL), ('2874','编辑报表SQL','reportSql/updateReportSql.htm','menu','2873',NULL,'','2016-10-25 16:13:42','2016-10-25 16:13:42','1','per','1',NULL,NULL,NULL,NULL,'',NULL), ('2875','报表图形管理','chart/chart.htm','menu','46',NULL,'','2016-10-25 16:14:02','2016-10-25 16:14:02','1','per','1',NULL,NULL,NULL,NULL,'',NULL), ('2876','编辑图形','chart/update.htm','menu','2875',NULL,'','2016-10-25 16:14:16','2016-10-25 16:14:16','1','per','1',NULL,NULL,NULL,NULL,'',NULL), ('2877','批量报表条件管理','commonCondition/commonCondition.htm','menu','46',NULL,'','2016-10-25 16:14:54','2016-10-25 16:14:54','1','per','1',NULL,NULL,NULL,NULL,'',NULL), ('2878','修改条件','commonCondition/addOrUpdateCommonCondition.htm','menu','2877',NULL,'','2016-10-25 16:15:12','2016-10-25 16:15:12','1','per','1',NULL,NULL,NULL,NULL,'',NULL), ('2879','柱状图1','tpl/tool/smartReportShow.jsp?reportFlag=bar1','menu','2862',NULL,'','2016-10-25 16:16:08','2016-10-25 16:16:08','1','omp','1',NULL,NULL,NULL,NULL,'',NULL);
INSERT INTO `uc_role` VALUES ('142','per_admin','管理员','','2016-10-18 12:55:47','2016-10-25 16:16:14','1',NULL,NULL,NULL,'100'), ('7787','common','',NULL,'2016-10-18 12:55:47','2016-10-18 12:55:47','1',NULL,NULL,'200000016','200000016');
INSERT INTO `uc_role_res` VALUES ('900',NULL,'per_admin','142','46'), ('901',NULL,'per_admin','142','2869'), ('902',NULL,'per_admin','142','2870'), ('903',NULL,'per_admin','142','2871'), ('904',NULL,'per_admin','142','2872'), ('905',NULL,'per_admin','142','2873'), ('906',NULL,'per_admin','142','2874'), ('907',NULL,'per_admin','142','2875'), ('908',NULL,'per_admin','142','2876'), ('909',NULL,'per_admin','142','2877'), ('910',NULL,'per_admin','142','2878'), ('911',NULL,'per_admin','142','81'), ('912',NULL,'per_admin','142','2080'), ('913',NULL,'per_admin','142','2060'), ('914',NULL,'per_admin','142','2082'), ('915',NULL,'per_admin','142','2083'), ('916',NULL,'per_admin','142','2084'), ('917',NULL,'per_admin','142','49'), ('918',NULL,'per_admin','142','50'), ('919',NULL,'per_admin','142','2085'), ('920',NULL,'per_admin','142','2086'), ('921',NULL,'per_admin','142','2087'), ('922',NULL,'per_admin','142','82'), ('923',NULL,'per_admin','142','2091'), ('924',NULL,'per_admin','142','2089'), ('925',NULL,'per_admin','142','2090'), ('926',NULL,'per_admin','142','2088'), ('927',NULL,'per_admin','142','83'), ('928',NULL,'per_admin','142','2095'), ('929',NULL,'per_admin','142','2093'), ('930',NULL,'per_admin','142','2094'), ('931',NULL,'per_admin','142','2220'), ('932',NULL,'per_admin','142','2092'), ('933',NULL,'per_admin','142','845'), ('934',NULL,'per_admin','142','2861'), ('935',NULL,'per_admin','142','2862'), ('936',NULL,'per_admin','142','2879'), ('937',NULL,'per_admin','142','2863'), ('938',NULL,'per_admin','142','2864'), ('939',NULL,'per_admin','142','2865'), ('940',NULL,'per_admin','142','2866'), ('941',NULL,'per_admin','142','2867'), ('942',NULL,'per_admin','142','2868');
