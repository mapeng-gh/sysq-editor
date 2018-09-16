(function(){
	var myInterview4InterviewListComponent = {
                template : `
                        <div class="interview-view-interview-list">
                        
                                <div class="common-title">访谈列表</div>
                                
                                <div class="common-search">
        
                                        <el-form label-width="80px" label-position="left">
                        
                                                <el-row :gutter="50">
                        
                                                        <el-col :span="8">
                                                        
                                                                <el-form-item label="患者姓名">
                                                                        <el-input v-model="search.name" placeholder="请输入患者姓名"></el-input>
                                                                </el-form-item>
                                                        
                                                        </el-col>
                                
                                                        <el-col :span="8">
                                
                                                                <el-form-item label="访谈类型">
                                                                        <el-select v-model="search.type" style="width:100%;">
                                                                                <el-option value="" label="全部"></el-option>
                                                                                <el-option v-for="item in $constants.INTERVIEW_TYPE.getInterviewTypeList()" :key="item.code" :label="item.text" :value="item.code"></el-option>
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
                                                :data="interviewList"
                                                border
                                                header-cell-class-name="common-table-header"
                                                style="width: 100%">
                                                <el-table-column prop="interview.id" label="编号" align="center"></el-table-column>
						<el-table-column prop="interview.type" label="访谈类型" width="80" align="center">
                                                        <template slot-scope="scope">
                                                                {{$constants.INTERVIEW_TYPE.getInterviewTypeText(scope.row.interview.type)}}
                                                        </template>
                                                </el-table-column>
                                                <el-table-column prop="patient.username" label="患者姓名" width="150" align="center"></el-table-column>
                                                <el-table-column prop="patient.mobile" label="联系电话" width="180" align="center"></el-table-column>
                                                <el-table-column prop="patient.address" label="患者地址" width="250" align="left"  :show-overflow-tooltip="true">
							<template slot-scope="scope">
                                                                        {{scope.row.patient.province + '-' + scope.row.patient.city + '-' + scope.row.patient.address}}
                                                        </template>
						</el-table-column>
                                                <el-table-column prop="interview.endTime" label="访谈日期" width="180" align="center">
							<template slot-scope="scope">
                                                                        {{$commons.formatDate(scope.row.interview.endTime)}}
                                                        </template>
						</el-table-column>
						<el-table-column prop="interview.versionId" label="问卷版本" width="80" align="center"></el-table-column>
                                                <el-table-column prop="operate" label="操作" align="center">
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
                        return {
                                
                                APIS : {
                                        INTERVIEW_LIST : '/myInterview/interviewList.do'
                                },
                                
                                interviewList : [],
                                
                                search : {
                                        name : '',
                                        type : ''
                                },
                                
                                paginate : {
                                        pageSize : 10,
                                        currentPage : 1,
                                        total : 0
                                }
                        }
                },
                
                methods : {
                        
                        init : function(){
                                var self = this;
                                
				//初始化列表
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
                                this.search = {name : '' , type : ''};
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
				this.$commons.openWindow('#/myInterview/questionaireList',{interviewId : scope.row.interview.id});
                        }
                },
                
                mounted : function(){
                        this.init();
                }
	};
	window.myInterview4InterviewListComponent = myInterview4InterviewListComponent;
})();


