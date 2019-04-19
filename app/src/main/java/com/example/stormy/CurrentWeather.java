package com.example.stormy;
// Author Ryan Dsouza 
// Contact - authenticryanis@gmail.com  

// Current weather model

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class CurrentWeather {

    private String locationLabel;
    private String icon;
    private String summary;
    private String formattedTime;
    private long time;
    private double temperature;
    private double humidity;
    private double precipitationChance;


    public CurrentWeather(String locationLabel, String icon, long time, double temperature, double humidity, double precipitationChance, String summary) {
        this.setLocationLabel(locationLabel);
        this.setIcon(icon);
        this.setTime(time);
        this.setTemperature(temperature);
        this.setHumidity(humidity);
        this.setPrecipitationChance(precipitationChance);
        this.setSummary(summary);
    }

    public CurrentWeather() {

    }

    public String getFormattedTime() {
        if (formattedTime == null) this.setFormattedTime(getLocationLabel());

        return formattedTime;
    }

    public void setFormattedTime(String locationLabel) {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");

        formatter.setTimeZone(TimeZone.getTimeZone(locationLabel));

        // Date constructor take time in milliseconds and getTime returns in seconds
        Date dateTime = new Date(getTime() * 1000);
        this.formattedTime = formatter.format(dateTime);
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPrecipitationChance() {
        return precipitationChance;
    }

    public void setPrecipitationChance(double precipitationChance) {
        this.precipitationChance = precipitationChance;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


//    following values: clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
    public int getIconId() {

        int iconId = R.drawable.clear_day;

        switch (icon){
            case "clear-day":
                iconId = R.drawable.clear_day;
                break;
            case "clear-night":
                iconId = R.drawable.clear_night;
                break;
            case "rain":
                iconId = R.drawable.rain;
                break;
            case "snow":
                iconId = R.drawable.snow;
                break;
            case "sleet":
                iconId = R.drawable.sleet;
                break;
            case "wind":
                iconId = R.drawable.wind;
                break;
            case "fog":
                iconId = R.drawable.fog;
                break;
            case "cloudy":
                iconId = R.drawable.cloudy;
                break;
            case "partly-cloudy-day":
                iconId = R.drawable.partly_cloudy;
                break;
            case "partly-cloudy-night":
                iconId = R.drawable.cloudy_night;
                break;
        }

        return iconId;
    }
}
