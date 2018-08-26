package com.huasheng.sysq.editor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huasheng.sysq.editor.dao.QuestionaireDao;
import com.huasheng.sysq.editor.dao.VersionDao;
import com.huasheng.sysq.editor.model.Questionaire;
import com.huasheng.sysq.editor.model.Version;
import com.huasheng.sysq.editor.service.BasicService;
import com.huasheng.sysq.editor.util.CallResult;
import com.huasheng.sysq.editor.util.LogUtils;

@Service
public class BasicServiceImpl implements BasicService{
	
	@Autowired
	private VersionDao versionDao;

	@Autowired
	private QuestionaireDao questionaireDao;
	
	@Override
	public CallResult<List<Questionaire>> findCurrentQuestionaireList(int type) {
		LogUtils.info(this.getClass(), "findCurrentQuestionaireList params : type = {}", type);
		
		try {
			//获取最新版本
			Version currentVersion = null;
			List<Version> versionList = versionDao.findAll();
			for(Version version : versionList) {
				if(version.getIsCurrent() == 1) {
					currentVersion = version;
					break;
				}
			}
			
			//获取问卷
			List<Questionaire> questionaireList = questionaireDao.findQuestionaireList(currentVersion.getId(), type);
			
			return CallResult.success(questionaireList);
		}catch(Exception e) {
			LogUtils.error(this.getClass(), "findCurrentQuestionaireList error", e);
			return CallResult.failure("获取问卷列表失败");
		}
	}

}
