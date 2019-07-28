(function(){
	var log4EditLogListComponent = {
		template : `
			<div class="log-edit-log-list">
				<div class="common-title">编辑日志</div>
				
				<!-- 搜索条件 -->
				<div class="common-search">
					<el-form label-width="80px" label-position="left">
						<el-row :gutter="50">
							<el-col :span="8">
								<el-form-item label="访谈编号">
									<el-input v-model.trim="search.interviewId" placeholder="请输入访谈编号" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="问卷编码">
									<el-input v-model.trim="search.questionaireCode" placeholder="请输入问卷编码" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="问题编码">
									<el-input v-model.trim="search.questionCode" placeholder="请输入问题编码" clearable></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="8">
								<el-form-item label="编辑账号">
									<el-input v-model.trim="search.loginName" placeholder="请输入编辑账号" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item label="编辑时间">
									<el-date-picker
											v-model="search.editDateRange"
											type="daterange"
											range-separator="至"
											start-placeholder="开始时间"
											end-placeholder="结束时间"
											format="yyyy-MM-dd HH:mm:ss"
											value-format="yyyy-MM-dd HH:mm:ss"
											:default-time="['00:00:00','23:59:59']"
											:editable="false"
											:clearable="true"
											style="width:100%;">
									</el-date-picker>
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
						:data="editLogList"
						border
						header-cell-class-name="common-table-header"
						style="width: 100%">
						<el-table-column prop="loginName" label="编辑账号" align="center"></el-table-column>
						<el-table-column prop="name" label="用户姓名" align="center"></el-table-column>
						<el-table-column prop="interviewId" label="访谈编号" align="center"></el-table-column>
						<el-table-column prop="questionaireCode" label="问卷编码" align="center"></el-table-column>
						<el-table-column prop="questionCode" label="问题编码" align="center"></el-table-column>
						<el-table-column prop="operateType" label="操作类型" align="center">
							<template slot-scope="scope">
								{{$constants.OPERATE_TYPE.getOperateTypeText(scope.row.operateType)}}
							</template>
						</el-table-column>
						<el-table-column prop="editTime" label="编辑时间" align="center" width="180">
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.editTime)}}
							</template>
						</el-table-column>
						<el-table-column prop="remark" label="备注" align="center" min-width="130" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="operate" label="操作" align="center" width="100">
							<template slot-scope="scope">
								<el-button :disabled="scope.row.operateType != $constants.OPERATE_TYPE.enums.EDIT" type="text" size="mini" @click="handleQuestionDialog(scope)">查看对比</el-button>
							</template>
						</el-table-column>
                    </el-table>
				</div>
				
				<!-- 分页数据 -->
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
				
				<!-- 分页数据 -->
				<el-dialog
					title="查看对比(左边修改前,右边修改后)"
					:visible.sync="viewQuestionDialog.visible"
					width="50%"
					:close-on-click-modal="false"
					:close-on-press-escape="false">
					<div class="question-dialog">
						<div class="question-desc" v-html="handleQuestionDesc(viewQuestionDialog.question.description)"></div>
						<el-row :gutter="50">
							<el-col :span="12">
								<div class="answer" v-for="answerResponse in viewQuestionDialog.answerList">
									<div class="answer-label">{{answerResponse.answer.label? answerResponse.answer.label : answerResponse.answer.code}}</div>
									<div class="answer-content">
										<el-input v-if="answerResponse.answer.type == 'text'" type="textarea" :rows="3" :value="viewQuestionDialog.beforeValue[answerResponse.answer.code]"></el-input>
										
										<el-radio-group v-if="answerResponse.answer.type == 'radiogroup'" :class="{vertical : answerResponse.answer.showType == 'vertical'}" :value="viewQuestionDialog.beforeValue[answerResponse.answer.code]">
											<el-radio v-for="item in JSON.parse(answerResponse.answer.extra)" :key="item.value" :label="item.value">{{item.text}}</el-radio>
										</el-radio-group>
										
										<el-checkbox-group v-if="answerResponse.answer.type == 'checkbox'" :class="{vertical : answerResponse.answer.showType == 'vertical'}" :value="viewQuestionDialog.beforeValue[answerResponse.answer.code].split(',')">
											<el-checkbox v-for="item in JSON.parse(answerResponse.answer.extra)" :key="item.value" :label="item.value">{{item.text}}</el-checkbox>
										</el-checkbox-group>
										
										<el-date-picker v-if="answerResponse.answer.type == 'calendar'"
											:value="viewQuestionDialog.beforeValue[answerResponse.answer.code]"
											type="date"
											format="yyyy-MM-dd"
											value-format="yyyy-MM-dd"
											:editable="false"
											:clearable="false">
										</el-date-picker>
										
										<el-input-number v-if="answerResponse.answer.type == 'spinbox'" 
											:value="viewQuestionDialog.beforeValue[answerResponse.answer.code]"
											:min="JSON.parse(answerResponse.answer.extra).start" 
											:step="JSON.parse(answerResponse.answer.extra).step" 
											:max="JSON.parse(answerResponse.answer.extra).end">
										</el-input-number>
										
										<el-select v-if="answerResponse.answer.type == 'dropdownlist'"
											:value="viewQuestionDialog.beforeValue[answerResponse.answer.code]">
											<el-option
												v-for="item in JSON.parse(answerResponse.answer.extra)"
												:key="item.value"
												:label="item.text"
												:value="item.value">
											</el-option>
										</el-select>
									</div>
								</div>
							</el-col>
							<el-col :span="12">
								<div class="answer" v-for="answerResponse in viewQuestionDialog.answerList">
									<div class="answer-label">{{answerResponse.answer.label? answerResponse.answer.label : answerResponse.answer.code}}</div>
									<div class="answer-content">
										<el-input v-if="answerResponse.answer.type == 'text'" type="textarea" :rows="3" :value="viewQuestionDialog.afterValue[answerResponse.answer.code]"></el-input>
										
										<el-radio-group v-if="answerResponse.answer.type == 'radiogroup'" :class="{vertical : answerResponse.answer.showType == 'vertical'}" :value="viewQuestionDialog.afterValue[answerResponse.answer.code]">
											<el-radio v-for="item in JSON.parse(answerResponse.answer.extra)" :key="item.value" :label="item.value">{{item.text}}</el-radio>
										</el-radio-group>
										
										<el-checkbox-group v-if="answerResponse.answer.type == 'checkbox'" :class="{vertical : answerResponse.answer.showType == 'vertical'}" :value="viewQuestionDialog.afterValue[answerResponse.answer.code].split(',')">
											<el-checkbox v-for="item in JSON.parse(answerResponse.answer.extra)" :key="item.value" :label="item.value">{{item.text}}</el-checkbox>
										</el-checkbox-group>
										
										<el-date-picker v-if="answerResponse.answer.type == 'calendar'"
											:value="viewQuestionDialog.afterValue[answerResponse.answer.code]"
											type="date"
											format="yyyy-MM-dd"
											value-format="yyyy-MM-dd"
											:editable="false"
											:clearable="false">
										</el-date-picker>
										
										<el-input-number v-if="answerResponse.answer.type == 'spinbox'" 
											:value="viewQuestionDialog.afterValue[answerResponse.answer.code]"
											:min="JSON.parse(answerResponse.answer.extra).start" 
											:step="JSON.parse(answerResponse.answer.extra).step" 
											:max="JSON.parse(answerResponse.answer.extra).end">
										</el-input-number>
										
										<el-select v-if="answerResponse.answer.type == 'dropdownlist'"
											:value="viewQuestionDialog.afterValue[answerResponse.answer.code]">
											<el-option
												v-for="item in JSON.parse(answerResponse.answer.extra)"
												:key="item.value"
												:label="item.text"
												:value="item.value">
											</el-option>
										</el-select>
									</div>
								</div>
							</el-col>
						</el-row>
					</div>
					<span slot="footer" class="dialog-footer">
						<el-button @click="viewQuestionDialog.visible = false">取消</el-button>
					</span>
				</el-dialog>
				
			</div>
		`,
		
		data : function(){
			var self = this;
			
			return {
				APIS : {
					EDIT_LOG_LIST : '/log/editLogList.do',
					VIEW_QUESTION : '/dataManage/viewQuestion.do'
				},
				
				search : {
					interviewId : '',
					questionaireCode : '',
					questionCode : '',
					loginName : '',
					editDateRange : [],
				},
				
				editLogList : [],
				
				paginate : {
					pageSize : 10,
					currentPage : 1,
					total : 0
				},
				
				viewQuestionDialog : {
					visible : false,
					question : {},
					answerList : [],
					beforeValue : {},
					afterValue : {},
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
					searchCopy.startTime = searchCopy.editDateRange[0];
					searchCopy.endTime = searchCopy.editDateRange[1];
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
			
			
			//查看问题：对话框
			handleQuestionDialog(scope){
				this.$request.sendGetRequest(this.APIS.VIEW_QUESTION,{'versionId' : scope.row.versionId , 'questionCode' : scope.row.questionCode},(resultObject)=>{
					this.viewQuestionDialog.question = resultObject.question;
					this.viewQuestionDialog.answerList = resultObject.answerList;
					
					this.viewQuestionDialog.beforeValue = JSON.parse(scope.row.beforeValue);
					this.viewQuestionDialog.afterValue = JSON.parse(scope.row.afterValue);
					
					this.viewQuestionDialog.visible = true;
				});
			},
			
			//问题描述处理
			handleQuestionDesc(desc){
				return desc && desc.replace(/<para>/g,'<br/>');
			}
		}
	};
	
	window.log4EditLogListComponent = log4EditLogListComponent;
})();


