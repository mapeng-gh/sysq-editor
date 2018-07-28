(function(){
	
	var menuList = [
	    {
			name: '首页',
			icon: 'fa fa-list nav-icon',
	        index: '/dashboard'
	    },
		{
			name: '商品中心',
			index: '/commodity',
			icon: 'fa fa-building-o',
			sub: [
				{index : '/commodity/indGoods' , name: '地采物料库'},
				{index : '/commodity/groupGoods' , name: '集采物料库'}
			]
	    },
		
		{
			name: '客户数据',
			index: '/customer',
			icon: 'fa fa-user-circle-o',
			sub: [
				{index : '/customer/list' , name: '客户列表'},
				{index : '/customer/orderList' , name: '客户订单信息'}
			]
	    },
	    
	    {
			name: '供货商数据(地采)',
			index: '/supplier',
			icon: 'fa fa-user-circle',
			sub: [
				{index : '/supplier/list' , name: '供货商列表'},
				{index : '/supplier/orderList' , name: '供货商订单信息'}
			]
	    },
	    
	    {
			name: '订单管理',
			index: '/order',
			icon: 'fa fa-file-word-o',
			sub: [
				{index : '/order/list' , name: '订单列表'}
			]
	    },
	    
	    {
			name: '数据中心',
			index: '/data',
			icon: 'fa fa-database',
			sub: [
				{index : '/data/material' , name: '商品分析'},
				{index : '/data/sale' , name: '销售分析'},
				{index : '/data/customer' , name: '客户分析'},
				{index : '/data/orderNumTrend' , name: '订单量趋势'},
				{index : '/data/saleAmountTrend' , name: '销售金额趋势'}
			]
	    },
	    
	    {
			name: '广告管理',
			index: '/advertising',
			icon: 'fa fa-podcast',
			sub: [
				{index : '/advertising/focus' , name: '焦点广告管理'},
				{index : '/advertising/fiery' , name: '热推广告管理'}
			]
	    },
	    
	    {
			name: '文章管理',
			index: '/article',
			icon: 'fa fa-file-text',
			sub: [
				{index : '/article/list' , name: '文章列表'},
			]
	    },

	];
	
	window.menuList = menuList;
	
})();
