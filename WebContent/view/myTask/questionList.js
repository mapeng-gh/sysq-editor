(function(){
	var myTask4QuestionListComponent = {
		
        template : `
            <div class="my-task-question-list">
                        
                <div class="common-title">问题列表</div>
				
                                
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


