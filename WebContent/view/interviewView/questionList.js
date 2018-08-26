(function(){
	var interviewViewQuestionListComponent = {
                template : `
                        <div class="interview-view-question-list">
                        
                                <div class="common-title">问题列表</div>
                                
                               
                                
                        </div>
                `,
                data : function(){
			
                        return {
                                
                                APIS : {
                                        QUESTION_LIST : '/interviewView/questionList.do'
                                },
				
				params : {
					interviewId : this.$route.query.interviewId,
					questionaireCode : this.$route.query.questionaireCode
				},
                                
                                questionList : []
                        }
                },
                
                methods : {
                        
                        init : function(){
                                var self = this;
                                
				this.$request.sendGetRequest(this.APIS.QUESTION_LIST,{interviewId : this.params.interviewId , questionaireCode : this.params.questionaireCode},(resultObject)=>{
					self.questionList = resultObject;
				});
                        }
                        
                },
                
                mounted : function(){
                        this.init();
                }
	};
	window.interviewViewQuestionListComponent = interviewViewQuestionListComponent;
})();


