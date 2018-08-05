(function(){
        
        var Message = Vue.prototype.$message;
      
        //全局配置
        axios.defaults.timeout = 10000;
        axios.defaults.baseURL = location.protocol + "//" + location.host;

        //错误请求处理
        var handleError = function(error){
                if(error.response){
                        Message.error('服务端处理失败: ' + error.response.status + ' ' + error.response.statusText);
                }else if(error.request){
                        Message.error('请求连接超时：' + error.config.url);
                }else{
                        Message.error(error.message);
                }
        };

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
                        window.hash = '#/login';
                }else if(result.code == 1){
                        successCB(result.resultObject);
                }else{
                        if(errorCB){
                                errorCB(result);
                        }else{
                                Message.error('业务处理失败：' + JSON.stringify(result));
                        }
                }
        }
        
        window.request = {
                sendGetRequest : sendGetRequest,
                sendPostRequest : sendPostRequest
        }
      
})();