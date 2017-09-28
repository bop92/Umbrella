
package com.example.admin.umbrella.model.HourlyReport;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Airport {

    @SerializedName("station")
    @Expose
    private List<Station> station = null;

    public List<Station> getStation() {
        return station;
    }

    public void setStation(List<Station> station) {
        this.station = station;
    }

}
