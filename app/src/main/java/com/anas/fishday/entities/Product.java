package com.anas.fishday.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product extends BaseEntity{

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("desc")
    private String description;
    @JsonProperty("images")
    private List<Image> images;
    @JsonProperty("kilo_price")
    private String kiloPrice;
    @JsonProperty("piece_price")
    private String piecePrice;
    @JsonProperty("promotion_kilo_price")
    private String promotionKiloPrice;
    @JsonProperty("promotion_piece_price")
    private String promotionPiecePrice;
    @JsonProperty("category")
    private Category category;
    @JsonProperty("quantity_type")
    private String quantity;
    @JsonProperty("quantity")
    private int quantity_count;

    private String real_Kiloprice;
    private String origin_Kiloprice;
    private String real_Pieceprice;
    private String origin_Pieceprice;

    public int getId() {
        return id;
    }

    public String getKiloPrice() {
        return kiloPrice;
    }

    public String getPiecePrice() {
        return piecePrice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getPromotionKiloPrice() {
        return promotionKiloPrice;
    }

    public String getPromotionPiecePrice() {
        return promotionPiecePrice;
    }

    public Category getCategory() {
        return category;
    }

    public String getQuantity() {
        return quantity;
    }

    public int getQuantity_count() {
        return quantity_count;
    }

    public String getReal_Kiloprice() {
        return real_Kiloprice;
    }

    public void setReal_Kiloprice(String real_Kiloprice) {
        this.real_Kiloprice = real_Kiloprice;
    }

    public String getOrigin_Kiloprice() {
        return origin_Kiloprice;
    }

    public void setOrigin_Kiloprice(String origin_Kiloprice) {
        this.origin_Kiloprice = origin_Kiloprice;
    }

    public String getReal_Pieceprice() {
        return real_Pieceprice;
    }

    public void setReal_Pieceprice(String real_Pieceprice) {
        this.real_Pieceprice = real_Pieceprice;
    }

    public String getOrigin_Pieceprice() {
        return origin_Pieceprice;
    }

    public void setOrigin_Pieceprice(String origin_Pieceprice) {
        this.origin_Pieceprice = origin_Pieceprice;
    }
}
