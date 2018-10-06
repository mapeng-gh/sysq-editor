(function(){
	var myTask4TaskDetailComponent = {
		
		template : `
			<div class="my-task-task-detail">
			
				<div class="common-title">任务详情</div>
				
				<el-collapse :value="['task']" style="padding-left:10px;">
				
					<el-collapse-item name="task">
						<span slot="title" class="task-title">任务信息</span>
						<el-form label-width="100px">
							<el-row>
								<el-col :span="12">
									<el-form-item label="任务编号">
										<el-input :value="taskDetail.task.id" readonly></el-input>
									</el-form-item>
									<el-form-item label="创建时间">
										<el-input :value="$commons.formatDate(taskDetail.task.createTime)" readonly></el-input>
									</el-form-item>
									<el-form-item label="更新时间">
										<el-input :value="$commons.formatDate(taskDetail.task.updateTime)" readonly></el-input>
									</el-form-item>
									<el-form-item label="任务状态">
										<el-input :value="$constants.TASK_STATUS.getTaskStatusText(taskDetail.task.status)" readonly></el-input>
									</el-form-item>
								</el-col>
							</el-row>
						</el-form>
					</el-collapse-item>
					
					<el-collapse-item name="interview">
						<span slot="title" class="task-title">访谈信息</span>
						<el-form label-width="100px">
							<el-row>
								<el-col :span="12">
									<el-form-item label="访谈编号">
										<el-input :value="taskDetail.interview.id" readonly></el-input>
									</el-form-item>
									<el-form-item label="访谈时间">
										<el-input :value="$commons.formatDate(taskDetail.interview.endTime)" readonly></el-input>
									</el-form-item>
									<el-form-item label="访谈类型">
										<el-input :value="$constants.INTERVIEW_TYPE.getInterviewTypeText(taskDetail.interview.type)" readonly></el-input>
									</el-form-item>
									<el-form-item label="问卷版本">
										<el-input :value="taskDetail.interview.versionId" readonly></el-input>
									</el-form-item>
								</el-col>
							</el-row>
						</el-form>
					</el-collapse-item>
					
					<el-collapse-item title="患者信息" name="patient">
						<span slot="title" class="task-title">患者信息</span>
						<el-form label-width="100px">
							<el-row>
								<el-col :span="12">,
									<el-form-item label="患者编号">
										<el-input :value="taskDetail.patient.id" readonly></el-input>
									</el-form-item>
									<el-form-item label="患者姓名">
										<el-input :value="taskDetail.patient.username" readonly></el-input>
									</el-form-item>
									<el-form-item label="联系电话">
										<el-input :value="taskDetail.patient.mobile" readonly></el-input>
									</el-form-item>
									<el-form-item label="家庭住址">
										<el-input :value="taskDetail.patient.province + '-' + taskDetail.patient.city + '-' + taskDetail.patient.address " readonly></el-input>
									</el-form-item>
								</el-col>
							</el-row>
						</el-form>
					</el-collapse-item>
					
					<el-collapse-item title="医生信息" name="doctor">
						<span slot="title" class="task-title">医生信息</span>
						<el-form label-width="100px">
							<el-row>
								<el-col :span="12">
									<el-form-item label="医生编号">
										<el-input :value="taskDetail.doctor.id" readonly></el-input>
									</el-form-item>
									<el-form-item label="医生姓名">
										<el-input :value="taskDetail.doctor.username" readonly></el-input>
									</el-form-item>
									<el-form-item label="联系电话">
										<el-input :value="taskDetail.doctor.mobile" readonly></el-input>
									</el-form-item>
									<el-form-item label="工作单位">
										<el-input :value="taskDetail.doctor.workingPlace" readonly></el-input>
									</el-form-item>
								</el-col>
							</el-row>
						</el-form>
					</el-collapse-item>
					
				</el-collapse>
				
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
				
				taskDetail : {}
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
			}
		}
	};
	
	window.myTask4TaskDetailComponent = myTask4TaskDetailComponent;
})();


