package com.onlineclass.pojo;

/**
 * * @author jht&cc
 * @since 2024.12.23
 * @version 1.2
 */
public class Course extends OnlineClassObject {

	/**
	 * 课程映射表
	 */
	private static final long serialVersionUID = 5632950604495414570L;

	private int course_id;// 课程id
	private String course_name;// 课程名
	private String course_cno;// 课程号
	private String course_information;// 课程信息

	// [修改] 对应数据库的 selected_num (已选人数)
	private int selected_num;

	// [新增] 对应数据库的 max_num (最大容量)
	private int max_num;

	private int credit;//课程学分
	//	private String teacher_name;
	private ClassTime classTime;// 选课时间安排
	private Teacher teacher;

	// [新增] 分页参数，解决 TeacherController 报错问题
	private int pageNo;
	private int pageSize;

	public ClassTime getClassTime() {
		return classTime;
	}

	public void setClassTime(ClassTime classTime) {
		this.classTime = classTime;
	}

	public Teacher getTeacher() { return teacher; }
	public void setTeacher(Teacher teacher) { this.teacher = teacher; }

	public int getCredit(){
		return credit;
	}

	public void setCredit(int credit){
		this.credit = credit;
	}


	public String getCourse_cno() {
		return course_cno;
	}

	public void setCourse_cno(String course_cno) {
		this.course_cno = course_cno;
	}

	public int getCourse_id() {
		return course_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public String getCourse_information() {
		return course_information;
	}

	// [修改] Getter for selected_num
	public int getSelected_num() {
		return selected_num;
	}

	// [新增] Getter for max_num
	public int getMax_num() {
		return max_num;
	}

	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public void setCourse_information(String course_information) {
		this.course_information = course_information;
	}

	// [修改] Setter for selected_num
	public void setSelected_num(int selected_num) {
		this.selected_num = selected_num;
	}

	// [新增] Setter for max_num
	public void setMax_num(int max_num) {
		this.max_num = max_num;
	}

	// [新增] 分页相关 Getter/Setter
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "Course [course_id=" + course_id + ", course_name=" + course_name + ", course_cno=" + course_cno
				+ ", course_information=" + course_information + ", selected_num=" + selected_num
				+ ", max_num=" + max_num + ", classTime=" + classTime + ", teacher=" + teacher
				+ " , credit=" + credit + "]";
	}

	// [修改] 更新构造函数，加入 max_num，替换 course_number 为 selected_num
	public Course(int course_id, String course_name, String course_cno, String course_information,
				  int selected_num, int max_num, ClassTime classTime, Teacher teacher, int credit) {
		super();
		this.course_id = course_id;
		this.course_name = course_name;
		this.course_cno = course_cno;
		this.course_information = course_information;
		this.selected_num = selected_num;
		this.max_num = max_num;
		this.classTime = classTime;
		this.credit = credit;
		this.teacher = teacher;
	}

	public Course() {

	}

}