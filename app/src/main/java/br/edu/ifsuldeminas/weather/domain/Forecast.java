package br.edu.ifsuldeminas.weather.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by emerson on 5/21/16.
 */

public class Forecast {
    private String dayAAAAMMDD;
    private String weather;
    private String maxTemperature;
    private String minTemperature;
    private String iuv;

    // Getter e Setters

    public String getDayAAAAMMDD() {
        return dayAAAAMMDD;
    }

    public void setDayAAAAMMDD(String dayAAAAMMDD) {
        this.dayAAAAMMDD = dayAAAAMMDD;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getMaxTemperature() {
        return maxTemperature + "ºC";
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getMinTemperature() {

        return minTemperature + "ºC";
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getIuv() {
        return iuv;
    }

    public void setIuv(String iuv) {
        this.iuv = iuv;
    }

    public String getDay() {
        // format 2016-12-30
        return dayAAAAMMDD.substring(8,10);
    }

    public String getMonth() {
        // format 2016-12-30
        return dayAAAAMMDD.substring(5,7);
    }

    public String getYear() {
        // format 2016-12-30
        return dayAAAAMMDD.substring(0,4);
    }

    public int getWeekDay() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = dateFormat.parse(dayAAAAMMDD);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);

            return calendar.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            return 1;
        }
    }
}
