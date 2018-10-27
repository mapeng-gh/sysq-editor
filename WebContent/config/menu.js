(function(){
	
	var menuList = [
                
		{
			name: '用户管理',
			index: '/userManage',
			icon: 'fa fa-address-book-o',
			sub: [
				{name:'用户列表',index:'/userManage/userList'}
			]
        },
		
		{
			name: '任务管理',
			index: '/taskManage',
			icon: 'fa fa-tasks',
			sub: [
				{index : '/taskManage/taskList' , name: '任务列表'}
			]
        },
		
		{
			name : '数据管理',
			index : '/dataManage',
			icon : 'fa fa-database',
			sub : [
				{name : '版本列表' , index : '/dataManage/versionList'}
			]
		},
	    
        {
			name: '我的任务',
			index: '/myTask',
			icon: 'fa fa-edit',
			sub: [
				{index : '/myTask/taskList' , name: '任务列表'}
			]
        },
	    
        {
			name: '访谈浏览',
			index: '/interview',
			icon: 'fa fa-eye',
			sub: [
				{index : '/interview/interviewList' , name: '访谈列表'}
			]
        },
	    
        {
			name: '个人中心',
			index: '/usercenter',
			icon: 'fa fa-user-o',
			sub: [
				{index : '/usercenter/profile' , name: '个人信息'},
				{index : '/usercenter/modifyPwd' , name: '修改密码'}
			]
        },
		
		{
			name : '操作日志',
			icon : 'fa fa-file-archive-o',
			index : 'log',
			sub : [
				{name : '登录日志' , index : '/log/loginLogList'},
				{name : '编辑日志' , index : '/log/editLogList'}
			]
			
		}
	];
	
	window.menuList = menuList;
	
})();
