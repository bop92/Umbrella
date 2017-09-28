
package com.example.admin.umbrella.model.HourlyReport;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pws {

    @SerializedName("station")
    @Expose
    private List<Station_> station = null;

    public List<Station_> getStation() {
        return station;
    }

    public void setStation(List<Station_> station) {
        this.station = station;
    }

}
