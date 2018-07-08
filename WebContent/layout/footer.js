(function(){
	var template = `
			<div class="footer-content">
					<span class="footer-info">&copy;抑郁症数据编辑平台 2018 V1.0</span>
			</div>
	`;
	
	var FooterComponent = {
			template : template,
			data : function(){
				return {
				}
			}
	};
	Vue.component('sysq-footer',FooterComponent);
})();