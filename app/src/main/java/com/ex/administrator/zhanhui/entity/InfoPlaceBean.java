package com.ex.administrator.zhanhui.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class InfoPlaceBean {
    private String successed;

    private String code;

    private String message;

    private List<Data> data ;

    public InfoPlaceBean() {
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public InfoPlaceBean(String successed, String code, String message, List<Data> data) {
        this.successed = successed;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public class Data{
        private int id;

        private String name;

        private String enName;

        private String code;

        private String fullName;

        private int type;

        private int parentId;

        private List<SubData> subData ;

        private int status;

        public Data() {
        }

        public Data(int id, String name, String enName, String code, String fullName, int type, int parentId, List<SubData> subData, int status) {
            this.id = id;
            this.name = name;
            this.enName = enName;
            this.code = code;
            this.fullName = fullName;
            this.type = type;
            this.parentId = parentId;
            this.subData = subData;
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<SubData> getSubData() {
            return subData;
        }

        public void setSubData(List<SubData> subData) {
            this.subData = subData;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class SubData{
        private int id;

        private String name;

        private String enName;

        private String code;

        private String fullName;

        private int type;

        private int parentId;

        private String subData;

        private int status;

        public SubData() {
        }

        public SubData(int id, int status, String subData, int parentId, int type, String fullName, String code, String enName, String name) {
            this.id = id;
            this.status = status;
            this.subData = subData;
            this.parentId = parentId;
            this.type = type;
            this.fullName = fullName;
            this.code = code;
            this.enName = enName;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSubData() {
            return subData;
        }

        public void setSubData(String subData) {
            this.subData = subData;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
