package com.Piyengar26.WeatherForecastApp.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
//import com.fasterxml.jackson.databind.ObjectMapper;

import com.Piyengar26.WeatherForecastApp.dtos.GridPoint.*;
import com.Piyengar26.WeatherForecastApp.models.GridLocation;
import com.Piyengar26.WeatherForecastApp.utils.GridPointParser;

class GridPointParserTest {

  @Test
  void testToGridPointObject_ValidJson() throws Exception {
    String validJson = "{ \"properties\": { \"gridId\": \"ABC\", \"gridX\": 10, \"gridY\": 20 } }";
    GridPointParser parser = new GridPointParser();

    GridPoint result = parser.ToGridPointObject(validJson);
    assertNotNull(result);
    assertEquals("ABC", result.getProperties().getGridId());
    assertEquals(10, result.getProperties().getGridX());
    assertEquals(20, result.getProperties().getGridY());
  }

  @Test
  void testToGridPointObject_InvalidJson() {
    String invalidJson = "Invalid JSON";
    GridPointParser parser = new GridPointParser();

    GridPoint result = parser.ToGridPointObject(invalidJson);
    assertNull(result);
  }

  @Test
  void testToJsonString_ValidObject() throws Exception {
    GridPoint gridPoint = Mockito.mock(GridPoint.class);
    GridPointParser parser = new GridPointParser();

    String json = parser.ToJsonString(gridPoint);
    assertNotNull(json);
  }

  @Test
  void testGetGridLocation() {
    // Mock the Properties object
    Properties mockProperties = Mockito.mock(Properties.class);
    Mockito.when(mockProperties.getGridId()).thenReturn("grid123");
    Mockito.when(mockProperties.getGridX()).thenReturn(10);
    Mockito.when(mockProperties.getGridY()).thenReturn(20);

    // Mock the GridPoint object
    GridPoint mockGridPoint = Mockito.mock(GridPoint.class);
    Mockito.when(mockGridPoint.getProperties()).thenReturn(mockProperties);

    // Instantiate the class and call the method
    GridPointParser gridPointParser = new GridPointParser(); // Replace MyClass with the actual class name
    GridLocation result = gridPointParser.getGridLocation(mockGridPoint);

    // Assert the result
    assertEquals("grid123", result.getGridId());
    assertEquals(10, result.getGridX());
    assertEquals(20, result.getGridY());
  }
}
