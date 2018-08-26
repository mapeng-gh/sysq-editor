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
                }
        ];
        
        var mappings = _.concat(userManageMappings,taskManageMappings,interviewEditMappings,interviewViewMappings);
	window.mappings = mappings;
})();