(function(){
	var interview4QuestionListComponent = {
		
		template : `
			<div class="interview-question-list">
			
				<div class="common-title">问题列表</div>
					
				<div class="question" v-for="questionWrap in questionList" :key="questionWrap.question.id">
				
					<div class="question-desc" v-html="questionWrap.question.description == '' ? '暂无描述内容' : handleQuestionDesc(questionWrap.question.description)"></div>
					
					<div class="answer" v-for="answerWrap in questionWrap.answerList" :key="answerWrap.answer.id">
						<span class="label">{{answerWrap.answer.label? answerWrap.answer.label : answerWrap.answer.code}}</span>
						<span class="value">{{transferAnswerValue(answerWrap.result,answerWrap.answer)}}</span>
					</div>
				
				</div>
						
			</div>
		`,
		
		data : function(){
	
			return {
						
				APIS : {
						QUESTION_LIST : '/interview/questionList.do'
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
			}
				
		},
                
		mounted : function(){
			this.init();
		}
	};
	
	window.interview4QuestionListComponent = interview4QuestionListComponent;
	
})();


