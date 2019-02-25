package com.bouchut.myfirstapplication.JsonClasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxime.esprit on 19/06/2018.
 */

public class FcstDay {
    private String date;

    public String getDate() { return this.date; }

    public void setDate(String date) { this.date = date; }

    private String day_short;

    public String getDayShort() { return this.day_short; }

    public void setDayShort(String day_short) { this.day_short = day_short; }

    private String day_long;

    public String getDayLong() { return this.day_long; }

    public void setDayLong(String day_long) { this.day_long = day_long; }

    private int tmin;

    public int getTmin() { return this.tmin; }

    public void setTmin(int tmin) { this.tmin = tmin; }

    private int tmax;

    public int getTmax() { return this.tmax; }

    public void setTmax(int tmax) { this.tmax = tmax; }

    private String condition;

    public String getCondition() { return this.condition; }

    public void setCondition(String condition) { this.condition = condition; }

    private String condition_key;

    public String getConditionKey() { return this.condition_key; }

    public void setConditionKey(String condition_key) { this.condition_key = condition_key; }

    private String icon;

    public String getIcon() { return this.icon; }

    public void setIcon(String icon) { this.icon = icon; }

    private String icon_big;

    public String getIconBig() { return this.icon_big; }

    public void setIconBig(String icon_big) { this.icon_big = icon_big; }
/*
    private HourlyData hourly_data;

    public HourlyData getHourlyData() { return this.hourly_data; }

    public void setHourlyData(HourlyData hourly_data) { this.hourly_data = hourly_data; }*/




    public FcstDay(JSONObject object) throws JSONException {

        date = object.getString("date");
        day_short = object.getString("day_short");
        day_long = object.getString("day_long");
        tmin = object.getInt("tmin");
        tmax = object.getInt("tmax");
        condition = object.getString("condition");
        condition_key = object.getString("condition_key");
        icon = object.getString("icon");
        icon_big = object.getString("icon_big");
    }

    public String toJsonString(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("date", date);
            jsonObject.put("day_short", day_short);
            jsonObject.put("day_long", day_long);
            jsonObject.put("tmin", tmin);
            jsonObject.put("tmax", tmax);
            jsonObject.put("condition", condition);
            jsonObject.put("condition_key", condition_key);
            jsonObject.put("icon", icon);
            jsonObject.put("icon_big", icon_big);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }
}
