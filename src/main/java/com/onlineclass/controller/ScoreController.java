package com.onlineclass.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onlineclass.pojo.Score;
import com.onlineclass.service.ScoreDaoService;

/**
 * 
 * @author jht&cc
 * @since 2024.12.23
 * @version 1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class ScoreController {
	@Autowired
	private ScoreDaoService scoreDaoService;

	// 学生课程选择
	@RequestMapping(value = "/student/courseChange")
	@ResponseBody
	public boolean courseChange(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONArray arr)
			throws ParseException {
		// 遍历选择课程的课程号
		for (int i = 0; i < arr.size(); i++) {
			JSONObject jsonObject = arr.getJSONObject(i);
			String teacher_tno = jsonObject.getString("teacher_tno");
			String course_cno = jsonObject.getString("course_cno");
			String sno = jsonObject.getString("userName");
			Map<String, Object> params = new HashMap<>();
			params.put("student_sno", sno);
			params.put("course_cno", course_cno);
			params.put("teacher_tno", teacher_tno);
			params.put("result", null); // 初始化输出参数
			scoreDaoService.courseSelect(params);
			Integer  flag = (Integer) params.get("result");
			System.out.println("result" + flag);
			if(flag == 0){
				System.out.println("result" + flag);
				return false;
			}
		}
		return true;
	}

}
