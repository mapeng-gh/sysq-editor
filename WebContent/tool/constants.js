(function(){
      
        var constants = {
                
                //用户类型
                USER_TYPE : {
                        enums : {
                                ADMIN : 1,
                                EDITOR : 2,
                                VIEWER : 3
                        },
                        getUserTypeText(code){
                                if(code == this.enums.ADMIN){
                                        return '管理员';
                                }else if(code == this.enums.EDITOR){
                                        return '编辑人员';
                                }else if(code == this.enums.VIEWER){
                                        return '浏览人员';
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
                                ING : 1,
                                PASS : 2,
                                REJECT : 3
                        },
                        getAuditStatusText(code){
                                if(code == this.enums.NONE){
                                        return '审核中';
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
                
                //访谈类型
                INTERVIEW_TYPE : {
                        enums : {
                                'CASE' : 1,
                                CONTRAST : 2
                        },
                        getInterviewTypeText(code){
                                if(code == this.enums['CASE']){
                                        return '病例';
                                }else if(code == this.enums.CONTRAST){
                                        return '对照';
                                }
                        },
                        getInterviewTypeList(){
                                var list = [];
                                for(var key in this.enums){
                                        list.push({'code' : this.enums[key] , 'text' : this.getInterviewTypeText(this.enums[key])});
                                }
                                return list;
                        }
                }
        };
        
        window.constants = constants;
        
})();