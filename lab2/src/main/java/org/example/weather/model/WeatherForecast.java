package org.example.weather.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public class WeatherForecast {
    private Long id;
    private Location location;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private int temperatureCelsius;
    private String condition;
    private int humidityPercent;
    private double windSpeedKmH;

    public WeatherForecast() {
    }

    public WeatherForecast(Long id, Location location, LocalDate date,
                           int temperatureCelsius, String condition,
                           int humidityPercent, double windSpeedKmH) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.temperatureCelsius = temperatureCelsius;
        this.condition = condition;
        this.humidityPercent = humidityPercent;
        this.windSpeedKmH = windSpeedKmH;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(int temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getHumidityPercent() {
        return humidityPercent;
    }

    public void setHumidityPercent(int humidityPercent) {
        this.humidityPercent = humidityPercent;
    }

    public double getWindSpeedKmH() {
        return windSpeedKmH;
    }

    public void setWindSpeedKmH(double windSpeedKmH) {
        this.windSpeedKmH = windSpeedKmH;
    }
}
