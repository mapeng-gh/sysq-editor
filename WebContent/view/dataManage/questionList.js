(function(){
	var dataManageQuestionListComponent = {
		
		template : `
			<div class="data-manage-question-list">
			
				<div class="common-title">问题列表</div>
					
				<div class="question" v-for="questionWrap in questionList" :key="questionWrap.question.id">
				
					<div class="question-desc" v-html="questionWrap.question.description == '' ? '暂无描述内容' : handleQuestionDesc(questionWrap.question.description)"></div>
					
					<div class="answer" v-for="answerWrap in questionWrap.answerList" :key="answerWrap.answer.id">
						<div class="answer-label">{{answerWrap.answer.label? answerWrap.answer.label : answerWrap.answer.code}}</div>
						<div class="answer-content">
							
							<el-input v-if="answerWrap.answer.type == 'text'" type="textarea" :rows="3"></el-input>
							
							<el-radio-group v-if="answerWrap.answer.type == 'radiogroup'" :class="{vertical : answerWrap.answer.showType == 'vertical'}" :value="JSON.parse(answerWrap.answer.extra)[0].value">
								<el-radio v-for="item in JSON.parse(answerWrap.answer.extra)" :key="item.value" :label="item.value">{{item.text}}</el-radio>
							</el-radio-group>
							
							<el-checkbox-group v-if="answerWrap.answer.type == 'checkbox'" :class="{vertical : answerWrap.answer.showType == 'vertical'}" :value="[JSON.parse(answerWrap.answer.extra)[0].value]">
								<el-checkbox v-for="item in JSON.parse(answerWrap.answer.extra)" :key="item.value" :label="item.value">{{item.text}}</el-checkbox>
							</el-checkbox-group>
							
							<el-date-picker v-if="answerWrap.answer.type == 'calendar'"
								value="1988-07-03"
								type="date"
								format="yyyy-MM-dd"
								value-format="yyyy-MM-dd"
								:editable="false"
								:clearable="false">
							</el-date-picker>
							
							<el-input-number v-if="answerWrap.answer.type == 'spinbox'" 
								:value="JSON.parse(answerWrap.answer.extra).start" 
								:min="JSON.parse(answerWrap.answer.extra).start" 
								:step="JSON.parse(answerWrap.answer.extra).step" 
								:max="JSON.parse(answerWrap.answer.extra).end">
							</el-input-number>
							
							<el-select v-if="answerWrap.answer.type == 'dropdownlist'">
								<el-option
									v-for="item in JSON.parse(answerWrap.answer.extra)"
									:key="item.value"
									:label="item.text"
									:value="item.value">
								</el-option>
							</el-select>
							
						</div>
						
					</div>
				
				</div>
						
			</div>
		`,
		
		data : function(){
	
			return {
						
				APIS : {
						QUESTION_LIST : '/dataManage/questionList.do'
				},
		
				params : {
					versionId : this.$route.query.versionId,
					questionaireCode : this.$route.query.questionaireCode
				},
				
				questionList : []
			}
		},
                
		methods : {
				
			init : function(){
				var self = this;
						
				this.$request.sendGetRequest(this.APIS.QUESTION_LIST,{versionId : this.params.versionId , questionaireCode : this.params.questionaireCode},(resultObject)=>{
					self.questionList = resultObject;
				});
			},
			
			//问题描述处理
			handleQuestionDesc(desc){
				return desc.replace(/<para>/g,'<br/>')
			}
				
		},
                
		mounted : function(){
			this.init();
		}
	};
	
	window.dataManageQuestionListComponent = dataManageQuestionListComponent;
	
})();


