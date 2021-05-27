package com.anas.fishday.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Anas on 3/9/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse implements Serializable{

    @JsonProperty("results")
    private List<GeoAddress> geoAddressList;

    @JsonProperty("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public List<GeoAddress> getGeoAddressList() {
        return geoAddressList;
    }

    public void setGeoAddressList(List<GeoAddress> geoAddressList) {
        this.geoAddressList = geoAddressList;
    }
}
