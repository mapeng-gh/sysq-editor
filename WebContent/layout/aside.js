(function(){
	
	var template = `
		<div class="aside-content">
				
				<el-menu default-active="2-1" background-color="#212E39" text-color="#A7B7BC" active-text-color="#FFF" :unique-opened="true" :router="true">
				
						<el-menu-item index="1">
								<i class="el-icon-menu"></i>
								<span slot="title">模块1</span>
						</el-menu-item>
				
						<el-submenu index="/module2">
								<template slot="title">模块2</template>
								<el-menu-item index="/module2/module21">
										<i class="el-icon-date"></i>
        								<span slot="title">子模块2-1</span>
								</el-menu-item>
            					<el-menu-item index="/module2/module22">
            							<i class="el-icon-mobile-phone"></i>
        								<span slot="title">子模块2-2</span>
            					</el-menu-item>
            					<el-menu-item index="/module2/module23">
            							<i class="el-icon-goods"></i>
        								<span slot="title">子模块2-3</span>
            					</el-menu-item>
						</el-submenu>
						
						<el-submenu index="/module3">
								<template slot="title">模块3</template>
								<el-menu-item index="/module3/module31">
										<i class="el-icon-date"></i>
        								<span slot="title">子模块3-1</span>
								</el-menu-item>
            					<el-menu-item index="/module3/module32">
            							<i class="el-icon-mobile-phone"></i>
        								<span slot="title">子模块3-2</span>
            					</el-menu-item>
            					<el-menu-item index="/module3/module33">
            							<i class="el-icon-goods"></i>
        								<span slot="title">子模块3-3</span>
            					</el-menu-item>
						</el-submenu>
				
				</el-menu>
				
				
		</div>
	`;
	
	var asideComponent = {
			template : template,
			data : function(){
				return {
				}
			}
	};
	Vue.component('sysq-aside',asideComponent);
})();