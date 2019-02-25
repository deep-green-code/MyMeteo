package com.bouchut.myfirstapplication.JsonClasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxime.esprit on 19/06/2018.
 */

public class CurrentCondition {
    private String date;

    public String getDate() { return this.date; }

    public void setDate(String date) { this.date = date; }

    private String hour;

    public String getHour() { return this.hour; }

    public void setHour(String hour) { this.hour = hour; }

    private int tmp;

    public int getTmp() { return this.tmp; }

    public void setTmp(int tmp) { this.tmp = tmp; }

    private int wnd_spd;

    public int getWndSpd() { return this.wnd_spd; }

    public void setWndSpd(int wnd_spd) { this.wnd_spd = wnd_spd; }

    private int wnd_gust;

    public int getWndGust() { return this.wnd_gust; }

    public void setWndGust(int wnd_gust) { this.wnd_gust = wnd_gust; }

    private String wnd_dir;

    public String getWndDir() { return this.wnd_dir; }

    public void setWndDir(String wnd_dir) { this.wnd_dir = wnd_dir; }

    private int pressure;

    public int getPressure() { return this.pressure; }

    public void setPressure(int pressure) { this.pressure = pressure; }

    private int humidity;

    public int getHumidity() { return this.humidity; }

    public void setHumidity(int humidity) { this.humidity = humidity; }

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


    public CurrentCondition(JSONObject object) throws JSONException {

        date = object.getString("date");
        hour = object.getString("hour");
        tmp = object.getInt("tmp");
        wnd_spd = object.getInt("wnd_spd");
        wnd_gust = object.getInt("wnd_gust");
        wnd_dir = object.getString("wnd_dir");
        pressure = object.getInt("pressure");
        humidity = object.getInt("humidity");
        condition = object.getString("condition");
        condition_key = object.getString("condition_key");
        icon = object.getString("icon");
        icon_big = object.getString("icon_big");
    }
}
