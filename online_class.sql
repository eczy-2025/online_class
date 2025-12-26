/*
 Navicat Premium Dump SQL
 Source Database       : online_class
 Target Server Type    : MySQL
 File Encoding         : 65001
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 1. 重建数据库
-- ----------------------------
DROP DATABASE IF EXISTS `online_class`;
CREATE DATABASE `online_class` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `online_class`;

-- ----------------------------
-- 2. Table structure for classtime
-- ----------------------------
DROP TABLE IF EXISTS `classtime`;
CREATE TABLE `classtime`  (
  `time_id` int NOT NULL AUTO_INCREMENT,
  `course_cno` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `class_weekend` int NULL DEFAULT NULL,
  `class_time` int NULL DEFAULT NULL,
  PRIMARY KEY (`time_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- Records of classtime (优化了时间分布，周一至周五都有课)
INSERT INTO `classtime` VALUES (1, '0001', 3, 5); -- 语文: 周三 第5节
INSERT INTO `classtime` VALUES (2, '0011', 5, 1); -- 高数: 周五 第1节
INSERT INTO `classtime` VALUES (3, '0002', 3, 3); -- 英语: 周三 第3节
INSERT INTO `classtime` VALUES (4, '1012', 1, 1); -- Java: 周一 第1节 (重点课)
INSERT INTO `classtime` VALUES (5, '1013', 1, 5); -- C语言: 周一 第5节
INSERT INTO `classtime` VALUES (7, '1103', 4, 3);
INSERT INTO `classtime` VALUES (8, '1011', 5, 4);
INSERT INTO `classtime` VALUES (9, '2011', 2, 5);
INSERT INTO `classtime` VALUES (10, '0301', 2, 3); -- Python: 周二 第3节
INSERT INTO `classtime` VALUES (17, '1102', 4, 7); -- 小程序: 周四 第7节
INSERT INTO `classtime` VALUES (20, '4568', 5, 5); -- Bootstrap: 周五 第5节
INSERT INTO `classtime` VALUES (27, '1234', 5, 4);

-- ----------------------------
-- 3. Table structure for course (Modified)
-- 修改：course_number 改名为 selected_num，增加 max_num 字段
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `course_cno` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `course_information` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `selected_num` int DEFAULT 0 COMMENT '已选人数',
  `max_num` int DEFAULT 50 COMMENT '最大容量',
  `credit` int NULL DEFAULT NULL,
  PRIMARY KEY (`course_id`) USING BTREE,
  INDEX `course_cno`(`course_cno` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- Records of course (预设演示场景数据)
-- 场景A: 爆款课程 (Java) - 容量50，已选49
INSERT INTO `course` VALUES (4, 'Java高级架构', '1012', 'Spring Boot微服务实战', 49, 50, 4);
-- 场景B: 已满课程 (Python) - 容量10，已选10 (红色满员)
INSERT INTO `course` VALUES (17, 'Python深度学习', '0301', '神经网络与AI入门', 10, 10, 5);
-- 场景C: 新开课程 (小程序) - 容量40，已选3 (绿色空闲)
INSERT INTO `course` VALUES (24, '微信小程序全栈', '1102', '云开发与商业变现', 3, 40, 3);
-- 场景D: 通识课 (英语) - 容量大，已选65
INSERT INTO `course` VALUES (3, '商务英语口语', '0002', '外企职场沟通技巧', 65, 100, 2);
-- 其他课程
INSERT INTO `course` VALUES (1, '大学语文鉴赏', '0001', '经典文学作品赏析', 12, 120, 2);
INSERT INTO `course` VALUES (2, '高等数学(上)', '0011', '微积分与极限', 3, 60, 5);
INSERT INTO `course` VALUES (5, 'C语言程序设计', '1013', '计算机编程入门', 0, 50, 5);
INSERT INTO `course` VALUES (7, 'CSS3进阶', '1103', '界面前端样式', 0, 50, 3);
INSERT INTO `course` VALUES (14, 'Web前端开发', '1011', '前端样式学习', 0, 50, 4);
INSERT INTO `course` VALUES (16, 'HTML 5核心', '2011', '跨平台界面实现', 0, 50, 5);
INSERT INTO `course` VALUES (27, 'Bootstrap响应式', '4568', '快速搭建网页UI', 12, 60, 2);
INSERT INTO `course` VALUES (34, '共享经济分析', '1234', '滴滴打车案例分析', 0, 50, 4);

-- ----------------------------
-- 4. Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `score_id` int NOT NULL AUTO_INCREMENT,
  `student_sno` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `course_cno` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `teacher_tno` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `daily_score` int NULL DEFAULT NULL,
  `exam_score` int NULL DEFAULT NULL,
  `total_score` int NULL DEFAULT NULL,
  PRIMARY KEY (`score_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- Records of score (初始化部分数据)
-- 1. 为测试账号 20170235 预选几门课
-- 正在学习中 (分数0)
INSERT INTO `score` VALUES (1, '20170235', '1012', '1005', 0, 0, 0); -- Java
-- 已结课 (有分数)
INSERT INTO `score` VALUES (2, '20170235', '0002', '1003', 85, 92, 88); -- 英语

-- 2. 填充一点其他数据，保证统计不为空
INSERT INTO `score` VALUES (3, '20170239', '0001', '1001', 80, 80, 80);
INSERT INTO `score` VALUES (4, '20170003', '1102', '1010', 86, 86, 86);
INSERT INTO `score` VALUES (5, '20170003', '1103', '1012', 93, 93, 93);
INSERT INTO `score` VALUES (6, '20171115', '1103', '1012', 89, 89, 89);

-- ----------------------------
-- 5. Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `student_name` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `student_sno` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `student_age` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `student_data` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `student_phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `student_address` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `student_class` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`student_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- Records of student
INSERT INTO `student` VALUES (1, '刘平', '20170235', '22', '1999-10-17', '13556989456', '四川成都', '软件5班');
INSERT INTO `student` VALUES (5, '小王', '20170239', '21', '2000-06-23', '13529465698', '四川绵阳', '软件4班');
INSERT INTO `student` VALUES (7, '李克', '20170535', '20', '1998-11-17', '13598768569', '四川德阳', '软件5班');
INSERT INTO `student` VALUES (8, '张三', '20170135', '22', '1997-08-05', '17798768569', '四川成都', '软件4班');

-- ----------------------------
-- 6. Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `teacher_id` int NOT NULL AUTO_INCREMENT,
  `teacher_name` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `teacher_tno` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `teacher_age` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `teacher_data` date NULL DEFAULT NULL,
  `teacher_phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `teacher_address` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `teacher_course_id` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- Records of teacher
INSERT INTO `teacher` VALUES (1, '刘飞', '1001', '35', '1984-10-07', '13578943654', '四川成都', '0001');
INSERT INTO `teacher` VALUES (2, '张芳', '1010', '35', '1983-12-10', '17789654562', '四川德阳', '0011');
INSERT INTO `teacher` VALUES (3, '张雨林', '1003', '30', '1989-11-02', '17762536398', '四川德阳', '0002');
INSERT INTO `teacher` VALUES (5, '肖伟', '1005', '35', '1984-08-06', '13706598634', '四川绵阳', '1012');
INSERT INTO `teacher` VALUES (7, '李阳', '1011', '36', '1983-08-01', '17765973265', '成都温江', '1103');

-- ----------------------------
-- 7. Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `user_password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `user_type` varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- Records of user
INSERT INTO `user` VALUES (1, 'admin', '9f81d20a9a1d77195dc7537c87ef9b3b', '管理员');
INSERT INTO `user` VALUES (4, '20170235', '9f81d20a9a1d77195dc7537c87ef9b3b', '学生'); -- 测试账号
INSERT INTO `user` VALUES (8, '1001', '9f81d20a9a1d77195dc7537c87ef9b3b', '教师');

-- ----------------------------
-- 8. Trigger: 选课自动计数 (满足要求8)
-- ----------------------------
DROP TRIGGER IF EXISTS `tri_add_student_count`;

DELIMITER $$
CREATE TRIGGER `tri_add_student_count`
AFTER INSERT ON `score`
FOR EACH ROW
BEGIN
    -- 当score表新增选课记录时，对应课程已选人数+1
    UPDATE `course`
    SET `selected_num` = `selected_num` + 1
    WHERE `course_cno` = NEW.course_cno;
END $$
DELIMITER ;

-- ==========================================
-- [新增] 8.5 Trigger: 退课自动减少计数
-- ==========================================
DROP TRIGGER IF EXISTS `tri_sub_student_count`;

DELIMITER $$
CREATE TRIGGER `tri_sub_student_count`
AFTER DELETE ON `score`
FOR EACH ROW
BEGIN
    -- 当 score 表删除一条选课记录时，对应课程已选人数 -1
    UPDATE `course`
    SET `selected_num` = `selected_num` - 1
    WHERE `course_cno` = OLD.course_cno;
END $$
DELIMITER ;

-- ----------------------------
-- 9. Stored Procedure: 学生选课 (满足要求5 - 自主选课且带容量限制)
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_enroll_student`;

DELIMITER $$
CREATE PROCEDURE `sp_enroll_student`(
    IN p_student_sno VARCHAR(20),
    IN p_course_cno VARCHAR(20),
    IN p_teacher_tno VARCHAR(20),
    OUT p_result INT
)
BEGIN
    DECLARE v_count INT DEFAULT 0;
    DECLARE v_max INT DEFAULT 0;
    DECLARE v_current INT DEFAULT 0;

    -- 1. 查重: 判断该学生是否已经选修过这门课
    SELECT COUNT(*) INTO v_count
    FROM score
    WHERE student_sno = p_student_sno AND course_cno = p_course_cno;

    IF v_count > 0 THEN
        SET p_result = 0; -- 返回0: 重复选课
    ELSE
        -- 2. 容量检查: 检查课程是否已满
        SELECT max_num, selected_num INTO v_max, v_current
        FROM course WHERE course_cno = p_course_cno;

        IF v_current >= v_max THEN
            SET p_result = -1; -- 返回-1: 课程已满
        ELSE
            -- 3. 选课成功: 插入记录 (触发器会自动更新selected_num)
            INSERT INTO score (student_sno, course_cno, teacher_tno, daily_score, exam_score, total_score)
            VALUES (p_student_sno, p_course_cno, p_teacher_tno, 0, 0, 0);

            SET p_result = 1; -- 返回1: 成功
        END IF;
    END IF;
END $$
DELIMITER ;

-- ----------------------------
-- 10. Stored Procedure: 课程统计 (满足要求7 - 统计分析)
-- ----------------------------
DROP PROCEDURE IF EXISTS `sp_course_statistics`;

DELIMITER $$
CREATE PROCEDURE `sp_course_statistics`(
    IN p_course_cno VARCHAR(20),
    OUT p_avg_score DECIMAL(5,2),
    OUT p_max_score INT,
    OUT p_pass_rate DECIMAL(5,2)
)
BEGIN
    -- 计算平均分和最高分
    SELECT AVG(total_score), MAX(total_score)
    INTO p_avg_score, p_max_score
    FROM score
    WHERE course_cno = p_course_cno;

    -- 计算及格率 (假设>=60及格)
    SELECT (SUM(CASE WHEN total_score >= 60 THEN 1 ELSE 0 END) / COUNT(*)) * 100
    INTO p_pass_rate
    FROM score
    WHERE course_cno = p_course_cno;
END $$
DELIMITER ;

-- ----------------------------
-- 11. 最终一致性检查 (可选)
-- 确保 selected_num 与实际 score 表行数一致
-- ----------------------------
-- UPDATE course c SET selected_num = (SELECT COUNT(*) FROM score s WHERE s.course_cno = c.course_cno);

SET FOREIGN_KEY_CHECKS = 1;