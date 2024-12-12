package com.Piyengar26.WeatherForecastApp.models;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;

@Entity
public class LastUsedStreetAddress {
  public static final String KEY = "LastUsedStreetAddressId";

  @Id
  private String id;

  private Long lastUsedStreetAddressId;

  public LastUsedStreetAddress() {
    this.id = KEY;
  }

  public LastUsedStreetAddress(Long lastUsedStreetAddressId) {
    this.id = KEY;
    this.lastUsedStreetAddressId = lastUsedStreetAddressId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = KEY;
  }

  public Long getLastUsedStreetAddressId() {
    return lastUsedStreetAddressId;
  }

  public void setLastUsedStreetAddressId(Long lastUsedStreetAddressId) {
    this.lastUsedStreetAddressId = lastUsedStreetAddressId;
  }

}
