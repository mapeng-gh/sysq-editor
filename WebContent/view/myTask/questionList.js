(function(){
	var myTask4QuestionListComponent = {
		
        template : `
            <div class="my-task-question-list">
                        
                <div class="common-title">问题列表</div>
				
				<div :class="{'question' : true , 'invalid' : questionResponse.editorQuestion.status == 0}" v-for="questionResponse in questionList" :key="questionResponse.question.code" @mouseover="handleMouseover(questionResponse,$event)" @mouseout="handleMouseout(questionResponse,$event)">
					<div class="question-operate hidden">
						<el-button type="primary" plain size="small" :class="{'hidden' : questionResponse.editorQuestion.status == 0}" @click="handleEditQuestionDialog(questionResponse)"">编辑</el-button>
						<el-button type="primary" plain size="small" :class="{'hidden' : questionResponse.editorQuestion.status == 1}" @click="handleEnableQuestion(questionResponse)">启用</el-button>
						<el-button type="primary" plain size="small" :class="{'hidden' : questionResponse.editorQuestion.status == 0}" @click="handleDisableQuestion(questionResponse)">禁用</el-button>
					</div>
					<div class="question-desc" v-html="handleQuestionDesc(questionResponse.question.description)"></div>
					<div class="answer" v-for="answerResponse in questionResponse.answerResponseList" v-if="answerResponse.result" :key="answerResponse.answer.code">
						<span class="label">{{answerResponse.answer.label? answerResponse.answer.label : answerResponse.answer.code}}</span>
						<span class="value">{{transferAnswerValue(answerResponse.result,answerResponse.answer)}}</span>
					</div>
				</div>
				
				<el-dialog
					title="编辑问题"
					:visible.sync="editQuestionDialog.visible"
					width="50%"
					:close-on-click-modal="false"
					:close-on-press-escape="false">
					<div class="edit-question">
						<div class="question-desc" v-html="handleQuestionDesc(editQuestionDialog.questionResponse.question.description)"></div>
						<div class="answer" v-for="answerResponse in editQuestionDialog.questionResponse.answerResponseList">
							<div class="answer-label">{{answerResponse.answer.label? answerResponse.answer.label : answerResponse.answer.code}}</div>
							<div class="answer-content">
								<el-input v-if="answerResponse.answer.type == 'text'" type="textarea" :rows="3" v-model="answerResponse.result.answerValue"></el-input>
								
								<el-radio-group v-if="answerResponse.answer.type == 'radiogroup'" :class="{vertical : answerResponse.answer.showType == 'vertical'}" v-model="answerResponse.result.answerValue">
									<el-radio v-for="item in JSON.parse(answerResponse.answer.extra)" :key="item.value" :label="item.value">{{item.text}}</el-radio>
								</el-radio-group>
								
								<el-checkbox-group v-if="answerResponse.answer.type == 'checkbox'" :class="{vertical : answerResponse.answer.showType == 'vertical'}" v-model="answerResponse.result.answerValue">
									<el-checkbox v-for="item in JSON.parse(answerResponse.answer.extra)" :key="item.value" :label="item.value">{{item.text}}</el-checkbox>
								</el-checkbox-group>
								
								<el-date-picker v-if="answerResponse.answer.type == 'calendar'"
									v-model="answerResponse.result.answerValue"
									type="date"
									format="yyyy-MM-dd"
									value-format="yyyy-MM-dd"
									:editable="false"
									:clearable="false">
								</el-date-picker>
								
								<el-input-number v-if="answerResponse.answer.type == 'spinbox'" 
									v-model="answerResponse.result.answerValue"
									:min="JSON.parse(answerResponse.answer.extra).start" 
									:step="JSON.parse(answerResponse.answer.extra).step" 
									:max="JSON.parse(answerResponse.answer.extra).end">
								</el-input-number>
								
								<el-select v-if="answerResponse.answer.type == 'dropdownlist'"
									v-model="answerResponse.result.answerValue">
									<el-option
										v-for="item in JSON.parse(answerResponse.answer.extra)"
										:key="item.value"
										:label="item.text"
										:value="item.value">
									</el-option>
								</el-select>
							</div>
						</div>
					</div>
					<span slot="footer" class="dialog-footer">
						<el-button @click="editQuestionDialog.visible = false">取消</el-button>
						<el-button type="primary" @click="handleEditQuestion">确定</el-button>
					</span>
				</el-dialog>
                                
			</div>
        `,
		
        data : function(){
			
            return {
                                
				APIS : {
					QUESTION_LIST : '/myTask/questionList.do',
					ENABLE_QUESTION : '/myTask/enableQuestion.do',
					DISABLE_QUESTION : '/myTask/disableQuestion.do',
					EDIT_QUESTION : '/myTask/editQuestion.do'
				},
				
				params : {
					taskId : this.$route.query.taskId,
					questionaireCode : this.$route.query.questionaireCode
				},
                                
                questionList : [],
				
				editQuestionDialog : {
					visible : false,
					questionResponse : {
						question : {},
						editorQuestion : {},
						answerResponseList : []
					}
				}
            }
        },
                
		methods : {
				
			init : function(){
				var self = this;
						
				this.$request.sendGetRequest(this.APIS.QUESTION_LIST,{taskId : this.params.taskId , questionaireCode : this.params.questionaireCode},function(resultObject){
					self.questionList = resultObject;
				});
			},
			
			//问题描述处理
			handleQuestionDesc(desc){
				return desc && desc.replace(/<para>/g,'<br/>');
			},
			
			//翻译答案值
			transferAnswerValue(result,answer){
				var type = answer.type;
				var extra = answer.extra;
				var value = result.answerValue;
				
				if(['calendar','spinbox','slider','text'].indexOf(type) > -1){
					return value;
				}else if(['dropdownlist','radiogroup'].indexOf(type) > -1){
					var extraJson = JSON.parse(extra);
					for(var i = 0 ; i < extraJson.length ; i++){
						if(extraJson[i].value == value){
							return extraJson[i].text;
						}
					}
				}else if(type == 'checkbox'){
					var texts = [];
					var extraJson = JSON.parse(extra);
					var values = value.split(',');
					for(var i = 0 ; i < extraJson.length ; i++){
						if(values.indexOf(extraJson[i].value) > -1){
							texts.push(extraJson[i].text);
						}
					}
					return texts.join(',');
				}
				return value;
			},
			
			//显示操作
			handleMouseover : function(questionResponse,$event){
				var operateDom = $event.currentTarget.firstChild;
				operateDom.classList.remove('hidden');
			},
			
			//隐藏操作
			handleMouseout : function(questionResponse,$event){
				var operateDom = $event.currentTarget.firstChild;
				operateDom.classList.add('hidden');
			},
			
			//启用问题
			handleEnableQuestion : function(questionResponse){
				var self = this;
				this.$commons.confirm('确定启用该问题吗？','确认',function(){
					self.$request.sendPostRequest(self.APIS.ENABLE_QUESTION,{taskId : self.params.taskId , questionaireCode : self.params.questionaireCode , questionCode : questionResponse.question.code},function(resultObject){
						self.$message.success('启用成功');
						
						//刷新问题
						self.$request.sendGetRequest(self.APIS.QUESTION_LIST,{taskId : self.params.taskId , questionaireCode : self.params.questionaireCode},function(resultObject){
							self.questionList = resultObject;
						});
					});
				});
			},
			
			//禁用问题
			handleDisableQuestion : function(questionResponse){
				var self = this;
				this.$commons.confirm('确定禁用该问题吗？','确认',function(){
					self.$request.sendPostRequest(self.APIS.DISABLE_QUESTION,{taskId : self.params.taskId , questionaireCode : self.params.questionaireCode , questionCode : questionResponse.question.code},function(resultObject){
						self.$message.success('禁用成功');
						
						//刷新问题
						self.$request.sendGetRequest(self.APIS.QUESTION_LIST,{taskId : self.params.taskId , questionaireCode : self.params.questionaireCode},function(resultObject){
							self.questionList = resultObject;
						});
					});
				});
			},
			
			//编辑问题对话框
			handleEditQuestionDialog : function(questionResponse){
				
				//数据复制
				var questionResponseCopy = JSON.parse(JSON.stringify(questionResponse));
				
				//空值、复选框处理
				var answerResponseList = questionResponseCopy.answerResponseList;
				for(var i = 0 ; i < answerResponseList.length ; i++){
					if(!answerResponseList[i].result){
						if(answerResponseList[i].answer.type == 'checkbox'){
							answerResponseList[i].result = {answerValue : []};
						}else{
							answerResponseList[i].result = {answerValue : ''};
						}
					}else{
						if(answerResponseList[i].answer.type == 'checkbox'){
							answerResponseList[i].result.answerValue = answerResponseList[i].result.answerValue.split(',');
						}
					}
				}
				
				this.editQuestionDialog.questionResponse = questionResponseCopy;
				this.editQuestionDialog.visible = true;
			},
			
			//编辑问题
			handleEditQuestion(){
				var self = this;
				
				//数据校验
				var answerResponseList = this.editQuestionDialog.questionResponse.answerResponseList;
				for(var i = 0 ; i < answerResponseList.length ; i++){
					if(answerResponseList[i].result.answerValue == '' || answerResponseList[i].result.answerValue.length == 0){
						this.$message.error('请编辑完问题再提交');
						return;
					}
				}
				
				//数据封装
				var params = {
					taskId : this.params.taskId,
					questionaireCode : this.params.questionaireCode,
					questionCode : this.editQuestionDialog.questionResponse.question.code,
					results : []
				};
				for(var i = 0 ; i < answerResponseList.length ; i++){
					var result = {};
					result.answerCode = answerResponseList[i].answer.code;
					if(answerResponseList[i].answer.type == 'checkbox'){
						result.answerValue = answerResponseList[i].result.answerValue.join(',');
					}else{
						result.answerValue = answerResponseList[i].result.answerValue;
					}
					params.results.push(result);
				}
				
				this.$request.sendPostRequest(this.APIS.EDIT_QUESTION,params,function(resultObject){
					self.$message.success('编辑成功');
					self.editQuestionDialog.visible = false;
					
					//刷新列表
					self.$request.sendGetRequest(self.APIS.QUESTION_LIST,{taskId : self.params.taskId , questionaireCode : self.params.questionaireCode},function(resultObject){
						self.questionList = resultObject;
					});
				});
			}
			
        },
                
		mounted : function(){
			this.init();
		}
		
	};
	
	window.myTask4QuestionListComponent = myTask4QuestionListComponent;
})();


