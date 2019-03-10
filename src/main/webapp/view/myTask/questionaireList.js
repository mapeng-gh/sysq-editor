(function(){
	var myTask4QuestionaireListComponent = {
		
        template : `
            <div class="my-task-questionaire-list">
                        
                <div class="common-title">问卷列表</div>
				
				<div class="common-list-operate">
					<el-button plain type="info" size="medium" @click="handleBack">返回</el-button>
				</div>
				
				<div class="questionaire" v-for="(editorQuestionaireResponse,index) in questionaireList" :key="editorQuestionaireResponse.questionaire.code" @click="handleQuestionList(editorQuestionaireResponse)">
					<div class="questionaire-header">[{{index+1}}] {{editorQuestionaireResponse.questionaire.code}} {{editorQuestionaireResponse.questionaire.title}}</div>
					<div class="questionaire-content">{{editorQuestionaireResponse.questionaire.introduction == '' ? '暂无描述内容' : editorQuestionaireResponse.questionaire.introduction}}</div>
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
		
		mounted : function(){
			this.init();
		},
                
		methods : {
			init : function(){
				var self = this;
						
				this.$request.sendGetRequest(this.APIS.QUESTIONAIRE_LIST,{taskId : this.params.taskId},function(resultObject){
					self.questionaireList = resultObject;
				});
			},
			
			//问题列表
			handleQuestionList : function(editorQuestionaireResponse){
				this.$commons.openWindow('#/myTask/questionList',{taskId : this.params.taskId , questionaireCode : editorQuestionaireResponse.questionaire.code});
			},
			
			//返回
			handleBack(){
				this.$router.push({name : 'myTask4TaskList'});
			}
        }
	};
	
	window.myTask4QuestionaireListComponent = myTask4QuestionaireListComponent;
})();


