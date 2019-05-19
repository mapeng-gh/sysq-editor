(function(){
	var myTask4TaskDetailComponent = {
		
		template : `
			<div class="my-task-task-detail">
				<div class="common-title">任务详情</div>
				
				<div class="common-detail">
					<el-form label-width="100px" label-position="left">
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="任务编号 : ">
									{{taskDetail.task.id}}
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="创建时间 : ">
									{{$commons.formatDate(taskDetail.task.createTime)}}
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="更新时间 : ">
									{{$commons.formatDate(taskDetail.task.updateTime)}}
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="任务状态 : ">
									{{$constants.TASK_STATUS.getTaskStatusText(taskDetail.task.status)}}
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="访谈编号">
									{{taskDetail.interview.id}}
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="访谈时间 : ">
									{{$commons.formatDate(taskDetail.interview.endTime)}}
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="访谈类型 : ">
									{{$constants.INTERVIEW_TYPE.getInterviewTypeText(taskDetail.interview.type)}}
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="问卷版本 : ">
									{{taskDetail.interview.versionId}}
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="访谈员编号 : ">
									{{taskDetail.doctor.id}}
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="访谈员姓名 : ">
									{{taskDetail.doctor.username}}
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="50">
							<el-col :span="12">
								<el-form-item label="访谈员电话 : ">
									{{taskDetail.doctor.mobile}}
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="访谈员单位 : ">
									{{taskDetail.doctor.workingPlace}}
								</el-form-item>
							</el-col>
						</el-row>
					</el-form>
				</div>
				
				<div class="common-list-operate">
					<el-button plain type="info" size="medium" @click="handleBack">返回</el-button>
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


