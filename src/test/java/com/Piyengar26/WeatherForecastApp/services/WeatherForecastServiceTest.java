package com.Piyengar26.WeatherForecastApp.services;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import com.Piyengar26.WeatherForecastApp.clients.WeatherForecastApiClient;
import com.Piyengar26.WeatherForecastApp.models.GridLocation;
import com.Piyengar26.WeatherForecastApp.models.StreetAddress;
import com.Piyengar26.WeatherForecastApp.services.IStreetAddressService;
import com.Piyengar26.WeatherForecastApp.services.WeatherForecastService;
import com.Piyengar26.WeatherForecastApp.dtos.WeatherForecast.*;
import com.Piyengar26.WeatherForecastApp.utils.WeatherForecastParser;

public class WeatherForecastServiceTest {

    private WeatherForecastService weatherForecastService;

    @Mock
    private IStreetAddressService mockStreetAddressService;

    @Mock
    private WeatherForecastApiClient mockApiClient;

    @Mock
    private WeatherForecastParser mockParser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherForecastService = new WeatherForecastService(mockStreetAddressService, mockApiClient, mockParser);
    }

    @Test
    void testGetWeatherForecastResponsesByIdWhenPresent() {
        // Arrange
        Long id = 1L;
        StreetAddress mockStreetAddress = new StreetAddress();
        mockStreetAddress.setGridLocation(new GridLocation());

        WeatherForecast mockForecast = new WeatherForecast();
        Properties mockProperties = new Properties();
        List<Period> mockPeriods = new ArrayList<>();
        mockPeriods.add(new Period(1, "Day 1", "2024-11-28T08:00:00Z", "2024-11-28T20:00:00Z", true, 70,
                "F", null, null, "10 mph", "NW", "icon.png", "Sunny", "Clear skies"));
        mockProperties.setPeriods(mockPeriods);
        mockForecast.setProperties(mockProperties);

        when(mockStreetAddressService.getStreetAddressById(id)).thenReturn(Optional.of(mockStreetAddress));
        when(mockApiClient.getWeatherForecast(any(GridLocation.class))).thenReturn(Optional.of("mockForecastString"));
        when(mockParser.toWeatherForecastObject(anyString())).thenReturn(mockForecast);

        // Act
        Optional<List<WeatherForecastResponse>> result = weatherForecastService.getWeatherForecastResponsesById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals("Day 1", result.get().get(0).getName());
        assertEquals(70, result.get().get(0).getTemperature());
        verify(mockStreetAddressService, times(1)).getStreetAddressById(id);
        verify(mockApiClient, times(1)).getWeatherForecast(any(GridLocation.class));
        verify(mockParser, times(1)).toWeatherForecastObject(anyString());
    }

    @Test
    void testGetWeatherForecastResponsesByIdWhenNotPresent() {
        // Arrange
        Long id = 1L;
        when(mockStreetAddressService.getStreetAddressById(id)).thenReturn(Optional.empty());

        // Act
        Optional<List<WeatherForecastResponse>> result = weatherForecastService.getWeatherForecastResponsesById(id);

        // Assert
        assertFalse(result.isPresent());
    }
}

