(function(){
	
    //用户管理
    var userManageMappings = [
		{
            name : 'userManageList',
			component : userManageListComponent,
			path : '/userManage/list',
			meta : {
                activeIndex : '/userManage/list'
			}
        },
		
		{
            name : 'userManageDetail',
			component : userManageDetailComponent,
			path : '/userManage/detail',
			meta : {
                activeIndex : '/userManage/list'
			}
        },
		
		{
            name : 'userManageUnAssignInterviewList',
			component : userManageUnAssignInterviewListComponent,
			path : '/userManage/unAssignInterviewList',
			meta : {
                activeIndex : '/userManage/list'
			}
        }
    ];
	
	//任务管理
	var taskManageMappings = [
		{
            name : 'taskManageList',
			component : taskManageListComponent,
			path : '/taskManage/list',
			meta : {
                activeIndex : '/taskManage/list'
			}
        }
	];
	
	//访谈编辑
	var interviewEditMappings = [
		{
            name : 'interviewEditInterviewList',
			component : interviewEditInterviewListComponent,
			path : '/interviewEdit/interviewList',
			meta : {
                activeIndex : '/interviewEdit/interviewList'
			}
        }
	];
        
    //访谈浏览
    var interviewViewMappings = [
        {
			name : 'interviewViewInterviewList',
			component : interviewViewInterviewListComponent,
			path : '/interviewView/interviewList',
			meta : {
				activeIndex : '/interviewView/interviewList'
			}
        },
		{
			name : 'interviewViewQuestionaireList',
			component : interviewViewQuestionaireListComponent,
			path : '/interviewView/questionaireList',
			meta : {
				activeIndex : '/interviewView/interviewList'
			}
        },
		{
			name : 'interviewViewQuestionList',
			component : interviewViewQuestionListComponent,
			path : '/interviewView/questionList',
			meta : {
				activeIndex : '/interviewView/interviewList'
			}
        }
    ];
	
	//个人中心
	var usercenterMappings = [
		{
			name : 'usercenterProfile',
			component : usercenterProfileComponent,
			path : '/usercenter/profile',
			meta : {
				activeIndex : '/usercenter/profile'
			}
		},
		{
			name : 'usercenterModifyPwd',
			component : usercenterModifyPwdComponent,
			path : '/usercenter/modifyPwd',
			meta : {
				activeIndex : '/usercenter/modifyPwd'
			}
		}
	];
	
	//数据管理
	var dataManageMappings = [
		{
			name : 'dataManageVersionList',
			component : dataManageVersionListComponent,
			path : '/dataManage/versionList',
			meta : {
				activeIndex : '/dataManage/versionList'
			}
		},
		{
			name : 'dataManageQuestionaireList',
			component : dataManageQuestionaireListComponent,
			path : '/dataManage/questionaireList',
			meta : {
				activeIndex : '/dataManage/versionList'
			}
		},
		{
			name : 'dataManageQuestionList',
			component : dataManageQuestionListComponent,
			path : '/dataManage/questionList',
			meta : {
				activeIndex : '/dataManage/versionList'
			}
		}
	];
        
    var mappings = _.concat(userManageMappings,taskManageMappings,dataManageMappings,interviewEditMappings,interviewViewMappings,usercenterMappings);
	window.mappings = mappings;
})();