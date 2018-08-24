(function(){
	var userManageTaskComponent = {
		template : `
			<div class="user-manage-task">
			
				<div class="common-title">任务分配</div>
				
				<div class="common-list-operate">
					<el-button type="primary">分配</el-button>
				</div>
				
				<div class="common-list">
					<el-table
						:data="taskList"
						border
						header-cell-class-name="common-table-header"
						style="width: 100%">
						<el-table-column type="selection" width="55"></el-table-column>
						<el-table-column prop="name" label="访谈编号" align="center"></el-table-column>
						<el-table-column prop="userType" label="类型"  align="center"></el-table-column>
						<el-table-column prop="workingPlace" label="患者姓名" align="center" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="workingPlace" label="医生姓名"  align="center" :show-overflow-tooltip="true"></el-table-column>
						<el-table-column prop="userType" label="问卷版本"  align="center"></el-table-column>
						<el-table-column prop="createTime" label="访谈日期" align="center">
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.createTime)}}
							</template>
						</el-table-column>
					</el-table>
                                </div>
                                        
				<div class="common-pagination">
					<el-pagination
						:page-sizes="[10,20,30,50]"
						:page-size="paginate.pageSize"
						:current-page="paginate.currentPage"
						layout="total,sizes,prev,pager,next,jumper"
						:total="paginate.total"
						:pager-count="7"
						background
						@current-change="handleCurrentChange"
						@size-change="handleSizeChange">
					</el-pagination>
				</div>
			
			</div>
		`,
		
		data : function(){
			
			return {
				
				APIS : {
					UNASSIGN_TASK : '/userManage/task.do'
				},
				
				params : {
					userId : this.$route.query.userId	
				},
				
				taskList : [],
				
				paginate : {
					pageSize : 10,
					currentPage : 1,
					total : 0
                                }
			}
		},
		
		mounted : function(){
			this.init();
		},
		
		methods : {
			
			init : function(){
				var self = this;
				
				
			}
			
			
		}
		
		
	};
	window.userManageTaskComponent = userManageTaskComponent;
})();


