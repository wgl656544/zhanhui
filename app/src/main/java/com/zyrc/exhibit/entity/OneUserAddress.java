package com.zyrc.exhibit.entity;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class OneUserAddress {
    private String successed;

    private String code;

    private String message;

    private Data data;

    public OneUserAddress() {
    }

    public OneUserAddress(String successed, String code, String message, Data data) {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private int id;

        private int userId;

        private String type;

        private String tagType;

        private Address address;

        private boolean selChecked;

        public Data() {
        }

        public Data(int id, int userId, String type, String tagType, Address address, boolean selChecked) {
            this.id = id;
            this.userId = userId;
            this.type = type;
            this.tagType = tagType;
            this.address = address;
            this.selChecked = selChecked;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTagType() {
            return tagType;
        }

        public void setTagType(String tagType) {
            this.tagType = tagType;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public boolean isSelChecked() {
            return selChecked;
        }

        public void setSelChecked(boolean selChecked) {
            this.selChecked = selChecked;
        }
    }

    public class Address {
        private String country;

        private String province;

        private String city;

        private String area;

        private String street;

        private String postCode;

        private String contactUserName;

        private String contactTel;

        public Address() {
        }

        public Address(String country, String province, String city, String area, String street, String postCode, String contactUserName, String contactTel) {
            this.country = country;
            this.province = province;
            this.city = city;
            this.area = area;
            this.street = street;
            this.postCode = postCode;
            this.contactUserName = contactUserName;
            this.contactTel = contactTel;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getContactUserName() {
            return contactUserName;
        }

        public void setContactUserName(String contactUserName) {
            this.contactUserName = contactUserName;
        }

        public String getContactTel() {
            return contactTel;
        }

        public void setContactTel(String contactTel) {
            this.contactTel = contactTel;
        }
    }
}
