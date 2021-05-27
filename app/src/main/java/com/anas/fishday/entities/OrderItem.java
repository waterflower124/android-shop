package com.anas.fishday.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by Anas on 2/26/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("cart_item")
public class OrderItem {
    
    @JsonProperty("id")
    private int id;
    @JsonProperty("order_id")
    private int orderId;
    @JsonProperty("product_id")
    private int productId;
    @JsonProperty("product")
    private Product product;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("unit_price")
    private String unitPrice;
    @JsonProperty("total_price")
    private String totalPrice;
    @JsonProperty("quantity_type")
    private Integer quantityType;
    @JsonProperty("slicing_type")
    private int cuttingWay;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(Integer quantityType) {
        this.quantityType = quantityType;
    }

    public int getCuttingWay() {
        return cuttingWay;
    }

    public void setCuttingWay(int cuttingWay) {
        this.cuttingWay = cuttingWay;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}


