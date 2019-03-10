(function(){
	var myTask4TaskDetailComponent = {
		
		template : `
			<div class="my-task-task-detail">
			
				<div class="common-title">任务详情</div>
				
				<div class="common-list-operate">
					<el-button plain type="info" size="medium" @click="handleBack">返回</el-button>
				</div>
				
				<div class="common-detail">
					<el-form label-width="100px" label-position="left">
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="任务编号">
									<el-input :value="taskDetail.task.id" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="创建时间">
									<el-input :value="$commons.formatDate(taskDetail.task.createTime)" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="更新时间">
									<el-input :value="$commons.formatDate(taskDetail.task.updateTime)" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="任务状态">
									<el-input :value="$constants.TASK_STATUS.getTaskStatusText(taskDetail.task.status)" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="访谈编号">
									<el-input :value="taskDetail.interview.id" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="访谈时间">
									<el-input :value="$commons.formatDate(taskDetail.interview.endTime)" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="访谈类型">
									<el-input :value="$constants.INTERVIEW_TYPE.getInterviewTypeText(taskDetail.interview.type)" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="问卷版本">
									<el-input :value="taskDetail.interview.versionId" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="受访者编号">
									<el-input :value="taskDetail.patient.id" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="受访者姓名">
									<el-input :value="taskDetail.patient.username" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="访谈员编号">
									<el-input :value="taskDetail.doctor.id" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="访谈员姓名">
									<el-input :value="taskDetail.doctor.username" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="访谈员电话">
									<el-input :value="taskDetail.doctor.mobile" readonly></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="访谈员单位">
									<el-input :value="taskDetail.doctor.workingPlace" readonly></el-input>
								</el-form-item>
							</el-col>
						</el-row>
					</el-form>
				</div>
			</div>
		`,
		
		data : function(){
			var self = this;
			
			return {
				APIS : {
					TASK_DETAI : '/myTask/taskDetail.do'
				},
				
				params : {
					taskId : this.$route.query.taskId
				},
				
				taskDetail : {
					task : {},
					interview : {},
					patient : {},
					doctor : {}
				}
			}
		},
		
		mounted : function(){
			this.init();
		},
		
		methods : {
			init : function(){
				var self = this;
				
				this.$request.sendGetRequest(this.APIS.TASK_DETAI,{taskId : this.params.taskId},(resultObject)=>{
					this.taskDetail = resultObject;
				});
			},
			
			//返回
			handleBack(){
				this.$router.push({name : 'myTask4TaskList'});
			}
		}
	};
	
	window.myTask4TaskDetailComponent = myTask4TaskDetailComponent;
})();


