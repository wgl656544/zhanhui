package com.zyrc.exhibit.entity.order;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class OrderHeader {
    private String orderNo;
    private int status;
    private int id;

    public OrderHeader() {
    }

    public OrderHeader(String orderNo, int status, int id) {
        this.orderNo = orderNo;
        this.status = status;
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
