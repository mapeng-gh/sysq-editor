(function(){
	var usercenterProfileComponent = {
		template : `
			<div class="usercenter-profile">
			
				<div class="common-title">个人信息</div>
				
				<div class="common-detail">
					
					<el-form label-width="100px">
						
						<el-row :gutter="50">
							<el-col :span="12">
								
								<el-form-item label="用户姓名">
									<el-input v-model.trim="form.name" placeholder="请输入用户姓名"></el-input>
								</el-form-item>
								
							</el-col>
							
							<el-col :span="12">
							
								<el-form-item label="联系电话">
									<el-input v-model.trim="form.mobile" placeholder="请输入联系电话"></el-input>
								</el-form-item>
							
							</el-col>
						</el-row>
						
						<el-row :gutter="50">
							<el-col :span="12">
								
								<el-form-item label="电子邮箱">
									<el-input v-model.trim="form.email" placeholder="请输入电子邮箱"></el-input>
								</el-form-item>
								
							</el-col>
							
							<el-col :span="12">
							
								<el-form-item label="工作单位">
									<el-input v-model.trim="form.workingPlace" placeholder="请输入工作单位"></el-input>
								</el-form-item>
							
							</el-col>
						</el-row>
					
					</el-form>
				</div>
				
				<div class="common-detail-operate">
					<el-button plain @click="handleModifyProfile">修改</el-button>
				</div>
			
			</div>
		`,
		
		data : function(){
			
			return {
				
				APIS : {
					USER_PROFILE : '/usercenter/profile.do',
					MODIFY_PROFILE : '/usercenter/modifyProfile.do'
				},
				
				form : {
					name : '',
					mobile : '',
					email : '',
					workingPlace : ''
				}
			}
		},
		
		mounted : function(){
			this.init();
		},
		
		methods : {
			
			init : function(){
				var self = this;
				
				//获取用户信息
				this.$request.sendGetRequest(this.APIS.USER_PROFILE,{},function(resultObject){
					self.form = {
						name : resultObject.name,
						mobile : resultObject.mobile,
						email : resultObject.email,
						workingPlace : resultObject.workingPlace
					}
				});
			},
			
			//修改个人信息
			handleModifyProfile(){
				var self = this;
				
				//参数校验
				if(this.form.name.length ==0){
					this.$message.error('请填写姓名');
					return;
				}
				if(this.form.mobile.length == 0){
					this.$message.error('请填写联系电话');
					return;
				}
				if(!/^1\d{10}$/g.test(this.form.mobile)){
					this.$message.error('联系电话格式不正确');
					return;
				}
				if(this.form.email.length ==0){
					this.$message.error('请填写电子邮箱');
					return;
				}
				if(this.form.workingPlace.length ==0){
					this.$message.error('请填写工作单位');
					return;
				}
				
				//提交表单
				this.$request.sendPostRequest(this.APIS.MODIFY_PROFILE,this.form,(resultObject)=>{
					self.$message.success('修改成功');
				});
			}
		}
		
		
	};
	window.usercenterProfileComponent = usercenterProfileComponent;
})();


