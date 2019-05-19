(function(){
	
	//日期格式化
	function formatDate(date,format){
		format = format || 'YYYY-MM-DD HH:mm:ss';
		return dateFns.format(date,format);
	}

	//对象序列化
	function serialize(params){
		var serializeStr = '';
		var serializeArray = [];
		if(params){
			for(var key in params){
				serializeArray.push(key + '=' + encodeURIComponent(params[key]));
			}
		}
		if(serializeArray.length > 0){
			serializeStr = serializeArray.join('&');
		}
		return serializeStr;
	}

	//打开新标签
	function openWindow(hash,params){
		var url = window.location.href.replace(window.location.hash,hash);

		if(params){
			url = url + '?' + this.serialize(params);
		}

		window.open(url,'_blank');
	}

	//下载
	function download(pathname,params){
		var loginUser = getLoginUser();
		if(!loginUser){
			Vue.prototype.$message.error('请重新登录后再操作');
			return;
		}
		
		if(!params){
			params = {};
		}
		params.Authorization = loginUser.token;
		
		var url = location.protocol + '//' + location.host + pathname;
		url += '?' + serialize(params);
		window.open(url,'_blank');
	}

	//关闭当前标签
	function closeWindow(){
		window.close();
	}

	//跳转登录
	function goLogin(){
		router.push({name : 'login'});
	}

	//获取登录用户信息
	function getLoginUser(){
		var loginUserStr = window.localStorage.getItem('loginUser');
		if(!loginUserStr){
			return null;
		}
		return JSON.parse(loginUserStr);
	}

	//跳转主页
	function getHomePage(){
		var loginUser = getLoginUser();
		if(!loginUser){
			return 'login';
		}else{
			var userType = loginUser.userType;
			if(userType == constants.USER_TYPE.enums.ADMIN){
				return 'userManage4UserList';
			}else if(userType == constants.USER_TYPE.enums.EDITOR){
				return 'myTask4TaskList';
			}else if(userType == constants.USER_TYPE.enums.VIEWER){
				return 'interview4InterviewList';
			}
		}
	}

	//确认对话框
	function confirm(content,title,confirmCallback){
		Vue.prototype.$confirm(content,title,{
			type : 'warning',
			dangerouslyUseHTMLString : true,
			closeOnClickModal : false,
			closeOnPressEscape : false,
			callback : function(action){
				if(action == 'confirm'){
					confirmCallback();
				}
			}
		});
	}

	//输入对话框
	function prompt(message,title,confirmCallback){
		Vue.prototype.$prompt(message,title,{
			dangerouslyUseHTMLString : true,
			closeOnClickModal : false,
			closeOnPressEscape : false,
			inputType : 'textarea',
			callback : function(action,instance){
				if(action == 'confirm'){
					confirmCallback(instance.inputValue);
				}
			}
		});
	}

	//提示对话框
	function alert(title,message,confirmBtnText,confirmCallback){
		Vue.prototype.$alert(message,title,{
			dangerouslyUseHTMLString : true,
			closeOnClickModal : false,
			closeOnPressEscape : false,
			confirmButtonText : confirmBtnText,
			callback : function(action){
				if(action == 'confirm'){
					confirmCallback();
				}
			}
		});
	}
	
	//显示加载框
	var loadingObj = null;
	function loading(text){
		loadingObj = Vue.prototype.$loading({
			fullscreen : true,
			lock : true,
			text : text || '正在处理中,请稍候...',
			spinner: 'el-icon-loading',
	        background: 'rgba(0, 0, 0, 0.5)'
		});
	}
	
	//关闭加载框
	function closeLoading(){
		if(loadingObj){
			loadingObj.close();
		}
	}

	window.commons = {
		formatDate : formatDate,
		serialize : serialize,
		openWindow : openWindow,
		closeWindow : closeWindow,
		getLoginUser : getLoginUser,
		goLogin : goLogin,
		getHomePage : getHomePage,
		confirm : confirm,
		prompt : prompt,
		alert : alert,
		download : download,
		loading,
		closeLoading
	}
      
})();