package com.zyrc.exhibit.entity.order;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class OrderBean {
    private int totalPages;

    private int totalElements;

    private String successed;

    private String code;

    private String message;

    private List<Data> data;

    public OrderBean() {
    }

    public OrderBean(int totalPages, int totalElements, String successed, String code, String message, List<Data> data) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.successed = successed;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
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

    //data
    public class Data {
        private int id;

        private String orderNo;

        private double amount;

        private String createdDate;

        private String refundedDate;

        private String startDate;

        private String endDate;

        private List<Items> items;

        private User user;

        private String payType;

        private String payedDate;

        private int status;

        private String couponNo;

        private int productCount;

        private double payAccount;

        private double couponAmount;

        private double payAmount;

        private double dispatchCost;

        private int orderType;

        private Address address;

        private String addrId;

        private String summary;

        private String payTypeString;

        private String statusString;

        private String orderTypeString;

        private String statusStr;

        public Data() {
        }

        public Data(int id, String orderNo, double amount, String createdDate, String refundedDate, String startDate, String endDate, List<Items> items, User user, String payType, String payedDate, int status, String couponNo, int productCount, double payAccount, double couponAmount, double payAmount, double dispatchCost, int orderType, Address address, String addrId, String summary, String payTypeString, String statusString, String orderTypeString, String statusStr) {
            this.id = id;
            this.orderNo = orderNo;
            this.amount = amount;
            this.createdDate = createdDate;
            this.refundedDate = refundedDate;
            this.startDate = startDate;
            this.endDate = endDate;
            this.items = items;
            this.user = user;
            this.payType = payType;
            this.payedDate = payedDate;
            this.status = status;
            this.couponNo = couponNo;
            this.productCount = productCount;
            this.payAccount = payAccount;
            this.couponAmount = couponAmount;
            this.payAmount = payAmount;
            this.dispatchCost = dispatchCost;
            this.orderType = orderType;
            this.address = address;
            this.addrId = addrId;
            this.summary = summary;
            this.payTypeString = payTypeString;
            this.statusString = statusString;
            this.orderTypeString = orderTypeString;
            this.statusStr = statusStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getRefundedDate() {
            return refundedDate;
        }

        public void setRefundedDate(String refundedDate) {
            this.refundedDate = refundedDate;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public List<Items> getItems() {
            return items;
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getPayedDate() {
            return payedDate;
        }

        public void setPayedDate(String payedDate) {
            this.payedDate = payedDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCouponNo() {
            return couponNo;
        }

        public void setCouponNo(String couponNo) {
            this.couponNo = couponNo;
        }

        public int getProductCount() {
            return productCount;
        }

        public void setProductCount(int productCount) {
            this.productCount = productCount;
        }

        public double getPayAccount() {
            return payAccount;
        }

        public void setPayAccount(double payAccount) {
            this.payAccount = payAccount;
        }

        public double getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(double couponAmount) {
            this.couponAmount = couponAmount;
        }

        public double getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(double payAmount) {
            this.payAmount = payAmount;
        }

        public double getDispatchCost() {
            return dispatchCost;
        }

        public void setDispatchCost(double dispatchCost) {
            this.dispatchCost = dispatchCost;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getAddrId() {
            return addrId;
        }

        public void setAddrId(String addrId) {
            this.addrId = addrId;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPayTypeString() {
            return payTypeString;
        }

        public void setPayTypeString(String payTypeString) {
            this.payTypeString = payTypeString;
        }

        public String getStatusString() {
            return statusString;
        }

        public void setStatusString(String statusString) {
            this.statusString = statusString;
        }

        public String getOrderTypeString() {
            return orderTypeString;
        }

        public void setOrderTypeString(String orderTypeString) {
            this.orderTypeString = orderTypeString;
        }

        public String getStatusStr() {
            return statusStr;
        }

        public void setStatusStr(String statusStr) {
            this.statusStr = statusStr;
        }
    }

    //items
    public class Items {
        private int id;

        private int productId;

        private String name;

        private String imageUrl;

        private int qty;

        private String sku;

        private String createdDate;

        private double price;

        private double amount;

        private String itemStatus;

        private String endDate;

        private int orderId;

        private String orders;

        private String itemStatusString;

        public Items() {
        }

        public Items(int id, int productId, String name, String imageUrl, int qty, String sku, String createdDate, double price, double amount, String itemStatus, String endDate, int orderId, String orders, String itemStatusString) {
            this.id = id;
            this.productId = productId;
            this.name = name;
            this.imageUrl = imageUrl;
            this.qty = qty;
            this.sku = sku;
            this.createdDate = createdDate;
            this.price = price;
            this.amount = amount;
            this.itemStatus = itemStatus;
            this.endDate = endDate;
            this.orderId = orderId;
            this.orders = orders;
            this.itemStatusString = itemStatusString;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(String itemStatus) {
            this.itemStatus = itemStatus;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrders() {
            return orders;
        }

        public void setOrders(String orders) {
            this.orders = orders;
        }

        public String getItemStatusString() {
            return itemStatusString;
        }

        public void setItemStatusString(String itemStatusString) {
            this.itemStatusString = itemStatusString;
        }
    }

    //user
    public class User {
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

        private int sex;

        private String city;

        private List<Roles> roles;

        private String createdDate;

        private String selChecked;

        private String createdDateStr;

        public User() {
        }

        public User(int id, String username, String password, String nickName, String email, String mobile, boolean activated, String activationKey, String birthday, String resetPasswordKey, String salt, String referralsMobile, String headerImgUrl, int sex, String city, List<Roles> roles, String createdDate, String selChecked, String createdDateStr) {
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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
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

    //roles
    public class Roles {
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

    public class Address{
        private String country;
        private String province ;
        private String city;
        private String area;
        private String street;
        private String postCode;
        private String contactUserName;
        private String contactTel;

        public Address() {
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
