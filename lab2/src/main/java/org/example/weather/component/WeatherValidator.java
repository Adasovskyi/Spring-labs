package org.example.weather.component;

import org.example.weather.model.WeatherForecast;

public class WeatherValidator {
    private final int minTemp;
    private final int maxTemp;

    public WeatherValidator(int minTemp, int maxTemp) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public boolean isValidTemperature(int temp) {
        return temp >= minTemp && temp <= maxTemp;
    }

    public boolean isValidForecast(WeatherForecast forecast) {
        return forecast != null
                && forecast.getDate() != null
                && forecast.getCondition() != null
                && !forecast.getCondition().isBlank()
                && isValidTemperature(forecast.getTemperatureCelsius());
    }
}
