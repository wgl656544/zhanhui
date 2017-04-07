package com.ex.administrator.zhanhui.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class UserBean {
    private String successed;

    private String code;

    private String message;

    private Data data;

    public UserBean() {
    }

    public UserBean(String successed, String code, String message, Data data) {
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

        private String username;

        private String password;

        private String nickName;

        private String email;

        private String mobile;

        private boolean activated;

        private String activationKey;

        private String birthday;

        private String resetPasswordKey;

        private String salt;

        private String referralsMobile;

        private String headerImgUrl;

        private String sex;

        private String city;

        private List<Roles> roles;

        private String createdDate;

        private String selChecked;

        private String createdDateStr;

        public Data() {
        }

        public Data(int id, String username, String password, String nickName, String email, String mobile, boolean activated, String activationKey, String birthday, String resetPasswordKey, String salt, String referralsMobile, String headerImgUrl, String sex, String city, List<Roles> roles, String createdDate, String selChecked, String createdDateStr) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.nickName = nickName;
            this.email = email;
            this.mobile = mobile;
            this.activated = activated;
            this.activationKey = activationKey;
            this.birthday = birthday;
            this.resetPasswordKey = resetPasswordKey;
            this.salt = salt;
            this.referralsMobile = referralsMobile;
            this.headerImgUrl = headerImgUrl;
            this.sex = sex;
            this.city = city;
            this.roles = roles;
            this.createdDate = createdDate;
            this.selChecked = selChecked;
            this.createdDateStr = createdDateStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public boolean isActivated() {
            return activated;
        }

        public void setActivated(boolean activated) {
            this.activated = activated;
        }

        public String getActivationKey() {
            return activationKey;
        }

        public void setActivationKey(String activationKey) {
            this.activationKey = activationKey;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getResetPasswordKey() {
            return resetPasswordKey;
        }

        public void setResetPasswordKey(String resetPasswordKey) {
            this.resetPasswordKey = resetPasswordKey;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getReferralsMobile() {
            return referralsMobile;
        }

        public void setReferralsMobile(String referralsMobile) {
            this.referralsMobile = referralsMobile;
        }

        public String getHeaderImgUrl() {
            return headerImgUrl;
        }

        public void setHeaderImgUrl(String headerImgUrl) {
            this.headerImgUrl = headerImgUrl;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<Roles> getRoles() {
            return roles;
        }

        public void setRoles(List<Roles> roles) {
            this.roles = roles;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getSelChecked() {
            return selChecked;
        }

        public void setSelChecked(String selChecked) {
            this.selChecked = selChecked;
        }

        public String getCreatedDateStr() {
            return createdDateStr;
        }

        public void setCreatedDateStr(String createdDateStr) {
            this.createdDateStr = createdDateStr;
        }
    }

    class Roles {
        private int id;

        private String name;

        private String nameCn;

        private String authority;

        public Roles() {
        }

        public Roles(int id, String name, String nameCn, String authority) {
            this.id = id;
            this.name = name;
            this.nameCn = nameCn;
            this.authority = authority;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameCn() {
            return nameCn;
        }

        public void setNameCn(String nameCn) {
            this.nameCn = nameCn;
        }

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }
    }
}
