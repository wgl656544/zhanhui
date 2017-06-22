package com.zyrc.exhibit.entity;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class VersionUpDateBean {
    private String successed;

    private String code;

    private String message;

    private Data data;

    public VersionUpDateBean() {
    }

    public String getSuccessed() {
        return successed;
    }

    public void setSuccessed(String successed) {
        this.successed = successed;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private int id;

        private String versionType;

        private String versionNo;

        private String appUrl;

        private int status;

        public Data() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersionType() {
            return versionType;
        }

        public void setVersionType(String versionType) {
            this.versionType = versionType;
        }

        public String getVersionNo() {
            return versionNo;
        }

        public void setVersionNo(String versionNo) {
            this.versionNo = versionNo;
        }

        public String getAppUrl() {
            return appUrl;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
