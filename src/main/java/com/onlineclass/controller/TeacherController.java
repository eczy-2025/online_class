package com.onlineclass.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onlineclass.pojo.Course;
import com.onlineclass.pojo.OnlineClassPage;
import com.onlineclass.pojo.Score;
import com.onlineclass.pojo.Student;
import com.onlineclass.pojo.Teacher;
import com.onlineclass.service.CourseDaoService;
import com.onlineclass.service.ScoreDaoService;
import com.onlineclass.service.StudentDaoService;
import com.onlineclass.service.TeacherDaoService;
import com.onlineclass.util.Age;

/**
 * * @author jht&cc
 * @since 2024.12.23
 * @version 1.2
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class TeacherController {
	@Autowired
	private TeacherDaoService teacherDaoService;

	@Autowired
	private StudentDaoService studentDaoService;

	@Autowired
	private ScoreDaoService scoreDaoService;

	@Autowired
	private CourseDaoService courseDaoService;

	// [新增接口] 教师查询自己的课程列表 (对应前端 /teacher/courseTea)
	@RequestMapping(value = "/teacher/courseTea")
	@ResponseBody
	public OnlineClassPage courseTea(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// 1. 获取参数
		String pageNs = request.getParameter("pageNopss");
		String course_name = request.getParameter("course_name");
		String teacher_tno = request.getParameter("teacher_tno"); // 获取工号

		// 2. 处理分页
		int pageNo = 1;
		if (pageNs != null && !pageNs.equals("")) {
			pageNo = Integer.parseInt(pageNs);
		}

		// 3. 封装查询条件
		Course course = new Course();
		course.setPageNo(pageNo);
		course.setPageSize(6); // 保持和前端卡片布局一致，每页6个
		course.setCourse_name(course_name);

		Teacher teacher = new Teacher();
		teacher.setTeacher_tno(teacher_tno); // 设置工号过滤

		// 4. 调用 Service 查询
		return courseDaoService.coursesSelPage(course, teacher);
	}

	// 显示所有已选课程(这里应该是查询某个课程的学生成绩列表)
	@RequestMapping("/teacher/scoreTea")
	@ResponseBody
	public OnlineClassPage scoreTea(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String course_cno = request.getParameter("course_cno");
		String teacher_tno = request.getParameter("teacher_tno");

		// 获取显示的页
		String pageNoss = request.getParameter("pageNopss");
		int pageNos = 1;
		if(pageNoss != null && !pageNoss.equals("")) {
			pageNos = Integer.parseInt(pageNoss);
		}
		int pageSize = 10;

		List<Score> scores = teacherDaoService.scoresTeacherChange(teacher_tno, "", "", "");

		// 计算总页数
		int pageNo = scores.size() / pageSize;
		if (scores.size() % pageSize != 0) {
			pageNo += 1;
		}

		List<Score> scoresPage = teacherDaoService.scoresTeacherChangePage(teacher_tno, "", "", "", pageNos, pageSize);

		OnlineClassPage onlineClassPage = new OnlineClassPage();
		onlineClassPage.setScore(scoresPage);
		onlineClassPage.setPageNo(pageNo);
		return onlineClassPage;
	}

	// 原有的查询接口 (保留以防其他地方用到)
	@RequestMapping("/teacher/teacherChange")
	@ResponseBody
	public OnlineClassPage courses(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String student_name = request.getParameter("student_name");
		String student_class = request.getParameter("student_class");
		String student_sno = request.getParameter("student_sno");
		String teacher_tno = request.getParameter("teacher_tno");
		// 获取显示的页
		String pageNoss = request.getParameter("pageNopss");
		int pageNos = Integer.parseInt(pageNoss);
		int pageSize = 10;

		List<Score> scores = teacherDaoService.scoresTeacherChange(teacher_tno, student_name, student_class,
				student_sno);
		// 显示分页的总页数
		int pageNo = scores.size() / 10;
		if (scores.size() % 10 != 0) {
			pageNo += 1;
		}
		List<Score> scoresPage = teacherDaoService.scoresTeacherChangePage(teacher_tno, student_name, student_class,
				student_sno, pageNos, pageSize);

		OnlineClassPage onlineClassPage = new OnlineClassPage();
		onlineClassPage.setScore(scoresPage);
		onlineClassPage.setPageNo(pageNo);
		return onlineClassPage;
	}

	// 分页显示学生信息
	@RequestMapping("/teacher/teaSelStudentsPage")
	@ResponseBody
	public OnlineClassPage TeaSelStudentsPage(HttpServletRequest request, HttpServletResponse response,
											  HttpSession session) {
		String student_name = request.getParameter("student_name");
		String student_class = request.getParameter("student_class");
		String student_sno = request.getParameter("student_sno");
		String teacher_tno = request.getParameter("teacher_tno");

		// 获取页数
		String pageNoss = request.getParameter("pageNopss");
		int pageNos = Integer.parseInt(pageNoss);
		Student student = new Student();
		student.setStudent_name(student_name);
		student.setStudent_class(student_class);
		student.setStudent_sno(student_sno);
		List<Student> students = studentDaoService.SelStudent(student);
		// 显示分页的总页数
		int pageNo = students.size() / 10;
		// 每页显示的行数
		int pageSize = 10;
		// 判断最后一页行数是否能显示一页，不能则加1
		if (students.size() % 10 != 0) {
			pageNo += 1;
		}
		List<Student> stu = studentDaoService.SelStudentPage(student_sno, student_name, student_class, pageNos,
				pageSize);
		OnlineClassPage onlineClassPage = new OnlineClassPage();
		onlineClassPage.setStudent(stu);
		onlineClassPage.setPageNo(pageNo);
		return onlineClassPage;
	}

	// 显示修改教师信息
	@RequestMapping("/teacher/teacherInformation")
	@ResponseBody
	public List<Teacher> teacherInformation(HttpServletRequest request, HttpServletResponse response,
											HttpSession session) {
		String username = request.getParameter("userName");
		Teacher teacher = new Teacher();
		teacher.setTeacher_tno(username);
		teacher.setTeacher_name("");
		List<Teacher> teachers = teacherDaoService.SelTeacher(teacher);
		return teachers;
	}

	// 修改教师信息
	@RequestMapping("/teacher/teacherUpdateInformation")
	@ResponseBody
	public boolean teacherUpdateInformation(HttpServletRequest request, HttpServletResponse response,
											HttpSession session) throws ParseException {
		// 页面获取教师信息
		String teachername = request.getParameter("teacher_name");
		String teachertno = request.getParameter("teacher_tno");
		String teacherdata = request.getParameter("teacher_data");
		// 获取年龄
		Age age = new Age();
		int userAge = age.parse(teacherdata);
		String teacherage = Integer.toString(userAge);

		String teacherphone = request.getParameter("teacher_phone");
		String teacheraddress = request.getParameter("teacher_address");
		String teachercourseid = request.getParameter("teacher_course_id");

		// 插入数据到Teacher对象表中
		Teacher teacher = new Teacher();
		teacher.setTeacher_name(teachername);
		teacher.setTeacher_tno(teachertno);
		teacher.setTeacher_age(teacherage);
		teacher.setTeacher_data(teacherdata);
		teacher.setTeacher_phone(teacherphone);
		teacher.setTeacher_address(teacheraddress);
		teacher.setTeacher_course_id(teachercourseid);

		int teacherUpdate = teacherDaoService.UpdataTeacher(teacher);
		if (teacherUpdate > 0) {
			System.out.println("修改成功");
			return true;
		}
		System.out.println("修改失败");
		return false;
	}

	// [最终修正] 成绩录入接口：对接真实 Service 实现数据库更新
	@RequestMapping("/teacher/insertScore")
	@ResponseBody
	public boolean insertScore(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// 1. 获取参数 (对应前端 teacher.html 传来的参数名)
		// 注意: 前端传的是 userName(学号), course_cno, daily_score, exam_score
		String student_sno = request.getParameter("userName");
		String course_cno = request.getParameter("course_cno");
		String daily_score = request.getParameter("daily_score");
		String exam_score = request.getParameter("exam_score");

		int daily = 0;
		int exam = 0;

		System.out.println("录入成绩请求: 学号=" + student_sno + ", 课程=" + course_cno + ", 平时=" + daily_score + ", 考试=" + exam_score);

		// 2. 数据校验与转换
		if (daily_score != null && !daily_score.isEmpty()) {
			try {
				// 兼容可能传过来的浮点数，先转double再转int
				daily = (int) Double.parseDouble(daily_score);
			} catch (NumberFormatException e) {
				System.out.println("平时成绩格式错误");
				return false;
			}
		}

		if (exam_score != null && !exam_score.isEmpty()) {
			try {
				exam = (int) Double.parseDouble(exam_score);
			} catch (NumberFormatException e) {
				System.out.println("考试成绩格式错误");
				return false;
			}
		}

		// 3. 调用 Service 更新数据库
		// 假设 scoreDaoService.scoresInput(学号, 课程号, 平时分, 考试分) 能执行 update 操作
		int result = scoreDaoService.scoresInput(student_sno, course_cno, daily, exam);

		if (result > 0) {
			System.out.println("成绩录入成功");
			return true;
		} else {
			System.out.println("成绩录入失败 (可能未找到选课记录)");
			return false;
		}
	}

}