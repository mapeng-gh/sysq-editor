(function(){
	
	var layoutComponent = {
			
		template : `
		
			<div>
				
				<sysq-header class="header"></sysq-header>
     
	           	<el-row>
	           	
	           		<el-col :span="4" class="aside">
	           			<sysq-aside></sysq-aside>
	           		</el-col>
	           		
	           		<el-col :span="20" class="content">
	              		<sysq-content></sysq-content>
	              	</el-col>
	           	
	           	</el-row>
                	
            	<sysq-footer class="footer"></sysq-footer>     
				
			</div>
			
		`,
		
		data : function(){
			return {
				
			}
		}
	};
	window.layoutComponent = layoutComponent;
})();