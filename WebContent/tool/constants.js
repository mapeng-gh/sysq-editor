(function(){
      
        var constants = {
                
                //用户类型
                USER_TYPE : {
                        enums : {
                            EDITOR : 2,
                            VIEWER : 3
                        },
                        getUserTypeText(code){
                            if(code == this.enums.EDITOR){
                                return '编辑';
                            }else if(code == this.enums.VIEWER){
                                return '浏览';
                            }
                        },
                        getUserTypeList(){
                            var list = [];
                            for(var key in this.enums){
                                list.push({'code' : this.enums[key] , 'text' : this.getUserTypeText(this.enums[key])});
                            }
                            return list;
                        }
                },
                
                //审核类型
                AUDIT_STATUS : {
                        enums : {
                            NONE : 0,
                            PASS : 1,
                            REJECT : 2
                        },
                        getAuditStatusText(code){
                            if(code == this.enums.NONE){
                                return '待审核';
                            }else if(code == this.enums.PASS){
                                return '审核通过';
                            }else if(code == this.enums.REJECT){
                                return '审核不通过';
                            }
                        },
                        getAuditStatusList(){
                            var list = [];
                            for(var key in this.enums){
                                list.push({'code' : this.enums[key] , 'text' : this.getAuditStatusText(this.enums[key])});
                            }
                            return list;
                        }
                },
        };
        
        window.constants = constants;
        
})();