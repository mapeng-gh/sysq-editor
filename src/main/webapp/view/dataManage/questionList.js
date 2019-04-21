(function(){
	var dataManage4QuestionListComponent = {
		
		template : `
			<div class="data-manage-question-list">
				<div class="common-title">问题列表</div>
				
				<div class="common-list-operate">
					<el-button plain type="info" size="medium" @click="handleBack">返回</el-button>
				</div>
					
				<div class="question" v-for="questionWrap in questionList" :key="questionWrap.question.id">
					<div class="question-desc" v-html="questionWrap.question.description == '' ? '暂无描述内容' : handleQuestionDesc(questionWrap.question.description)"></div>
					
					<div class="answer" v-for="answerWrap in questionWrap.answerList" :key="answerWrap.answer.id">
						<div class="answer-label" v-if="questionWrap.answerList.length > 1">{{answerWrap.answer.code}} {{answerWrap.answer.label && answerWrap.answer.label.startsWith(answerWrap.answer.code) ? answerWrap.answer.label.substring(answerWrap.answer.code.length) : answerWrap.answer.label}}</div>
						<div class="answer-content">
							
							<el-input v-if="answerWrap.answer.type == 'text'" type="textarea" :rows="3" disabled></el-input>
							
							<el-radio-group v-else-if="answerWrap.answer.type == 'radiogroup'" :class="{vertical : answerWrap.answer.showType == 'vertical'}" value="" disabled>
								<el-radio v-for="item in JSON.parse(answerWrap.answer.extra)" :key="item.value" :label="item.value">{{item.text}}</el-radio>
							</el-radio-group>
							
							<el-checkbox-group v-else-if="answerWrap.answer.type == 'checkbox'" :class="{vertical : answerWrap.answer.showType == 'vertical'}" value="" disabled>
								<el-checkbox v-for="item in JSON.parse(answerWrap.answer.extra)" :key="item.value" :label="item.value">{{item.text}}</el-checkbox>
							</el-checkbox-group>
							
							<el-date-picker v-else-if="answerWrap.answer.type == 'calendar'"
								value=""
								type="date"
								format="yyyy-MM-dd"
								value-format="yyyy-MM-dd"
								:editable="false"
								:clearable="false"
								disabled>
							</el-date-picker>
							
							<el-input-number v-else-if="answerWrap.answer.type == 'spinbox'" 
								value="" 
								disabled>
							</el-input-number>
							
							<el-select v-else-if="answerWrap.answer.type == 'dropdownlist'" value="" disabled>
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
					type : this.$route.query.type,
					questionaireCode : this.$route.query.questionaireCode
				},
				
				questionList : []
			}
		},
		
		mounted : function(){
			this.init();
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
			},
				
			//返回
			handleBack(){
				this.$router.push({name : 'dataManage4QuestionaireList' , query : {versionId : this.params.versionId , type : this.params.type}});
			}
		}
	};
	
	window.dataManage4QuestionListComponent = dataManage4QuestionListComponent;
	
})();


