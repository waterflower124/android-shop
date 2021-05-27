package com.anas.fishday.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Created by Anas on 2/26/2018.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "order")
public class OrderNew {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("status")
    private int status;
    @JsonProperty("subtotal")
    private String subTotal;
    @JsonProperty("total")
    private String total;
    @JsonProperty("tax")
    private String tax;
    @JsonProperty("delivery")
    private String delivery;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("cart_items")
    private List<OrderItem> orderItems;

    @JsonProperty("user_phone_number")
    private String userPhoneNumber;
    @JsonProperty("user_full_name")
    private String userFullName;
    @JsonProperty("address_lat")
    private double addressLat;
    @JsonProperty("address_long")
    private double addressLon;
    @JsonProperty("address")
    private String address;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("payment_method")
    private String payment_method;
    @JsonProperty("payment_status")
    private String payment_status;
    @JsonProperty("verification_code")
    private String verification_code;
    @JsonProperty("target_url")
    private String target_url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public double getAddressLat() {
        return addressLat;
    }

    public void setAddressLat(double addressLat) {
        this.addressLat = addressLat;
    }

    public double getAddressLon() {
        return addressLon;
    }

    public void setAddressLon(double addressLon) {
        this.addressLon = addressLon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }
}
