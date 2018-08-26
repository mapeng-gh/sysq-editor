(function(){
	var interviewViewQuestionaireListComponent = {
                template : `
                        <div class="interview-view-questionaire-list">
                        
                                <div class="common-title">问卷列表</div>
                                
                               <div class="questionaire" v-for="(item,index) in questionaireList" :key="item.id">
					<div class="questionaire-header">[{{index+1}}] {{item.code}} {{item.title}}</div>
					<div class="questionaire-content">{{item.introduction == '' ? '暂无描述内容' : item.introduction}}</div>
				</div>
                                
                        </div>
                `,
                data : function(){
			
                        return {
                                
                                APIS : {
                                        QUESTIONAIRE_LIST : '/interviewView/currentQuestionaireList.do'
                                },
				
				params : {
					interviewId : this.$route.query.interviewId,
					type : this.$route.query.type
				},
                                
                                questionaireList : []
                        }
                },
                
                methods : {
                        
                        init : function(){
                                var self = this;
                                
				this.$request.sendGetRequest(this.APIS.QUESTIONAIRE_LIST,{type : this.params.type},(resultObject)=>{
					self.questionaireList = resultObject;
				});
                        }
                        
                },
                
                mounted : function(){
                        this.init();
                }
	};
	window.interviewViewQuestionaireListComponent = interviewViewQuestionaireListComponent;
})();


