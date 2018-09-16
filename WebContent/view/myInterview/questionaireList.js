(function(){
	var myInterview4QuestionaireListComponent = {
		
        template : `
            <div class="interview-view-questionaire-list">
                        
                <div class="common-title">问卷列表</div>

                    <div class="questionaire" v-for="(item,index) in questionaireList" :key="item.id" @click="handleQuestionList(item.code)">
						<div class="questionaire-header">[{{index+1}}] {{item.code}} {{item.title}}</div>
						<div class="questionaire-content">{{item.introduction == '' ? '暂无描述内容' : item.introduction}}</div>
					</div>
                                
                </div>
			</div>
        `,
		
        data : function(){
			
            return {
                                
				APIS : {
						QUESTIONAIRE_LIST : '/myInterview/questionaireList.do'
				},
				
				params : {
					interviewId : this.$route.query.interviewId
				},
                                
                questionaireList : []
            }
        },
                
		methods : {
				
			init : function(){
				var self = this;
						
				this.$request.sendGetRequest(this.APIS.QUESTIONAIRE_LIST,{interviewId : this.params.interviewId},(resultObject)=>{
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
	
	window.myInterview4QuestionaireListComponent = myInterview4QuestionaireListComponent;
	
})();


