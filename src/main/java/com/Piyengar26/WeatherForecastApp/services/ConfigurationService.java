package com.Piyengar26.WeatherForecastApp.services;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Piyengar26.WeatherForecastApp.models.LastUsedStreetAddress;
import com.Piyengar26.WeatherForecastApp.repositories.IConfigurationRepository;

@Service
@SuppressWarnings("FieldMayBeFinal")
public class ConfigurationService implements IConfigurationService {

  @Autowired
  private IConfigurationRepository configurationRepository;

  private static final Logger LOGGER = Logger.getLogger(ConfigurationService.class.getName());

  @Autowired
  public ConfigurationService(IConfigurationRepository configurationRepository) {
    this.configurationRepository = configurationRepository;
  }

  @Override
  public void saveLastUsedStreetAddressId(Long id) {

    Optional<LastUsedStreetAddress> optionalLastUsedStreetAddress = configurationRepository
        .findById(LastUsedStreetAddress.KEY);

    LastUsedStreetAddress lastUsedStreetAddress;
    if (optionalLastUsedStreetAddress.isPresent()) {
      lastUsedStreetAddress = optionalLastUsedStreetAddress.get();
      lastUsedStreetAddress.setLastUsedStreetAddressId(id);
      LOGGER.log(Level.INFO, "Updating last used street address id: {0}", lastUsedStreetAddress.getLastUsedStreetAddressId());
    } else {
      lastUsedStreetAddress = new LastUsedStreetAddress();
      lastUsedStreetAddress.setLastUsedStreetAddressId(id);
      LOGGER.log(Level.INFO, "Creating last used street address id: {0}", lastUsedStreetAddress.getLastUsedStreetAddressId());
    }
    LOGGER.log(Level.INFO, "Saving last used street address id: {0}", lastUsedStreetAddress.getLastUsedStreetAddressId());
    configurationRepository.save(lastUsedStreetAddress);
  }

  @Override
  public Optional<Long> getLastUsedStreetAddressId() {
    Optional<LastUsedStreetAddress> lastUsedStreetAddress = configurationRepository
        .findById(LastUsedStreetAddress.KEY);
    if (lastUsedStreetAddress.isPresent()) {
      Long id = lastUsedStreetAddress.get().getLastUsedStreetAddressId();
      LOGGER.log(Level.INFO, "Getting last used street address id: {0}", id);
      return Optional.of(id);
    } else {
      LOGGER.log(Level.WARNING, "Failed to get last used street address id");
      return Optional.empty();
    }
  }
}
