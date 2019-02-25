package com.bouchut.myfirstapplication.JsonClasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by maxime.esprit on 19/06/2018.
 */

public class DonneesMeteo
    {
        private CityInfo city_info;

        public CityInfo getCityInfo() { return this.city_info; }

        public void setCityInfo(CityInfo city_info) { this.city_info = city_info; }

        private ForecastInfo forecast_info;

        public ForecastInfo getForecastInfo() { return this.forecast_info; }

        public void setForecastInfo(ForecastInfo forecast_info) { this.forecast_info = forecast_info; }

        private CurrentCondition current_condition;

        public CurrentCondition getCurrentCondition() { return this.current_condition; }

        public void setCurrentCondition(CurrentCondition current_condition) { this.current_condition = current_condition; }

        private FcstDay fcst_day_0;

        public FcstDay getFcstDay0() { return this.fcst_day_0; }

        public void setFcstDay0(FcstDay fcst_day_0) { this.fcst_day_0 = fcst_day_0; }

        private FcstDay fcst_day_1;

        public FcstDay getFcstDay1() { return this.fcst_day_1; }

        public void setFcstDay1(FcstDay fcst_day_1) { this.fcst_day_1 = fcst_day_1; }

        private FcstDay fcst_day_2;

        public FcstDay getFcstDay2() { return this.fcst_day_2; }

        public void setFcstDay2(FcstDay fcst_day_2) { this.fcst_day_2 = fcst_day_2; }

        private FcstDay fcst_day_3;

        public FcstDay getFcstDay3() { return this.fcst_day_3; }

        public void setFcstDay3(FcstDay fcst_day_3) { this.fcst_day_3 = fcst_day_3; }

        private FcstDay fcst_day_4;

        public FcstDay getFcstDay4() { return this.fcst_day_4; }

        public void setFcstDay4(FcstDay fcst_day_4) { this.fcst_day_4 = fcst_day_4; }

        private String m_sJonString;


        public DonneesMeteo(String sJsonString){
            try {
                m_sJonString = sJsonString;

                JSONObject object = new  JSONObject(sJsonString);

                city_info = new CityInfo(new JSONObject(object.getString("city_info")));

                current_condition = new CurrentCondition(new JSONObject(object.getString("current_condition")));

                forecast_info = new ForecastInfo(new JSONObject(object.getString("forecast_info")));

                fcst_day_0 = new FcstDay(new JSONObject(object.getString("fcst_day_0")));

                fcst_day_1 = new FcstDay(new JSONObject(object.getString("fcst_day_1")));

                fcst_day_2 = new FcstDay(new JSONObject(object.getString("fcst_day_2")));

                fcst_day_3 = new FcstDay(new JSONObject(object.getString("fcst_day_3")));

                fcst_day_4 = new FcstDay(new JSONObject(object.getString("fcst_day_4")));
            }
            catch (Exception e) {
                String sMsg = e.getMessage();
            }
        }

        public String toJsonString(){
            return m_sJonString;
        }
}
