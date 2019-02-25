package com.bouchut.myfirstapplication.JsonClasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxime.esprit on 19/06/2018.
 */

public class CityInfo {
    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private String country;

    public String getCountry() { return this.country; }

    public void setCountry(String country) { this.country = country; }

    private String latitude;

    public String getLatitude() { return this.latitude; }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    private String longitude;

    public String getLongitude() { return this.longitude; }

    public void setLongitude(String longitude) { this.longitude = longitude; }

    private String elevation;

    public String getElevation() { return this.elevation; }

    public void setElevation(String elevation) { this.elevation = elevation; }

    private String sunrise;

    public String getSunrise() { return this.sunrise; }

    public void setSunrise(String sunrise) { this.sunrise = sunrise; }

    private String sunset;

    public String getSunset() { return this.sunset; }

    public void setSunset(String sunset) { this.sunset = sunset; }


    public CityInfo(JSONObject object) throws JSONException {

        name = object.getString("name");
        country = object.getString("country");
        latitude = object.getString("latitude");
        longitude = object.getString("longitude");
        elevation = object.getString("elevation");
        sunrise = object.getString("sunrise");
        sunset = object.getString("sunset");
    }

    public String toJsonString(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("country", country);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            jsonObject.put("elevation", elevation);
            jsonObject.put("sunrise", sunrise);
            jsonObject.put("sunset", sunset);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }
}
