package com.anas.fishday.entities;

//import android.support.annotation.DrawableRes;

import androidx.annotation.DrawableRes;

import com.anas.fishday.utils.ResourceTypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anas on 2/15/2018.
 */

public class ItemMenu {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("icon")
    @Expose
    @JsonAdapter(ResourceTypeAdapter.class)
    @DrawableRes
    private int icon;
    @SerializedName("position")
    @Expose
    private int position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
