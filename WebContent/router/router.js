 (function(){
	 
	 var router = new VueRouter({
		 routes : [
			 {
					path : '/',
					redirect : '/userManage/list',
					component : layoutComponent,
					children : mappings
			 }
		] 
	 });
	 
	 router.afterEach((to, from) => {
	        var activeIndex = to.meta.activeIndex;
	        store.commit('setActiveIndex',activeIndex);
	 });
	 
	 window.router = router;
	 
 })(); 
 