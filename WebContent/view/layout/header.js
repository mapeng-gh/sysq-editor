(function(){
	var headerComponent = {
			
		template : `
			<el-row>
				
				<el-col class="left" :span="4">
					<img class="logo" src="/static/image/logo.png"/>
					<span class="title">抑郁症数据编辑平台</span>
				</el-col>
				
				<el-col class="right" :span="2" :offset="18">
					<i class="fa fa-user-circle"></i>
					<el-dropdown trigger="click">
						<span>张三<i class="el-icon-arrow-down"></i></span>
						 <el-dropdown-menu slot="dropdown">
						 	<el-dropdown-item>修改密码</el-dropdown-item>
							<el-dropdown-item divided>退出登录</el-dropdown-item>
						  </el-dropdown-menu>
					</el-dropdown>
				</el-col>
				
			</el-row>
		`,
		
		data : function(){
			return {
				
			}
		}
	};
	Vue.component('sysq-header',headerComponent);
})();