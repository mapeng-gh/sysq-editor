package com.huasheng.sysq.editor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huasheng.sysq.editor.params.EditorQuestionResponse;
import com.huasheng.sysq.editor.params.EditorQuestionaireResponse;
import com.huasheng.sysq.editor.params.TaskResponse;
import com.huasheng.sysq.editor.service.TaskService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;
import com.huasheng.sysq.editor.util.SessionCache;

@Controller
@RequestMapping(value="/myTask")
public class MyTaskController {

	@Autowired
	private TaskService taskService;

	/**
	 * 任务列表
	 * @param token
	 * @param searchParams
	 * @return
	 */
	@RequestMapping(value="/taskList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<TaskResponse>> taskList(@RequestHeader(value="Authorization") String token,@RequestParam Map<String,String> searchParams) {
		LogUtils.info(this.getClass(), "taskList params : token = {},searchParams = {}",token,JsonUtils.toJson(searchParams));
		
		//参数处理
		int userId = 0;
		int currentPage = 0;
		int pageSize = 0;
		Map<String,Object> handledParams = new HashMap<String,Object>();
		try {
			userId = SessionCache.get(token).getId();
			handledParams.put("patientName",searchParams.get("patientName"));
			String taskStatus = searchParams.get("taskStatus");
			if(!StringUtils.isBlank(taskStatus)) {
				handledParams.put("taskStatus", Integer.parseInt(taskStatus));
			}
			currentPage = Integer.parseInt(searchParams.get("currentPage"));
			pageSize = Integer.parseInt(searchParams.get("pageSize"));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "taskList error", e);
			return CallResult.failure("获取我的任务失败");
		}
		
		CallResult<Page<TaskResponse>> result = taskService.findUserTaskPage(userId, handledParams, currentPage, pageSize);
		LogUtils.info(this.getClass(), "taskList result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 任务详情
	 * @param taskId
	 * @return
	 */
	@RequestMapping(value="/taskDetail.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<TaskResponse> taskDetail(@RequestParam(value="taskId") int taskId) {
		LogUtils.info(this.getClass(), "taskDetail params : taskId = {}",taskId);
		
		CallResult<TaskResponse> result = taskService.getTaskDetail(taskId);
		LogUtils.info(this.getClass(), "taskDetail result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 初始化任务
	 * @param requestJsonStr
	 * @return
	 */
	@RequestMapping(value="/initTask.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> initTask(@RequestBody String requestJsonStr) {
		LogUtils.info(this.getClass(), "initTask params : {}",requestJsonStr);
		
		int taskId;
		try {
			JSONObject requestJson = JSON.parseObject(requestJsonStr);
			taskId = requestJson.getIntValue("taskId");
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "initTask error", e);
			return CallResult.failure("参数格式不正确");
		}
		
		CallResult<Boolean> result = taskService.initTask(taskId);
		LogUtils.info(this.getClass(), "initTask result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 问卷列表
	 * @param taskIdStr
	 * @return
	 */
	@RequestMapping(value="/questionaireList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<List<EditorQuestionaireResponse>> questionaireList(@RequestParam(value="taskId") int taskId) {
		LogUtils.info(this.getClass(), "questionaireList params : taskId = {}",taskId);
		
		CallResult<List<EditorQuestionaireResponse>> result = taskService.getTaskQuestionaireList(taskId);
		LogUtils.info(this.getClass(), "questionaireList result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 问题列表
	 * @param taskIdStr
	 * @return
	 */
	@RequestMapping(value="/questionList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<List<EditorQuestionResponse>> questionList(@RequestParam(value="taskId") int taskId,@RequestParam(value="questionaireCode") String questionaireCode) {
		LogUtils.info(this.getClass(), "questionList params : taskId = {},questionaireCode = {}",taskId,questionaireCode);
		
		CallResult<List<EditorQuestionResponse>> result = taskService.getTaskQuestionList(taskId, questionaireCode);
		LogUtils.info(this.getClass(), "questionList result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 启用问题
	 * @param requestJsonStr
	 * @return
	 */
	@RequestMapping(value="/enableQuestion.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> enableQuestion(@RequestBody String requestJsonStr) {
		LogUtils.info(this.getClass(), "enableQuestion params : {}",requestJsonStr);
		
		int taskId;
		String questionaireCode;
		String questionCode;
		String remark;
		try {
			JSONObject requestJson = JSON.parseObject(requestJsonStr);
			taskId = requestJson.getIntValue("taskId");
			questionaireCode = requestJson.getString("questionaireCode");
			questionCode = requestJson.getString("questionCode");
			remark = requestJson.getString("remark");
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "enableQuestion error", e);
			return CallResult.failure("参数格式不正确");
		}
		
		CallResult<Boolean> result = taskService.enableQuestion(taskId, questionaireCode, questionCode , remark);
		LogUtils.info(this.getClass(), "enableQuestion result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 禁用问题
	 * @param requestJsonStr
	 * @return
	 */
	@RequestMapping(value="/disableQuestion.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> disableQuestion(@RequestBody String requestJsonStr) {
		LogUtils.info(this.getClass(), "disableQuestion params : {}",requestJsonStr);
		
		int taskId;
		String questionaireCode;
		String questionCode;
		String remark;
		try {
			JSONObject requestJson = JSON.parseObject(requestJsonStr);
			taskId = requestJson.getIntValue("taskId");
			questionaireCode = requestJson.getString("questionaireCode");
			questionCode = requestJson.getString("questionCode");
			remark = requestJson.getString("remark");
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "disableQuestion error", e);
			return CallResult.failure("参数格式不正确");
		}
		
		CallResult<Boolean> result = taskService.disableQuestion(taskId, questionaireCode, questionCode , remark);
		LogUtils.info(this.getClass(), "disableQuestion result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 编辑问题
	 * @param requestJsonStr
	 * @return
	 */
	@RequestMapping(value="/editQuestion.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> editQuestion(@RequestBody String requestJsonStr) {
		LogUtils.info(this.getClass(), "editQuestion params : {}",requestJsonStr);
		
		int taskId;
		String questionaireCode;
		String questionCode;
		String results;
		String remark;
		try {
			JSONObject requestJson = JSON.parseObject(requestJsonStr);
			taskId = requestJson.getIntValue("taskId");
			questionaireCode = requestJson.getString("questionaireCode");
			questionCode = requestJson.getString("questionCode");
			results = requestJson.getString("results");
			remark = requestJson.getString("remark");
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "editQuestion error", e);
			return CallResult.failure("参数格式不正确");
		}
		
		CallResult<Boolean> result = taskService.editQuestion(taskId, questionaireCode, questionCode, results , remark);
		LogUtils.info(this.getClass(), "editQuestion result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 完成任务
	 * @param requestJsonStr
	 * @return
	 */
	@RequestMapping(value="/finishTask.do",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Boolean> finishTask(@RequestBody String requestJsonStr) {
		LogUtils.info(this.getClass(), "finishTask params : {}",requestJsonStr);
		
		int taskId;
		try {
			JSONObject requestJson = JSON.parseObject(requestJsonStr);
			taskId = requestJson.getIntValue("taskId");
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "finishTask error", e);
			return CallResult.failure("参数格式不正确");
		}
		
		CallResult<Boolean> result = taskService.finishTask(taskId);
		LogUtils.info(this.getClass(), "finishTask result : {}", JsonUtils.toJson(result));
		return result;
	}
}
