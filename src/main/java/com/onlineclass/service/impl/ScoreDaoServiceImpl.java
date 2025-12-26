package com.onlineclass.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineclass.dao.ScoreDao;
import com.onlineclass.pojo.Score;
import com.onlineclass.service.ScoreDaoService;

/**
 * 
 * @author jht&cc
 * @since 2024.12.23
 * @version 1.0
 */

@Service(value = "scoreDaoService")
public class ScoreDaoServiceImpl implements ScoreDaoService {
	@Autowired
	private ScoreDao scoreDao;

	// 课程取消
	public int courseCancel(String student_sno, String course_cno) {
		return scoreDao.courseCancel(student_sno, course_cno);
	}

	// 查询课程是否已选
	public List<Score> scoresSel(String student_sno, String course_cno) {
		return scoreDao.scoresSel(student_sno, course_cno);
	}


	// 课程选择
	public int courseChange(String student_sno, String course_cno, String teacher_tno) {
		return scoreDao.courseChange(student_sno, course_cno, teacher_tno);
	}

	public Integer courseSelect(Map<String, Object> params) {
		return scoreDao.courseSelect(params);
	};

	// 课程成绩录入
	public int scoresInput(String student_sno, String course_cno, int daily_score,int exam_score) {
		return scoreDao.scoresInput(student_sno, course_cno, daily_score,exam_score);
	}

}
