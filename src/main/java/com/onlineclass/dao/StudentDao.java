package com.onlineclass.dao;

import java.util.List;
import java.util.Map; // [新增] 引入 Map

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.onlineclass.pojo.Student;

/**
 * * @author jht&cc
 * @since 2024.12.23
 * @version 1.1
 */
@Mapper
@Repository(value = "studentDao")
public interface StudentDao {
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
	 * [新增] 学生自主选课 (调用存储过程)
	 * @param params 包含 studentSno, courseCno, teacherTno 和 输出参数 result
	 */
	public void enrollStudent(Map<String, Object> params);

}