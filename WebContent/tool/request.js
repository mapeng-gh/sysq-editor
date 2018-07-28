import axios from 'axios';
import domain from '@src/config/domain';
import { Message } from 'element-ui';

//全局配置
axios.defaults.timeout = 10000;
axios.defaults.baseURL = domain.apiUrl;

//错误请求处理
var handleError = (error) => {
        if(error.response){
		Message.error('服务端处理失败: ' + error.response.status + ' ' + error.response.statusText);
        }else if(error.request){
                Message.error('请求连接超时：' + error.config.url);
        }else{
                Message.error(error.message);
        }
};

//GET请求
var sendGetRequest = (url , params , successCB , errorCB) => {
        axios.get(url,{
                params : params
        })
        .then((response) =>{
                handleRequest(response,successCB,errorCB);
        })
        .catch((error) =>{
                handleError(error);
        });
};

//POST请求
var sendPostRequest = (url , data , successCB , errorCB) => {
        axios.post(url,data)
        .then((response) =>{
                handleRequest(response,successCB,errorCB);
        })
        .catch((error) =>{
                handleError(error);
        });
};

//响应处理
var handleRequest = (response,successCB,errorCB) => {
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

export default {
        sendGetRequest,
        sendPostRequest
}
