package com.onlineclass.service.impl;

import java.util.HashMap; // [新增] 引入 HashMap
import java.util.List;
import java.util.Map;     // [新增] 引入 Map

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineclass.dao.StudentDao;
import com.onlineclass.pojo.Student;
import com.onlineclass.service.StudentDaoService;

/**
 * * @author jht&cc
 * @since 2024.12.23
 * @version 1.1
 */
@Service(value = "studentDaoService")
public class StudentDaoServiceImpl implements StudentDaoService {
	@Autowired
	private StudentDao studentDao;

	// 查询所有学生
	public List<Student> SelStudent(Student student) {

		return studentDao.SelStudent(student);
	}

	// 修改学生信息
	public int UpdataStudent(Student student) {

		return studentDao.UpdataStudent(student);
	}

	// 删除学生
	public int DelStudent(String student_sno) {

		return studentDao.DelStudent(student_sno);
	}

	// 添加学生信息
	public int AddStudent(Student student) {

		return studentDao.AddStudent(student);
	}

	public List<Student> SelStudentPage(String student_sno, String student_name, String student_class, int pageNo,
										int pageSize) {
		// TODO Auto-generated method stub
		return studentDao.SelStudentPage(student_sno, student_name, student_class, pageNo, pageSize);
	}

	/**
	 * [新增] 实现选课逻辑：调用DAO -> 存储过程 -> 解析返回值
	 */
	@Override
	public String enrollCourse(String studentSno, String courseCno, String teacherTno) {
		// 1. 封装存储过程需要的参数
		Map<String, Object> params = new HashMap<>();
		params.put("studentSno", studentSno);
		params.put("courseCno", courseCno);
		params.put("teacherTno", teacherTno);
		params.put("result", 0); // 初始化输出参数

		try {
			// 2. 调用DAO执行存储过程
			studentDao.enrollStudent(params);

			// 3. 获取返回值 (MyBatis会将OUT参数回填到Map中)
			Object resultObj = params.get("result");

			if (resultObj != null) {
				int result = (Integer) resultObj;
				// 根据存储过程定义的返回值进行判断
				// 1: 成功, 0: 重复选课, -1: 课程已满
				if (result == 1) {
					return "success";
				} else if (result == 0) {
					return "duplicate";
				} else if (result == -1) {
					return "full";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		return "error";
	}

}