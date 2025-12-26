package com.onlineclass.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onlineclass.pojo.ClassTime;
import com.onlineclass.pojo.Course;
import com.onlineclass.pojo.OnlineClassPage;
import com.onlineclass.pojo.Score;
import com.onlineclass.pojo.Teacher;
import com.onlineclass.service.ClassTimeDaoService;
import com.onlineclass.service.CourseDaoService;
import com.onlineclass.service.ScoreDaoService;
import com.onlineclass.service.TeacherDaoService;

/**
 * * @author jht&cc
 * @since 2024.12.23
 * @version 1.2
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class CourseController {
	@Autowired
	private CourseDaoService courseDaoService;

	@Autowired
	private ScoreDaoService ScoreDaoService;

	@Autowired
	private TeacherDaoService teacherDaoService;

	@Autowired
	private ClassTimeDaoService classTimeDaoService;

	// 分页查询所有课程
	@RequestMapping("/courseSel")
	@ResponseBody
	public OnlineClassPage courseSelPage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		String course_name = request.getParameter("course_name");
		String course_cno = request.getParameter("course_cno");
		String pageNoss = request.getParameter("pageNopss");

		int pageNo = Integer.parseInt(pageNoss);

		// [修改] 接收 List<Teacher> 而不是 List<Course>
		List<Teacher> courses = courseDaoService.getCourse(course_cno, course_name);

		// 计算总页数
		int pageNos = courses.size() / 10;
		int pageSize = 10;
		if (courses.size() % 10 != 0) {
			pageNos += 1;
		}

		// [修改] Service 直接返回 OnlineClassPage，无需再手动封装 List
		OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(course_cno, course_name, pageNo, pageSize);

		// 更新正确的总页数 (因为 Service 里可能只算了当前页，这里用全量数据算出的总页数覆盖)
		onlineClassPage.setPageNo(pageNos);

		System.out.println(onlineClassPage.toString());
		return onlineClassPage;
	}

	// 分页查询所有可选课程 (学生端用)
	@RequestMapping("/student/courseTeaSel")
	@ResponseBody
	public OnlineClassPage courseTeaSel(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		String course_name = request.getParameter("course_name");
		String course_cno = request.getParameter("course_cno");
		String teacher_name = request.getParameter("teacher_name");
		String pageNoss = request.getParameter("pageNopss");

		int pageNo = Integer.parseInt(pageNoss);

		// 这里本来就是 List<Teacher>，保持原样
		List<Teacher> courses = courseDaoService.coursesSel(course_name, course_cno, teacher_name);

		int pageNos = courses.size() / 10;
		int pageSize = 10;
		if (courses.size() % 10 != 0) {
			pageNos += 1;
		}

		// [修改] 使用 Service 层的逻辑 (Service 中实际上返回的是 List<Teacher>)
		// 但如果在 ServiceImpl 里 coursesSelPage 返回的是 OnlineClassPage，这里需要调整。
		// 根据之前的 CourseDaoServiceImpl，coursesSelPage(String...) 返回的是 List<Teacher>
		List<Teacher> coursePage = courseDaoService.coursesSelPage(course_name, course_cno, teacher_name, pageNo, pageSize);

		OnlineClassPage onlineClassPage = new OnlineClassPage();
		onlineClassPage.setPageNo(pageNos);
		onlineClassPage.setTeacher(coursePage);
		System.out.println(onlineClassPage.toString());
		return onlineClassPage;
	}

	// 分页显示所有已选课程
	@RequestMapping("/courses")
	@ResponseBody
	public List<Score> courses(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String student_sno = request.getParameter("student_sno");
		String course_cno = request.getParameter("course_cno");
		String teacher_tno = request.getParameter("teacher_tno");

		return courseDaoService.scores(student_sno, course_cno, teacher_tno);
	}

	// 显示所有已选课程 (学生端)
	@RequestMapping(value = "/student/courses")
	@ResponseBody
	public OnlineClassPage studentcourses(HttpServletRequest request, HttpServletResponse response,
										  HttpSession session) {
		String student_sno = request.getParameter("student_sno");
		String course_cno = request.getParameter("course_cno");
		String teacher_tno = request.getParameter("teacher_tno");

		List<Score> scores = courseDaoService.scores(student_sno, course_cno, teacher_tno);
		String pageNoss = request.getParameter("pageNopss");
		int pageNo = Integer.parseInt(pageNoss);
		int pageSize = 10;

		int pageNos = scores.size() / 10;
		if (scores.size() % 10 != 0) {
			pageNos += 1;
		}
		System.out.println(scores.toString());

		List<Score> scoresPage = courseDaoService.scoresPage(student_sno, course_cno, teacher_tno, pageNo, pageSize);
		OnlineClassPage onlineClassPage = new OnlineClassPage();
		onlineClassPage.setPageNo(pageNos);
		onlineClassPage.setScore(scoresPage);
		return onlineClassPage;
	}

	// 根据学号、姓名、课程名查询学生成绩
	@RequestMapping(value = "/student/coursesStu")
	@ResponseBody
	public OnlineClassPage coursesStu(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String student_sno = request.getParameter("student_sno");
		String student_name = request.getParameter("student_name");
		String teacher_name = request.getParameter("teacher_name");
		String course_name = request.getParameter("course_name");

		List<Score> scores = courseDaoService.scoresStu(student_sno, student_name, teacher_name, course_name);
		String pageNoss = request.getParameter("pageNopss");
		int pageNo = Integer.parseInt(pageNoss);
		int pageSize = 10;

		int pageNos = scores.size() / 10;
		if (scores.size() % 10 != 0) {
			pageNos += 1;
		}

		List<Score> scoresPage = courseDaoService.scoresStuPage(student_sno, student_name, teacher_name, course_name,
				pageNo, pageSize);
		OnlineClassPage onlineClassPage = new OnlineClassPage();
		onlineClassPage.setPageNo(pageNos);
		onlineClassPage.setScore(scoresPage);
		return onlineClassPage;
	}

	// 修改课程信息
	@RequestMapping(value = "/courses/coursesUpdate")
	@ResponseBody
	public OnlineClassPage coursesUpdate(HttpServletRequest request, HttpServletResponse response,
										 HttpSession session) {
		String course_cno = request.getParameter("course_cno");
		String course_name = request.getParameter("course_name");
		String course_information = request.getParameter("course_information");

		// 获取课程时间
		String class_weekend = request.getParameter("class_weekend");
		String class_time = request.getParameter("class_time");
		int weekend = Integer.parseInt(class_weekend);
		int times = Integer.parseInt(class_time);

		ClassTime classTime = new ClassTime();
		classTime.setCourse_cno(course_cno);
		classTime.setClass_weekend(weekend);
		classTime.setClass_time(times);

		String pageNoss = request.getParameter("pageNopss");
		int pageNo = Integer.parseInt(pageNoss);

		// [修改] List<Teacher>
		List<Teacher> courses = courseDaoService.getCourse(course_cno, null);

		int pageNos = courses.size() / 10;
		int pageSize = 10;
		if (courses.size() % 10 != 0) {
			pageNos += 1;
		}

		if (weekend > 7 || weekend < 1 || times > 6 || times < 1) {
			System.out.println("课程时间不对");

			// [修改] 直接获取 OnlineClassPage
			OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(course_cno, null, pageNo, pageSize);
			onlineClassPage.setPageNo(pageNos);
			onlineClassPage.setMessgae("0");
			return onlineClassPage;
		}

		// 执行更新
		int courseUpdates = courseDaoService.coursesUpdate(course_name, course_cno, course_information);
		int classtimesUpdates = classTimeDaoService.UpdateClassTime(classTime);

		if (courseUpdates > 0 && classtimesUpdates > 0) {
			System.out.println("修改成功");

			// [修改] 直接获取 OnlineClassPage
			OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(course_cno, null, pageNo, pageSize);
			onlineClassPage.setPageNo(pageNos);
			onlineClassPage.setMessgae("1");
			return onlineClassPage;
		}

		System.out.println("修改失败");

		// [修改] 直接获取 OnlineClassPage
		OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(course_cno, null, pageNo, pageSize);
		onlineClassPage.setPageNo(pageNos);
		onlineClassPage.setMessgae("0");
		return onlineClassPage;
	}

	// 删除课程信息
	@RequestMapping(value = "/courses/coursesDel")
	@ResponseBody
	public OnlineClassPage coursesDel(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String course_cno = request.getParameter("course_cno");

		String pageNoss = request.getParameter("pageNopss");
		int pageNo = Integer.parseInt(pageNoss);

		// 判断课程是否被学生选择
		List<Score> scores = ScoreDaoService.scoresSel(null, course_cno);
		// 判断是否有老师关联
		List<Teacher> teachers = teacherDaoService.SelTeacherss(course_cno);

		if (scores.size() > 0 || teachers.size() > 0) {
			System.out.println("课程不可删除！！学生或教师已选择！！！");

			// [修改] 直接获取 OnlineClassPage
			OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(course_cno, null, 1, 12);
			onlineClassPage.setPageNo(1);
			onlineClassPage.setMessgae("0");
			return onlineClassPage;
		}

		// 判断是否被删除
		int coursedel = courseDaoService.coursesDel(course_cno);
		int classtime = classTimeDaoService.DelClassTime(course_cno);
		if (coursedel > 0 && classtime > 0) {
			System.out.println("删除成功");

			// [修改] List<Teacher>
			List<Teacher> courses = courseDaoService.getCourse(null, null);

			int pageNos = courses.size() / 10;
			int pageSize = 10;
			if (courses.size() % 10 != 0) {
				pageNos += 1;
			}

			// [修改] 直接获取 OnlineClassPage
			OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(null, null, pageNo, pageSize);
			onlineClassPage.setPageNo(pageNos);
			onlineClassPage.setMessgae("1");
			return onlineClassPage;
		}

		System.out.println("删除失败");
		// [修改] 直接获取 OnlineClassPage
		OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(course_cno, null, 1, 12);
		onlineClassPage.setPageNo(1);
		onlineClassPage.setMessgae("0");
		return onlineClassPage;
	}

	// 添加课程信息
	@RequestMapping(value = "/courses/coursesIns")
	@ResponseBody
	public OnlineClassPage coursesIns(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String course_cno = request.getParameter("course_cno");
		String course_name = request.getParameter("course_name");
		String credit = request.getParameter("credit");
		String course_information = request.getParameter("course_information");

		Course course = new Course();
		course.setCourse_cno(course_cno);
		course.setCourse_name(course_name);
		course.setCourse_information(course_information);
		course.setSelected_num(0); // 设置初始已选人数为 0
		course.setMax_num(50);     // 设置默认最大容量为 50
		course.setCredit(Integer.parseInt(credit));

		// 获取课程时间
		String class_weekend = request.getParameter("class_weekend");
		String class_time = request.getParameter("class_time");
		int weekend = Integer.parseInt(class_weekend);
		int times = Integer.parseInt(class_time);

		ClassTime classTime = new ClassTime();
		classTime.setCourse_cno(course_cno);
		classTime.setClass_weekend(weekend);
		classTime.setClass_time(times);

		String pageNoss = request.getParameter("pageNopss");
		int pageNo = Integer.parseInt(pageNoss);

		if (weekend > 7 || weekend < 1 || times > 6 || times < 1) {
			// [修改] List<Teacher>
			List<Teacher> courses = courseDaoService.getCourse(null, null);
			int pageNos = courses.size() / 10;
			int pageSize = 10;
			if (courses.size() % 10 != 0) {
				pageNos += 1;
			}
			System.out.println("课程时间错误！！！");

			// [修改]
			OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(null, null, pageNo, pageSize);
			onlineClassPage.setPageNo(pageNos);
			onlineClassPage.setMessgae("0");
			return onlineClassPage;
		}

		// [修改] List<Teacher>
		List<Teacher> courses = courseDaoService.getCourse(course_cno, null);

		if (courses.size() > 0) {
			int pageNos = courses.size() / 10;
			int pageSize = 10;
			if (courses.size() % 10 != 0) {
				pageNos += 1;
			}
			System.out.println("课程已存在");

			// [修改]
			OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(null, null, pageNo, pageSize);
			onlineClassPage.setPageNo(pageNos);
			onlineClassPage.setMessgae("0");
			return onlineClassPage;
		}

		int CourseInsert = courseDaoService.coursesInsert(course);
		int classtimesInsert = classTimeDaoService.InsertClassTime(classTime);

		if (CourseInsert > 0 && classtimesInsert > 0) {
			System.out.println("添加成功");

			// [修改]
			OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(course_cno, null, pageNo, 1);
			onlineClassPage.setPageNo(1);
			onlineClassPage.setMessgae("1");
			return onlineClassPage;
		}

		// [修改] List<Teacher>
		courses = courseDaoService.getCourse(null, null); // 重新获取所有以计算页码
		int pageNos = courses.size() / 10;
		int pageSize = 10;
		if (courses.size() % 10 != 0) {
			pageNos += 1;
		}
		System.out.println("添加失败");

		// [修改]
		OnlineClassPage onlineClassPage = courseDaoService.getCoursePages(null, null, pageNo, pageSize);
		onlineClassPage.setPageNo(pageNos);
		onlineClassPage.setMessgae("0");
		return onlineClassPage;
	}
}