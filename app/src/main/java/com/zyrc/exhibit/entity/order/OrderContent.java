package com.zyrc.exhibit.entity.order;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class OrderContent {
    private String name;
    private String productPic;
    private double price;
    private int qty;

    public OrderContent() {
    }

    public OrderContent(String name, String productPic, int price, int qty) {
        this.name = name;
        this.productPic = productPic;
        this.price = price;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }
}
