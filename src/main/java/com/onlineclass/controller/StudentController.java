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

import com.onlineclass.pojo.Score;
import com.onlineclass.pojo.Student;
import com.onlineclass.service.ScoreDaoService;
import com.onlineclass.service.StudentDaoService;
import com.onlineclass.service.UserDaoService;
import com.onlineclass.util.Age;

/**
 * * @author jht&cc
 * @since 2024.12.23
 * @version 1.1
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class StudentController {
	@Autowired
	private UserDaoService userDaoService;// 用户依赖注入

	@Autowired
	private StudentDaoService studentDaoService;// 学生依赖注入

	@Autowired
	private ScoreDaoService scoreDaoService;// 成绩依赖注入

	// 学生个人信息修改
	@RequestMapping(value = "/student/studentUpdate")
	@ResponseBody
	public boolean studentUpdate(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ParseException {
		String student_name = request.getParameter("student_name");


		String student_sno = request.getParameter("student_sno");
		String student_data = request.getParameter("student_data");
		String student_phone = request.getParameter("student_phone");
		String student_address = request.getParameter("student_address");
		String student_class = request.getParameter("student_class");

		// 获取年龄
		Age age = new Age();
		int userAge = age.parse(student_data);
		String studentage = Integer.toString(userAge);

		// 数据注入
		Student student = new Student();
		student.setStudent_name(student_name);
		student.setStudent_sno(student_sno);
		student.setStudent_data(student_data);
		student.setStudent_age(studentage);
		student.setStudent_phone(student_phone);
		student.setStudent_address(student_address);
		student.setStudent_class(student_class);
		// 用户是否修改信息成功
		int studentUpdate = studentDaoService.UpdataStudent(student);
		if (studentUpdate > 0) {
			System.out.println("修改成功");
			return true;
		}
		System.out.println("修改失败");
		return false;
	}

	// 学生信息查询
	@RequestMapping(value = "/student/studentInformation")
	@ResponseBody
	public List<Student> studentInformation(HttpServletRequest request, HttpServletResponse response,
											HttpSession session) throws ParseException {
		String student_name = request.getParameter("student_name");
		String student_sno = request.getParameter("student_sno");

		System.out.println(student_sno);
		// 数据注入
		Student student = new Student();
		student.setStudent_name(student_name);
		student.setStudent_sno(student_sno);
		// 学生个人信息查询
		List<Student> students = studentDaoService.SelStudent(student);
		return students;
	}

	/**
	 * [新增] 学生选课接口
	 * 调用带有容量控制和查重逻辑的 Service -> 存储过程
	 */
	@RequestMapping(value = "/student/chooseCourse")
	@ResponseBody
	public String chooseCourse(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String student_sno = request.getParameter("student_sno");
		String course_cno = request.getParameter("course_cno");
		String teacher_tno = request.getParameter("teacher_tno");

		System.out.println("选课请求: 学号" + student_sno + " 课程号" + course_cno + " 教师号" + teacher_tno);

		// 调用业务层
		String result = studentDaoService.enrollCourse(student_sno, course_cno, teacher_tno);

		// 根据 Service 返回的状态码返回给前端提示信息
		if ("success".equals(result)) {
			return "选课成功！";
		} else if ("duplicate".equals(result)) {
			return "选课失败：您已选修过该课程，请勿重复选择。";
		} else if ("full".equals(result)) {
			return "选课失败：手慢了，该课程名额已满！";
		} else {
			return "选课失败：系统异常，请稍后再试。";
		}
	}

	// 取消选课
	@RequestMapping(value = "/student/courseCancel")
	@ResponseBody
	public boolean courseCancel(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ParseException {
		String student_sno = request.getParameter("student_sno");
		String course_cno = request.getParameter("course_cno");
		System.out.println(student_sno + " " + course_cno);

		// 判断该课程是否有成绩
		List<Score> scores = scoreDaoService.scoresSel(student_sno, course_cno);
		if (scores != null && scores.size() > 0 && scores.get(0).getTotal_score() != 0) {
			System.out.println("取消选课失败，该课程已开始学习");
			return false;
		} else {
			int cancel = scoreDaoService.courseCancel(student_sno, course_cno);
			if (cancel > 0) {
				System.out.println("取消成功");
				return true;
			} else {
				System.out.println("取消选课失败，该课程已开始学习或未找到记录");
				return false;
			}
		}
	}
}