package com.onlineclass.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.onlineclass.pojo.Course;
import com.onlineclass.pojo.Score;
import com.onlineclass.pojo.Teacher;

@Mapper
@Repository(value = "courseDao")
public interface CourseDao {

	// [修改] 改为 Map 传参，支持动态查询条件 (name, cno, teacher_name, teacher_tno)
	// 返回值修正为 Teacher 以匹配 Mapper XML 中的 resultMap="coursesSels"
	List<Teacher> coursesSel(Map<String, Object> map);

	// [修改] 改为 Map 传参，支持分页和多条件筛选 (pageNo, pageSize, teacher_tno...)
	List<Teacher> coursesSelPage(Map<String, Object> map);

	// 课程修改
	int coursesUpdate(Course course);

	// 删除课程
	int coursesDel(Course course);

	// 课程添加
	int coursesInsert(Course course);

	// 查询所有课程信息 (根据 XML 使用 Course 对象传参更合适，或者保持 Param)
	// 这里为了配合 Service 层可能的调用，改为接收 Course 对象
	List<Teacher> getCourse(Course course);

	// [修改] 分页查询课程信息，改为 Map 传参
	List<Teacher> getCoursePages(Map<String, Object> map);

	// 根据工号查询教师 (对应 XML 中的 getTeacher)
	Teacher getTeacher(Teacher teacher);

	// --- 以下为成绩相关查询，保持原有 @Param 方式不变 ---

	// 查看已选课程
	List<Score> scores(@Param("student_sno") String student_sno, @Param("course_cno") String course_cno,
					   @Param("teacher_tno") String teacher_tno);

	// 分页显示已选课程信息
	List<Score> scoresPage(@Param("student_sno") String student_sno, @Param("course_cno") String course_cno,
						   @Param("teacher_tno") String teacher_tno, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

	// 查询学生已选课程
	List<Score> scoresStu(@Param("student_sno") String student_sno, @Param("student_name") String student_name,
						  @Param("teacher_name") String teacher_name, @Param("course_name") String course_name);

	// 分页显示学生已选课程信息
	List<Score> scoresStuPage(@Param("student_sno") String student_sno, @Param("student_name") String student_name,
							  @Param("teacher_name") String teacher_name, @Param("course_name") String course_name,
							  @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

}