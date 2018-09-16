(function(){
	
    //用户管理
    var userManageMappings = [
		{
            name : 'userManageList',
			path : '/userManage/list',
			component : userManageListComponent,
			meta : {
                activeIndex : '/userManage/list'
			}
        },
		
		{
            name : 'userManageDetail',
			path : '/userManage/detail',
			component : userManageDetailComponent,
			meta : {
                activeIndex : '/userManage/list'
			}
        },
		
		{
            name : 'userManageUnAssignInterviewList',
			path : '/userManage/unAssignInterviewList',
			component : userManageUnAssignInterviewListComponent,
			meta : {
                activeIndex : '/userManage/list'
			}
        }
    ];
	
	//任务管理
	var taskManageMappings = [
		{
            name : 'taskManageList',
			path : '/taskManage/list',
			component : taskManageListComponent,
			meta : {
                activeIndex : '/taskManage/list'
			}
        }
	];
	
	//我的任务
	var myTaskMappings = [
		{
            name : 'myTask4TaskList',
			path : '/myTask/taskList',
			component : myTask4TaskListComponent,
			meta : {
                activeIndex : '/myTask/taskList'
			}
        },
		{
            name : 'myTask4QuestionaireList',
			path : '/myTask/questionaireList',
			component : myTask4QuestionaireListComponent,
			meta : {
                activeIndex : '/myTask/taskList'
			}
        }
	];
        
    //我的访谈
    var myInterviewMappings = [
        {
			name : 'myInterview4InterviewList',
			path : '/myInterview/interviewList',
			component : myInterview4InterviewListComponent,
			meta : {
				activeIndex : '/myInterview/interviewList'
			}
        },
		{
			name : 'myInterview4QuestionaireList',
			path : '/myInterview/questionaireList',
			component : myInterview4QuestionaireListComponent,
			meta : {
				activeIndex : '/myInterview/interviewList'
			}
        },
		{
			name : 'myInterview4QuestionList',
			path : '/myInterview/questionList',
			component : myInterview4QuestionListComponent,
			meta : {
				activeIndex : '/myInterview/interviewList'
			}
        }
    ];
	
	//个人中心
	var usercenterMappings = [
		{
			name : 'usercenterProfile',
			path : '/usercenter/profile',
			component : usercenterProfileComponent,
			meta : {
				activeIndex : '/usercenter/profile'
			}
		},
		{
			name : 'usercenterModifyPwd',
			path : '/usercenter/modifyPwd',
			component : usercenterModifyPwdComponent,
			meta : {
				activeIndex : '/usercenter/modifyPwd'
			}
		}
	];
	
	//数据管理
	var dataManageMappings = [
		{
			name : 'dataManageVersionList',
			path : '/dataManage/versionList',
			component : dataManageVersionListComponent,
			meta : {
				activeIndex : '/dataManage/versionList'
			}
		},
		{
			name : 'dataManageQuestionaireList',
			path : '/dataManage/questionaireList',
			component : dataManageQuestionaireListComponent,
			meta : {
				activeIndex : '/dataManage/versionList'
			}
		},
		{
			name : 'dataManageQuestionList',
			path : '/dataManage/questionList',
			component : dataManageQuestionListComponent,
			meta : {
				activeIndex : '/dataManage/versionList'
			}
		}
	];
        
    var mappings = _.concat(userManageMappings,taskManageMappings,dataManageMappings,myTaskMappings,myInterviewMappings,usercenterMappings);
	window.mappings = mappings;
})();