(function(){
	
	var store = new Vuex.Store({
	    modules : {
	        menu : menuStore
	    }
	});
	
	window.store = store;
	
})();
