package com.Piyengar26.WeatherForecastApp.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.Piyengar26.WeatherForecastApp.models.LastUsedStreetAddress;

class LastUsedStreetAddressTest {

  @Test
  void testDefaultConstructor() {
    LastUsedStreetAddress address = new LastUsedStreetAddress();
    assertEquals("LastUsedStreetAddressId", address.getId());
    assertNull(address.getLastUsedStreetAddressId());
  }

  @Test
  void testParameterizedConstructor() {
    LastUsedStreetAddress address = new LastUsedStreetAddress(12345L);
    assertEquals("LastUsedStreetAddressId", address.getId());
    assertEquals(12345L, address.getLastUsedStreetAddressId());
  }

  @Test
  void testSetAndGetLastUsedStreetAddressId() {
    LastUsedStreetAddress address = new LastUsedStreetAddress();
    address.setLastUsedStreetAddressId(67890L);
    assertEquals(67890L, address.getLastUsedStreetAddressId());
  }

  @Test
  void testSetIdShouldNotChangeKey() {
    LastUsedStreetAddress address = new LastUsedStreetAddress();
    address.setId("AnotherKey");
    assertEquals("LastUsedStreetAddressId", address.getId());
  }
}
