package org.example.weather.controller;

import org.example.weather.model.WeatherForecast;
import org.example.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // Перегляд прогнозу в заданій локації на вказану дату (користувач)
    @GetMapping("/")
    public String viewWeather(
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {

        List<WeatherForecast> forecasts = weatherService.getForecasts(locationId, date);

        model.addAttribute("locations", weatherService.getAllLocations());
        model.addAttribute("forecasts", forecasts);
        model.addAttribute("selectedLocationId", locationId);
        model.addAttribute("selectedDate", date);
        model.addAttribute("beanInfo", weatherService.getPrototypeDemoInfo());

        return "index";
    }

    // Форма введення інформації про погоду
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("locations", weatherService.getAllLocations());
        model.addAttribute("forecasts", weatherService.getForecasts(null, null));
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("beanInfo", weatherService.getPrototypeDemoInfo());
        return "admin";
    }

    // Додавання нового прогнозу (адміністратор)
    @PostMapping("/admin/forecast")
    public String addForecast(
            @RequestParam Long locationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam int temperature,
            @RequestParam String condition,
            @RequestParam int humidity,
            @RequestParam double windSpeed,
            RedirectAttributes redirectAttributes) {
        try {
            weatherService.addForecast(locationId, date, temperature, condition, humidity, windSpeed);
            redirectAttributes.addFlashAttribute("successMessage", "Прогноз погоди успішно додано!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin";
    }

    // Додавання нової локації (адмінімтратор)
    @PostMapping("/admin/location")
    public String addLocation(
            @RequestParam String city,
            @RequestParam String country,
            RedirectAttributes redirectAttributes) {
        weatherService.addLocation(city, country);
        redirectAttributes.addFlashAttribute("successMessage", "Нову локацію (" + city + ") додано!");
        return "redirect:/admin";
    }
}
