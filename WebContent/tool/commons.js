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
	var userType = getLoginUser().userType;
	if(userType == constants.USER_TYPE.enums.ADMIN){
		return {name : 'userManage4UserList'};
	}else if(userType == constants.USER_TYPE.enums.EDITOR){
		return {name : 'myTask4TaskList'};
	}else if(userType == constants.USER_TYPE.enums.VIEWER){
		return {name : 'myInterview4InterviewList'};
	}
}

window.commons = {
	formatDate : formatDate,
	serialize : serialize,
	openWindow : openWindow,
	closeWindow : closeWindow,
	getLoginUser : getLoginUser,
	goLogin : goLogin,
	getHomePage : getHomePage
}
      
})();