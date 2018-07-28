(function(){
	
	var menuStore = {
		
		state : {
			activeIndex : ''
	    },

	    mutations : {
            setActiveIndex(state , activeIndex){
                state.activeIndex = activeIndex;
            }
	    }
	};
	
	window.menuStore = menuStore;
	
})();
