(function(){
	var userManageListComponent = {
			template : `
                                <div class="userManageList">
                                
                                        <div class="common-title">用户列表</div>
                                        
                                        <div class="common-search">
		
                                                <el-form label-width="80px" label-position="left">
				
                                                        <el-row :gutter="50">
				
                                                                <el-col :span="8">
                                                                
                                                                        <el-form-item label="姓名">
                                                                                <el-input v-model="search.name" placeholder="请输入姓名"></el-input>
                                                                        </el-form-item>
                                                                
                                                                </el-col>
					
                                                                <el-col :span="8">
					
                                                                        <el-form-item label="类型">
                                                                                <el-select v-model="search.userType" style="width:100%;">
                                                                                        <el-option value="" label="全部"></el-option>
                                                                                        <el-option v-for="item in constants.USER_TYPE.getUserTypeList()" :key="item.code" :label="item.text" :value="item.code"></el-option>
                                                                                </el-select>
                                                                        </el-form-item>
					
                                                                </el-col>
                                                                
                                                                <el-col :span="8">
					
                                                                        <el-form-item label="审核状态">
                                                                                <el-select v-model="search.auditStatus" style="width:100%;">
                                                                                        <el-option value="" label="全部"></el-option>
                                                                                        <el-option v-for="item in constants.AUDIT_STATUS.getAuditStatusList()" :key="item.code" :label="item.text" :value="item.code"></el-option>
                                                                                </el-select>
                                                                        </el-form-item>
					
                                                                </el-col>
					
                                                        </el-row>
				
                                                        <div class="common-search-opt">
                                                                <el-button type="primary" @click="handleSearch">查询</el-button>
                                                                <el-button @click="handleReset">重置</el-button>
                                                        </div>
			
                                                </el-form>
		
                                        </div>
                                        
                                        <div class="common-list">
                                                <el-table
                                                        :data="userList"
                                                        border
                                                        header-cell-class-name="common-table-header"
                                                        style="width: 100%">
                                                        <el-table-column prop="name" label="姓名" width="150" align="center"></el-table-column>
                                                        <el-table-column prop="userType" label="类型" width="150" align="center">
                                                                <template slot-scope="scope">
                                                                        {{constants.USER_TYPE.getUserTypeText(scope.row.userType)}}
                                                                </template>
                                                        </el-table-column>
                                                        <el-table-column prop="mobile" label="联系电话" width="150" align="center"></el-table-column>
                                                        <el-table-column prop="workingPlace" label="工作单位" width="180" align="center" :show-overflow-tooltip="true"></el-table-column>
                                                        <el-table-column prop="createTime" label="创建日期" width="180" align="center">
                                                                <template slot-scope="scope">
                                                                        {{commons.formatDate(scope.row.createTime)}}
                                                                </template>
                                                        </el-table-column>
                                                        <el-table-column prop="auditStatus" label="审核状态" width="150" align="center">
                                                                <template slot-scope="scope">
                                                                        <el-tag type="info" v-if="scope.row.auditStatus == constants.AUDIT_STATUS.enums.NONE">{{constants.AUDIT_STATUS.getAuditStatusText(scope.row.auditStatus)}}</el-tag>
                                                                        <el-tag type="success" v-if="scope.row.auditStatus == constants.AUDIT_STATUS.enums.PASS">{{constants.AUDIT_STATUS.getAuditStatusText(scope.row.auditStatus)}}</el-tag>
                                                                        <el-tag type="danger" v-if="scope.row.auditStatus == constants.AUDIT_STATUS.enums.REJECT">{{constants.AUDIT_STATUS.getAuditStatusText(scope.row.auditStatus)}}</el-tag>
                                                                </template>
                                                        </el-table-column>
                                                        <el-table-column prop="operate" label="操作" align="center">
                                                                <template slot-scope="scope">
                                                                        <el-button type="text" size="mini" @click="handleOrderDetail(scope)">查看</el-button>
                                                                        <el-button type="text" size="mini" @click="handleAudit(scope)">审核</el-button>
                                                                        <el-button type="text" size="mini" @click="handleOrderDetail(scope)">授权</el-button>
                                                                        <el-button type="text" size="mini" @click="handleOrderDetail(scope)">任务分配</el-button>
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
                                                LIST : '/userManage/list.do'
                                        },
                                        
                                        userList : [],
                                        
                                        search : {
                                                name : '',
                                                userType : '',
                                                auditStatus : ''
                                        },
                                        
                                        paginate : {
                                                pageSize : 10,
                                                currentPage : 1,
                                                total : 0
                                        }
                                        
				}
			},
                        
                        methods : {
                                
                                init : function(){
                                        var self = this;
                                        
                                        this.request.sendGetRequest(this.APIS.LIST,
                                                _.assignIn({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),
                                                function(resultObject){
                                                        self.userList = resultObject.data;
                                                        self.paginate.total = resultObject.total;
                                                
                                                }
                                        );
                                },
                                
                                //搜索
                                handleSearch(){
                                        var self = this;
                                        
                                        this.paginate.currentPage = 1;
                                        
                                        this.request.sendGetRequest(this.APIS.LIST,
                                                _.assignIn({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),
                                                function(resultObject){
                                                        self.userList = resultObject.data;
                                                        self.paginate.total = resultObject.total;
                                                
                                                }
                                        );
                                },
                                
                                //重置
                                handleReset(){
                                        var self = this;
                                        
                                        this.search = {name : '',userType : '',auditStatus : ''},
                                        
                                        this.paginate.currentPage = 1;
                                        
                                        this.request.sendGetRequest(this.APIS.LIST,
                                                _.assignIn({},this.search,{currentPage:this.paginate.currentPage,pageSize:self.paginate.pageSize}),
                                                function(resultObject){
                                                        self.userList = resultObject.data;
                                                        self.paginate.total = resultObject.total;
                                                
                                                }
                                        );
                                },
                                
                                handleAudit(scope){
                                        
                                     
                                }
                        },
                        
                        mounted : function(){
                                this.init();
                        }
	};
	window.userManageListComponent = userManageListComponent;
})();


