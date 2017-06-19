package com.zyrc.exhibit.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class OrderInfoUtils {
    /**
     * create the order info. 支付宝 创建订单信息
     */
    public static String getOrderInfo(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject object = jsonObject.getJSONObject("data");
        String partner = object.getString("partner");
        String seller = object.getString("seller");
        String outTradeNo = object.getString("outTradeNo");
        String subject = object.getString("subject");
        String body = object.getString("body");
        String totalFee = object.getString("totalFee");
        String notiyUrl = object.getString("notiyUrl");

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + partner + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + seller + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + outTradeNo + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + totalFee + "\"";

        // 服务器异步通知页面路径
        orderInfo += "¬ify_url=" + "\"" + notiyUrl
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空。这个参数需要注意一下。否则会报错
        // orderInfo += "&return_url=\"\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
}
