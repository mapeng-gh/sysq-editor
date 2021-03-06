(function(){
	var userManage4UserListComponent = {
		template : `
            <div class="userManageList">
				<div class="common-title">用户列表</div>
				
				<!-- 列表搜索 -->
				<div class="common-search">
                    <el-form label-width="80px" label-position="left">
						<el-row :gutter="50">
							<el-col :span="8">
								<el-form-item label="姓名">
									<el-input v-model="search.name" placeholder="请输入姓名"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="类型">
									<el-select v-model="search.userType" style="width:100%;">
										<el-option value="" label="全部"></el-option>
										<el-option v-for="item in $constants.USER_TYPE.getUserTypeList()" :key="item.code" :label="item.text" :value="item.code"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="审核状态">
									<el-select v-model="search.auditStatus" style="width:100%;">
										<el-option value="" label="全部"></el-option>
										<el-option v-for="item in $constants.AUDIT_STATUS.getAuditStatusList()" :key="item.code" :label="item.text" :value="item.code"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<div class="common-search-opt">
							<el-button plain type="primary" size="medium" @click="handleSearch">查询</el-button>
							<el-button plain type="info" size="medium" @click="handleReset">重置</el-button>
						</div>
                    </el-form>
                </div>
                            
				<!-- 列表数据 -->			
				<div class="common-list">
					<el-table
						:data="userList"
						border
						header-cell-class-name="common-table-header"
						style="width: 100%">
						<el-table-column prop="loginName" label="登录账号" align="center" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="userType" label="类型" align="center" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								{{$constants.USER_TYPE.getUserTypeText(scope.row.userType)}}
							</template>
						</el-table-column>
						<el-table-column prop="name" label="姓名" align="center" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="workingPlace" label="工作单位" align="center" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="createTime" label="创建日期" align="center" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.createTime)}}
							</template>
						</el-table-column>
						<el-table-column prop="auditStatus" label="审核状态" align="center" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								<el-tag type="info" v-if="scope.row.auditStatus == $constants.AUDIT_STATUS.enums.ING">{{$constants.AUDIT_STATUS.getAuditStatusText(scope.row.auditStatus)}}</el-tag>
								<el-tag type="success" v-if="scope.row.auditStatus == $constants.AUDIT_STATUS.enums.PASS">{{$constants.AUDIT_STATUS.getAuditStatusText(scope.row.auditStatus)}}</el-tag>
								<el-tag type="danger" v-if="scope.row.auditStatus == $constants.AUDIT_STATUS.enums.REJECT">{{$constants.AUDIT_STATUS.getAuditStatusText(scope.row.auditStatus)}}</el-tag>
							</template>
						</el-table-column>
						<el-table-column prop="operate" label="操作" align="center" :show-overflow-tooltip="true" width="220">
							<template slot-scope="scope">
								<el-button type="text" size="small" @click="handleUserDetail(scope)">查看</el-button>
								<el-button type="text" size="small" @click="handleAuditDialog(scope)">审核</el-button>
								<el-button type="text" size="small" @click="handleAssignTask(scope)" :disabled="scope.row.userType != $constants.USER_TYPE.enums.EDITOR">分配</el-button>
								<el-dropdown trigger="click" @command="handleCommand" style="margin-left:10px;">
									<el-button type="text" size="small">更多操作<i class="el-icon-arrow-down"></i></el-button>
									<el-dropdown-menu slot="dropdown">
										<el-dropdown-item :command="{'code' : 'resetPwd' , 'data' : scope}">密码重置</el-dropdown-item>
										<el-dropdown-item :command="{'code' : 'changeType' , 'data' : scope}" :disabled="scope.row.userType != $constants.USER_TYPE.enums.VIEWER">身份更改</el-dropdown-item>
									</el-dropdown-menu>
								</el-dropdown>
							</template>
						</el-table-column>
					</el-table>
				</div>
                         
				<!-- 列表分页 -->			
				<div class="common-pagination">
					<el-pagination
						:page-sizes="[10,20,30,50]"
						:page-size="paginate.pageSize"
						:current-page="paginate.currentPage"
						layout="total,sizes,prev,pager,next,jumper"
						:total="paginate.total"
						:pager-count="7"
						background
						@current-change="handleCurrentChange"
						@size-change="handleSizeChange">
					</el-pagination>
				</div>
					
				<!-- 账号审核对话框 -->
				<el-dialog
					title="账号审核"
					:visible.sync="auditDialog.visible"
					width="50%">
					<el-form  label-width="80px">
						<el-form-item label="审核结果">
							<el-radio-group v-model="auditDialog.auditStatus">
								<el-radio v-for="item in auditDialog.auditStatusList" :label="item.code" :key="item.code">{{item.text}}</el-radio>
							</el-radio-group>
						</el-form-item>
						<el-form-item label="备注">
							<el-input v-model="auditDialog.remark" type="textarea" :rows="4" placeholder="请输入备注信息"></el-input>
						</el-form-item>
					</el-form>
					<span slot="footer" class="dialog-footer">
						<el-button @click="auditDialog.visible = false">取 消</el-button>
						<el-button type="primary" @click="handleAudit" :loading="auditDialog.loading">确 定</el-button>
					</span>
				</el-dialog>
					
            </div>
        `,
		
		data : function(){
			var self = this;
				
			return {
                                        
                APIS : {
                    USER_LIST : '/userManage/userList.do',
					USER_AUDIT : '/userManage/auditUser.do',
					USER_RESET_PWD : '/userManage/resetPwd.do',
					USER_CHANGE_TYPE : '/userManage/changeType.do'
                },
                                        
				userList : [],
				
				search : {
					name : '',
					userType : '',
					auditStatus : ''
				},
				
				paginate : {
					pageSize : 10,
					currentPage : 1,
					total : 0
				},
					
				auditDialog :{
					visible :false,
					auditStatusList : this.$lodash.filter(this.$constants.AUDIT_STATUS.getAuditStatusList(),function(item){return item.code != self.$constants.AUDIT_STATUS.enums.ING}),
					userId : '',
					auditStatus : '',
					remark : ''
				}
			}
		},
			
		mounted : function(){
            this.init();
        },
                        
        methods : {
                                
            init : function(){
                var self = this;
					
                this.$request.sendGetRequest(this.APIS.USER_LIST,this.$lodash.assign({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
					self.userList = resultObject.data;
					self.paginate.total = resultObject.total;
                });
            },
                                
			//搜索
			handleSearch : function(){
				var self = this;        
				this.paginate.currentPage = 1;
				
				this.$request.sendGetRequest(this.APIS.USER_LIST,this.$lodash.assign({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
					self.userList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
                                
			//重置
			handleReset : function(){
				var self = this;
				this.search = {name : '',userType : '',auditStatus : ''},
				this.paginate.currentPage = 1;
				
				this.$request.sendGetRequest(this.APIS.USER_LIST,this.$lodash.assign({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
					self.userList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
                                
			 //切换分页
			handleCurrentChange : function(currentPage){
				var self = this;
				this.paginate.currentPage = currentPage;

				this.$request.sendGetRequest(this.APIS.USER_LIST,this.$lodash.assign({},this.search,{currentPage:this.paginate.currentPage,pageSize:this.paginate.pageSize}),function(resultObject){
					self.userList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
                                
			//切换大小
			handleSizeChange : function(currentSize){
				var self = this;
				this.paginate.pageSize = currentSize;
				this.paginate.currentPage = 1;
 
				this.$request.sendGetRequest(this.APIS.USER_LIST,this.$lodash.assign({},this.search,{currentPage:this.paginate.currentPage,pageSize:this.paginate.pageSize}),function(resultObject){
					self.userList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
                                
			//查看详情
            handleUserDetail : function(scope){
				this.$router.push({name : 'userManage4UserDetail' , query : {userId : scope.row.id}});
            },
				
			//账号审核对话框
			handleAuditDialog : function(scope){
				this.auditDialog.visible = true;
				this.auditDialog.userId = scope.row.id;
				
				//数据清空
				this.auditDialog.auditStatus = this.$constants.AUDIT_STATUS.enums.PASS;
				this.auditDialog.remark = '';
			},
				
			//账号审核
			handleAudit : function(){
				var self = this;
				
				//参数校验
				if(this.auditDialog.auditStatus == this.$constants.AUDIT_STATUS.enums.REJECT && this.auditDialog.remark.trim().length == 0){
					this.$message.error('请在备注处填写审核不通过原因');
					return;
				}
				
				//发送请求
				this.$request.sendPostRequest(this.APIS.USER_AUDIT,{userId : this.auditDialog.userId , auditStatus : this.auditDialog.auditStatus , remark : this.auditDialog.remark},function(resultObject){
					self.$message.success('操作成功');
					
					//关闭对话框
					self.auditDialog.visible = false;
					
					//刷新列表
					self.$request.sendGetRequest(self.APIS.USER_LIST,self.$lodash.assign({},self.search,{currentPage:self.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
						self.userList = resultObject.data;
						self.paginate.total = resultObject.total;
					});
				});
				
			},
				
			//任务分配
			handleAssignTask : function(scope){
				this.$router.push({name : 'userManage4InterviewList' , query : {userId : scope.row.id}});
			},
			
			//更多操作
			handleCommand : function(command){
				var code = command.code;
				if(code == 'resetPwd'){
					this.resetPwd(command.data);
				}else if(code == 'changeType'){
					this.changeType(command.data);
				}
			},
			
			//密码重置
			resetPwd : function(scope){
				var self = this;
				self.$commons.confirm('确定将用户【'+scope.row.loginName+','+scope.row.name+'】密码重置为初始密码【sysq123】吗？','确认',function(){
					self.$request.sendFormRequest(self.APIS.USER_RESET_PWD,{userId : scope.row.id},function(resultObject){
						self.$message.success('操作成功');
						
						//刷新列表
						self.$request.sendGetRequest(self.APIS.USER_LIST,self.$lodash.assign({},self.search,{currentPage:self.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
							self.userList = resultObject.data;
							self.paginate.total = resultObject.total;
						});
					});
				});
			},
			
			//身份修改
			changeType : function(scope){
				var self = this;
				self.$commons.confirm('确定将用户【'+scope.row.loginName+','+scope.row.name+'】身份从【'+this.$constants.USER_TYPE.getUserTypeText(this.$constants.USER_TYPE.enums.VIEWER)+'】更改为【'+this.$constants.USER_TYPE.getUserTypeText(this.$constants.USER_TYPE.enums.EDITOR)+'】吗？','确认',function(){
					self.$request.sendFormRequest(self.APIS.USER_CHANGE_TYPE,{userId : scope.row.id , userType : self.$constants.USER_TYPE.enums.EDITOR},function(resultObject){
						self.$message.success('操作成功');
						
						//刷新列表
						self.$request.sendGetRequest(self.APIS.USER_LIST,self.$lodash.assign({},self.search,{currentPage:self.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
							self.userList = resultObject.data;
							self.paginate.total = resultObject.total;
						});
					});
				});
			}
        }
	};
	
	window.userManage4UserListComponent = userManage4UserListComponent;
})();


