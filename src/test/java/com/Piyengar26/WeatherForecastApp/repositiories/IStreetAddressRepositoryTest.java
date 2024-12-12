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
package com.Piyengar26.WeatherForecastApp.repositiories;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ActiveProfiles;

import com.Piyengar26.WeatherForecastApp.models.GridLocation;
import com.Piyengar26.WeatherForecastApp.models.StreetAddress;
import com.Piyengar26.WeatherForecastApp.repositories.IStreetAddressRepository;

@SpringBootTest
@ActiveProfiles("test")
public class IStreetAddressRepositoryTest {

  @Autowired
  private IStreetAddressRepository repository;

  @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
  @Test
  public void testCreateAndRetrieveStreetAddress() {
    GridLocation gridLocation = new GridLocation("G123", 10, 20);
    StreetAddress address = new StreetAddress("123", "Main St", "Anytown", "CA", "12345");
    address.setGridLocation(gridLocation);
    repository.save(address);

    Optional<StreetAddress> retrieved = repository.findById(address.getId());
    assertTrue(retrieved.isPresent());
    assertEquals("123", retrieved.get().getNumber());
    assertEquals("Main St", retrieved.get().getStreet());
    assertNotNull(retrieved.get().getGridLocation());
    assertEquals("G123", retrieved.get().getGridLocation().getGridId());
  }

  @Test
  public void testUpdateStreetAddress() {
    GridLocation gridLocation = new GridLocation("G123", 10, 20);
    StreetAddress address = new StreetAddress("123", "Main St", "Anytown", "CA", "12345");
    address.setGridLocation(gridLocation);
    repository.save(address);

    address.setStreet("Updated St");
    repository.save(address);

    Optional<StreetAddress> updated = repository.findById(address.getId());
    assertTrue(updated.isPresent());
    assertEquals("Updated St", updated.get().getStreet());
  }

  @Test
  public void testDeleteStreetAddress() {
    GridLocation gridLocation = new GridLocation("G123", 10, 20);
    StreetAddress address = new StreetAddress("123", "Main St", "Anytown", "CA", "12345");
    address.setGridLocation(gridLocation);
    repository.save(address);

    repository.deleteById(address.getId());

    Optional<StreetAddress> deleted = repository.findById(address.getId());
    assertFalse(deleted.isPresent());
  }

  @Test
  public void testFindByNumberAndStreetAndCityAndStateAndZipCode() {
    GridLocation gridLocation = new GridLocation("G123", 10, 20);
    StreetAddress address = new StreetAddress("123", "Main St", "Anytown", "CA", "12345");
    address.setGridLocation(gridLocation);
    repository.save(address);

    StreetAddress found = repository.findByNumberAndStreetAndCityAndStateAndZipCode(
        "123", "Main St", "Anytown", "CA", "12345");

    assertNotNull(found);
    assertEquals("123", found.getNumber());
    assertEquals("Main St", found.getStreet());
  }

  @Test
  public void testFindByNumberAndStreetAndCityAndStateAndZipCodeNotFound() {
    StreetAddress found = repository.findByNumberAndStreetAndCityAndStateAndZipCode(
        "999", "Unknown St", "Nowhere", "ZZ", "99999");

    assertNull(found);
  }

  @Test
  public void testFindByNumberAndStreetAndCityAndStateAndZipCodeWithNulls() {
    StreetAddress found = repository.findByNumberAndStreetAndCityAndStateAndZipCode(
        null, null, null, null, null);

    assertNull(found);
  }
}