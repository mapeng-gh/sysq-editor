 (function(){
	 
	var Message = Vue.prototype.$message;
	 
	var router = new VueRouter({
        routes : [
			{
				path : '/',
				redirect : function(to){
					var homePage = commons.getHomePage();
					return {name : homePage};
				},
				component : layoutComponent,
				children : mappings
			},
			{
				name : 'login',
				path : '/login',
				component : loginComponent
			}
		] 
	});
	
	router.beforeEach(function(to,from,next){
		if(to.name == 'login'){
			next();
			return;
		}
		
		var loginUser = commons.getLoginUser();
		if(!loginUser || !loginUser.token){
			Message.error('用户未登录');
			commons.goLogin();
			return;
		}
		
		next();
	});
	 
	router.afterEach((to, from) => {
		var activeIndex = to.meta.activeIndex;
		store.commit('setActiveIndex',activeIndex);
	});
	 
	window.router = router;
})(); 
 