(function(){
      
/**
* 日期格式化
* @param  {[type]} date   日期对象
* @param  {[type]} format 标准格式串
* @return {[type]}        格式化后字符串
*/
function formatDate(date,format){
        format = format || 'YYYY-MM-DD HH:mm:ss';
        return dateFns.format(date,format);
}

/**
* 对象序列化
* @param  {[type]} params 参数对象
* @return {[type]}        序列化字符串
*/
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

/**
* 打开新标签
* @param  {[type]} hash   地址hash值
* @param  {[type]} params 查询参数对象【可选】
*/
function open(hash,params){
        var url = window.location.href.replace(window.location.hash,hash);

        if(params){
                url = url + '?' + this.serialize(params);
        }

        window.open(url,'_blank');
}

function logout(){
        window.localStorage.removeItem('loginUser');
        window.location.hash = '#/login';
}

window.commons = {
        formatDate : formatDate,
        serialize : serialize,
        open : open,
        logout : logout
}
      
})();