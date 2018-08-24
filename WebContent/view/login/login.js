(function(){
      
        var loginComponent = {
			
		template : `
                        
                        <div class="login">
		
                                <div class="header">
			
                                        <div class="left el-col-5">
                                                <img class="logo" src="/static/image/logo.png"/>
                                                <span class="title">抑郁症数据编辑平台</span>
                                        </div>
		
                                        <div class="center el-col-15"></div>
		
                                        <div class="right el-col-4">
                                                服务热线：400-100-5619
                                        </div>
			
                                </div>
		
                                <div class="main">
                                        
                                        <div class="content">
                                                
                                                <div class="title">账号登录</div>
                                                
                                                <div class="name">
                                                        <el-input
                                                                placeholder="请输入您的账号"
                                                                v-model="loginName">
                                                                <i slot="prefix" class="el-input__icon fa fa-user"></i>
                                                        </el-input>
                                                </div>
                                                
                                                <div class="pwd">
                                                        <el-input
                                                                placeholder="请输入您的密码"
                                                                v-model="loginPwd"
                                                                type="password">
                                                                <i slot="prefix" class="el-input__icon fa fa-key"></i>
                                                        </el-input>
                                                </div>
                                                
                                                <div class="operate">
                                                        <el-button style="width:100%;" type="primary" @click="login">登录</el-button>
                                                </div>
                                                
                                                <div class="forget">
                                                        <el-button type="text">用户注册？</el-button>
                                                </div>
                                                
                                                
                                        </div>
                                        
                                </div>
		
                                <div class="footer">
                                        <div class="info">北京华晟信息技术有限公司版权所有</div>
                                </div>
		
                        </div>
		`,
		
		data : function(){
			return {
				loginName : '18110879836',
                                loginPwd : '123456'
			}
		},
                
                methods: {
        	
                        //登录
			login : function(){
                                var self = this;
                                
				this.$request.sendPostRequest('/login.do',{loginName : this.loginName,loginPwd : this.loginPwd},function(resultObject){
					window.localStorage.setItem('loginUser',JSON.stringify(resultObject));
                                        
                                        self.$router.push({name : 'userManageList'});
				});
			}
                }
                
	};
	window.loginComponent = loginComponent;
      
})();