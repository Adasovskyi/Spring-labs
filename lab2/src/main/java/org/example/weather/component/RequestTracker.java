package org.example.weather.component;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// Анотація @Component, створення біна типу prototype
@Component
@Scope("prototype")
public class RequestTracker {
    private final String instanceId;
    private final String createdAt;

    public RequestTracker() {
        this.instanceId = UUID.randomUUID().toString().substring(0, 8);
        this.createdAt = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
    }

    public String getTrackingInfo() {
        return "Prototype Bean ID: " + instanceId + " (створено о " + createdAt + ")";
    }
}