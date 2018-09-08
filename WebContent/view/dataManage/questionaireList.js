(function(){
	var dataManageQuestionaireListComponent = {
		
        template : `
            <div class="data-manage-questionaire-list">
                        
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
					QUESTIONAIRE_LIST : '/dataManage/questionaireList.do'
				},
				
				params : {
					versionId : this.$route.query.versionId,
					type : this.$route.query.type
				},
                                
                questionaireList : []
            }
        },
                
		methods : {
				
			init : function(){
				var self = this;
						
				this.$request.sendGetRequest(this.APIS.QUESTIONAIRE_LIST,{versionId : this.params.versionId , type : this.params.type},(resultObject)=>{
					self.questionaireList = resultObject;
				});
			},
			
			//问题列表
			handleQuestionList : function(questionaireCode){
				//this.$commons.openWindow('#/interviewView/questionList',{interviewId : this.params.interviewId,questionaireCode : questionaireCode});
			}
                        
        },
                
		mounted : function(){
			this.init();
		}
		
	};
	
	window.dataManageQuestionaireListComponent = dataManageQuestionaireListComponent;
	
})();


