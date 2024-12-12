/**
 * MIT License
 *
 * Copyright (c) 2024 Scott Davis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.Piyengar26.WeatherForecastApp.utils;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.Piyengar26.WeatherForecastApp.dtos.GridPoint.GridPoint;
import com.Piyengar26.WeatherForecastApp.dtos.GridPoint.Properties;
import com.Piyengar26.WeatherForecastApp.dtos.Location.Location;
import com.Piyengar26.WeatherForecastApp.clients.GridPointApiClient;
import com.Piyengar26.WeatherForecastApp.utils.GridPointUtils;

public class GridPointUtils {

  private static final Logger LOGGER = Logger.getLogger(GridPointUtils.class.getName());

  private final GridPointApiClient apiClient;
  private final GridPointParser gridPointParser;

  public GridPointUtils(GridPointApiClient apiClient, GridPointParser gridPointParser) {
    this.apiClient = apiClient;
    this.gridPointParser = gridPointParser;
  }

  public static String getGridId(GridPoint gridPoint) {
    Properties properties = gridPoint.getProperties();
    if (properties != null) {
      return properties.getGridId();
    }
    return null;
  }

  public static int getGridX(GridPoint gridPoint) {
    Properties properties = gridPoint.getProperties();
    if (properties != null) {
      return properties.getGridX();
    }
    return -1; // Return a default value if properties are not set
  }

  public static int getGridY(GridPoint gridPoint) {
    Properties properties = gridPoint.getProperties();
    if (properties != null) {
      return properties.getGridY();
    }
    return -1; // Return a default value if properties are not set
  }

  // public static Optional<GridPoint> getGridPoint(Location location) {
  public Optional<GridPoint> getGridPoint(Location location) {
    Optional<String> gridPointString = Optional.empty();

    try {
      gridPointString = apiClient.getGridPoint(location, 0);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error getting grid point from client: " + e.getMessage());
      return Optional.empty();
    }

    if (gridPointString.isPresent()) {
      return Optional.of(gridPointParser.ToGridPointObject(gridPointString.get()));
    }

    return Optional.empty();
  }
}
