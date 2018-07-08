 (function(){
	 var router = new VueRouter({
		 routes : [
			 	{path : '/' , redirect : {name : 'module1'}},
	  			{path : '/module1' , component : Module1Component , name : 'module1'},
	  			{path : '/module2/module21' , component : Module21Component , name : 'module21'},
	  			{path : '/module2/module22' , component : Module22Component , name : 'module22'}
		] 
	 });
	 window.router = router;
 })(); 
 