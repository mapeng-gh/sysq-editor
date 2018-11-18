(function(){
	var usercenterModifyPwdComponent = {
		template : `
			<div class="usercenter-modify-pwd">
			
				<div class="common-title">修改密码</div>
				
				<div class="bg-warning">提示：密码仅由大小写字母、数字、特殊符号(-_)组成，最少6位，最大长度不超过20位</div>
				
				<div class="common-detail">
					
					<el-form label-width="100px">
						
						<el-row>
							<el-col :span="12">
								
								<el-form-item label="原密码">
									<el-input type="password" v-model.trim="form.oldPwd" placeholder="请输入原密码"></el-input>
								</el-form-item>
								
								<el-form-item label="新密码">
									<el-input type="password" v-model.trim="form.newPwd" placeholder="请输入新密码"></el-input>
								</el-form-item>
								
								<el-form-item label="确认新密码">
									<el-input type="password" v-model.trim="form.confirmPwd" placeholder="请再次确认密码"></el-input>
								</el-form-item>
								
								<div style="text-align : right;">
									<el-button type="primary" @click="handleModifyPwd">修改</el-button>
								</div>
								
							</el-col>
							
						</el-row>
						
					</el-form>
				</div>
				
			</div>
		`,
		
		data : function(){
			
			return {
				
				APIS : {
					MODIFY_PWD : '/usercenter/modifyPwd.do'
				},
				
				form : {
					oldPwd : '',
					newPwd : '',
					confirmPwd : ''
				}
			}
		},
		
		mounted : function(){
		},
		
		methods : {
			
			//修改个人信息
			handleModifyPwd(){
				var self = this;
				
				//数据校验
				if(this.form.oldPwd.length == 0){
					this.$message.error('请输入原密码');
					return;
				}
				if(!/^[0-9a-zA-Z_-]{6,20}$/g.test(this.form.oldPwd)){
					this.$message.error('原密码格式不正确');
					return;
				}
				
				if(this.form.newPwd.length == 0){
					this.$message.error('请输入新密码');
					return;
				}
				if(!/^[0-9a-zA-Z_-]{6,20}$/g.test(this.form.newPwd)){
					this.$message.error('新密码格式不正确');
					return;
				}
				if(this.form.newPwd == this.form.oldPwd){
					this.$message.error('新密码不能与旧密码一样');
					return;
				}
				
				if(this.form.confirmPwd.length == 0){
					this.$message.error('请输入确认密码');
					return;
				}
				if(this.form.confirmPwd != this.form.newPwd){
					this.$message.error('新密码两次输入不一致');
					return;
				}
				
				//提交表单
				this.$request.sendPostRequest(this.APIS.MODIFY_PWD,{oldPwd : this.form.oldPwd , newPwd : this.form.newPwd},(resultObject)=>{
					self.$message.success('修改成功');
				});
			}
		}
		
		
	};
	window.usercenterModifyPwdComponent = usercenterModifyPwdComponent;
})();


