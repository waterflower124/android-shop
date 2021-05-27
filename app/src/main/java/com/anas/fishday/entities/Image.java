package com.anas.fishday.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Anas on 2/25/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Image implements Serializable {

    @JsonProperty("image_url_small")
    private String small;
    @JsonProperty("image_url_medium")
    private String medium;
    @JsonProperty("image_url_large")
    private String large;
    @JsonProperty("image_url_original")
    private String original;

    public String getSmall() {
        return small;
    }

    public String getMedium() {
        return medium;
    }

    public String getLarge() {
        return large;
    }

    public String getOriginal() {
        return original;
    }
}
