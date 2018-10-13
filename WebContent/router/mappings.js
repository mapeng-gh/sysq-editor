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
        
    var mappings = _.concat(userManageMappings,taskManageMappings,dataManageMappings,myTaskMappings,myInterviewMappings,usercenterMappings);
	window.mappings = mappings;
})();