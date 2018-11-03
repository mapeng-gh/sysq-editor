(function(){
	var log4EditLogListComponent = {
		
		template : `
			<div class="log-edit-log-list">
			
				<div class="common-title">编辑日志</div>
				
				<div class="common-search">
					<el-form label-width="80px" label-position="left">
						<el-row :gutter="50">
							<el-col :span="8">
								<el-form-item label="编辑日期">
									<el-date-picker
											v-model="search.editDateRange"
											type="daterange"
											range-separator="至"
											start-placeholder="开始日期"
											end-placeholder="结束日期"
											format="yyyy-MM-dd"
											value-format="yyyy-MM-dd"
											:editable="false"
											style="width:100%;">
									</el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="编辑账号">
									<el-input v-model="search.loginName" placeholder="请输入编辑账号" clearable></el-input>
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
						:data="editLogList"
						border
						header-cell-class-name="common-table-header"
						style="width: 100%">
						<el-table-column prop="loginName" label="编辑账号" align="center"></el-table-column>
						<el-table-column prop="name" label="用户姓名" align="center"></el-table-column>
						<el-table-column prop="interviewId" label="访谈编号" align="center"></el-table-column>
						<el-table-column prop="questionaireName" label="问卷名称" align="center">
							<template slot-scope="scope">
								{{scope.row.questionaireCode + ' ' + scope.row.questionaireTitle}}
							</template>
						</el-table-column>
						<el-table-column prop="questionCode" label="问题编号" align="center"></el-table-column>
						<el-table-column prop="operateType" label="操作类型" align="center">
							<template slot-scope="scope">
								{{$constants.OPERATE_TYPE.getOperateTypeText(scope.row.operateType)}}
							</template>
						</el-table-column>
						<el-table-column prop="beforeValue" label="编辑前值" align="center">
							<template slot-scope="scope">
								<el-button type="text" size="mini" @click="handleViewQuestion(scope)">查看</el-button>
							</template>
						</el-table-column>
						<el-table-column prop="afterValue" label="编辑后值" align="center">
							<template slot-scope="scope">
								<el-button type="text" size="mini" @click="handleViewQuestion(scope)">查看</el-button>
							</template>
						</el-table-column>
						<el-table-column prop="editTime" label="编辑时间" align="center" width="180">
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.editTime)}}
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
					EDIT_LOG_LIST : '/log/editLogList.do',
				},
				
				search : {
					editDateRange : [],
					loginName : ''
				},
				
				editLogList : [],
				
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
				
				var handledSearch = this.handleParams(this.search);
				this.$request.sendGetRequest(this.APIS.EDIT_LOG_LIST,this.$lodash.assign({},handledSearch,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					this.editLogList = resultObject.data;
					this.paginate.total = resultObject.total;
				});
			},
			
			//参数处理
			handleParams : function(search){
				var searchCopy = this.$lodash.assign({},search);
				if(searchCopy.editDateRange && searchCopy.editDateRange.length == 2){
					searchCopy.startTime = searchCopy.editDateRange[0] + ' 00:00:00';
					searchCopy.endTime = searchCopy.editDateRange[1] + ' 23:59:59';
				}else{
					searchCopy.startTime = '';
					searchCopy.endTime = '';
				}
				searchCopy = this.$lodash.omit(searchCopy,['editDateRange']);
				return searchCopy;
			},
			
			//搜索
			handleSearch : function(){
				var self = this;        
				this.paginate.currentPage = 1;
				
				var handledSearch = this.handleParams(this.search);
				this.$request.sendGetRequest(this.APIS.EDIT_LOG_LIST,this.$lodash.assign({},handledSearch,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.editLogList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
			
			//重置
			handleReset : function(){
				var self = this;
				this.search = {editDateRange : []},
				this.paginate.currentPage = 1;
				
				var handledSearch = this.handleParams(this.search);
				this.$request.sendGetRequest(this.APIS.EDIT_LOG_LIST,this.$lodash.assign({},handledSearch,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.editLogList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
			
			 //切换分页
			handleCurrentChange : function(currentPage){
				var self = this;
				this.paginate.currentPage = currentPage;
				
				var handledSearch = this.handleParams(this.search);
				this.$request.sendGetRequest(this.APIS.EDIT_LOG_LIST,this.$lodash.assign({},handledSearch,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.editLogList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
			
			//切换大小
			handleSizeChange : function(currentSize){
				var self = this;
				this.paginate.currentPage = 1;
				this.paginate.pageSize = currentSize;
				 
				var handledSearch = this.handleParams(this.search);
				this.$request.sendGetRequest(this.APIS.EDIT_LOG_LIST,this.$lodash.assign({},handledSearch,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.editLogList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
			
			//查看问题
			handleViewQuestion(scope){
				
			}
		}
	};
	
	window.log4EditLogListComponent = log4EditLogListComponent;
})();


