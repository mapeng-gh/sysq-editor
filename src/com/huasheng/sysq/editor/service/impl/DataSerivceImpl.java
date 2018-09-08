package com.huasheng.sysq.editor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huasheng.sysq.editor.dao.QuestionaireDao;
import com.huasheng.sysq.editor.dao.VersionDao;
import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.model.Version;
import com.huasheng.sysq.editor.service.DataService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.JsonUtils;
import com.huasheng.sysq.editor.util.LogUtils;
import com.huasheng.sysq.editor.util.Page;

@Service
public class DataSerivceImpl implements DataService{
	
	@Autowired
	private VersionDao versionDao;
	
	@Autowired
	private QuestionaireDao questionaireDao;

	@Override
	public CallResult<Page<Version>> findVersionPage(Map<String, Object> searchParams, int currentPage,int pageSize) {
		LogUtils.info(this.getClass(), "findVersionPage params : searchParams = {},currentPage = {},pageSize = {}", JsonUtils.toJson(searchParams),currentPage,pageSize);
		
		try {
			List<Version> versionList = versionDao.findVersionPage(searchParams, currentPage, pageSize);
			
			int total = versionDao.countVersion(searchParams);
			
			return CallResult.success(new Page<Version>(versionList,currentPage,pageSize,total));
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "findVersionPage error", e);
			return CallResult.failure("获取版本列表失败");
		}
	}

	@Override
	public CallResult<List<Questionaire>> getQuestionaireList(int versionId,int type) {
		LogUtils.info(this.getClass(), "getQuestionaireList params : versionId = {}", versionId);
		
		try {
			List<Questionaire> questionaireList = questionaireDao.findQuestionaireList(versionId, type);
			return CallResult.success(questionaireList);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "getQuestionaireList error", e);
			return CallResult.failure("获取问卷列表失败");
		}
	}
	
}
