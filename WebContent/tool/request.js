(function(){
        
        var Message = Vue.prototype.$message;
      
        //全局配置
        axios.defaults.timeout = 30000;
        axios.defaults.baseURL = location.protocol + "//" + location.host;

        //错误请求处理
        var handleError = function(error){
                if(error.response){
                        Message.error('服务端处理失败: ' + error.response.status + ' ' + error.response.statusText);
                }else if(error.request){
                        Message.error('请求连接超时：' + error.config.url);
                }else{
                        Message.error(error);
                }
        };
        
        //请求拦截
        axios.interceptors.request.use((config)=>{
                if(config.url == '/login.do'){
                        return config;
                }else{
                        var token = (JSON.parse(localStorage.getItem("loginUser")) || {}).token;
                        if(!token){
                                commons.logout();
                                return Promise.reject("用户未登录");
                        }else{
                                config.headers.common['Authorization'] = token;
                                return config;
                        }
                }
        },(error)=>{
                return Promise.reject(error);
        });

        //GET请求
        var sendGetRequest = function(url , params , successCB , errorCB){
                axios.get(url,{
                        params : params
                })
                .then(function(response){
                        handleRequest(response,successCB,errorCB);
                })
                .catch(function(error){
                        handleError(error);
                });
        };

        //POST请求
        var sendPostRequest = function(url , data , successCB , errorCB){
                axios.post(url,data)
                .then(function(response){
                        handleRequest(response,successCB,errorCB);
                })
                .catch(function(error){
                        handleError(error);
                });
        };

        //响应处理
        var handleRequest = function(response,successCB,errorCB){
                var result = response.data;
                if(result.code == -2){
                        commons.logout();
                }else if(result.code == 1){
                        successCB(result.resultObject);
                }else{
                        if(errorCB){
                                errorCB(result);
                        }else{
                                Message.error(result.msg);
                        }
                }
        }
        
        window.request = {
                sendGetRequest : sendGetRequest,
                sendPostRequest : sendPostRequest
        }
      
})();