package com.onlineclass.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.onlineclass.pojo.Student;

/**
 * * @author jht&cc
 * @since 2024.12.23
 * @version 1.1
 */
public interface StudentDaoService {
	// 查询学生所有信息
	public List<Student> SelStudent(Student student);

	// 修改学生信息
	public int UpdataStudent(Student student);

	// 删除学生信息
	public int DelStudent(@Param("student_sno") String student_sno);

	// 添加学生信息
	public int AddStudent(Student student);

	// 分页显示学生信息
	public List<Student> SelStudentPage(@Param("student_sno") String student_sno,
										@Param("student_name") String student_name, @Param("student_class") String student_class,
										@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

	/**
	 * [新增] 学生选课业务处理
	 * @param studentSno 学号
	 * @param courseCno 课程号
	 * @param teacherTno 教师号
	 * @return 选课结果状态 ("success", "duplicate", "full", "error")
	 */
	public String enrollCourse(String studentSno, String courseCno, String teacherTno);

}