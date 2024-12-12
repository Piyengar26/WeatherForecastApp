package com.Piyengar26.WeatherForecastApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Piyengar26.WeatherForecastApp.models.LastUsedStreetAddress;

@Repository
public interface IConfigurationRepository extends JpaRepository<LastUsedStreetAddress, String> {
}