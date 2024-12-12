package com.Piyengar26.WeatherForecastApp.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import com.Piyengar26.WeatherForecastApp.models.LastUsedStreetAddress;
import com.Piyengar26.WeatherForecastApp.repositories.IConfigurationRepository;
import com.Piyengar26.WeatherForecastApp.services.ConfigurationService;
import com.Piyengar26.WeatherForecastApp.services.IConfigurationService;

public class ConfigurationServiceTest {

    private IConfigurationService configurationService;

    @Mock
    private IConfigurationRepository mockRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        configurationService = new ConfigurationService(mockRepository);
    }

    @Test
    void testSaveLastUsedStreetAddressIdWhenAddressExists() {
        // Arrange
        Long id = 1L;
        LastUsedStreetAddress existingAddress = new LastUsedStreetAddress();
        when(mockRepository.findById(anyString())).thenReturn(Optional.of(existingAddress));

        // Act
        configurationService.saveLastUsedStreetAddressId(id);

        // Assert
        verify(mockRepository, times(1)).save(existingAddress);
        assertEquals(id, existingAddress.getLastUsedStreetAddressId());
    }

    @Test
    void testSaveLastUsedStreetAddressIdWhenAddressDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(mockRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        configurationService.saveLastUsedStreetAddressId(id);

        // Assert
        verify(mockRepository, times(1)).save(any(LastUsedStreetAddress.class));
    }

    @Test
    void testGetLastUsedStreetAddressIdWhenPresent() {
        // Arrange
        Long id = 1L;
        LastUsedStreetAddress mockAddress = new LastUsedStreetAddress();
        mockAddress.setLastUsedStreetAddressId(id);
        when(mockRepository.findById(anyString())).thenReturn(Optional.of(mockAddress));

        // Act
        Optional<Long> result = configurationService.getLastUsedStreetAddressId();

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get());
    }

    @Test
    void testGetLastUsedStreetAddressIdWhenNotPresent() {
        // Arrange
        when(mockRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        Optional<Long> result = configurationService.getLastUsedStreetAddressId();

        // Assert
        assertFalse(result.isPresent());
    }
}