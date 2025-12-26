package com.onlineclass.pojo;

/**
 * 
 * @author jht&cc
 * @since 2024.12.23
 * @version 1.0
 */
public class Score extends OnlineClassObject {

	/**
	 * 成绩映射表
	 */
	private static long serialVersionUID = 4219537463591868936L;

	private int score_id;// 成绩id
	private int daily_score;//平时成绩
	private int exam_score;//考试成绩
	private int total_score;// 课程总成绩
	private Student student;// 学生信息
	private Course course;// 课程信息
	private Teacher teacher;// 教师信息

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getScore_id() {
		return score_id;
	}

	public Student getStudent() {
		return student;
	}

	public Course getCourse() {
		return course;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	// 为 daily_score 属性提供 getter 方法
	public int getDaily_score() {
		return daily_score;
	}

	// 为 exam_score 属性提供 getter 方法
	public int getExam_score() {
		return exam_score;
	}

	// 为 total_score 属性提供 getter 方法
	public int getTotal_score() {
		return total_score;
	}

//	public int getScore() {
//		return
//			daily_score;
//
//	}

	public static void setSerialVersionUID(long serialVersionUID) {
		Score.serialVersionUID = serialVersionUID;
	}

	public void setScore_id(int score_id) {
		this.score_id = score_id;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public void setScore(int daily_score, int exam_score, int total_score) {
		this.daily_score = daily_score;
		this.exam_score = exam_score;
		this.total_score = total_score;
	}

	@Override
	public String toString() {
		return "Score [score_id=" + score_id + ", student=" + student + ", course=" + course + ", teacher=" + teacher
				+ ", daily_score=" + daily_score+ ", exam_score=" + exam_score +" , total_score=" + total_score + "]";
	}

	public Score() {

	}

	public Score(int score_id, Student student, Course course, Teacher teacher, int daily_score,int exam_score,int total_score) {
		super();
		this.score_id = score_id;
		this.student = student;
		this.course = course;
		this.teacher = teacher;
		this.daily_score = daily_score;
		this.exam_score = exam_score;
		this.total_score = total_score;
	}

}
