package com.ex.administrator.zhanhui.constant;

/**
 * Created by Administrator on 2017/3/1 0001.
 * 服务器接口集合
 */

public class UrlConstant {
    //服务器ip地址
    public static final String HTTP_URL_IP = "http://139.129.202.208:8080/ysext-server";
    //获取验证码接口
    public static final String HTTP_URL_GET_MESSAGE_CODE = "/user/getMessageCode";
    //手机登录接口
    public static final String HTTP_URL_MOBILE_LOGIN = "/user/mobileLogin";
    //用户名登录
    public static final String HTTP_URL_USER_LOGIN = "/user/userLogin";
    //热门城市
    public static final String HTTP_URL_HOT_CITY = "/apicommon/hotCity";
    //展会类型
    public static final String HTTP_URL_FIND_EXHIBITION_TYPE = "/apicommon/busiType?";
    //轮播广告
    public static final String HTTP_URL_PAGE_ADVERS = "/apicommon/pageAdsData";
    //首页业务
    public static final String HTTP_URL_HOME_BUS = "/apicommon/hotBusiDataHome";
    //搜索展会
    public static final String HTTP_URL_SEARCH_EXHIBITION = "/apicommon/searchExhibit?";
    //资讯分类接口
    public static final String HTTP_URL_INFO_CATEGORY = "/apicommon/blogCategory?";
    //查询海南所有县市
    public static final String HTTP_URL_INFO_HAINAN_ALL_CITY = "/apicommon/provinceCity";
    //查询资讯
    public static final String HTTP_URL_INFO_SEARCH_BLOG = "/apicommon/searchBlog";
    //查询团购的种类
    public static final String HTTP_URL_TEAM_CATEGOTY = "/apicommon/busiType?";
    //搜索团购
    public static final String HTTP_URL_SEARCH_TEAM = "/apicommon/searchTeam?";
    //展会门票类型
    public static final String HTTP_URL_TICKET_TYPE = "/apicommon/busiType?";
    //搜索门票
    public static final String HTTP_URL_SEARCH_TICKET = "/apicommon/searchTicket?";
    //展会详细信息
    public static final String HTTP_URL_DETAIL_EX = "/apiexhib/findExhib?exhibId=";
    //展会详细页面广告
    public static final String HTTP_URL_DETAIL_EX_ADVERT = "/apiexhib/pageAdsData?";
    //展会详细信息(嘉宾，论坛，票务，资讯)
    public static final String HTTP_URL_DETAIL_EX_INFO = "/apiexhib/findExhibHome?";
    //展会嘉宾
    public static final String HTTP_URL_DETAIL_EX_HONOR = "/apiexhib/searchHonor?";
    //展会门票
    public static final String HTTP_URL_DETAIL_EX_TICKET = "/apiexhib/searchTicket?page=1&itemPerPage=20&";
    //展会资讯
    public static final String HTTP_URL_DETAIL_EX_NEWS = "/apiexhib/searchBlog?page=1&itemPerPage=20&";
    //上传图片
    public static final String HTTP_URL_FILE_UPLOAD = "/common//fileUpload";
    //修改用户资料
    public static final String HTTP_URL_USER_UPDATE = "/user/update";
    //查询用户收货地址
    public static final String HTTP_URL_FIND_USER_ADDRESS = "/userAddress/findByUserId";
    //保存或修改用户收货地址
    public static final String HTTP_URL_SAVE_USER_ADDRESS = "/userAddress/save";
    //删除用户地址
    public static final String HTTP_URL_DELETE_USER_ADDRESS = "/userAddress/del";
    //查询单个用户地址
    public static final String HTTP_URL_FIND_ONE_USER_ADDRESS = "/userAddress/findById";



}
