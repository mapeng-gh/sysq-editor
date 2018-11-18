(function(){
	var contentComponent = {
			
		template : `
		
			<router-view></router-view>
			
		`,
		
		data : function(){
			return {
			}
		}
	};
	Vue.component('sysq-content',contentComponent);
})();