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
package com.Piyengar26.WeatherForecastApp.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.Piyengar26.WeatherForecastApp.models.StreetAddress;

public class StreetAddressTests {

  @Test
  public void testSettersAndGetters() {
    StreetAddress address = new StreetAddress();

    address.setNumber("123");
    assertEquals("123", address.getNumber());

    address.setStreet("Main St");
    assertEquals("Main St", address.getStreet());

    address.setCity("Anytown");
    assertEquals("Anytown", address.getCity());

    address.setState("CA");
    assertEquals("CA", address.getState());

    address.setZipCode("12345");
    assertEquals("12345", address.getZipCode());
  }

  @Test
  public void testFormatAddress() {
    //5200+Sierra+College+Blvd%2C+Rocklin%2C+CA+95677
    StreetAddress address = new StreetAddress("4600", "Silver Hill Rd", "Washington", "DC", "20233");
    String expected = "4600+Silver+Hill+Rd%2C+Washington%2C+DC+20233";
    assertEquals(expected, address.formatAddress());
  }

  @Test
  public void testFormatAddressWithSpaces() {
    StreetAddress address = new StreetAddress("123", "Main Street", "Anytown", "CA", "12345");
    String expected = "123+Main+Street%2C+Anytown%2C+CA+12345";
    assertEquals(expected, address.formatAddress());
  }

  @Test
  public void testFormatAddressWithMultipleSpaces() {
    StreetAddress address = new StreetAddress("789", "Elm   Street", "Othertown", "TX", "67890");
    String expected = "789+Elm+Street%2C+Othertown%2C+TX+67890";
    assertEquals(expected, address.formatAddress());
  }

  @Test
  public void testFormatAddressWithSpacesInStreetAndCity() {
    StreetAddress address = new StreetAddress("789", "Elm Street", "Fort Worth", "TX", "67890");
    String expected = "789+Elm+Street%2C+Fort+Worth%2C+TX+67890";
    assertEquals(expected, address.formatAddress());
  }
}
