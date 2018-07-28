(function(){
	
	var mappings = [
		{
			path : '/userManage/list',
			component : userManageListComponent,
			name : 'userManageList',
			meta : {
                                activeIndex : '/userManage/list'
			}
		}
	];
	
	window.mappings = mappings;
	
})();