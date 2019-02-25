package com.bouchut.myfirstapplication.JsonClasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxime.esprit on 19/06/2018.
 */

public class ForecastInfo {
    private String latitude;

    public String getLatitude() { return this.latitude; }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    private String longitude;

    public String getLongitude() { return this.longitude; }

    public void setLongitude(String longitude) { this.longitude = longitude; }

    private String elevation;

    public String getElevation() { return this.elevation; }

    public void setElevation(String elevation) { this.elevation = elevation; }


    public ForecastInfo(JSONObject object) throws JSONException {

        latitude = object.getString("latitude");
        longitude = object.getString("longitude");
        elevation = object.getString("elevation");
    }
}
