(function(){
	var headerComponent = {
			
		template : `
			<el-row>
				<el-col :span="4" class="header-left">
					<img class="logo" src="/static/image/logo.jpg"/>
					<span class="title">抑郁症数据编辑平台</span>
				</el-col>
				
				<el-col :span="4" :offset="16" class="header-right">
					<el-dropdown trigger="click" @command="handleCommand">
						<span>{{name}} <i class="el-icon-arrow-down"></i></span>
						 <el-dropdown-menu slot="dropdown">
							<el-dropdown-item command="logout">退出登录</el-dropdown-item>
						  </el-dropdown-menu>
					</el-dropdown>
				</el-col>
			</el-row>
		`,
		
		data : function(){
			return {
				APIS : {
						USER_LOGOUT : '/logout.do'
				}
			}
		},
                
        computed : {
			name : function(){
				var loginUserStr = window.localStorage.getItem('loginUser');
				if(loginUserStr){
					var name = JSON.parse(loginUserStr).name;
					if(name.length > 4){
						name = name.substring(0,4) + '...';
					}
					return name;
				}
				return '';
			}
        },
                
        methods : {
                        
			//菜单处理
			handleCommand(command){
				if(command == 'logout'){
					this.handleLogout();
				}
			},
                        
			//退出登录
			handleLogout(){
				var self = this;
					
				this.$confirm('确定退出登录吗？','确认',{
					callback : function(action){
						if(action == 'confirm'){
							self.$request.sendPostRequest(self.APIS.USER_LOGOUT,{},function(resultObject){
									window.localStorage.removeItem('loginUser');
									self.$commons.goLogin();
							});
						}
					}
				});
			}
        }
	};
	Vue.component('sysq-header',headerComponent);
})();