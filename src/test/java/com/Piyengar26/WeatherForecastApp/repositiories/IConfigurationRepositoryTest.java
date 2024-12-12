package com.Piyengar26.WeatherForecastApp.repositiories;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.Piyengar26.WeatherForecastApp.models.LastUsedStreetAddress;
import com.Piyengar26.WeatherForecastApp.repositories.IConfigurationRepository;

@DataJpaTest
public class IConfigurationRepositoryTest {

    @Autowired
    private IConfigurationRepository repository;

    @Test
    void testSaveAndFindById() {
        // Arrange
        LastUsedStreetAddress address = new LastUsedStreetAddress();
        address.setLastUsedStreetAddressId(1L);
        repository.save(address);

        // Act
        Optional<LastUsedStreetAddress> result = repository.findById(LastUsedStreetAddress.KEY);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getLastUsedStreetAddressId());
    }
}

