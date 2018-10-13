(function(){
	var myTask4QuestionListComponent = {
		
        template : `
            <div class="my-task-question-list">
                        
                <div class="common-title">问题列表</div>
				
				<div :class="{'question' : true , 'invalid' : questionResponse.editorQuestion.status == 0}" v-for="questionResponse in questionList" :key="questionResponse.question.code">
					<div class="question-desc" v-html="questionResponse.question.description == '' ? '暂无描述内容' : handleQuestionDesc(questionResponse.question.description)"></div>
					<div class="answer" v-for="answerResponse in questionResponse.answerResponseList" v-if="answerResponse.result" :key="answerResponse.answer.code">
						<span class="label">{{answerResponse.answer.label? answerResponse.answer.label : answerResponse.answer.code}}</span>
						<span class="value">{{transferAnswerValue(answerResponse.result,answerResponse.answer)}}</span>
					</div>
				</div>
                                
			</div>
        `,
		
        data : function(){
			
            return {
                                
				APIS : {
					QUESTION_LIST : '/myTask/questionList.do'
				},
				
				params : {
					taskId : this.$route.query.taskId,
					questionaireCode : this.$route.query.questionaireCode
				},
                                
                questionList : []
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
				return desc.replace(/<para>/g,'<br/>')
			},
			
			//翻译答案值
			transferAnswerValue(result,answer){
				var type = answer.type;
				var extra = answer.extra;
				var value = result.answerValue;
				
				if(['calendar','spinbox','slider','text'].indexOf(type) > -1){
					return value;
				}else if(['checkbox','dropdownlist','radiogroup'].indexOf(type) > -1){
					var extraJson = JSON.parse(extra);
					for(var i = 0 ; i < extraJson.length ; i++){
						if(extraJson[i].value == value){
							return extraJson[i].text;
						}
					}
				}
				
				return value;
			},
			
			//显示操作
			handleMouseover : function(item,$event){
				var questionDom = $event.currentTarget.firstChild;
				questionDom.classList.remove('hidden');
			},
			
			//隐藏操作
			handleMouseout : function(item,$event){
				var questionDom = $event.currentTarget.firstChild;
				questionDom.classList.add('hidden');
			},
			
			//启用问卷
			enableQuestionaire : function(item){
				var self = this;
				this.$commons.confirm('确定启用该问卷吗？','确认',function(){
					self.$request.sendPostRequest(self.APIS.ENABLE_QUESTIONAIRE,{taskId : self.params.taskId , questionaireCode : item.questionaire.code},function(resultObject){
						self.$message.success('启用成功');
						
						//刷新问卷
						self.$request.sendGetRequest(self.APIS.QUESTIONAIRE_LIST,{taskId : self.params.taskId},function(resultObject){
							self.questionaireList = resultObject;
						});
					});
				});
			},
			
			//禁用问卷
			disableQuestionaire : function(item){
				var self = this;
				this.$commons.confirm('确定禁用该问卷吗？','确认',function(){
					self.$request.sendPostRequest(self.APIS.DISABLE_QUESTIONAIRE,{taskId : self.params.taskId , questionaireCode : item.questionaire.code},function(resultObject){
						self.$message.success('禁用成功');
						
						//刷新问卷
						self.$request.sendGetRequest(self.APIS.QUESTIONAIRE_LIST,{taskId : self.params.taskId},function(resultObject){
							self.questionaireList = resultObject;
						});
					});
				});
			},
			
			//问题列表
			handleQuestionList : function(item,$event){
				this.$commons.openWindow('#/myTask/questionList',{taskId : this.params.taskId , questionaireCode : item.questionaire.questionaireCode});
			}
			
        },
                
		mounted : function(){
			this.init();
		}
		
	};
	
	window.myTask4QuestionListComponent = myTask4QuestionListComponent;
})();


