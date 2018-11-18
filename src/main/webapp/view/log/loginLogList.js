(function(){
	var log4LoginLogComponent = {
		
		template : `
			<div class="log-login-log-list">
			
				<div class="common-title">登录日志</div>
				
				<div class="common-search">
					<el-form label-width="80px" label-position="left">
						<el-row :gutter="50">
							<el-col :span="8">
								<el-form-item label="登录日期">
									<el-date-picker
											v-model="search.loginDateRange"
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
								<el-form-item label="登录账号">
									<el-input v-model="search.loginName" placeholder="请输入用户登录账号" clearable></el-input>
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
						:data="loginLogList"
						border
						header-cell-class-name="common-table-header"
						style="width: 100%">
						<el-table-column prop="loginName" label="登录账号" align="center"></el-table-column>
						<el-table-column prop="userType" label="用户类型" align="center">
							<template slot-scope="scope">
								{{$constants.USER_TYPE.getUserTypeText(scope.row.userType)}}
							</template>
						</el-table-column>
						<el-table-column prop="name" label="用户姓名" align="center"></el-table-column>
						<el-table-column prop="workingPlace" label="工作单位" align="center" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="loginTime" label="登录时间" align="center">
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.loginTime)}}
							</template>
						</el-table-column>
						<el-table-column prop="loginIp" label="登录地点" align="center"></el-table-column>
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
					LOGIN_LOG_LIST : '/log/loginLogList.do',
				},
				
				search : {
					loginDateRange : [],
					loginName : ''
				},
				
				loginLogList : [],
				
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
				this.$request.sendGetRequest(this.APIS.LOGIN_LOG_LIST,this.$lodash.assign({},handledSearch,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					this.loginLogList = resultObject.data;
					this.paginate.total = resultObject.total;
				});
			},
			
			//参数处理
			handleParams : function(search){
				var searchCopy = this.$lodash.assign({},search);
				if(searchCopy.loginDateRange && searchCopy.loginDateRange.length == 2){
					searchCopy.startTime = searchCopy.loginDateRange[0] + ' 00:00:00';
					searchCopy.endTime = searchCopy.loginDateRange[1] + ' 23:59:59';
				}else{
					searchCopy.startTime = '';
					searchCopy.endTime = '';
				}
				searchCopy = this.$lodash.omit(searchCopy,['loginDateRange']);
				return searchCopy;
			},
			
			//搜索
			handleSearch : function(){
				var self = this;        
				this.paginate.currentPage = 1;
				
				var handledSearch = this.handleParams(this.search);
				this.$request.sendGetRequest(this.APIS.LOGIN_LOG_LIST,this.$lodash.assign({},handledSearch,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.loginLogList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
			
			//重置
			handleReset : function(){
				var self = this;
				this.search = {loginDateRange : ''},
				this.paginate.currentPage = 1;
				
				var handledSearch = this.handleParams(this.search);
				this.$request.sendGetRequest(this.APIS.LOGIN_LOG_LIST,this.$lodash.assign({},handledSearch,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.loginLogList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
			
			 //切换分页
			handleCurrentChange : function(currentPage){
				var self = this;
				this.paginate.currentPage = currentPage;
				
				var handledSearch = this.handleParams(this.search);
				this.$request.sendGetRequest(this.APIS.LOGIN_LOG_LIST,this.$lodash.assign({},handledSearch,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.loginLogList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
			
			//切换大小
			handleSizeChange : function(currentSize){
				var self = this;
				this.paginate.currentPage = 1;
				this.paginate.pageSize = currentSize;
				 
				var handledSearch = this.handleParams(this.search);
				this.$request.sendGetRequest(this.APIS.LOGIN_LOG_LIST,this.$lodash.assign({},handledSearch,{currentPage : this.paginate.currentPage,pageSize:this.paginate.pageSize}),(resultObject)=>{
					self.loginLogList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			}
		}
	};
	
	window.log4LoginLogComponent = log4LoginLogComponent;
})();


