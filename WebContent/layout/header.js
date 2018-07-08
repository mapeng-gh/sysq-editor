(function(){
	var headerComponent = {
			template : `
				<div class="header-content">
						<div class="header-wrapper">
								<img class="header-logo" src="/img/logo.png"/>
								<span class="header-title">抑郁症数据编辑平台</span>
								
								<el-dropdown class="header-name" trigger="click">
										<span>
												张三<i class="el-icon-caret-bottom el-icon--right"></i>
										</span>
										  <el-dropdown-menu slot="dropdown">
										    	<el-dropdown-item>退出登录</el-dropdown-item>
										  </el-dropdown-menu>
								</el-dropdown>
						</div>
						
				</div>
			`,
			data : function(){
				return {
				}
			}
	};
	Vue.component('sysq-header',headerComponent);
})();