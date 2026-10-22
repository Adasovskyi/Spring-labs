package org.example.weather.repository;

import org.example.weather.model.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LocationRepository {
    private final Map<Long, Location> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public LocationRepository() {
        save(new Location(null, "Київ", "Україна"));
        save(new Location(null, "Львів", "Україна"));
        save(new Location(null, "Одеса", "Україна"));
        save(new Location(null, "Харків", "Україна"));
    }

    public Location save(Location location) {
        if (location.getId() == null) {
            location.setId(idGenerator.getAndIncrement());
        }
        storage.put(location.getId(), location);
        return location;
    }

    public List<Location> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Location> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }
}