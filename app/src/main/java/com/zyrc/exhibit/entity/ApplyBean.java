package com.zyrc.exhibit.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class ApplyBean {
    private int totalPages;

    private int totalElements;

    private String successed;

    private String code;

    private String message;

    private List<Data> data ;

    public ApplyBean() {
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
    public class Data {
        private int id;

        private int exhibId;

        private int typeTag;

        private String busiType;

        private String busiData;

        private String userJob;

        private String attendUserCount;

        private String description;

        private String username;

        private String mobile;

        private String email;

        private Exhibit exhibit;

        private String entityId;

        private String entityName;

        private Corp corp;

        private String createdDate;

        private String startDate;

        private String endDate;

        private int userId;

        private int status;

        private boolean selChecked;

        private String busiTypeStr;

        public Data() {
        }

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setExhibId(int exhibId){
            this.exhibId = exhibId;
        }
        public int getExhibId(){
            return this.exhibId;
        }
        public void setTypeTag(int typeTag){
            this.typeTag = typeTag;
        }
        public int getTypeTag(){
            return this.typeTag;
        }
        public void setBusiType(String busiType){
            this.busiType = busiType;
        }
        public String getBusiType(){
            return this.busiType;
        }
        public void setBusiData(String busiData){
            this.busiData = busiData;
        }
        public String getBusiData(){
            return this.busiData;
        }
        public void setUserJob(String userJob){
            this.userJob = userJob;
        }
        public String getUserJob(){
            return this.userJob;
        }
        public void setAttendUserCount(String attendUserCount){
            this.attendUserCount = attendUserCount;
        }
        public String getAttendUserCount(){
            return this.attendUserCount;
        }
        public void setDescription(String description){
            this.description = description;
        }
        public String getDescription(){
            return this.description;
        }
        public void setUsername(String username){
            this.username = username;
        }
        public String getUsername(){
            return this.username;
        }
        public void setMobile(String mobile){
            this.mobile = mobile;
        }
        public String getMobile(){
            return this.mobile;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public String getEmail(){
            return this.email;
        }
        public void setExhibit(Exhibit exhibit){
            this.exhibit = exhibit;
        }
        public Exhibit getExhibit(){
            return this.exhibit;
        }
        public void setEntityId(String entityId){
            this.entityId = entityId;
        }
        public String getEntityId(){
            return this.entityId;
        }
        public void setEntityName(String entityName){
            this.entityName = entityName;
        }
        public String getEntityName(){
            return this.entityName;
        }
        public void setCorp(Corp corp){
            this.corp = corp;
        }
        public Corp getCorp(){
            return this.corp;
        }
        public void setCreatedDate(String createdDate){
            this.createdDate = createdDate;
        }
        public String getCreatedDate(){
            return this.createdDate;
        }
        public void setStartDate(String startDate){
            this.startDate = startDate;
        }
        public String getStartDate(){
            return this.startDate;
        }
        public void setEndDate(String endDate){
            this.endDate = endDate;
        }
        public String getEndDate(){
            return this.endDate;
        }
        public void setUserId(int userId){
            this.userId = userId;
        }
        public int getUserId(){
            return this.userId;
        }
        public void setStatus(int status){
            this.status = status;
        }
        public int getStatus(){
            return this.status;
        }
        public void setSelChecked(boolean selChecked){
            this.selChecked = selChecked;
        }
        public boolean getSelChecked(){
            return this.selChecked;
        }
        public void setBusiTypeStr(String busiTypeStr){
            this.busiTypeStr = busiTypeStr;
        }
        public String getBusiTypeStr(){
            return this.busiTypeStr;
        }
    }
    public class Exhibit {
        private int id;

        private BusiType busiType;

        private Corp corp;

        private String name;

        private String description;

        private String imageUrl;

        private String logo;

        private double longitude;

        private double latitude;

        private String imageUrlWidth;

        private String content;

        private Address address;

        private String email;

        private String startDate;

        private String endDate;

        private String minuteOffer;

        private String minuteReal;

        private int hotStatus;

        private String menuOrder;

        private String layout;

        private String parentId;

        private int statusMain;

        private String exhibPosition;

        private String tel;

        private String telBusi;

        private String contactBusi;

        private String countOffer;

        private String countAttend;

        private String countBook;

        private int status;

        private int countView;

        private String countWish;

        private String createdDate;

        private String modified;

        private boolean selChecked;

        private String statusStr;

        private String imageUrlMid;

        private String imageUrlMin;

        private String statusMainStr;

        private String hotStatusStr;

        public Exhibit() {
        }

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setBusiType(BusiType busiType){
            this.busiType = busiType;
        }
        public BusiType getBusiType(){
            return this.busiType;
        }
        public void setCorp(Corp corp){
            this.corp = corp;
        }
        public Corp getCorp(){
            return this.corp;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setDescription(String description){
            this.description = description;
        }
        public String getDescription(){
            return this.description;
        }
        public void setImageUrl(String imageUrl){
            this.imageUrl = imageUrl;
        }
        public String getImageUrl(){
            return this.imageUrl;
        }
        public void setLogo(String logo){
            this.logo = logo;
        }
        public String getLogo(){
            return this.logo;
        }
        public void setLongitude(double longitude){
            this.longitude = longitude;
        }
        public double getLongitude(){
            return this.longitude;
        }
        public void setLatitude(double latitude){
            this.latitude = latitude;
        }
        public double getLatitude(){
            return this.latitude;
        }
        public void setImageUrlWidth(String imageUrlWidth){
            this.imageUrlWidth = imageUrlWidth;
        }
        public String getImageUrlWidth(){
            return this.imageUrlWidth;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setAddress(Address address){
            this.address = address;
        }
        public Address getAddress(){
            return this.address;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public String getEmail(){
            return this.email;
        }
        public void setStartDate(String startDate){
            this.startDate = startDate;
        }
        public String getStartDate(){
            return this.startDate;
        }
        public void setEndDate(String endDate){
            this.endDate = endDate;
        }
        public String getEndDate(){
            return this.endDate;
        }
        public void setMinuteOffer(String minuteOffer){
            this.minuteOffer = minuteOffer;
        }
        public String getMinuteOffer(){
            return this.minuteOffer;
        }
        public void setMinuteReal(String minuteReal){
            this.minuteReal = minuteReal;
        }
        public String getMinuteReal(){
            return this.minuteReal;
        }
        public void setHotStatus(int hotStatus){
            this.hotStatus = hotStatus;
        }
        public int getHotStatus(){
            return this.hotStatus;
        }
        public void setMenuOrder(String menuOrder){
            this.menuOrder = menuOrder;
        }
        public String getMenuOrder(){
            return this.menuOrder;
        }
        public void setLayout(String layout){
            this.layout = layout;
        }
        public String getLayout(){
            return this.layout;
        }
        public void setParentId(String parentId){
            this.parentId = parentId;
        }
        public String getParentId(){
            return this.parentId;
        }
        public void setStatusMain(int statusMain){
            this.statusMain = statusMain;
        }
        public int getStatusMain(){
            return this.statusMain;
        }
        public void setExhibPosition(String exhibPosition){
            this.exhibPosition = exhibPosition;
        }
        public String getExhibPosition(){
            return this.exhibPosition;
        }
        public void setTel(String tel){
            this.tel = tel;
        }
        public String getTel(){
            return this.tel;
        }
        public void setTelBusi(String telBusi){
            this.telBusi = telBusi;
        }
        public String getTelBusi(){
            return this.telBusi;
        }
        public void setContactBusi(String contactBusi){
            this.contactBusi = contactBusi;
        }
        public String getContactBusi(){
            return this.contactBusi;
        }
        public void setCountOffer(String countOffer){
            this.countOffer = countOffer;
        }
        public String getCountOffer(){
            return this.countOffer;
        }
        public void setCountAttend(String countAttend){
            this.countAttend = countAttend;
        }
        public String getCountAttend(){
            return this.countAttend;
        }
        public void setCountBook(String countBook){
            this.countBook = countBook;
        }
        public String getCountBook(){
            return this.countBook;
        }
        public void setStatus(int status){
            this.status = status;
        }
        public int getStatus(){
            return this.status;
        }
        public void setCountView(int countView){
            this.countView = countView;
        }
        public int getCountView(){
            return this.countView;
        }
        public void setCountWish(String countWish){
            this.countWish = countWish;
        }
        public String getCountWish(){
            return this.countWish;
        }
        public void setCreatedDate(String createdDate){
            this.createdDate = createdDate;
        }
        public String getCreatedDate(){
            return this.createdDate;
        }
        public void setModified(String modified){
            this.modified = modified;
        }
        public String getModified(){
            return this.modified;
        }
        public void setSelChecked(boolean selChecked){
            this.selChecked = selChecked;
        }
        public boolean getSelChecked(){
            return this.selChecked;
        }
        public void setStatusStr(String statusStr){
            this.statusStr = statusStr;
        }
        public String getStatusStr(){
            return this.statusStr;
        }
        public void setImageUrlMid(String imageUrlMid){
            this.imageUrlMid = imageUrlMid;
        }
        public String getImageUrlMid(){
            return this.imageUrlMid;
        }
        public void setImageUrlMin(String imageUrlMin){
            this.imageUrlMin = imageUrlMin;
        }
        public String getImageUrlMin(){
            return this.imageUrlMin;
        }
        public void setStatusMainStr(String statusMainStr){
            this.statusMainStr = statusMainStr;
        }
        public String getStatusMainStr(){
            return this.statusMainStr;
        }
        public void setHotStatusStr(String hotStatusStr){
            this.hotStatusStr = hotStatusStr;
        }
        public String getHotStatusStr(){
            return this.hotStatusStr;
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

        public void setCountry(String country){
            this.country = country;
        }
        public String getCountry(){
            return this.country;
        }
        public void setProvince(String province){
            this.province = province;
        }
        public String getProvince(){
            return this.province;
        }
        public void setCity(String city){
            this.city = city;
        }
        public String getCity(){
            return this.city;
        }
        public void setArea(String area){
            this.area = area;
        }
        public String getArea(){
            return this.area;
        }
        public void setStreet(String street){
            this.street = street;
        }
        public String getStreet(){
            return this.street;
        }
        public void setPostCode(String postCode){
            this.postCode = postCode;
        }
        public String getPostCode(){
            return this.postCode;
        }
        public void setContactUserName(String contactUserName){
            this.contactUserName = contactUserName;
        }
        public String getContactUserName(){
            return this.contactUserName;
        }
        public void setContactTel(String contactTel){
            this.contactTel = contactTel;
        }
        public String getContactTel(){
            return this.contactTel;
        }
    }

    public class Corp {
        private int id;

        private String name;

        private String corpType;

        private String description;

        private BusiType busiType;

        private String imageUrl;

        private String logo;

        private String code;

        private String codeImageUrl;

        private String email;

        private String tel;

        private Address address;

        private String content;

        private String statusIdent;

        private String statusPurch;

        private String statusExhib;

        private String statusService;

        private String createdDate;

        private String modified;

        private boolean selChecked;

        private String userId;

        private String imageUrlMid;

        private String imageUrlMin;

        private String corpTypeStr;

        public Corp() {
        }

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setCorpType(String corpType){
            this.corpType = corpType;
        }
        public String getCorpType(){
            return this.corpType;
        }
        public void setDescription(String description){
            this.description = description;
        }
        public String getDescription(){
            return this.description;
        }
        public void setBusiType(BusiType busiType){
            this.busiType = busiType;
        }
        public BusiType getBusiType(){
            return this.busiType;
        }
        public void setImageUrl(String imageUrl){
            this.imageUrl = imageUrl;
        }
        public String getImageUrl(){
            return this.imageUrl;
        }
        public void setLogo(String logo){
            this.logo = logo;
        }
        public String getLogo(){
            return this.logo;
        }
        public void setCode(String code){
            this.code = code;
        }
        public String getCode(){
            return this.code;
        }
        public void setCodeImageUrl(String codeImageUrl){
            this.codeImageUrl = codeImageUrl;
        }
        public String getCodeImageUrl(){
            return this.codeImageUrl;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public String getEmail(){
            return this.email;
        }
        public void setTel(String tel){
            this.tel = tel;
        }
        public String getTel(){
            return this.tel;
        }
        public void setAddress(Address address){
            this.address = address;
        }
        public Address getAddress(){
            return this.address;
        }
        public void setContent(String content){
            this.content = content;
        }
        public String getContent(){
            return this.content;
        }
        public void setStatusIdent(String statusIdent){
            this.statusIdent = statusIdent;
        }
        public String getStatusIdent(){
            return this.statusIdent;
        }
        public void setStatusPurch(String statusPurch){
            this.statusPurch = statusPurch;
        }
        public String getStatusPurch(){
            return this.statusPurch;
        }
        public void setStatusExhib(String statusExhib){
            this.statusExhib = statusExhib;
        }
        public String getStatusExhib(){
            return this.statusExhib;
        }
        public void setStatusService(String statusService){
            this.statusService = statusService;
        }
        public String getStatusService(){
            return this.statusService;
        }
        public void setCreatedDate(String createdDate){
            this.createdDate = createdDate;
        }
        public String getCreatedDate(){
            return this.createdDate;
        }
        public void setModified(String modified){
            this.modified = modified;
        }
        public String getModified(){
            return this.modified;
        }
        public void setSelChecked(boolean selChecked){
            this.selChecked = selChecked;
        }
        public boolean getSelChecked(){
            return this.selChecked;
        }
        public void setUserId(String userId){
            this.userId = userId;
        }
        public String getUserId(){
            return this.userId;
        }
        public void setImageUrlMid(String imageUrlMid){
            this.imageUrlMid = imageUrlMid;
        }
        public String getImageUrlMid(){
            return this.imageUrlMid;
        }
        public void setImageUrlMin(String imageUrlMin){
            this.imageUrlMin = imageUrlMin;
        }
        public String getImageUrlMin(){
            return this.imageUrlMin;
        }
        public void setCorpTypeStr(String corpTypeStr){
            this.corpTypeStr = corpTypeStr;
        }
        public String getCorpTypeStr(){
            return this.corpTypeStr;
        }
    }

    public class BusiType {
        private int id;

        private String name;

        private String imageUrl;

        private String code;

        private String pageTag;

        private int level;

        private String menuOrder;

        private int parentId;

        private boolean selChecked;

        private String levelName;

        public BusiType() {
        }

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setImageUrl(String imageUrl){
            this.imageUrl = imageUrl;
        }
        public String getImageUrl(){
            return this.imageUrl;
        }
        public void setCode(String code){
            this.code = code;
        }
        public String getCode(){
            return this.code;
        }
        public void setPageTag(String pageTag){
            this.pageTag = pageTag;
        }
        public String getPageTag(){
            return this.pageTag;
        }
        public void setLevel(int level){
            this.level = level;
        }
        public int getLevel(){
            return this.level;
        }
        public void setMenuOrder(String menuOrder){
            this.menuOrder = menuOrder;
        }
        public String getMenuOrder(){
            return this.menuOrder;
        }
        public void setParentId(int parentId){
            this.parentId = parentId;
        }
        public int getParentId(){
            return this.parentId;
        }
        public void setSelChecked(boolean selChecked){
            this.selChecked = selChecked;
        }
        public boolean getSelChecked(){
            return this.selChecked;
        }
        public void setLevelName(String levelName){
            this.levelName = levelName;
        }
        public String getLevelName(){
            return this.levelName;
        }
    }

}
