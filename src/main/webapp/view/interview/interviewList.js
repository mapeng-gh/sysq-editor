(function(){
	var interview4InterviewListComponent = {
		template : `
			<div class="interview-interview-list">
				<div class="common-title">访谈列表</div>
					
				<!-- 列表搜索 -->
				<div class="common-search">
					<el-form label-width="80px" label-position="left">
						<el-row :gutter="50">
							<el-col :span="8">
								<el-form-item label="访谈编号 : ">
									<el-input v-model="search.interviewId" placeholder="请输入访谈编号" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="访谈类型 : ">
									<el-select v-model="search.interviewType" style="width:100%;">
										<el-option value="" label="全部"></el-option>
										<el-option v-for="item in $constants.INTERVIEW_TYPE.getInterviewTypeList()" :key="item.code" :label="item.text" :value="item.code"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="访谈员 : ">
									<el-input v-model="search.doctorName" placeholder="请输入访谈员姓名" clearable></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="8">
								<el-form-item label="受访者 : ">
									<el-input v-model="search.patientName" placeholder="请输入受访者姓名" clearable></el-input>
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
						:data="interviewList"
						border
						header-cell-class-name="common-table-header"
						style="width: 100%">
						<el-table-column prop="interview.id" label="访谈编号" align="center" show-overflow-tooltip></el-table-column>
						<el-table-column prop="interview.type" label="访谈类型" align="center" show-overflow-tooltip>
							<template slot-scope="scope">
								{{$constants.INTERVIEW_TYPE.getInterviewTypeText(scope.row.interview.type)}}
							</template>
						</el-table-column>
						<el-table-column prop="doctor.username" label="访谈员" align="center" show-overflow-tooltip></el-table-column>
						<el-table-column prop="patient.username" label="受访者" align="center" show-overflow-tooltip></el-table-column>
						<el-table-column prop="interview.startTime" label="访谈日期" align="center" show-overflow-tooltip>
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.interview.endTime)}}
							</template>
						</el-table-column>
						<el-table-column prop="interview.versionId" label="问卷版本" align="center" show-overflow-tooltip></el-table-column>
						<el-table-column prop="operate" label="操作" align="center" width="180">
							<template slot-scope="scope">
								<el-button type="text" size="mini" @click="handleQuestionaireList(scope)">问卷列表</el-button>
								<el-button type="text" size="mini" @click="handleDownloadAudio(scope)">下载录音</el-button>
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
			
			</div>
		`,
		
		data : function(){
			return {
					
				APIS : {
					INTERVIEW_LIST : '/interview/interviewList.do',
					DOWNLOAD_AUDIO : '/common/downloadAudio.do'
				},
				
				interviewList : [],
				
				search : {
					interviewId : '',
					interviewType : '',
					doctorName : '',
					patientName : ''
				},
				
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
			//初始化列表
			init : function(){
				var self = this;
				
				this.$request.sendGetRequest(this.APIS.INTERVIEW_LIST,this.$lodash.assign({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
					self.interviewList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
				
			//搜索
			handleSearch(){
				var self = this;
				this.paginate.currentPage = 1;
						
				this.$request.sendGetRequest(this.APIS.INTERVIEW_LIST,this.$lodash.assign({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
					self.interviewList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
				
			//重置
			handleReset(){
				var self = this;
				this.search = {interviewId : '' , interviewType : '' , doctorName : '' , patientName : ''};
				this.paginate.currentPage = 1;
						
				this.$request.sendGetRequest(this.APIS.INTERVIEW_LIST,this.$lodash.assign({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
					self.interviewList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
				
			//切换分页
			handleCurrentChange(currentPage){
				var self = this;
				this.paginate.currentPage = currentPage;
		
				this.$request.sendGetRequest(this.APIS.INTERVIEW_LIST,this.$lodash.assign({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
					self.interviewList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
				
			//切换大小
			handleSizeChange(currentSize){
				var self = this;
				this.paginate.currentPage = 1;
				this.paginate.pageSize = currentSize;
		
			   this.$request.sendGetRequest(this.APIS.INTERVIEW_LIST,this.$lodash.assign({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),function(resultObject){
					self.interviewList = resultObject.data;
					self.paginate.total = resultObject.total;
				});
			},
				
			//问卷列表
			handleQuestionaireList(scope){
				this.$router.push({name : 'interview4QuestionaireList' , query : {interviewId : scope.row.interview.id}});
			},
			
			//下载录音
			handleDownloadAudio(scope){
				this.$commons.download(this.APIS.DOWNLOAD_AUDIO,{'interviewId' : scope.row.interview.id});
			}
		}
	};
	
	window.interview4InterviewListComponent = interview4InterviewListComponent;
})();


