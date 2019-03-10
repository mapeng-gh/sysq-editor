(function(){
	
    //用户管理
    var userManageMappings = [
		{
            name : 'userManage4UserList',
			path : '/userManage/userList',
			component : userManage4UserListComponent,
			meta : {
                activeIndex : '/userManage/userList'
			}
        },
		
		{
            name : 'userManage4UserDetail',
			path : '/userManage/userDetail',
			component : userManage4UserDetailComponent,
			meta : {
                activeIndex : '/userManage/userList'
			}
        },
		
		{
            name : 'userManage4InterviewList',
			path : '/userManage/interviewList',
			component : userManage4InterviewListComponent,
			meta : {
                activeIndex : '/userManage/userList'
			}
        }
    ];
	
	//任务管理
	var taskManageMappings = [
		{
            name : 'taskManage4TaskList',
			path : '/taskManage/taskList',
			component : taskManage4TaskListComponent,
			meta : {
                activeIndex : '/taskManage/taskList'
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
			name : 'myTask4TaskDetail',
			path : '/myTask/taskDetail',
			component : myTask4TaskDetailComponent,
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
        },
		{
			name : 'myTask4QuestionList',
			path : '/myTask/questionList',
			component : myTask4QuestionListComponent,
			meta : {
				activeIndex : '/myTask/taskList'
			}
		}
	];
        
    //访谈浏览
    var interviewMappings = [
        {
			name : 'interview4InterviewList',
			path : '/interview/interviewList',
			component : interview4InterviewListComponent,
			meta : {
				activeIndex : '/interview/interviewList'
			}
        },
		{
			name : 'interview4QuestionaireList',
			path : '/interview/questionaireList',
			component : interview4QuestionaireListComponent,
			meta : {
				activeIndex : '/interview/interviewList'
			}
        },
		{
			name : 'interview4QuestionList',
			path : '/interview/questionList',
			component : interview4QuestionListComponent,
			meta : {
				activeIndex : '/interview/interviewList'
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
			name : 'dataManage4VersionList',
			path : '/dataManage/versionList',
			component : dataManage4VersionListComponent,
			meta : {
				activeIndex : '/dataManage/versionList'
			}
		},
		{
			name : 'dataManage4QuestionaireList',
			path : '/dataManage/questionaireList',
			component : dataManage4QuestionaireListComponent,
			meta : {
				activeIndex : '/dataManage/versionList'
			}
		},
		{
			name : 'dataManage4QuestionList',
			path : '/dataManage/questionList',
			component : dataManage4QuestionListComponent,
			meta : {
				activeIndex : '/dataManage/versionList'
			}
		}
	];
	
	//操作日志
	var operateLogMappings = [
		{
			name : 'log4LoginLogList',
			path : '/log/loginLogList',
			component : log4LoginLogComponent,
			meta : {
				activeIndex : '/log/loginLogList'
			}
		},
		{
			name : 'log4EditLogList',
			path : '/log/editLogList',
			component : log4EditLogListComponent,
			meta : {
				activeIndex : '/log/editLogList'
			}
		}
	];
        
    var mappings = _.concat(userManageMappings,taskManageMappings,dataManageMappings,myTaskMappings,interviewMappings,usercenterMappings,operateLogMappings);
	window.mappings = mappings;
})();