(function(){
	var taskManage4TaskListComponent = {
		template : `
            <div class="task-manage-list">
                                
                <div class="common-title">任务列表</div>
                             
				<!-- 列表搜索 -->		
				<div class="common-search">
					<el-form label-width="80px" label-position="left">
						<el-row :gutter="50">
							<el-col :span="8">
								<el-form-item label="编辑人员">
									<el-input v-model="search.editorName" placeholder="请输入编辑人员姓名"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="访谈员">
									<el-input v-model="search.doctorName" placeholder="请输入访谈员姓名"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="任务状态">
									<el-select v-model="search.taskStatus" style="width:100%;">
										<el-option value="" label="全部"></el-option>
										<el-option v-for="item in $constants.TASK_STATUS.getTaskStatusList()" :key="item.code" :label="item.text" :value="item.code"></el-option>
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
						:data="taskList"
						border
						header-cell-class-name="common-table-header"
						style="width: 100%">
						<el-table-column prop="task.id" label="任务编号" align="center" :show-overflow-tooltip="true"></el-table-column>,
						<el-table-column prop="interview.id" label="访谈编号" align="center" :show-overflow-tooltip="true"></el-table-column>,
						<el-table-column prop="interview.type" label="访谈类型" align="center" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								{{$constants.INTERVIEW_TYPE.getInterviewTypeText(scope.row.interview.type)}}
							</template>
						</el-table-column>
						<el-table-column prop="doctor.username" label="访谈员" align="center" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="user.name" label="编辑员" align="center" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="task.createTime" label="创建时间" align="center" width="180" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.task.createTime)}}
							</template>
						</el-table-column>
						<el-table-column prop="task.updateTime" label="更新时间" align="center" width="180" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.task.updateTime)}}
							</template>
						</el-table-column>
						<el-table-column prop="task.status" label="任务状态" align="center" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								<el-tag v-if="scope.row.task.status == $constants.TASK_STATUS.enums.ASSIGNED" type="info">{{$constants.TASK_STATUS.getTaskStatusText(scope.row.task.status)}}</el-tag>
								<el-tag v-if="scope.row.task.status == $constants.TASK_STATUS.enums.EDITING" type="warning">{{$constants.TASK_STATUS.getTaskStatusText(scope.row.task.status)}}</el-tag>
								<el-tag v-if="scope.row.task.status == $constants.TASK_STATUS.enums.FINISHED" type="success">{{$constants.TASK_STATUS.getTaskStatusText(scope.row.task.status)}}</el-tag>
							</template>
						</el-table-column>
						<el-table-column prop="operate" label="操作" align="center" :show-overflow-tooltip="true" width="120">
							<template slot-scope="scope">
								<el-button type="text" size="small" @click="handleReAssignDialog(scope)">重新分配</el-button>
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
				
				<!-- 重新分配 -->
				<el-dialog
					title="重新分配"
					:visible.sync="reAssignDialog.visible"
					width="50%">
					<el-form  label-width="80px" label-position="left">
						<el-form-item label="编辑员 ：">
							 <el-select v-model="reAssignDialog.userId" filterable placeholder="请选择编辑员" style="width:100%;">
								<el-option v-for="item in reAssignDialog.userList" :key="item.id" :label="item.name" :value="item.id"></el-option>
							</el-select>
						</el-form-item>
					</el-form>
					<span slot="footer">
						<el-button @click="reAssignDialog.visible = false">取 消</el-button>
						<el-button type="primary" @click="handleReAssign">确 定</el-button>
					</span>
				</el-dialog>
					
            </div>
        `,
		
		data : function(){
			var self = this;
			
			return {
									
				APIS : {
					TASK_LIST : '/taskManage/taskList.do',
					EDITOR_LIST : '/userManage/getEditorList.do',
					TASK_REASSIGN : '/taskManage/reAssign.do'
				},
				
				search : {
					editorName : '',
					taskStatus : '',
					doctorName : ''
				},
				
				taskList : [],
									
				paginate : {
					pageSize : 10,
					currentPage : 1,
					total : 0
				},
				
				reAssignDialog:{
					visible : false,
					taskId : '',
					userList : [],
					userId : ''
				}
				
			}
		},
			
		mounted : function(){
            this.init();
        },
                        
        methods : {
                                
            init : function(){
                var self = this;
					
				this.$request.sendGetRequest(this.APIS.TASK_LIST,this.$lodash.assign({},this.search,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					this.taskList = resultObject.data;
					this.paginate.total = resultObject.total;
				});
            },
                                
            //搜索
            handleSearch : function(){
				var self = this;        
				this.paginate.currentPage = 1;
                                        
                this.$request.sendGetRequest(this.APIS.TASK_LIST,this.$lodash.assign({},this.search,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.taskList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
            },
                                
            //重置
            handleReset : function(){
				var self = this;
				this.search = {editorName : '' , taskStatus : '' , doctorName : ''};
				this.paginate.currentPage = 1;
                                        
                this.$request.sendGetRequest(this.APIS.TASK_LIST,this.$lodash.assign({},this.search,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.taskList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
            },
                                
            //切换分页
            handleCurrentChange : function(currentPage){
				var self = this;
				this.paginate.currentPage = currentPage;
					
                this.$request.sendGetRequest(this.APIS.TASK_LIST,this.$lodash.assign({},this.search,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
						self.taskList = resultObject.data;
						self.paginate.total = resultObject.total;
				});
            },
                                
            //切换大小
            handleSizeChange : function(currentSize){
				var self = this;
				this.paginate.currentPage = 1;
				this.paginate.pageSize = currentSize;
					 
                this.$request.sendGetRequest(this.APIS.TASK_LIST,this.$lodash.assign({},this.search,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.taskList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
            },
			
			//重新分配对话框
			handleReAssignDialog : function(scope){
				var self = this;
				
				this.reAssignDialog.userList = [];
				this.reAssignDialog.userId = '';
				this.reAssignDialog.taskId = scope.row.task.id;
				
				//获取编辑员列表
				this.$request.sendGetRequest(this.APIS.EDITOR_LIST,{},(resultObject)=>{
					self.reAssignDialog.userList = self.$lodash.reject(resultObject,{id : scope.row.user.id});//剔除自己
				});
				
				this.reAssignDialog.visible = true;
			},
			
			//重新分配
			handleReAssign : function(){
				var self = this;
				
				if(self.reAssignDialog.userList.length == 0){
					self.$message.error('没有可选的编辑员可分配');
					return;
				}
				if(self.reAssignDialog.userId == ''){
					self.$message.error('请选择重新分配的编辑员');
					return;
				}
				
				self.$request.sendFormRequest(self.APIS.TASK_REASSIGN,{taskId : self.reAssignDialog.taskId , userId : self.reAssignDialog.userId},function(resultObject){
					self.$message.success('操作成功');
					self.reAssignDialog.visible = false;
					
					//刷新列表
					self.$request.sendGetRequest(self.APIS.TASK_LIST,self.$lodash.assign({},self.search,{currentPage : self.paginate.currentPage,pageSize:self.paginate.pageSize}),(resultObject)=>{
						self.taskList = resultObject.data;
						self.paginate.total = resultObject.total;
					});
				});
			}
        }
	};
	
	window.taskManage4TaskListComponent = taskManage4TaskListComponent;
})();


