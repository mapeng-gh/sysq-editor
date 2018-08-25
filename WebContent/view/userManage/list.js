(function(){
	var userManageListComponent = {
			template : `
                                <div class="userManageList">
                                
                                        <div class="common-title">用户列表</div>
                                        
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
                                                                <el-button type="primary" @click="handleSearch">查询</el-button>
                                                                <el-button @click="handleReset">重置</el-button>
                                                        </div>
			
                                                </el-form>
		
                                        </div>
                                        
                                        <div class="common-list">
                                                <el-table
                                                        :data="userList"
                                                        border
                                                        header-cell-class-name="common-table-header"
                                                        style="width: 100%"
							v-loading="isLoading"
							element-loading-text="正在加载中..."
							element-loading-spinner="el-icon-loading">
                                                        <el-table-column prop="name" label="姓名" width="150" align="center"></el-table-column>
                                                        <el-table-column prop="userType" label="类型" width="150" align="center">
                                                                <template slot-scope="scope">
                                                                        {{$constants.USER_TYPE.getUserTypeText(scope.row.userType)}}
                                                                </template>
                                                        </el-table-column>
                                                        <el-table-column prop="workingPlace" label="工作单位" width="250" align="center" :show-overflow-tooltip="true"></el-table-column>
                                                        <el-table-column prop="createTime" label="创建日期" width="180" align="center">
                                                                <template slot-scope="scope">
                                                                        {{$commons.formatDate(scope.row.createTime)}}
                                                                </template>
                                                        </el-table-column>
                                                        <el-table-column prop="auditStatus" label="审核状态" width="150" align="center">
                                                                <template slot-scope="scope">
                                                                        <el-tag type="info" v-if="scope.row.auditStatus == $constants.AUDIT_STATUS.enums.ING">{{$constants.AUDIT_STATUS.getAuditStatusText(scope.row.auditStatus)}}</el-tag>
                                                                        <el-tag type="success" v-if="scope.row.auditStatus == $constants.AUDIT_STATUS.enums.PASS">{{$constants.AUDIT_STATUS.getAuditStatusText(scope.row.auditStatus)}}</el-tag>
                                                                        <el-tag type="danger" v-if="scope.row.auditStatus == $constants.AUDIT_STATUS.enums.REJECT">{{$constants.AUDIT_STATUS.getAuditStatusText(scope.row.auditStatus)}}</el-tag>
                                                                </template>
                                                        </el-table-column>
                                                        <el-table-column prop="operate" label="操作" align="center">
                                                                <template slot-scope="scope">
                                                                        <el-button type="text" size="mini" @click="handleUserDetail(scope)">查看详情</el-button>
                                                                        <el-button type="text" size="mini" @click="handleAuditDialog(scope)" :disabled="scope.row.userType == $constants.USER_TYPE.enums.ADMIN">账号审核</el-button>
                                                                        <el-button type="text" size="mini" @click="handleAssignTask(scope)" :disabled="scope.row.userType != $constants.USER_TYPE.enums.EDITOR">任务分配</el-button>
                                                                </template>
                                                        </el-table-column>
                                                </el-table>
                                        </div>
                                        
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
                                                USER_LIST : '/userManage/list.do',
						USER_AUDIT : 'userManage/audit.do'
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
					
					isLoading : false,
					
					auditDialog :{
						visible :false,
						auditStatusList : this.$lodash.filter(this.$constants.AUDIT_STATUS.getAuditStatusList(),function(item){return item.code != self.$constants.AUDIT_STATUS.enums.ING}),
						userId : '',
						auditStatus : '',
						remark : '',
						loading : false
					}
				}
			},
			
			mounted : function(){
                                this.init();
                        },
                        
                        methods : {
                                
                                init : function(){
                                        var self = this;
                                        this.isLoading = true;
					
                                        this.$request.sendGetRequest(this.APIS.USER_LIST,
                                                _.assignIn({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),
                                                function(resultObject){
							self.isLoading = false;
							
                                                        self.userList = resultObject.data;
                                                        self.paginate.total = resultObject.total;
                                                
                                                }
                                        );
                                },
                                
                                //搜索
                                handleSearch : function(){
                                        var self = this;        
                                        this.paginate.currentPage = 1;
					 this.isLoading = true;
                                        
                                        this.$request.sendGetRequest(this.APIS.USER_LIST,
                                                _.assignIn({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),
                                                function(resultObject){
							 self.isLoading = false;
							
                                                        self.userList = resultObject.data;
                                                        self.paginate.total = resultObject.total;
                                                
                                                }
                                        );
                                },
                                
                                //重置
                                handleReset : function(){
                                        var self = this;
                                        this.search = {name : '',userType : '',auditStatus : ''},
                                        this.paginate.currentPage = 1;
					this.isLoading = true;
                                        
                                        this.$request.sendGetRequest(this.APIS.USER_LIST,
                                                this.$lodash.assignIn({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),
                                                function(resultObject){
							self.isLoading = false;
							
                                                        self.userList = resultObject.data;
                                                        self.paginate.total = resultObject.total;
                                                
                                                }
                                        );
                                },
                                
                                 //切换分页
                                handleCurrentChange : function(currentPage){
                                        var self = this;
                                        this.paginate.currentPage = currentPage;
					this.isLoading = true;
					
                                        this.$request.sendGetRequest(this.APIS.USER_LIST,this.$lodash.assignIn({},this.search,{currentPage:this.paginate.currentPage,pageSize:this.paginate.pageSize}),function(resultObject){
                                                self.isLoading = false;
						
						self.userList = resultObject.data;
                                                self.paginate.total = resultObject.total;
                                        });
                                },
                                
                                //切换大小
                                handleSizeChange : function(currentSize){
                                        var self = this;
                                        this.paginate.pageSize = currentSize;
                                        this.paginate.currentPage = 1;
					this.isLoading = true; 
					 
                                         this.$request.sendGetRequest(this.APIS.USER_LIST,this.$lodash.assignIn({},this.search,{currentPage:this.paginate.currentPage,pageSize:this.paginate.pageSize}),function(resultObject){
                                                self.isLoading = false;
						
						self.userList = resultObject.data;
                                                self.paginate.total = resultObject.total;
                                        });
                                },
                                
				//查看详情
                                handleUserDetail : function(scope){
					this.$commons.openWindow('#/userManage/detail',{userId : scope.row.id});
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
					this.auditDialog.loading = true;
					this.$request.sendPostRequest(this.APIS.USER_AUDIT,{userId : this.auditDialog.userId , auditStatus : this.auditDialog.auditStatus , remark : this.auditDialog.remark},function(resultObject){
						self.auditDialog.loading = false;
						
						//关闭对话框
						self.auditDialog.visible = false;
						
						//刷新列表
						self.isLoading = true;
						self.$request.sendGetRequest(self.APIS.USER_LIST,self.$lodash.assignIn({},self.search,{currentPage:self.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
							self.isLoading = false;
							
                                                        self.userList = resultObject.data;
                                                        self.paginate.total = resultObject.total;
                                                });
					});
					
				},
				
				//任务分配
				handleAssignTask : function(scope){
					this.$commons.openWindow('#/userManage/task',{userId : scope.row.id});
				}
                        }
                        
                       
	};
	window.userManageListComponent = userManageListComponent;
})();


