(function(){
	var userManage4UserDetailComponent = {
		template : `
			<div class="user-manage-detail">
				<div class="common-title">用户详情</div>
				
				<div class="common-detail">
					<el-form label-width="100px">
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="登录账号">
									<el-input :value="userDetail.loginName"  class="readonly" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="账号类型">
									<el-input :value="$constants.USER_TYPE.getUserTypeText(userDetail.userType)" class="readonly" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="用户姓名">
									<el-input :value="userDetail.name" class="readonly" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="联系电话">
									<el-input :value="userDetail.mobile" class="readonly" readonly></el-input>
								</el-form-item>
							
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="电子邮箱">
									<el-input :value="userDetail.email" class="readonly" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="工作单位">
									<el-input :value="userDetail.workingPlace" class="readonly" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="审核状态">
									<el-input :value="$constants.AUDIT_STATUS.getAuditStatusText(userDetail.auditStatus)" class="readonly" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="创建时间">
									<el-input :value="$commons.formatDate(userDetail.createTime)" class="readonly" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="备注">
									<el-input type="textarea" :rows="4" :value="userDetail.remark" class="readonly" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
					</el-form>
				</div>
				
				<div class="common-detail-operate">
					<el-button plain type="info" size="medium" @click="handleBack">返回</el-button>
				</div>
			</div>
		`,
		
		data : function(){
			return {
				APIS : {
					USER_DETAIL : '/userManage/userDetail.do'
				},
				
				params : {
					userId : this.$route.query.userId	
				},
				
				userDetail : {}
			}
		},
		
		mounted : function(){
			this.init();
		},
		
		methods : {
			init : function(){
				var self = this;
				
				this.$request.sendGetRequest(this.APIS.USER_DETAIL,{userId : this.params.userId},function(resultObject){
					self.userDetail = resultObject;
				});
			},
			
			//返回
			handleBack(){
				this.$router.push({name : 'userManage4UserList'});
			}
		}
	};
	
	window.userManage4UserDetailComponent = userManage4UserDetailComponent;
})();


