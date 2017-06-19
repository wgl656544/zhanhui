package com.zyrc.exhibit.constant;

/**
 * Created by Administrator on 2017/3/1 0001.
 * 服务器接口集合
 */

public class UrlConstant {
    //展会信息ip地址
    public static final String HTTP_URL_EX_IP = "http://www.gztrib.com/exhib/conference/content/simple.html";
    //服务器ip地址
    public static final String HTTP_URL_IP = "http://www.gztrib.com/ysext-server";
    //头像上传地址
    public static final String HTTP_URL_TOUXIANG = "http://www.gztrib.com:8080/ysext-server";
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
    public static final String HTTP_URL_SEARCH_EXHIBITION = "/apicommon/searchExhibit";
    //获取住论坛与分论坛
    public static final String HTTP_URL_GET_ALL_EX = "/apiexhib/findExhibAll";
    //资讯分类接口
    public static final String HTTP_URL_INFO_CATEGORY = "/apicommon/blogCategory?";
    //查询海南所有县市
    public static final String HTTP_URL_INFO_HAINAN_ALL_CITY = "/apicommon/provinceCity";
    //查询资讯
    public static final String HTTP_URL_INFO_SEARCH_BLOG = "/apicommon/searchBlog";
    //查询团购的种类
    public static final String HTTP_URL_TEAM_CATEGORY = "/apicommon/busiType?";
    //搜索团购
    public static final String HTTP_URL_SEARCH_TEAM = "/apicommon/searchTeam?";
    //展会门票类型
    public static final String HTTP_URL_TICKET_TYPE = "/apicommon/busiType?";
    //搜索门票
    public static final String HTTP_URL_SEARCH_TICKET = "/apicommon/searchTicket?";
    //招商类型
    public static final String HTTP_URL_INVITE_TYPE = "/apicommon/busiType";
    //搜索招商
    public static final String HTTP_URL_SEARCH_INVITE = "/apicommon/searchInvite";
    //展会详细信息
    public static final String HTTP_URL_DETAIL_EX = "/apiexhib/findExhib?exhibId=";
    //展会详细页面广告
    public static final String HTTP_URL_DETAIL_EX_ADVERT = "/apiexhib/pageAdsData";
    //展会详细信息(嘉宾，论坛，票务，资讯)
    public static final String HTTP_URL_DETAIL_EX_INFO = "/apiexhib/findExhibHome?";
    //展会嘉宾
    public static final String HTTP_URL_DETAIL_EX_HONOR = "/apiexhib/searchHonor?";
    //展会场馆
    public static final String HTTP_URL_DETAIL_EX_POSITION = "/apiexhib/searchPosition";
    //展会门票
    public static final String HTTP_URL_DETAIL_EX_TICKET = "/apiexhib/searchTicket?";
    //展会资讯
    public static final String HTTP_URL_DETAIL_EX_NEWS = "/apiexhib/searchBlog?page=1&itemPerPage=20&";
    //展会团购
    public static final String HTTP_URL_DETAIL_EX_TEAM = "/apiexhib/searchTeam";
    //展会团购
    public static final String HTTP_URL_DETAIL_EX_PLAN = "/apiexhib/searchEvent";
    //上传图片
    public static final String HTTP_URL_FILE_UPLOAD = "/common//fileUpload";
    //修改用户资料
    public static final String HTTP_URL_USER_UPDATE = "/user/update";
    //查询用户资料
    public static final String HTTP_URL_FIND_USERINFO = "/user/findById";
    //查询用户收货地址
    public static final String HTTP_URL_FIND_USER_ADDRESS = "/userAddress/findByUserId";
    //根据地址id查地址
    public static final String HTTP_URL_FIND_ID_ADDRESS = "/userAddress/findById";
    //保存或修改用户收货地址
    public static final String HTTP_URL_SAVE_USER_ADDRESS = "/userAddress/save";
    //删除用户地址
    public static final String HTTP_URL_DELETE_USER_ADDRESS = "/userAddress/del";
    //查询单个用户地址
    public static final String HTTP_URL_FIND_ONE_USER_ADDRESS = "/userAddress/findById";
    //计算产品总价格
    public static final String HTTP_URL_TOTAL_PRICE = "/product/calcAmount";
    //主论坛页面频道
    public static final String HTTP_URL_CONFENCE_CHANNEL = "/apiexhib/findExhibSetting";
    //保存订单
    public static final String HTTP_URL_SAVE_ORDER = "/orders/save";
    //查询用户订单
    public static final String HTTP_URL_FIND_ORDER = "/orders/pageQuery";
    //提交报名
    public static final String HTTP_URL_COMMIT = "/exhibInviteBusi/save";
    //查询报名
    public static final String HTTP_URL_SEARCH_APPLY = "/exhibInviteBusi/pageQuery";
    //根据关键字查询展会
    public static final String HTTP_URL_SEARCH_EXHIBI_BY_KEY = "/apicommon/searchExhibByKey";
    //支付
    public static final String HTTP_URL_PAY = "/payCommon/payUserOrder";
    //支付宝
    public static final String HTTP_URL_ZFB_PAY = "/aliPay/createPayInfo";
    //记录用户当前所在城市
    public static final String HTTP_URL_CITY_LOG = "/userCityLog/save";
    //获取用户未读推送信息条数
    public static final String HTTP_URL_MSG_COUNT = "/userMessage/notice";
    //查询用户消息
    public static final String HTTP_URL_SEARCH_MSG = "/userMessage/pageQuery";
    //绑定检查
    public static final String HTTP_URL_BIND_CHECK = "/userSocial/bind";
    //绑定第三方账号
    public static final String HTTP_URL_BIND_USER = "/user/mobileLoginBind";
    //展会动态_展会
    public static final String HTTP_URL_EX_TREND_EX = "/apicommon/searchExhibitDync";
    //展会动态_资讯
    public static final String HTTP_URL_EX_TREND_INFO = "/apicommon/searchBlogDync";
    //展会动态_门票
    public static final String HTTP_URL_EX_TREND_TICKET = "/apicommon/searchTicketDync";
    //展会动态_团购
    public static final String HTTP_URL_EX_TREND_TEAM = "/apicommon/searchTeamDync";
    //发现类型查询
    public static final String HTTP_URL_FIND_HAINAN_TYPE = "/apicommon/blogCategory";
    //发现查询
    public static final String HTTP_URL_SEARCH_FIND = "/apicommon/searchBlog";
    //点击收藏
    public static final String HTTP_URL_ADD_COLLECT = "/userEvent/save";
    //查询所有收藏
    public static final String HTTP_URL_SEARCH_COLLECT = "/userEvent/userWish";
    //查询某个是否收藏
    public static final String HTTP_URL_IS_COLLECT = "/userEvent/findUserWish";
    //取消收藏
    public static final String HTTP_URL_CANCEL_COLLECT = "/userEvent/removeUserWish";
    //根据业务对象得到展会id
    public static final String HTTP_URL_GET_EXHIBID_FOR_NAME = "/apiexhib/findExhibByEntity";
    //展会签到
    public static final String HTTP_URL_SIGN_IN = "/exhibUserTicket/save";
    //查询我的展会，我的签到
    public static final String API_GET_MY_EXHIBIT = "/exhibUserTicket/pageQuery";
    //上下轮播动态
    public static final String API_GET_BANNER = "/apicommon/searchDync";


}
