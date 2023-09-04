/*
 Navicat Premium Data Transfer

 Source Server         : DataBase
 Source Server Type    : MySQL
 Source Server Version : 50741
 Source Host           : localhost:3306
 Source Schema         : register_center

 Target Server Type    : MySQL
 Target Server Version : 50741
 File Encoding         : 65001

 Date: 20/07/2023 09:17:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for db_account
-- ----------------------------
DROP TABLE IF EXISTS `db_account`;
CREATE TABLE `db_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_name`(`username`) USING BTREE,
  UNIQUE INDEX `unique_email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_account
-- ----------------------------
INSERT INTO `db_account` VALUES (1, 'ciaocaio@163.com', 'admin', '$2a$10$2Qx4s27lALLdjWESHL5eWOM/0.1SnDUE/wyWDBzIYmfE2r0j8G34C');
INSERT INTO `db_account` VALUES (2, '1578460248@qq.com', 'ciaociao', '$2a$10$6cQpfElN9SLibKV/DbxO.uLKSsPM0ytVFRw1vRmyUai/X9RKwXrYC');
INSERT INTO `db_account` VALUES (3, '1984462252@qq.com', 'ricardo', '$2a$10$UDqsAYEf1cQ0FOox3HY4quWJq8RRTQnNNvYYVWy0vvy3TyHOcZeSi');
INSERT INTO `db_account` VALUES (4, '1452091560@qq.com', 'admins', '$2a$10$QNkm9P7U7IOlvmhghaV4tOlDMtC1WCSxYmQ.kHzNK.TldryRWnbpS');
INSERT INTO `db_account` VALUES (5, 'ricardociaociao@gmail.com', 'hana', '$2a$10$78rpJtC8LfdtzkSDQjFy5OUjTOE1G23Oaj9ErcL..1L6hJiWES6oC');
INSERT INTO `db_account` VALUES (6, 'cia0cia0@163.com', 'sun', '$2a$10$RYAlUyVM2T/iRSe0re4EUu5ULdbb6wF7ugSIFd5sMAh3y3Zz9MsBO');
INSERT INTO `db_account` VALUES (7, 'cia0cia0@qq.com', 'doside', '$2a$10$zICj8u3ekfSTuMZo8zE6g.C352Ocm46HYyJtdkeEpVhc0abwZ8cCS');

-- ----------------------------
-- Table structure for db_account_info
-- ----------------------------
DROP TABLE IF EXISTS `db_account_info`;
CREATE TABLE `db_account_info`  (
  `uid` int(11) NOT NULL,
  `sex` enum('male','female') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `qq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wechat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `blog` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_account_info
-- ----------------------------
INSERT INTO `db_account_info` VALUES (2, 'male', '15073107407', '22222222222', '', 'https://www.bilibili.com/', 'hhhhhhh');
INSERT INTO `db_account_info` VALUES (3, 'female', '15073107407', '1578460248', 'ciaocaio', 'https://www.bilibili.com/', '15073107407');

-- ----------------------------
-- Table structure for db_account_privacty
-- ----------------------------
DROP TABLE IF EXISTS `db_account_privacty`;
CREATE TABLE `db_account_privacty`  (
  `uid` int(11) NOT NULL,
  `email` smallint(6) NULL DEFAULT NULL,
  `sex` smallint(6) NULL DEFAULT NULL,
  `phone` smallint(6) NULL DEFAULT NULL,
  `qq` smallint(6) NULL DEFAULT NULL,
  `wechat` smallint(6) NULL DEFAULT NULL,
  `blog` smallint(6) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of db_account_privacty
-- ----------------------------
INSERT INTO `db_account_privacty` VALUES (2, 0, 0, 0, 1, 1, 1);

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
