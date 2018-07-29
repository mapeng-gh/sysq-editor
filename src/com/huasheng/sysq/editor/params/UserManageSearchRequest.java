package com.huasheng.sysq.editor.params;

import org.apache.commons.lang3.StringUtils;

import com.huasheng.sysq.editor.model.User;

public class UserManageSearchRequest {

        private String name;
        private String currentPage;
        private String pageSize;
        
        public boolean validate() {
        	if(StringUtils.isBlank(this.currentPage) || !StringUtils.isNumeric(this.currentPage)) {
        		return false;
        	}
        	if(StringUtils.isBlank(this.pageSize) || !StringUtils.isNumeric(this.pageSize)) {
        		return false;
        	}
                return true;
        }
        
        public User toUser() {
                User user = new User();
                user.setName(this.name);
                return user;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getCurrentPage() {
                return currentPage;
        }

        public void setCurrentPage(String currentPage) {
                this.currentPage = currentPage;
        }

        public String getPageSize() {
                return pageSize;
        }

        public void setPageSize(String pageSize) {
                this.pageSize = pageSize;
        }
        
}
