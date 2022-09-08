package com.energia.lce.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Basket {

    private BigDecimal totalPrice;

    private BigDecimal payBack;
    private Map<Item, Integer> itemsMap;

    public Basket(){

    }

    public Basket(BigDecimal totalPrice, BigDecimal payBack, Map<Item, Integer> itemsMap) {
        this.totalPrice = totalPrice;
        this.itemsMap = itemsMap;
        this.payBack = payBack;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getPayBack() {
        return payBack;
    }

    public void setPayBack(BigDecimal payBack) {
        this.payBack = payBack;
    }

    public Map<Item, Integer> getItemsMap() {
        return itemsMap;
    }

    public void setItemsList(Map<Item, Integer> itemsMap) {
        this.itemsMap = itemsMap;
    }
}
