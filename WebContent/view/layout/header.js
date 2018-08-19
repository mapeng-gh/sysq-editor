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
					<el-dropdown trigger="click"@command="handleCommand">
						<span>{{name}}<i class="el-icon-arrow-down"></i></span>
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
                                        USER_LOGOUT : '/userManage/logout.do'
                                }
			}
		},
                
                computed : {
			name : function(){
				var loginUser = JSON.parse(window.localStorage.getItem('loginUser')) || {};
				return loginUser.name || '';
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
                                                               self.$commons.logout();
                                                        });
                                                }
                                        }
                                });
                        }
                }
	};
	Vue.component('sysq-header',headerComponent);
})();