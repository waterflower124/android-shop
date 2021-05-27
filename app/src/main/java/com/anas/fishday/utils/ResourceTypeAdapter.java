package com.anas.fishday.utils;


import com.anas.fishday.app.FishDayApplication;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Anas on 2/16/2018.
 */

public class ResourceTypeAdapter implements JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null)
            return 0;
        Integer drawable = FishDayApplication.getContext().getResources().getIdentifier(
                json.getAsString(),"drawable", FishDayApplication.getContext().getPackageName());

        return drawable;
    }
}
