package com.anas.fishday.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("offer")
public class Offer implements Serializable{

    private int id;
    private String link;
    private String text;
    private String image;

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }
}
