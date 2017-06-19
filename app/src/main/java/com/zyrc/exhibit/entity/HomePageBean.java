package com.zyrc.exhibit.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class HomePageBean {
    private String successed;

    private String code;

    private String message;

    private List<Data> data;

    public HomePageBean() {
    }

    public HomePageBean(String successed, String code, String message, List<Data> data) {
        this.successed = successed;
        this.code = code;
        this.message = message;
        this.data = data;
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

    public class Data {
        private String name;

        private String layoutType;

        private List<CommonBean.Data> dataList;

        public Data() {
        }

        public Data(String name, String layoutType, List<CommonBean.Data> dataList) {
            this.name = name;
            this.layoutType = layoutType;
            this.dataList = dataList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLayoutType() {
            return layoutType;
        }

        public void setLayoutType(String layoutType) {
            this.layoutType = layoutType;
        }

        public List<CommonBean.Data> getDataList() {
            return dataList;
        }

        public void setDataList(List<CommonBean.Data> dataList) {
            this.dataList = dataList;
        }
    }


}

