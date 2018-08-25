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
                        name : 'userManageTask',
			component : userManageTaskComponent,
			path : '/userManage/task',
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
        
        //访谈浏览
        var browseMappings = [
                {
                        name : 'browseInterviewList',
                        component : browseInterviewListComponent,
                        path : '/browse/interviewList',
                        meta : {
                                activeIndex : '/browse/interviewList'
                        }
                }
        ];
        
        var mappings = _.concat(userManageMappings,taskManageMappings,myTaskMappings,browseMappings);
	window.mappings = mappings;
})();