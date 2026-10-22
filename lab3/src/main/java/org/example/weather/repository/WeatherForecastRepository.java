package org.example.weather.repository;

import org.example.weather.model.Location;
import org.example.weather.model.WeatherForecast;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class WeatherForecastRepository {
    private final Map<Long, WeatherForecast> forecasts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public WeatherForecastRepository(LocationRepository locationRepository) {
        List<Location> locations = locationRepository.findAll();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        if (!locations.isEmpty()) {
            Location kyiv = locations.get(0);
            Location lviv = locations.get(1);
            Location odesa = locations.get(2);

            save(new WeatherForecast(null, kyiv, today, 14, "Мінлива хмарність", 62, 12.5));
            save(new WeatherForecast(null, kyiv, tomorrow, 17, "Сонячно", 55, 9.0));
            save(new WeatherForecast(null, lviv, today, 11, "Невеликий дощ", 80, 18.0));
            save(new WeatherForecast(null, odesa, today, 18, "Ясно та сонячно", 50, 15.0));
        }
    }

    public WeatherForecast save(WeatherForecast forecast) {
        if (forecast.getId() == null) {
            forecast.setId(idGenerator.getAndIncrement());
        }
        forecasts.put(forecast.getId(), forecast);
        return forecast;
    }

    public List<WeatherForecast> findAll() {
        return forecasts.values().stream()
                .sorted(Comparator.comparing(WeatherForecast::getDate))
                .toList();
    }

    public List<WeatherForecast> findByLocationAndDate(Long locationId, LocalDate date) {
        return forecasts.values().stream()
                .filter(f -> f.getLocation().getId().equals(locationId))
                .filter(f -> f.getDate().equals(date))
                .toList();
    }

    public List<WeatherForecast> findByLocation(Long locationId) {
        return forecasts.values().stream()
                .filter(f -> f.getLocation().getId().equals(locationId))
                .sorted(Comparator.comparing(WeatherForecast::getDate))
                .toList();
    }

    public List<WeatherForecast> findByDate(LocalDate date) {
        return forecasts.values().stream()
                .filter(f -> f.getDate().equals(date))
                .toList();
    }

    public void deleteById(Long id) {
        forecasts.remove(id);
    }
}
