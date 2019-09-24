/*
 Navicat Premium Data Transfer

 Source Server         : localMysql
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : wh_mysql

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 24/09/2019 09:31:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for crs_dd_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `crs_dd_dictionary`;
CREATE TABLE `crs_dd_dictionary`  (
  `DICTYPECODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据字典分类代码',
  `DICCODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据字典代码',
  `DICNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据字典名称',
  `STARTTIME` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '生效时间',
  `STOPFLAG` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '停用标记',
  `STOPTIME` timestamp(0) NULL DEFAULT NULL COMMENT '结束时间',
  `RESERVED` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`DICTYPECODE`, `DICCODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crs_dd_dictype
-- ----------------------------
DROP TABLE IF EXISTS `crs_dd_dictype`;
CREATE TABLE `crs_dd_dictype`  (
  `DICCODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据字典分类代码',
  `DICNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据字典分类名称',
  `RESERVED` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`DICCODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crs_dd_sysparameter
-- ----------------------------
DROP TABLE IF EXISTS `crs_dd_sysparameter`;
CREATE TABLE `crs_dd_sysparameter`  (
  `PARAMETERCODE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内部参数代码',
  `PARAMETERNAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数名',
  `PARAMETERVALUE` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数值',
  `INPUTSCOP` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输入格式及范围描述',
  `ISMAINTAIN` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '0可维护；1：不可维护',
  `OPERATOR` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最近一次维护人员',
  `OPERATETIME` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最近一次变更时间',
  PRIMARY KEY (`PARAMETERCODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crs_sys_core_logs
-- ----------------------------
DROP TABLE IF EXISTS `crs_sys_core_logs`;
CREATE TABLE `crs_sys_core_logs`  (
  `LOGID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志ID',
  `ORGCODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构代码',
  `USERNO` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户登录代码',
  `USERNAME` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `IPADDR` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `BUSCODE` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型代码',
  `OPDESC` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊用途说明',
  `OPURL` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问URL',
  `OPTYPE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `OPFUN` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作功能',
  `OPTIME` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `OPTEXT` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作内容',
  PRIMARY KEY (`LOGID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统核心操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crs_sys_login_error
-- ----------------------------
DROP TABLE IF EXISTS `crs_sys_login_error`;
CREATE TABLE `crs_sys_login_error`  (
  `USERID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID',
  `USERERRNUM` int(11) NOT NULL DEFAULT 1 COMMENT '登录错误次数',
  `LSTTIME` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后错误时间',
  `LSTIP` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  PRIMARY KEY (`USERID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录错误信息日记录表，此表数据每日凌晨清空。' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crs_sys_logs
-- ----------------------------
DROP TABLE IF EXISTS `crs_sys_logs`;
CREATE TABLE `crs_sys_logs`  (
  `LOGID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志ID',
  `ORGCODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构代码',
  `USERNO` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户登录代码',
  `USERNAME` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `IPADDR` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `BUSCODE` char(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务类型代码',
  `OPDESC` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊用途说明',
  `OPURL` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问URL',
  `OPTYPE` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `OPFUN` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作功能',
  `OPTIME` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '操作时间',
  `OPTEXT` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作内容',
  PRIMARY KEY (`LOGID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rbac_org
-- ----------------------------
DROP TABLE IF EXISTS `rbac_org`;
CREATE TABLE `rbac_org`  (
  `ORGID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ORGCODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构编码，此系统为14位金融机构编码',
  `ORGNAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构简称',
  `ORGFNAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构全称',
  `ORGENAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构英文名称',
  `PORGID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级机构代码，空标示顶级机构',
  `SORGID` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构快速检索号',
  `ORGADDRESS` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构地址',
  `AREACODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构地区代码',
  `ORGSTS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '机构状态，1=正常，2=停用，3=注销',
  `LSTTIME` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后变更日期',
  `LSTUSER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后变更人',
  `REPORTSAVERULE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否允许另存报告',
  `REPORTPRINTRULE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否允许打印报告',
  `TIMELIMIT` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询时间，例如：08:20-15:32',
  `ADDRESS` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `ZIP` char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `LINKMAN` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `LINKMODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `OTHERLINKMODE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他联系方式',
  `CREATEUSER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATETIME` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ORGID`) USING BTREE,
  UNIQUE INDEX `UIDX_ORG_ORGCODE`(`ORGCODE`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织机构信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rbac_resource
-- ----------------------------
DROP TABLE IF EXISTS `rbac_resource`;
CREATE TABLE `rbac_resource`  (
  `RESID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统主键',
  `RESNO` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单唯一编码，用于LOG记录跟踪',
  `RESNAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `PRESID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级菜单ID',
  `RESURL` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `RESLEAF` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否末节点，Y=是，N=否',
  `RESORDER` bigint(20) NULL DEFAULT NULL COMMENT '菜单显示顺序',
  `RESSTS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '菜单状态，1=正常，2=停用，3=注销',
  `LSTTIME` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后变更日期',
  `LSTUSER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后变更人',
  PRIMARY KEY (`RESID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单资源信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rbac_role
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role`;
CREATE TABLE `rbac_role`  (
  `ROLEID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ROLENAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `ROLEDESC` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `ROLESTS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '角色状态，1=正常，2=停用，3=注销',
  `LSTTIME` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后变更日期',
  `LSTUSER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后变更人',
  `ORGID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建机构id',
  PRIMARY KEY (`ROLEID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rbac_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role_resource`;
CREATE TABLE `rbac_role_resource`  (
  `RESID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统主键',
  `ROLEID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PTYPE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'E' COMMENT '权限类型 E=Enable，G=GRANT',
  PRIMARY KEY (`RESID`, `ROLEID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色资源信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rbac_role_user
-- ----------------------------
DROP TABLE IF EXISTS `rbac_role_user`;
CREATE TABLE `rbac_role_user`  (
  `ROLEID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `USERID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ROLEID`, `USERID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rbac_user
-- ----------------------------
DROP TABLE IF EXISTS `rbac_user`;
CREATE TABLE `rbac_user`  (
  `USERID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `USERNO` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户代码或工号',
  `USERNAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `USERPWD` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `USERPWDDAY` date NULL DEFAULT NULL COMMENT '上次修改密码日期',
  `PWDINVALID` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '密码修改标识：1：不修改  0：修改',
  `PMD5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限校验码，权限集合的MD5值',
  `USERSTS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '用户状态，1=正常，2=停用',
  `LSTTIME` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '锁定变更日期',
  `LSTUSER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '锁定变更人',
  `ORGID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户所属机构',
  `DEPTNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户在所属机构的部门',
  `REPORTSAVERULE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否允许保存信用报告',
  `REPORTPRINTRULE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否允许打印信用报告',
  `ISAMOUNTLIMIT` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单日是否限制最大查询量(0：是；1：否)',
  `AMOUNTLIMIT` int(11) NULL DEFAULT NULL COMMENT '单日最大查询量',
  `ISTIMELIMIT` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询时间是否限制(0：是；1：否)',
  `TIMELIMIT` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询时间，例如：08:20-15:32',
  `ISFREQUENCYLIMIT` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否设置最大查询频率(0:是,1:否)',
  `FREQUENCYLIMIT` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '每分钟查询最大频率',
  `BIND_ADDRESS_NUM` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定地址',
  `USERLEVEL` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户级别，S-超级管理员；A-管理员；B-业务员',
  `CREATEUSER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `CREATETIME` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `STARTUSER` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '启停变更人',
  `STARTTIME` timestamp(0) NULL DEFAULT NULL COMMENT '启停变更时间',
  `USERLOCKSTS` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户锁定状态0-正常；1-锁定',
  `IDNO` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `LSTLOGINDATE` date NULL DEFAULT NULL COMMENT '最后一次登录时间',
  PRIMARY KEY (`USERID`) USING BTREE,
  UNIQUE INDEX `UIDX_USER_USERNO`(`USERNO`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
