package com.onlineclass.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.onlineclass.pojo.Score;

/**
 * 
 * @author jht&cc
 * @since 2024.12.23
 * @version 1.0
 */
public interface ScoreDaoService {
	// 课程选择
	int courseChange(@Param("student_sno") String student_sno, @Param("course_cno") String course_cno,
			@Param("teacher_tno") String teacher_tno);

	Integer courseSelect(Map<String, Object> params);
	// 课程取,
	int courseCancel(@Param("student_sno") String student_sno, @Param("course_cno") String course_cno);

	// 选课查询(判断是否已经选择)
	List<Score> scoresSel(@Param("student_sno") String student_sno, @Param("course_cno") String course_cno);

	// 学生成绩录入
	int scoresInput(@Param("student_sno") String student_sno, @Param("course_cno") String course_cno,
			@Param("daily_score") int daily_score,@Param("exam_score") int exam_score);

}
