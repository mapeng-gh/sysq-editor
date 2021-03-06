package com.huasheng.sysq.editor.util;

import java.util.List;

public class Page<T> {

        private List<T> data;
        private int currentPage;
        private int pageSize;
        private int total;
        private int totalPages;
        
	
        public Page() {
        }

        public Page(List<T> data,int currentPage,int pageSize,int total) {
                this.data = data;
                this.currentPage = currentPage;		
                this.pageSize = pageSize;
                this.total = total;
                this.totalPages = total%pageSize == 0?total/pageSize:total/pageSize+1;
        }

        public List<T> getData() {
                return data;
        }

        public void setData(List<T> data) {
                this.data = data;
        }

        public int getCurrentPage() {
                return currentPage;
        }

        public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
        }

        public int getPageSize() {
                return pageSize;
        }

        public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
        }

        public int getTotal() {
                return total;
        }

        public void setTotal(int total) {
                this.total = total;
        }

        public int getTotalPages() {
                return totalPages;
        }

        public void setTotalPages(int totalPages) {
                this.totalPages = totalPages;
        }
	
	
}
