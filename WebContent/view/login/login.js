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
                                                        <el-button type="text" @click="handleRegisterDialog">注册用户？</el-button>
                                                </div>
                                                
                                                
                                        </div>
                                        
                                </div>
		
                                <div class="footer">
                                        <div class="info">北京华晟信息技术有限公司版权所有</div>
                                </div>
				
				<el-dialog
					title="注册用户"
					:visible.sync="registerDialog.visible"
					width="50%"
					:close-on-click-modal="false"
					:close-on-press-escape="false">
					
					<el-form  label-width="80px">
					
						<el-form-item label="用户类型">
							<el-radio-group v-model="register.userType">
								<el-radio v-for="item in $constants.USER_TYPE.getUserTypeList()" :label="item.code" :key="item.code" v-if="item.code !=$constants.USER_TYPE.enums.ADMIN">{{item.text}}</el-radio>
							</el-radio-group>
						</el-form-item>
					
						<el-form-item label="登录账号">
							<el-input  v-model.trim="register.loginName" :placeholder="this.register.userType == $constants.USER_TYPE.enums.EDITOR?'提示：登录账号仅由大小写字母、数字、特殊符号(-_)组成，最大长度不超过20位' : '请填写您使用App绑定的手机号'"></el-input>
						</el-form-item>
						
						<el-form-item label="登录密码">
							<el-input type="password" v-model.trim="register.loginPwd" placeholder="提示：登录密码仅由大小写字母、数字、特殊符号(-_)组成，最少6位，最大长度不超过20位" ></el-input>
						</el-form-item>
						
						<el-form-item label="用户姓名">
							<el-input v-model.trim="register.name" placeholder="提示：用户姓名最大长度不超过10位" ></el-input>
						</el-form-item>
						
						<el-form-item label="联系电话">
							<el-input v-model.trim="register.mobile" placeholder="请输入联系电话" ></el-input>
						</el-form-item>
						
						<el-form-item label="电子邮箱">
							<el-input v-model.trim="register.email" placeholder="请输入电子邮箱" ></el-input>
						</el-form-item>
						
						<el-form-item label="工作单位">
							<el-input v-model.trim="register.workingPlace" placeholder="提示：工作单位最大长度不超过50位" ></el-input>
						</el-form-item>
						
					</el-form>
				
					<span slot="footer" class="dialog-footer">
						<el-button @click="registerDialog.visible = false">取 消</el-button>
						<el-button type="primary" @click="handleRegister">确 定</el-button>
					</span>
				</el-dialog>
		
                        </div>
			
			
		`,
		
		data : function(){
			
			return {
				
				APIS : {
					REGISTER_USER : '/register.do'
				},
				
				loginName : '18110879836',
                                loginPwd : '123456',
				
				registerDialog : {
					visible : false
				},
				
				register : {
					userType : this.$constants.USER_TYPE.enums.EDITOR,
					loginName : '',
					loginPwd : '',
					name : '',
					mobile : '',
					email : '',
					workingPlace : ''
				}
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
			},
			
			//注册对话框
			handleRegisterDialog : function(){
				this.registerDialog.visible = true;
				this.register = {
					userType : this.$constants.USER_TYPE.enums.EDITOR,
					loginName : '',
					loginPwd : '',
					name : '',
					mobile : '',
					email : '',
					workingPlace : ''
				};
			},
			
			//注册
			handleRegister : function(){
				var self = this;
				
				//参数校验
				if(this.register.loginName.length == 0){
					this.$message.error('请填写登录账号');
					return;
				}
				if(this.register.userType == this.$constants.USER_TYPE.enums.EDITOR){
					if(!/^[\da-zA-Z_-]{1,20}$/g.test(this.register.loginName)){
						this.$message.error('登录账号格式不正确');
						return;
					}
				}else if(this.register.userType == this.$constants.USER_TYPE.enums.VIEWER){
					if(!/^1\d{10}$/g.test(this.register.loginName)){
						this.$message.error('登陆账号格式不正确，浏览人员须使用App关联的手机号');
						return;
					}
				}
				
				if(this.register.loginPwd.length == 0){
					this.$message.error('请填写登录密码');
					return;
				}
				if(!/^[\da-zA-Z_-]{6,20}$/g.test(this.register.loginPwd)){
					this.$message.error('登录密码格式不正确');
					return;
				}
				
				if(this.register.name.length == 0){
					this.$message.error('请填写用户姓名');
					return;
				}
				if(this.register.name.length > 10){
					this.$message.error('用户姓名格式不正确');
					return;
				}
				
				if(this.register.mobile.length == 0){
					this.$message.error('请填写联系电话');
					return;
				}
				if(!/^1\d{10}$/g.test(this.register.mobile)){
					this.$message.error('联系电话格式不正确');
					return;
				}
				
				if(this.register.email.length == 0){
					this.$message.error('请填写电子邮箱');
					return;
				}
				
				if(this.register.workingPlace.length == 0){
					this.$message.error('请填写工作单位');
					return;
				}
				if(this.register.workingPlace.length > 50){
					this.$message.error('工作单位格式不正确');
					return;
				}
				
				//确认
				this.$confirm('注册成功后需要管理员进行审核，审核通过后才能登陆使用系统','确认',{
                                        callback : function(action){
                                                if(action == 'confirm'){
                                                       
						       //提交数据
							self.$request.sendPostRequest(self.APIS.REGISTER_USER,self.register,(resultObject)=>{
								self.$message.success('注册成功');
								self.registerDialog.visible = false;
							});
                                                }
                                        }
                                });
			}
                }
                
	};
	window.loginComponent = loginComponent;
      
})();