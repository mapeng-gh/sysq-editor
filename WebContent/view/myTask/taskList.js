(function(){
	var myTask4TaskListComponent = {
		template : `
			<div class="my-task-task-list">
			
				<div class="common-title">任务列表</div>
				
				<div class="common-search">
					<el-form label-width="80px" label-position="left">
						<el-row :gutter="50">
							<el-col :span="8">
								<el-form-item label="患者姓名">
									<el-input v-model="search.patientName" placeholder="请输入患者姓名"></el-input>
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
							<el-button type="primary" @click="handleSearch">查询</el-button>
							<el-button @click="handleReset">重置</el-button>
						</div>
					</el-form>
	
				</div>
				
				<div class="common-list">
					<el-table
						:data="taskList"
						border
						header-cell-class-name="common-table-header"
						style="width: 100%">
						<el-table-column prop="task.id" label="任务编号" align="left" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="interview.id" label="访谈编号" align="left" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="interview.type" label="访谈类型" align="left" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								{{$constants.INTERVIEW_TYPE.getInterviewTypeText(scope.row.interview.type)}}
							</template>
						</el-table-column>
						<el-table-column prop="interview.versionId" label="问卷版本" align="left" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="patient.username" label="患者姓名" align="left" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="interview.endTime" label="访谈时间" align="left" width="180" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.interview.endTime)}}
							</template>
						</el-table-column>
						<el-table-column prop="task.createTime" label="分配时间" align="left" width="180" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.task.createTime)}}
							</template>
						</el-table-column>
						<el-table-column prop="task.status" label="任务状态" align="left" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								<el-tag v-if="scope.row.task.status == $constants.TASK_STATUS.enums.ASSIGNED" type="info">{{$constants.TASK_STATUS.getTaskStatusText(scope.row.task.status)}}</el-tag>
								<el-tag v-if="scope.row.task.status == $constants.TASK_STATUS.enums.EDITING" type="warning">{{$constants.TASK_STATUS.getTaskStatusText(scope.row.task.status)}}</el-tag>
								<el-tag v-if="scope.row.task.status == $constants.TASK_STATUS.enums.FINISHED" type="success">{{$constants.TASK_STATUS.getTaskStatusText(scope.row.task.status)}}</el-tag>
							</template>
						</el-table-column>
						<el-table-column prop="operate" label="操作" align="left" width="120" :show-overflow-tooltip="true">
							<template slot-scope="scope">
								<el-button type="text" size="mini" @click="handleQuestionaireList(scope)">问卷列表</el-button>
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
			var self = this;
			
			return {
				
				APIS : {
					TASK_LIST : '/myTask/taskList.do'
				},
				
				search : {
					patientName : '',
					taskStatus : ''
				},
				
				taskList : [],
				
				paginate : {
					pageSize : 10,
					currentPage : 1,
					total : 0
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
				this.search = {patientName : '' , taskStatus : ''},
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
			
			//问卷列表
			handleQuestionaireList(scope){
				this.$commons.openWindow('#/myTask/questionaireList',{taskId : scope.row.task.id});
			}
		}
	};
	window.myTask4TaskListComponent = myTask4TaskListComponent;
})();


