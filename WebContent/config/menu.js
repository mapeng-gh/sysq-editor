(function(){
	
	var menuList = [
                
		{
			name: '用户管理',
			index: '/userManage',
			icon: 'fa fa-address-book-o',
			sub: [
				{name:'用户列表',index:'/userManage/list'}
			]
        },
		
		{
			name: '任务管理',
			index: '/taskManage',
			icon: 'fa fa-tasks',
			sub: [
				{index : '/taskManage/list' , name: '任务列表'}
			]
        },
		
		{
			name : '数据管理',
			index : '/dataManage',
			icon : 'fa fa-suitcase',
			sub : [
				{name : '版本列表' , index : '/dataManage/versionList'}
			]
		},
	    
        {
			name: '访谈编辑',
			index: '/interviewEdit',
			icon: 'fa fa-edit',
			sub: [
				{index : '/interviewEdit/interviewList' , name: '访谈列表'}
			]
        },
	    
        {
			name: '访谈浏览',
			index: '/interviewView',
			icon: 'fa fa-eye',
			sub: [
				{index : '/interviewView/interviewList' , name: '访谈列表'}
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
        }
	];
	
	window.menuList = menuList;
	
})();
