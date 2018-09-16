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
	
	//我的任务
	var myTaskMappings = [
		{
            name : 'myTaskList',
			component : myTaskListComponent,
			path : '/myTask/taskList',
			meta : {
                activeIndex : '/myTask/taskList'
			}
        }
	];
        
    //我的访谈
    var myInterviewMappings = [
        {
			name : 'myInterview4InterviewList',
			component : myInterview4InterviewListComponent,
			path : '/myInterview/interviewList',
			meta : {
				activeIndex : '/myInterview/interviewList'
			}
        },
		{
			name : 'myInterview4QuestionaireList',
			component : myInterview4QuestionaireListComponent,
			path : '/myInterview/questionaireList',
			meta : {
				activeIndex : '/myInterview/interviewList'
			}
        },
		{
			name : 'myInterview4QuestionList',
			component : myInterview4QuestionListComponent,
			path : '/myInterview/questionList',
			meta : {
				activeIndex : '/myInterview/interviewList'
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
        
    var mappings = _.concat(userManageMappings,taskManageMappings,dataManageMappings,myTaskMappings,myInterviewMappings,usercenterMappings);
	window.mappings = mappings;
})();