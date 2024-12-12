package com.Piyengar26.WeatherForecastApp.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import com.Piyengar26.WeatherForecastApp.clients.GridPointApiClient;
import com.Piyengar26.WeatherForecastApp.clients.LocationApiClient;
import com.Piyengar26.WeatherForecastApp.models.StreetAddress;
import com.Piyengar26.WeatherForecastApp.dtos.StreetAddressCreateRequest;
import com.Piyengar26.WeatherForecastApp.dtos.StreetAddressResponse;
import com.Piyengar26.WeatherForecastApp.dtos.Location.*;
import com.Piyengar26.WeatherForecastApp.dtos.GridPoint.*;
import com.Piyengar26.WeatherForecastApp.utils.GridPointParser;
import com.Piyengar26.WeatherForecastApp.repositories.IStreetAddressRepository;
import com.Piyengar26.WeatherForecastApp.services.StreetAddressService;

public class StreetAddressServiceTest {

    private StreetAddressService streetAddressService;

    @Mock
    private IStreetAddressRepository mockRepository;

    @Mock
    private LocationApiClient mockLocationApiClient;

    @Mock
    private GridPointApiClient mockGridPointApiClient;

    @Mock
    private GridPointParser mockGridPointParser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        streetAddressService = new StreetAddressService(mockRepository);
    }

    // @Test
    void testSaveStreetAddress() {
        // Arrange
        StreetAddressCreateRequest request = new StreetAddressCreateRequest();
        StreetAddress mockStreetAddress = new StreetAddress();
        GridPoint mockGridPoint = new GridPoint();

        when(mockRepository.save(any(StreetAddress.class))).thenReturn(mockStreetAddress);
        when(mockLocationApiClient.getLocation(any(StreetAddress.class))).thenReturn(Optional.of("MockLocation"));
        when(mockGridPointApiClient.getGridPoint(any(Location.class), anyInt()))
            .thenReturn(Optional.of("MockGridPoint"));
        when(mockGridPointParser.ToGridPointObject(anyString())).thenReturn(mockGridPoint);

        // Act
        StreetAddressResponse response = streetAddressService.saveStreetAddress(request);

        // Assert
        assertNotNull(response);
        verify(mockRepository, times(1)).save(any(StreetAddress.class));
    }

    @Test
    void testGetStreetAddressById() {
        // Arrange
        StreetAddress mockStreetAddress = new StreetAddress();
        when(mockRepository.findById(anyLong())).thenReturn(Optional.of(mockStreetAddress));

        // Act
        Optional<StreetAddress> result = streetAddressService.getStreetAddressById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockStreetAddress, result.get());
    }

    @Test
    void testGetStreetAddresses() {
        // Arrange
        when(mockRepository.findAll()).thenReturn(List.of(new StreetAddress()));

        // Act
        List<StreetAddressResponse> result = streetAddressService.getStreetAddresses();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // @Test
    void testStreetAddressExists() {
        // Arrange
        StreetAddressCreateRequest request = new StreetAddressCreateRequest();
        when(mockRepository.findByNumberAndStreetAndCityAndStateAndZipCode(
                anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(new StreetAddress());

        // Act
        boolean exists = streetAddressService.streetAddressExists(request);

        // Assert
        assertTrue(exists);
    }

    @Test
    void testDeleteStreetAddress() {
        // Arrange
        Long id = 1L;

        // Act
        streetAddressService.deleteStreetAddress(id);

        // Assert
        verify(mockRepository, times(1)).deleteById(id);
    }

    @Test
    void testCountStreetAddresses() {
        // Arrange
        when(mockRepository.count()).thenReturn(10L);

        // Act
        long count = streetAddressService.countStreetAddresses();

        // Assert
        assertEquals(10L, count);
    }
}