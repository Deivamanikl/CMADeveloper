package com.hackerrank.sample.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * .
 */
@Entity
public class Item {
    @Id
    private Long skuId;
    private String productName;
    private String productLabel;
    private int inventoryOnHand;
    private int minQtyReq;
    private double price;

    public Item() {
    }

    public Item(Long skuId, String productName, String productLabel, int inventoryOnHand, int minQtyReq, double price) {
        this.skuId = skuId;
        this.productName = productName;
        this.productLabel = productLabel;
        this.inventoryOnHand = inventoryOnHand;
        this.minQtyReq = minQtyReq;
        this.price = price;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public int getInventoryOnHand() {
        return inventoryOnHand;
    }

    public void setInventoryOnHand(int inventoryOnHand) {
        this.inventoryOnHand = inventoryOnHand;
    }

    public int getMinQtyReq() {
        return minQtyReq;
    }

    public void setMinQtyReq(int minQtyReq) {
        this.minQtyReq = minQtyReq;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "skuId=" + skuId +
                ", productName='" + productName + '\'' +
                ", productLabel='" + productLabel + '\'' +
                ", inventoryOnHand=" + inventoryOnHand +
                ", minQtyReq=" + minQtyReq +
                ", price=" + price +
                '}';
    }
}
