package com.Piyengar26.WeatherForecastApp.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.Piyengar26.WeatherForecastApp.models.GridLocation;

class GridLocationTest {

  @Test
  void testDefaultConstructor() {
    GridLocation location = new GridLocation();
    assertNull(location.getGridId());
    assertEquals(0, location.getGridX());
    assertEquals(0, location.getGridY());
  }

  @Test
  void testParameterizedConstructor() {
    GridLocation location = new GridLocation("grid-123", 10, 20);
    assertEquals("grid-123", location.getGridId());
    assertEquals(10, location.getGridX());
    assertEquals(20, location.getGridY());
  }

  @Test
  void testSetAndGetGridId() {
    GridLocation location = new GridLocation();
    location.setGridId("grid-456");
    assertEquals("grid-456", location.getGridId());
  }

  @Test
  void testSetAndGetGridX() {
    GridLocation location = new GridLocation();
    location.setGirdX(15);
    assertEquals(15, location.getGridX());
  }

  @Test
  void testSetAndGetGridY() {
    GridLocation location = new GridLocation();
    location.setGridY(25);
    assertEquals(25, location.getGridY());
  }
}
