
package com.example.admin.umbrella.model.HourlyReport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearbyWeatherStations {

    @SerializedName("airport")
    @Expose
    private Airport airport;
    @SerializedName("pws")
    @Expose
    private Pws pws;

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public Pws getPws() {
        return pws;
    }

    public void setPws(Pws pws) {
        this.pws = pws;
    }

}
