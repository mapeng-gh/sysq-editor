(function(){
	var myTask4QuestionaireListComponent = {
		
        template : `
            <div class="my-task-questionaire-list">
                        
                <div class="common-title">问卷列表</div>

                    <div class="questionaire" v-for="(item,index) in questionaireList" :key="item.questionaire.code" @click="handleQuestionList(item.questionaire.code)">
						<div class="questionaire-header">[{{index+1}}] {{item.questionaire.code}} {{item.questionaire.title}}</div>
						<div class="questionaire-content">{{item.questionaire.introduction == '' ? '暂无描述内容' : item.questionaire.introduction}}</div>
					</div>
                                
                </div>
			</div>
        `,
		
        data : function(){
			
            return {
                                
				APIS : {
					QUESTIONAIRE_LIST : '/myTask/questionaireList.do'
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
						
				this.$request.sendGetRequest(this.APIS.QUESTIONAIRE_LIST,{taskId : this.params.taskId},(resultObject)=>{
					self.questionaireList = resultObject;
				});
			},
			
			//问题列表
			handleQuestionList : function(questionaireCode){
				this.$commons.openWindow('#/myInterview/questionList',{interviewId : this.params.interviewId,questionaireCode : questionaireCode});
			}
                        
        },
                
		mounted : function(){
			this.init();
		}
		
	};
	
	window.myTask4QuestionaireListComponent = myTask4QuestionaireListComponent;
	
})();


