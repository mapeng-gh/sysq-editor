(function(){
	var dataManageVersionListComponent = {
		template : `
            <div class="userManageList">
                                
                <div class="common-title">版本列表</div>
                                        
                                        
                <div class="common-list">
                    <el-table
                        :data="versionList"
                        border
						header-cell-class-name="common-table-header"
						style="width: 100%"
						:row-class-name="tableRowClassName">
                        <el-table-column prop="id" label="编号" align="left" width="80"></el-table-column>
                        <el-table-column prop="name" label="名称" align="left"  :show-overflow-tooltip="true"></el-table-column>
                        <el-table-column prop="publishDate" label="发布时间" align="left">
							<template slot-scope="scope">
								{{$commons.formatDate(scope.row.publishDate,'YYYY-MM-DD')}}
							</template>
						</el-table-column>
                        <el-table-column prop="isCurrent" label="当前版本" align="left">
							<template slot-scope="scope">
								{{scope.row.isCurrent == 1?'是':'否'}}
							</template>
                        </el-table-column>
						<el-table-column prop="operate" label="操作" align="center">
							<template slot-scope="scope">
								<el-button type="text" size="mini" @click="handleCaseQuestionaire(scope)">病例问卷</el-button>
								<el-button type="text" size="mini" @click="handleContrastQuestionaire(scope)">对照问卷</el-button>
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
			var self = this;
			return {
				
                APIS : {
                    VERSION_LIST : '/dataManage/versionList.do',
				},
				
				versionList : [],
				
				paginate : {
					pageSize : 10,
					currentPage : 1,
					total : 0
                },
			};
		},
			
		mounted : function(){
            this.init();
        },
                        
        methods : {
                                
            init : function(){
                var self = this;
					
                this.$request.sendGetRequest(this.APIS.VERSION_LIST,{currentPage:this.paginate.currentPage,pageSize:this.paginate.pageSize},function(resultObject){
					self.versionList = resultObject.data;
					self.paginate.total = resultObject.total;
                });
            },
			
			//表格高亮：当前版本
			tableRowClassName : function(params){
				if(params.row.isCurrent == 1){
					return 'success-row';
				}
				return '';
			},
                                
            //切换分页
            handleCurrentChange : function(currentPage){
                var self = this;
                this.paginate.currentPage = currentPage;
					
                this.$request.sendGetRequest(this.APIS.VERSION_LIST,{currentPage:this.paginate.currentPage,pageSize:this.paginate.pageSize},function(resultObject){
					self.versionList = resultObject.data;
                    self.paginate.total = resultObject.total;
				});
            },
                                
            //切换大小
            handleSizeChange : function(currentSize){
                var self = this;
				this.paginate.pageSize = currentSize;
				this.paginate.currentPage = 1;
					 
                this.$request.sendGetRequest(this.APIS.VERSION_LIST,{currentPage:this.paginate.currentPage,pageSize:this.paginate.pageSize},function(resultObject){
					self.versionList = resultObject.data;
                    self.paginate.total = resultObject.total;
                });
            },
			
			//查看病例问卷
			handleCaseQuestionaire : function(scope){
				this.$commons.openWindow('#/dataManage/questionaireList',{versionId : scope.row.id  , type : this.$constants.INTERVIEW_TYPE.enums.CASE});
			},
                                
			//查看对照问卷
            handleContrastQuestionaire : function(scope){
				this.$commons.openWindow('#/dataManage/questionaireList',{versionId : scope.row.id , type : this.$constants.INTERVIEW_TYPE.enums.CONTRAST});
            }
			
        }
	};
	window.dataManageVersionListComponent = dataManageVersionListComponent;
})();


