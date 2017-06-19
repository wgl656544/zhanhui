package com.zyrc.exhibit.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/4/28 0028.
 */

public class AddOrderBean {
    private User user;

    private int addrld;

    private List<Items> items;

    public AddOrderBean() {
    }

    public AddOrderBean(User user, int addrld, List<Items> items) {
        this.user = user;
        this.addrld = addrld;
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAddrld() {
        return addrld;
    }

    public void setAddrld(int addrld) {
        this.addrld = addrld;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public class User {
        private int id;

        public User() {
        }

        public User(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public class Items {
        private int productId;

        private int price;

        private int qty;

        public Items() {
        }

        public Items(int productId, int price, int qty) {
            this.productId = productId;
            this.price = price;
            this.qty = qty;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }
    }
}
