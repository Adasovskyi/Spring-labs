package org.example.weather.service;

import org.example.weather.component.RequestTracker;
import org.example.weather.component.WeatherValidator;
import org.example.weather.model.Location;
import org.example.weather.model.WeatherForecast;
import org.example.weather.repository.LocationRepository;
import org.example.weather.repository.WeatherForecastRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Scope("singleton")
public class WeatherService {

    private final WeatherForecastRepository forecastRepository;

    private LocationRepository locationRepository;

    @Autowired
    private WeatherValidator weatherValidator;

    @Autowired
    private ObjectProvider<RequestTracker> requestTrackerProvider;

    @Autowired
    public WeatherService(WeatherForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Перегляд прогнозу в заданій локації на вказаний час (користувач)
    public List<WeatherForecast> getForecasts(Long locationId, LocalDate date) {
        if (locationId != null && date != null) {
            return forecastRepository.findByLocationAndDate(locationId, date);
        } else if (locationId != null) {
            return forecastRepository.findByLocation(locationId);
        } else if (date != null) {
            // Додано перевірку, якщо вибрана ТІЛЬКИ дата
            return forecastRepository.findByDate(date);
        }
        return forecastRepository.findAll();
    }

    // Введення інформації про погоду (адміністратор)
    public void addForecast(Long locationId, LocalDate date, int temperature,
                            String condition, int humidity, double windSpeed) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Локацію не знайдено!"));

        WeatherForecast forecast = new WeatherForecast(
                null, location, date, temperature, condition, humidity, windSpeed
        );

        if (!weatherValidator.isValidForecast(forecast)) {
            throw new IllegalArgumentException(
                    "Некоректні дані прогнозу! Перевірте температуру (-90..+60 °C) та опис."
            );
        }

        forecastRepository.save(forecast);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public void addLocation(String city, String country) {
        if (city != null && !city.isBlank() && country != null && !country.isBlank()) {
            locationRepository.save(new Location(null, city.trim(), country.trim()));
        }
    }

    // Демонстрація роботи prototype та singleton
    public String getPrototypeDemoInfo() {
        RequestTracker tracker = requestTrackerProvider.getObject();
        return tracker.getTrackingInfo() + " | Singleton Service Hash: " + System.identityHashCode(this);
    }
}
