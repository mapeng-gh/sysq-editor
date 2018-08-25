(function(){
	var userManageTaskComponent = {
		template : `
			<div class="user-manage-task">
			
				<div class="common-title">任务分配</div>
				
				<div class="bg-warning">
					温馨提示：暂不支持跨页进行任务分配
				</div>
				
				<div class="common-list-operate">
					<el-button type="primary" @click="handleAssignTask">分配</el-button>
				</div>
				
				<div class="common-list">
					<el-table
						:data="taskList"
						border
						header-cell-class-name="common-table-header"
						style="width: 100%"
						@select="handleSelect"
						@select-all="handleSelectAll">
						<el-table-column type="selection" width="55"></el-table-column>
						<el-table-column prop="interview.id" label="访谈编号" align="center"></el-table-column>
						<el-table-column prop="interview.type" label="类型"  align="center">
							<template slot-scope="scope">
                                                                {{$constants.INTERVIEW_TYPE.getInterviewTypeText(scope.row.interview.type)}}
                                                        </template>
						</el-table-column>
						<el-table-column prop="patient.username" label="患者姓名" align="center" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="doctor.username" label="医生姓名"  align="center" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="interview.versionId" label="问卷版本"  align="center"></el-table-column>
						<el-table-column prop="interview.endTime" label="访谈日期" align="center">
							<template slot-scope="scope">
                                                                {{$commons.formatDate(scope.row.interview.endTime)}}
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
			
			</div>
		`,
		
		data : function(){
			
			return {
				
				APIS : {
					TASK_LIST : '/userManage/taskList.do',
					ASSIGN_TASK : '/userManage/assignTask.do'
				},
				
				params : {
					userId : this.$route.query.userId	
				},
				
				taskList : [],
				
				paginate : {
					pageSize : 10,
					currentPage : 1,
					total : 0
                                },
				
				selectedTaskList : []
			}
		},
		
		mounted : function(){
			this.init();
		},
		
		methods : {
			
			init : function(){
				var self = this;
				
				this.$request.sendGetRequest(this.APIS.TASK_LIST,{currentPage:this.paginate.currentPage,pageSize:this.paginate.pageSize},function(resultObject){
                                        self.taskList = resultObject.data;
                                        self.paginate.total = resultObject.total;
                                });
			},
			
			//切换分页
			handleCurrentChange : function(currentPage){
				var self = this;
				this.paginate.currentPage = currentPage;
				
				this.$request.sendGetRequest(this.APIS.TASK_LIST,{currentPage:this.paginate.currentPage,pageSize:this.paginate.pageSize},function(resultObject){
					self.taskList = resultObject.data;
                                        self.paginate.total = resultObject.total;
				});
			},
			
			
			//切换大小
			handleSizeChange : function(currentSize){
				var self = this;
				this.paginate.currentPage = 1;
				this.paginate.pageSize = currentSize;
				 
				this.$request.sendGetRequest(this.APIS.TASK_LIST,{currentPage:this.paginate.currentPage,pageSize:this.paginate.pageSize},function(resultObject){
					self.taskList = resultObject.data;
                                        self.paginate.total = resultObject.total;
				});
			},
			
			//选择任务
			handleSelect : function(selection,row){
				this.selectedTaskList = selection;
			},
			
			//全选任务
			handleSelectAll : function(selection){
				this.selectedTaskList = selection;
			},
			
			//分配任务
			handleAssignTask : function(){
				var self = this;
				
				if(this.selectedTaskList.length == 0){
					this.$message.error('请先选择任务再进行分配');
					return;
				}
				
				var taskIdArray = this.$lodash.map(this.selectedTaskList,'interview.id');	
				this.$request.sendPostRequest(this.APIS.ASSIGN_TASK,{userId : this.params.userId , taskIds : taskIdArray.join(',')},(resultObject)=>{
					self.$message.success('分配成功');
					
					self.$request.sendGetRequest(self.APIS.TASK_LIST,{currentPage:self.paginate.currentPage,pageSize:self.paginate.pageSize},function(resultObject){
						self.taskList = resultObject.data;
						self.paginate.total = resultObject.total;
					});
				});
			}
		}
		
		
	};
	window.userManageTaskComponent = userManageTaskComponent;
})();


