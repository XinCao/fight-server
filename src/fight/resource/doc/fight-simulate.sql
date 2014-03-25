/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : fighter_simulate

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2014-02-24 10:10:31
*/
USE fighter_simulate;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `fighter`
-- ----------------------------
DROP TABLE IF EXISTS `fighter`;
CREATE TABLE `fighter` (
  `name` varchar(255) NOT NULL DEFAULT '',
  `life` int(11) DEFAULT NULL,
  `phy_atk` int(11) DEFAULT NULL,
  `mag_atk` int(11) DEFAULT NULL,
  `phy_def` int(11) DEFAULT NULL,
  `mag_def` int(11) DEFAULT NULL,
  `crit` int(11) DEFAULT NULL,
  `hit` int(11) DEFAULT NULL,
  `dodge` int(11) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fighter
-- ----------------------------
INSERT INTO `fighter` VALUES ('caoxin', '1000', '50', '0', '10', '0', '2', '1000', '10');
INSERT INTO `fighter` VALUES ('lijing', '1000', '40', '0', '20', '0', '4', '1000', '100');
INSERT INTO `fighter` VALUES ('caoxin1', '1000', '50', '0', '10', '0', '2', '1000', '10');
INSERT INTO `fighter` VALUES ('caoxin2', '1000', '50', '0', '10', '0', '2', '1000', '10');
INSERT INTO `fighter` VALUES ('caoxin3', '1000', '50', '0', '10', '0', '2', '1000', '10');
INSERT INTO `fighter` VALUES ('caoxin4', '1000', '50', '0', '10', '0', '2', '1000', '10');
INSERT INTO `fighter` VALUES ('caoxin5', '1000', '50', '0', '10', '0', '2', '1000', '10');
INSERT INTO `fighter` VALUES ('caoxin6', '1000', '50', '0', '10', '0', '2', '1000', '10');
INSERT INTO `fighter` VALUES ('lijing1', '1000', '40', '0', '20', '0', '4', '1000', '100');
INSERT INTO `fighter` VALUES ('lijing2', '1000', '40', '0', '20', '0', '4', '1000', '100');
INSERT INTO `fighter` VALUES ('lijing3', '1000', '40', '0', '20', '0', '4', '1000', '100');
INSERT INTO `fighter` VALUES ('lijing4', '1000', '40', '0', '20', '0', '4', '1000', '100');
INSERT INTO `fighter` VALUES ('lijing5', '1000', '40', '0', '20', '0', '4', '1000', '100');
INSERT INTO `fighter` VALUES ('lijing6', '1000', '40', '0', '20', '0', '4', '1000', '100');