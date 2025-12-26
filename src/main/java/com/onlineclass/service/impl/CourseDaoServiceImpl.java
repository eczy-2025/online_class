package com.onlineclass.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineclass.dao.CourseDao;
import com.onlineclass.pojo.Course;
import com.onlineclass.pojo.OnlineClassPage;
import com.onlineclass.pojo.Score;
import com.onlineclass.pojo.Teacher;
import com.onlineclass.service.CourseDaoService;

/**
 * @author jht&cc
 * @version 1.2
 * @since 2024.12.23
 */

@Service(value = "courseDaoService")
public class CourseDaoServiceImpl implements CourseDaoService {
    @Autowired
    private CourseDao courseDao;

    // [新增] 教师端查询课程列表 (核心方法，解决 TeacherController 报错)
    @Override
    public OnlineClassPage coursesSelPage(Course course, Teacher teacher) {
        // 1. 准备 Map 参数给 MyBatis
        Map<String, Object> map = new HashMap<>();

        // 放入分页参数
        map.put("pageNo", course.getPageNo());
        map.put("pageSize", course.getPageSize());

        // 放入查询条件
        if (course.getCourse_name() != null) {
            map.put("course_name", course.getCourse_name());
        }
        if (teacher.getTeacher_tno() != null) {
            map.put("teacher_tno", teacher.getTeacher_tno());
        }

        // 2. 调用 DAO
        List<Teacher> teachers = courseDao.coursesSelPage(map);

        // 3. 计算总页数 (需要查总数，这里复用查询条件查全部来计算)
        Map<String, Object> allMap = new HashMap<>(map);
        allMap.remove("pageNo"); // 移除分页参数查总数
        allMap.remove("pageSize");

        List<Teacher> allTeachers = courseDao.coursesSel(allMap);

        int total = allTeachers.size();
        int pageSize = course.getPageSize() > 0 ? course.getPageSize() : 10;
        int pageNo = total / pageSize;
        if (total % pageSize != 0) {
            pageNo++;
        }

        // 4. 封装返回
        OnlineClassPage page = new OnlineClassPage();
        page.setTeacher(teachers);
        page.setPageNo(pageNo);
        return page;
    }

    // 查询课程信息 (旧接口适配)
    @Override
    public List<Teacher> coursesSel(String course_name, String course_cno, String teacher_name) {
        Map<String, Object> map = new HashMap<>();
        map.put("course_name", course_name);
        map.put("course_cno", course_cno);
        map.put("teacher_name", teacher_name);
        return courseDao.coursesSel(map);
    }

    // 分页查询课程信息 (旧接口适配)
    @Override
    public List<Teacher> coursesSelPage(String course_name, String course_cno, String teacher_name, int pageNo,
                                        int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("course_name", course_name);
        map.put("course_cno", course_cno);
        map.put("teacher_name", teacher_name);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        return courseDao.coursesSelPage(map);
    }

    // 课程修改
    @Override
    public int coursesUpdate(String course_name, String course_cno, String course_information) {
        Course course = new Course();
        course.setCourse_name(course_name);
        course.setCourse_cno(course_cno);
        course.setCourse_information(course_information);
        return courseDao.coursesUpdate(course);
    }

    // 课程添加
    @Override
    public int coursesInsert(Course course) {
        return courseDao.coursesInsert(course);
    }

    // 查询课程信息 (注意返回值改为 List<Teacher>)
    @Override
    public List<Teacher> getCourse(String course_cno, String course_name) {
        Course course = new Course();
        course.setCourse_cno(course_cno);
        course.setCourse_name(course_name);
        return courseDao.getCourse(course);
    }

    // 分页查询所有课程信息 (注意返回值改为 OnlineClassPage)
    @Override
    public OnlineClassPage getCoursePages(String course_cno, String course_name, int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("course_cno", course_cno);
        map.put("course_name", course_name);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);

        List<Teacher> list = courseDao.getCoursePages(map);

        OnlineClassPage page = new OnlineClassPage();
        page.setTeacher(list);
        page.setPageNo(1); // 简化处理，如需精确总页数可参考 coursesSelPage
        return page;
    }

    // 课程删除
    @Override
    public int coursesDel(String course_cno) {
        Course course = new Course();
        course.setCourse_cno(course_cno);
        return courseDao.coursesDel(course);
    }

    // --- 以下为成绩相关方法，透传调用即可 ---

    @Override
    public List<Score> scores(String student_sno, String course_cno, String teacher_tno) {
        return courseDao.scores(student_sno, course_cno, teacher_tno);
    }

    @Override
    public List<Score> scoresPage(String student_sno, String course_cno, String teacher_tno, int pageNo, int pageSize) {
        return courseDao.scoresPage(student_sno, course_cno, teacher_tno, pageNo, pageSize);
    }

    @Override
    public List<Score> scoresStu(String student_sno, String student_name, String teacher_name, String course_name) {
        return courseDao.scoresStu(student_sno, student_name, teacher_name, course_name);
    }

    @Override
    public List<Score> scoresStuPage(String student_sno, String student_name, String teacher_name, String course_name,
                                     int pageNo, int pageSize) {
        return courseDao.scoresStuPage(student_sno, student_name, teacher_name, course_name, pageNo, pageSize);
    }

}