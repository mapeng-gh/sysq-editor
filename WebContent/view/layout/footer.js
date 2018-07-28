(function(){
	var FooterComponent = {
			
		template : `
			<div>
				<div class="info">&copy;抑郁症数据编辑平台 2018 V1.0</div>
			</div>
		`,
		
		data : function(){
			return {
				
			}
		}
	};
	Vue.component('sysq-footer',FooterComponent);
})();