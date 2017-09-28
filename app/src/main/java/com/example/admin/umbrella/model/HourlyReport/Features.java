
package com.example.admin.umbrella.model.HourlyReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Features {

    @SerializedName("hourly10day")
    @Expose
    private Integer hourly10day;
    @SerializedName("geolookup")
    @Expose
    private Integer geolookup;

    public Integer getHourly10day() {
        return hourly10day;
    }

    public void setHourly10day(Integer hourly10day) {
        this.hourly10day = hourly10day;
    }

    public Integer getGeolookup() {
        return geolookup;
    }

    public void setGeolookup(Integer geolookup) {
        this.geolookup = geolookup;
    }

}
