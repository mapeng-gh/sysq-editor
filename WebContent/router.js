 (function(){
	 var router = new VueRouter({
		 routes : [
			  	 {
			  		 path : '/' , 
			  		 component : layoutComponent,
			  		 redirect : {name : 'module1'},
			  		 children : [
			  			 {path : '/module1' , component : Module1Component , name : 'module1'},
			  			 {path : '/module2' , component : Module2Component , name : 'module2'}
			  		 ]
			  	 }
		] 
	 });
	 window.router = router;
 })(); 
 