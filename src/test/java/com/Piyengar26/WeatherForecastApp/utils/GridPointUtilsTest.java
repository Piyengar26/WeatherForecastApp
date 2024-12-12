package com.Piyengar26.WeatherForecastApp.utils;

import com.Piyengar26.WeatherForecastApp.dtos.GridPoint.GridPoint;
import com.Piyengar26.WeatherForecastApp.dtos.GridPoint.Properties;
import com.Piyengar26.WeatherForecastApp.dtos.Location.Location;
import com.Piyengar26.WeatherForecastApp.utils.GridPointParser;
import com.Piyengar26.WeatherForecastApp.utils.GridPointUtils;
import com.Piyengar26.WeatherForecastApp.clients.GridPointApiClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GridPointUtilsTest {

  @Test
  void testGetGridId_withValidProperties() {
    Properties properties = new Properties();
    properties.setGridId("ABC123");

    GridPoint gridPoint = new GridPoint();
    gridPoint.setProperties(properties);

    String gridId = GridPointUtils.getGridId(gridPoint);

    assertEquals("ABC123", gridId);
  }

  @Test
  void testGetGridId_withNullProperties() {
    GridPoint gridPoint = new GridPoint();

    String gridId = GridPointUtils.getGridId(gridPoint);

    assertNull(gridId);
  }

  @Test
  void testGetGridX_withValidProperties() {
    Properties properties = new Properties();
    properties.setGridX(42);

    GridPoint gridPoint = new GridPoint();
    gridPoint.setProperties(properties);

    int gridX = GridPointUtils.getGridX(gridPoint);

    assertEquals(42, gridX);
  }

  @Test
  void testGetGridX_withNullProperties() {
    GridPoint gridPoint = new GridPoint();

    int gridX = GridPointUtils.getGridX(gridPoint);

    assertEquals(-1, gridX);
  }

  @Test
  void testGetGridY_withValidProperties() {
    Properties properties = new Properties();
    properties.setGridY(84);

    GridPoint gridPoint = new GridPoint();
    gridPoint.setProperties(properties);

    int gridY = GridPointUtils.getGridY(gridPoint);

    assertEquals(84, gridY);
  }

  @Test
  void testGetGridY_withNullProperties() {
    GridPoint gridPoint = new GridPoint();

    int gridY = GridPointUtils.getGridY(gridPoint);

    assertEquals(-1, gridY);
  }

  @Test
  void testGetGridPoint_withValidLocation() {
    GridPointApiClient mockClient = Mockito.mock(GridPointApiClient.class);
    Location mockLocation = Mockito.mock(Location.class);

    String mockResponse = "{ \"properties\": { \"gridId\": \"XYZ\", \"gridX\": 10, \"gridY\": 20 } }";
    when(mockClient.getGridPoint(mockLocation, 0)).thenReturn(Optional.of(mockResponse));

    GridPointParser mockParser = Mockito.mock(GridPointParser.class);
    GridPoint mockGridPoint = new GridPoint();
    when(mockParser.ToGridPointObject(mockResponse)).thenReturn(mockGridPoint);

    GridPointUtils gridPointUtils = new GridPointUtils(mockClient, mockParser);

    Optional<GridPoint> gridPoint = gridPointUtils.getGridPoint(mockLocation);

    assertTrue(gridPoint.isPresent());
    assertEquals(mockGridPoint, gridPoint.get());
  }

  @Test
  void testGetGridPoint_withInvalidLocation() {
    GridPointApiClient mockClient = Mockito.mock(GridPointApiClient.class);
    Location mockLocation = Mockito.mock(Location.class);
    GridPointParser mockParser = Mockito.mock(GridPointParser.class);

    when(mockClient.getGridPoint(mockLocation, 0)).thenReturn(Optional.empty());

    GridPointUtils gridPointUtils = new GridPointUtils(mockClient, mockParser);

    Optional<GridPoint> gridPoint = gridPointUtils.getGridPoint(mockLocation);

    assertFalse(gridPoint.isPresent());
  }
}