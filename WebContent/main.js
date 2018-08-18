
//注入工具方法
Vue.prototype.constants = constants;
Vue.prototype.$commons = commons;
Vue.prototype.$request = request;
Vue.prototype.$lodash = _;

var app = new Vue({
	  el: '#app',
	  template : '<App></App>',
	  router : router,
	  store : store
});