(function(){
	var dataManage4QuestionaireListComponent = {
		
        template : `
            <div class="data-manage-questionaire-list">
                        
                <div class="common-title">问卷列表</div>
				
				<div class="common-list-operate">
					<el-button plain type="info" size="medium" @click="handleBack">返回</el-button>
				</div>
				
				<div class="questionaire" v-for="(item,index) in questionaireList" :key="item.id" @click="handleQuestionList(item.code)">
					<div class="questionaire-header">[{{index+1}}] {{item.code}} {{item.title}}</div>
					<div class="questionaire-content">{{item.introduction == '' ? '暂无描述内容' : item.introduction}}</div>
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
		
		mounted : function(){
			this.init();
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
				this.$router.push({name : 'dataManage4QuestionList' , query : {versionId : this.params.versionId , type : this.params.type , questionaireCode : questionaireCode}});
			},
			
			//返回
			handleBack(){
				this.$router.push({name : 'dataManage4VersionList'});
			}
        }
	};
	
	window.dataManage4QuestionaireListComponent = dataManage4QuestionaireListComponent;
})();


