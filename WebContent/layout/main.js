(function(){
	var MainComponent = {
			template : `
				<div class="main-content">
						<router-view></router-view>
				</div>
			`,
			data : function(){
				return {
				}
			}
	};
	Vue.component('sysq-main',MainComponent);
})();