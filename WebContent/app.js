(function(){
	
	var appComponent = {
			
		template : `
			<router-view></router-view>
		`,
		
		data : function(){
			return {
				
			}
		}
	};
	
	Vue.component('App',appComponent);
})();