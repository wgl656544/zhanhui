package com.zyrc.exhibit.util;

import com.zyrc.exhibit.entity.order.OrderBean;
import com.zyrc.exhibit.entity.order.OrderContent;
import com.zyrc.exhibit.entity.order.OrderFoot;
import com.zyrc.exhibit.entity.order.OrderHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class OrderDataHealper {
    /**
     * 获取所有订单
     */
    public static List<Object> getDataAfterHandle(List<OrderBean.Data> datas) {
        List<Object> dataList = new ArrayList<>();
        //遍历每张大订单
        for (OrderBean.Data data : datas) {
            OrderHeader header = new OrderHeader();
            header.setOrderNo(data.getOrderNo());//获取订单号
            header.setStatus(data.getStatus());//获取订单状态
            header.setId(data.getId());//订单id
            dataList.add(header);
            //每一张订单里面的每一件商品
            List<OrderBean.Items> itemses = data.getItems();
            //遍历每张订单里面的商品
            for (OrderBean.Items item : itemses) {
                OrderContent content = new OrderContent();
                content.setName(item.getName());
                content.setPrice(item.getPrice());
                content.setQty(item.getQty());
                content.setProductPic(item.getImageUrl());
                dataList.add(content);
            }
            //脚部信息
            OrderFoot foot = new OrderFoot();
            foot.setStatus(data.getStatus());
            foot.setAmount(data.getAmount());
            foot.setOrderId(String.valueOf(data.getId()));
            dataList.add(foot);
        }
        return dataList;
    }

    /**
     * 获取所有未付款订单
     */
    public static List<Object> getPayNotData(List<OrderBean.Data> datas) {
        List<Object> dataList = new ArrayList<>();
        //遍历每张大订单
        for (OrderBean.Data data : datas) {
            if (data.getStatus() < 2) {
                OrderHeader header = new OrderHeader();
                header.setOrderNo(data.getOrderNo());//获取订单号
                header.setStatus(data.getStatus());//获取订单状态
                header.setId(data.getId());//订单id
                dataList.add(header);
                //每一张订单里面的每一件商品
                List<OrderBean.Items> itemses = data.getItems();
                //遍历每张订单里面的商品
                for (OrderBean.Items item : itemses) {
                    OrderContent content = new OrderContent();
                    content.setName(item.getName());
                    content.setPrice(item.getPrice());
                    content.setQty(item.getQty());
                    content.setProductPic(item.getImageUrl());
                    dataList.add(content);
                }
                //脚步信息
                OrderFoot foot = new OrderFoot();
                foot.setStatus(data.getStatus());
                foot.setAmount(data.getAmount());
                foot.setOrderId(String.valueOf(data.getId()));
                dataList.add(foot);
            }
        }
        return dataList;
    }

    /**
     * 获取所有已付款订单
     */
    public static List<Object> getPayOkData(List<OrderBean.Data> datas) {
        List<Object> dataList = new ArrayList<>();
        //遍历每张大订单
        for (OrderBean.Data data : datas) {
            if (data.getStatus() >= 2) {
                OrderHeader header = new OrderHeader();
                header.setOrderNo(data.getOrderNo());//获取订单号
                header.setStatus(data.getStatus());//获取订单状态
                header.setId(data.getId());//订单id
                dataList.add(header);
                //每一张订单里面的每一件商品
                List<OrderBean.Items> itemses = data.getItems();
                //遍历每张订单里面的商品
                for (OrderBean.Items item : itemses) {
                    OrderContent content = new OrderContent();
                    content.setName(item.getName());
                    content.setPrice(item.getPrice());
                    content.setQty(item.getQty());
                    content.setProductPic(item.getImageUrl());
                    dataList.add(content);
                }
                //脚步信息
                OrderFoot foot = new OrderFoot();
                foot.setStatus(data.getStatus());
                foot.setAmount(data.getAmount());
                dataList.add(foot);
            }
        }
        return dataList;
    }

}
