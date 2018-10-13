(function(){
	var myTask4QuestionaireListComponent = {
		
        template : `
            <div class="my-task-questionaire-list">
                        
                <div class="common-title">问卷列表</div>
				
				<div :class="{'questionaire' : true , 'invalid' : item.editorQuestionaire.status == 0}" v-for="(item,index) in questionaireList" :key="item.questionaire.code" @mouseover="handleMouseover(item,$event)" @mouseout="handleMouseout(item,$event)">
					<div class="questionaire-operate hidden">
						<el-button type="primary" plain size="small" :class="{'hidden' : item.editorQuestionaire.status == 0}" @click="handleQuestionList(item)"">问题列表</el-button>
						<el-button type="primary" plain size="small" :class="{'hidden' : item.editorQuestionaire.status == 1}" @click="enableQuestionaire(item)">启用</el-button>
						<el-tooltip class="item" effect="dark" content="禁用问卷意味着访谈不包含该问卷" placement="top-start">
							<el-button type="primary" plain size="small" :class="{'hidden' : item.editorQuestionaire.status == 0}" @click="disableQuestionaire(item)">禁用<i class="el-icon-question"></i></el-button>
						</el-tooltip>
					</div>
					<div class="questionaire-header">[{{index+1}}] {{item.questionaire.code}} {{item.questionaire.title}}</div>
					<div class="questionaire-content">{{item.questionaire.introduction == '' ? '暂无描述内容' : item.questionaire.introduction}}</div>
				</div>
                                
                </div>
			</div>
        `,
		
        data : function(){
			
            return {
                                
				APIS : {
					QUESTIONAIRE_LIST : '/myTask/questionaireList.do',
					ENABLE_QUESTIONAIRE : '/myTask/enableQuestionaire.do',
					DISABLE_QUESTIONAIRE : '/myTask/disableQuestionaire.do'
				},
				
				params : {
					taskId : this.$route.query.taskId
				},
                                
                questionaireList : []
            }
        },
                
		methods : {
				
			init : function(){
				var self = this;
						
				this.$request.sendGetRequest(this.APIS.QUESTIONAIRE_LIST,{taskId : this.params.taskId},function(resultObject){
					self.questionaireList = resultObject;
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
				this.$message.info('question');
				
				// this.$commons.openWindow('#/myInterview/questionList',{interviewId : this.params.interviewId,questionaireCode : questionaireCode});
			}
			
        },
                
		mounted : function(){
			this.init();
		}
		
	};
	
	window.myTask4QuestionaireListComponent = myTask4QuestionaireListComponent;
})();


