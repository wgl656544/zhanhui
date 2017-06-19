package com.zyrc.exhibit.entity.order;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class OrderFoot {
    private int status;
    private double amount;
    private String orderId;

    public OrderFoot() {
    }

    public OrderFoot(int status, int amount) {
        this.status = status;
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
