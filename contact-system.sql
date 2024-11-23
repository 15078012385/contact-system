/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : contact-system

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 23/11/2024 17:45:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `flag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '唯一标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '吊打一切', 'admin');
INSERT INTO `sys_role` VALUES (2, '用户', '菜鸡', 'user');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'user' COMMENT '角色',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '启用状态：1启用，0禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$Y.R1kv03jUPE6nZW/GaGfugsFdAImTbDsIhNWyP2daHHQYMRA5eI2', '宝子', '1507801@qq.com', '15078012222', '北京市', '2024-10-17 11:10:57', '2024-11-23 14:53:11', 'http://localhost:8080/files/a82a8dd1ac8242aa80cd41a162684cf9.png', 'admin', 1);
INSERT INTO `sys_user` VALUES (42, 'test', '$2a$10$Y.R1kv03jUPE6nZW/GaGfugsFdAImTbDsIhNWyP2daHHQYMRA5eI2', 'test', '321395678@qq.com', '15078012385', '广西壮族自治区玉林市北流市', '2024-11-23 14:53:00', '2024-11-23 17:15:51', 'http://localhost:8080/files/39c8191c51a24f2a88f923ef7f26ea82.jpeg', 'user', 1);

-- ----------------------------
-- Table structure for tb_contact
-- ----------------------------
DROP TABLE IF EXISTS `tb_contact`;
CREATE TABLE `tb_contact`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID（某个用户联系人）',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系人姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `mobile` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系地址',
  `birthday` timestamp NULL DEFAULT NULL COMMENT '生日',
  `company` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公司名称',
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '职位',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '备注信息',
  `collect_status` int NULL DEFAULT 1 COMMENT '状态：1-收藏联系人  0未收藏',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通讯录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_contact
-- ----------------------------
INSERT INTO `tb_contact` VALUES (8, 1, '张三', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '13800138000,13800138000,13800138000,13800138000,13800138000', 'zhangsan@example.com', '北京市朝阳区', '1990-01-01 00:00:00', '阿里巴巴', '软件工程师', '工作认真负责', 1, '2023-10-01 12:00:00', '2024-11-23 17:28:50');
INSERT INTO `tb_contact` VALUES (9, 1, '李四', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '13900139000', 'lisi@example.com', '上海市浦东新区', '1992-02-02 00:00:00', '腾讯', '产品经理', '擅长产品设计', 1, '2023-10-01 12:00:00', '2024-11-23 15:54:38');
INSERT INTO `tb_contact` VALUES (10, 1, '王五', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '13700137000', 'wangwu@example.com', '广州市天河区', '1991-03-03 00:00:00', '百度', '数据分析师', '数据分析能力强', 1, '2023-10-01 12:00:00', '2024-11-23 15:54:38');
INSERT INTO `tb_contact` VALUES (11, 1, '赵六', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '13600136000', 'zhaoliu@example.com', '深圳市南山区', '1993-04-04 00:00:00', '华为', '项目经理', '项目管理经验丰富', 1, '2023-10-01 12:00:00', '2024-11-23 15:54:37');
INSERT INTO `tb_contact` VALUES (12, 1, '孙七', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '13500135000', 'sunqi@example.com', '杭州市西湖区', '1994-05-05 00:00:00', '字节跳动', '前端工程师', '前端技术精湛', 1, '2023-10-01 12:00:00', '2024-11-23 15:54:36');
INSERT INTO `tb_contact` VALUES (13, 1, '周八', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '13400134000', 'zhouba@example.com', '成都市高新区', '1995-06-06 00:00:00', '美团', '后端工程师', '后端技术扎实', 1, '2023-10-01 12:00:00', '2024-11-23 15:54:36');
INSERT INTO `tb_contact` VALUES (14, 1, '吴九', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '13300133000', 'wujiu@example.com', '重庆市渝中区', '1996-07-07 00:00:00', '京东', '测试工程师', '测试经验丰富', 1, '2023-10-01 12:00:00', '2024-11-23 15:54:35');
INSERT INTO `tb_contact` VALUES (15, 1, '郑十', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '13200132000', 'zhengshi@example.com', '武汉市洪山区', '1997-08-08 00:00:00', '小米', '运维工程师', '运维技术过硬', 1, '2023-10-01 12:00:00', '2024-11-23 15:54:35');
INSERT INTO `tb_contact` VALUES (16, 1, '王十一', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '13100131000', 'wangshiyi@example.com', '西安市雁塔区', '1998-09-09 00:00:00', '网易', 'UI设计师', 'UI设计经验丰富', 1, '2023-10-01 12:00:00', '2024-11-23 15:54:35');
INSERT INTO `tb_contact` VALUES (17, 1, '李十二', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '13000130000', 'lishier@example.com', '南京市鼓楼区', '1999-10-10 00:00:00', '新浪', '运营经理', '运营经验丰富', 1, '2023-10-01 12:00:00', '2024-11-23 15:51:09');
INSERT INTO `tb_contact` VALUES (18, 1, '张十三', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '12900129000', 'zhangshisan@example.com', '天津市和平区', '2000-11-11 00:00:00', '搜狐', '市场经理', '市场经验丰富', 1, '2023-10-01 12:00:00', '2024-11-23 15:51:09');
INSERT INTO `tb_contact` VALUES (19, 1, '王十四', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '12800128000', 'wangshisi@example.com', '长沙市岳麓区', '2001-12-12 00:00:00', '360', '安全工程师', '安全技术过硬', 1, '2023-10-01 12:00:00', '2024-11-23 15:51:09');
INSERT INTO `tb_contact` VALUES (20, 1, '李十五', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '12700127000', 'lishiwu@example.com', '郑州市金水区', '2002-01-13 00:00:00', '携程', '产品经理', '产品设计经验丰富', 1, '2023-10-01 12:00:00', '2024-11-23 15:51:09');
INSERT INTO `tb_contact` VALUES (21, 1, '张十六', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '12600126000', 'zhangshiliu@example.com', '合肥市蜀山区', '2003-02-14 00:00:00', '滴滴', '数据分析师', '数据分析能力强', 0, '2023-10-01 12:00:00', '2024-11-23 15:50:55');
INSERT INTO `tb_contact` VALUES (22, 1, '王十七', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '12500125000', 'wangshiqi@example.com', '济南市历下区', '2004-03-15 00:00:00', '快手', '前端工程师', '前端技术精湛', 0, '2023-10-01 12:00:00', '2024-11-23 15:51:09');
INSERT INTO `tb_contact` VALUES (23, 1, '李十八', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '12400124000', 'lishiba@example.com', '青岛市市南区', '2005-04-16 00:00:00', 'B站', '后端工程师', '后端技术扎实', 0, '2023-10-01 12:00:00', '2024-11-23 15:51:09');
INSERT INTO `tb_contact` VALUES (24, 1, '张十九', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '12300123000', 'zhangshijiu@example.com', '大连市中山区', '2006-05-17 00:00:00', '知乎', '测试工程师', '测试经验丰富', 0, '2023-10-01 12:00:00', '2024-11-23 15:51:09');
INSERT INTO `tb_contact` VALUES (25, 1, '王二十', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '12200122000', 'wangershi@example.com', '沈阳市和平区', '2007-06-18 00:00:00', '豆瓣', '运维工程师', '运维技术过硬', 0, '2023-10-01 12:00:00', '2024-11-23 15:51:09');
INSERT INTO `tb_contact` VALUES (26, 1, '李二十一', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '12100121000', 'liershiyi@example.com', '哈尔滨市道里区', '2008-07-19 00:00:00', '虎扑', 'UI设计师', 'UI设计经验丰富', 0, '2023-10-01 12:00:00', '2024-11-23 15:51:09');
INSERT INTO `tb_contact` VALUES (27, 1, '张二十二', 'http://localhost:8080/files/c5ecf95aa0044ff3ac2f89aff36d2b02.jpg', '12000120000', 'zhangershier@example.com', '长春市朝阳区', '2009-08-20 00:00:00', '雪球', '运营经理', '运营经验丰富', 0, '2023-10-01 12:00:00', '2024-11-23 15:51:09');
INSERT INTO `tb_contact` VALUES (28, 42, '111', 'http://localhost:8080/files/469cf36145a0418685ea1a510c113487.jpeg', '15078012385', '321395678@qq.com', '北京海淀', '2024-10-04 00:00:00', '阿里云', '软件开发', '很吊', 1, '2024-11-23 16:14:54', '2024-11-23 16:14:54');

SET FOREIGN_KEY_CHECKS = 1;
