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
        
        var mappings = _.concat(userManageMappings,browseMappings);
	window.mappings = mappings;
})();