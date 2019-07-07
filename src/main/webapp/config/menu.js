(function(){
	var menuList = [
		{
			name: '用户管理',
			index: '/userManage',
			icon: 'fa fa-address-book-o',
			auths : [1],
			sub: [
				{name:'用户列表',index:'/userManage/userList',auths:[1]}
			]
        },
		
		{
			name: '任务管理',
			index: '/taskManage',
			icon: 'fa fa-tasks',
			auths : [1],
			sub: [
				{index : '/taskManage/taskList' , name: '任务列表',auths : [1]}
			]
        },
		
		{
			name : '问卷档案',
			index : '/dataManage',
			icon : 'fa fa-database',
			auths : [1,2,3],
			sub : [
				{name : '版本列表' , index : '/dataManage/versionList',auths : [1,2,3]}
			]
		},
	    
        {
			name: '我的任务',
			index: '/myTask',
			icon: 'fa fa-edit',
			auths : [2],
			sub: [
				{index : '/myTask/taskList' , name: '任务列表' , auths : [2]}
			]
        },
	    
        {
			name: '访谈查询',
			index: '/interview',
			icon: 'fa fa-eye',
			auths : [1,2,3],
			sub: [
				{index : '/interview/interviewList' , name: '访谈列表' , auths : [1,2,3]}
			]
        },
	    
        {
			name: '个人中心',
			index: '/usercenter',
			icon: 'fa fa-user-o',
			auths : [1,2,3],
			sub: [
				{index : '/usercenter/profile' , name: '个人信息' , auths : [1,2,3]},
				{index : '/usercenter/modifyPwd' , name: '修改密码' , auths : [1,2,3]}
			]
        },
		
		{
			name : '操作日志',
			icon : 'fa fa-file-archive-o',
			index : 'log',
			auths : [1],
			sub : [
				{name : '登录日志' , index : '/log/loginLogList' , auths : [1]},
				{name : '编辑日志' , index : '/log/editLogList' , auths : [1]}
			]
			
		}
	];
	
	window.menuList = menuList;
})();
