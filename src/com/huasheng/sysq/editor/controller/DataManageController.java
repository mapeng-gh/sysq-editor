package com.huasheng.sysq.editor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.model.Version;
import com.huasheng.sysq.editor.params.QuestionResponse;
import com.huasheng.sysq.editor.service.DataService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Controller
@RequestMapping(value="/dataManage")
public class DataManageController {
	
	@Autowired
	private DataService dataService;

	/**
	 * 版本列表
	 * @param searchParams
	 * @return
	 */
	@RequestMapping(value="/versionList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<Page<Version>> versionList(@RequestParam Map<String,String> searchParams) {
		LogUtils.info(this.getClass(), "versionList params : {}",JsonUtils.toJson(searchParams));
		
		//参数处理
		int currentPage = 0;
		int pageSize = 0;
		Map<String,Object> handledParams = new HashMap<String,Object>();
		try {
			currentPage = Integer.parseInt(searchParams.get("currentPage"));
			pageSize = Integer.parseInt(searchParams.get("pageSize"));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "versionList error", e);
			return CallResult.failure("获取版本列表失败");
		}
		
		//查询版本
		CallResult<Page<Version>> result = dataService.findVersionPage(handledParams, currentPage, pageSize);
		LogUtils.info(this.getClass(), "versionList result : {}", JsonUtils.toJson(result));
		return result;
	}
	
	/**
	 * 问卷列表
	 * @param versionId
	 * @return
	 */
	@RequestMapping(value="/questionaireList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<List<Questionaire>> questionaireList(@RequestParam(value="versionId") int versionId , @RequestParam(value="type") int type) {
		LogUtils.info(this.getClass(), "questionaireList params : versionId = {}",versionId);
		
		//查询问卷
		CallResult<List<Questionaire>> result = dataService.getQuestionaireList(versionId, type);
		LogUtils.info(this.getClass(), "questionaireList result : {}", JsonUtils.toJson(result));
		
		return result;
	}
	
	/**
	 * 问题列表
	 * @param versionId
	 * @return
	 */
	@RequestMapping(value="/questionList.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<List<QuestionResponse>> questionList(@RequestParam(value="versionId") int versionId , @RequestParam(value="questionaireCode") String questionaireCode) {
		LogUtils.info(this.getClass(), "questionList params : versionId = {},questionaireCode = {}",versionId,questionaireCode);
		
		//查询问题
		CallResult<List<QuestionResponse>> result = dataService.getQuestionList(versionId, questionaireCode);
		LogUtils.info(this.getClass(), "questionList result : {}", JsonUtils.toJson(result));
		
		return result;
	}
	
	/**
	 * 查看问题
	 * @param versionId
	 * @return
	 */
	@RequestMapping(value="/viewQuestion.do",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public CallResult<QuestionResponse> viewQuestion(@RequestParam(value="versionId") int versionId , @RequestParam(value="questionCode") String questionCode) {
		LogUtils.info(this.getClass(), "viewQuestion params : versionId = {},questionCode = {}",versionId,questionCode);
		
		CallResult<QuestionResponse> result = dataService.getQuestion(versionId, questionCode);
		LogUtils.info(this.getClass(), "viewQuestion result : {}", JsonUtils.toJson(result));
		
		return result;
	}
	
}
