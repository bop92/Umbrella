package com.example.admin.umbrella.util;

import com.example.admin.umbrella.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 9/27/2017.
 */

public class CONSTANTS {
    public static final String MY_REPORT_CACHE = "MY_REPORT_CACHE";
    public static final String ZIP_KEY = "ZIP_KEY";
    public static final String UNIT_KEY = "UNIT_KEY";
    public static final String ZIPCODE_VAL = "zipcode";
    public static final String UNIT_VAL = "units";
    public static final String LOAD_REPORT = "LOAD_REPORT";

    public static final String API_KEY = "1abced5fd624551e";
    public static final String INFO_TYPE = "hourly10day";
    public static final String QUERY_TYPE = "geolookup";

    public static HashMap<String, String> myMap = new HashMap<>();
    static {
        myMap.put("chanceflurries", "cloud_hail.xml");
        myMap.put("chancerain", "cloud_rain_alt.xml");
        myMap.put("chancesleet", "cloud_drizzle_alt.xml");
        myMap.put("chancesnow", "cloud_snow_alt.xml");
        myMap.put("chancetstorms", "cloud_lightning_sun.xml");
        myMap.put("clear", "sun.xml");
        myMap.put("cloudy", "cloud.xml");
        myMap.put("flurries", "cloud_hail_alt.xml");
        myMap.put("fog", "cloud_fog.xml");
        myMap.put("hazy", "cloud_fog_alt.xml");
        myMap.put("mostlycloudy", "cloud.xml");
        myMap.put("mostlysunny", "cloud_sun.xml");
        myMap.put("partlycloudy", "cloud_sun.xml");
        myMap.put("partlysunny", "cloud.xml");
        myMap.put("rain", "cloud_rain.xml");
        myMap.put("sleet", "cloud_snow.xml");
        myMap.put("snow", "snowflake.xml");
        myMap.put("sunny", "sun.xml");
        myMap.put("tstorms", "cloud_lightning.xml");
        myMap.put("unknown", "umbrella.xml");
    }

    public static class MessageEvent {
        String Action;
        Object object;

        public MessageEvent(String Action, Object object){
            this.Action = Action;
            this.object = object;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }



        public String getAction() {
            return Action;
        }

        public void setAction(String action) {
            Action = action;
        }
    }
}
