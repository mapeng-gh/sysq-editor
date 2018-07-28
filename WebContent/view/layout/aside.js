(function(){
	
	var asideComponent = {
			
		template : `
		
			<el-menu 
			
				:router="true"
				:unique-opened = "true"
				:default-active="activeIndex" 
				background-color="#eef1f6"
				text-color="#48576a"
				active-text-color="#20a0ff">
	
				<template v-for="item in menuList">

				<template v-if="!item.sub || item.sub.length == 0">
					<el-menu-item :index="item.index" :key="item.index">
						<i :class="item.icon"></i>
						<span slot="title">{{item.name}}</span>
					</el-menu-item>
				</template>

				<template v-else>
					<el-submenu :index="item.index">
						<template slot="title">
							<i :class="item.icon"></i>
							<span slot="title">{{item.name}}</span>
						</template>
						<template v-for="subItem in item.sub">
							<el-menu-item :index="subItem.index">
								<span slot="title">{{subItem.name}}</span>
							</el-menu-item>
						</template>
					</el-submenu>       
				</template>
				
			</template>
	
			</el-menu>
			
		`,
		
		data : function(){
			return {
				
				menuList : menuList
			}
		},
		
		computed : {
            activeIndex(){
                    return this.$store.state.menu.activeIndex
            }
		}
	};
	Vue.component('sysq-aside',asideComponent);
	
})();