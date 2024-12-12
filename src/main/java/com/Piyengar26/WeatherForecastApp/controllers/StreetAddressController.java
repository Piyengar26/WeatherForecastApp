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
package com.Piyengar26.WeatherForecastApp.controllers;

import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Piyengar26.WeatherForecastApp.dtos.LastUsedStreetAddressResponse;
import com.Piyengar26.WeatherForecastApp.dtos.LastUsedStreetAddressSelectRequest;
import com.Piyengar26.WeatherForecastApp.dtos.StreetAddressCreateRequest;
import com.Piyengar26.WeatherForecastApp.dtos.StreetAddressResponse;
import com.Piyengar26.WeatherForecastApp.exceptions.DuplicateResourceException;
import com.Piyengar26.WeatherForecastApp.exceptions.ResourceNotFoundException;
import com.Piyengar26.WeatherForecastApp.services.IConfigurationService;
import com.Piyengar26.WeatherForecastApp.services.IStreetAddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/street-address")
public class StreetAddressController {

  @Autowired
  private IStreetAddressService streetAddressService;

  @Autowired
  private IConfigurationService configurationService;

   private static final Logger LOGGER = Logger.getLogger(StreetAddressController.class.getName());

  @GetMapping("/")
  public ResponseEntity<List<StreetAddressResponse>> getAllStreetAddresses() {
    return ResponseEntity.ok(streetAddressService.getStreetAddresses());
  }

  @GetMapping("/last-used-street-address")
  public ResponseEntity<LastUsedStreetAddressResponse> getLastUsedStreetAddress() {
    Long lastUsedStreetAddressId = configurationService.getLastUsedStreetAddressId()
        .orElseThrow(() -> new ResourceNotFoundException("Last used street address not found"));

    return ResponseEntity.ok(new LastUsedStreetAddressResponse(lastUsedStreetAddressId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<StreetAddressResponse> getStreetAddressById(@PathVariable Long id) {
    StreetAddressResponse streetAddressResponse = streetAddressService.getStreetAddressResponseById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Street address not found for id: " + id));


    return ResponseEntity.ok(streetAddressResponse);
  }

  @PostMapping("/")
  public ResponseEntity<StreetAddressResponse> setStreetAddress(
      @Valid @RequestBody StreetAddressCreateRequest streetAddressCreateRequest) {

    if (streetAddressService.streetAddressExists(streetAddressCreateRequest)) {
      LOGGER.log(Level.WARNING, "Address already exists {0}", streetAddressCreateRequest);
      throw new DuplicateResourceException("Address already exists");
    }

    StreetAddressResponse streetAddressResponse = streetAddressService.saveStreetAddress(streetAddressCreateRequest);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(streetAddressResponse.getId())
        .toUri();

    configurationService.saveLastUsedStreetAddressId(streetAddressResponse.getId());

    return ResponseEntity.created(location).body(streetAddressResponse);
  }

  @PostMapping("/last-used-street-address")
  public ResponseEntity<Long> setLastUsedStreetAddressId(@RequestBody LastUsedStreetAddressSelectRequest lastUsedStreetAddressSelectRequest) {
    Long lastUsedStreetAddressId = lastUsedStreetAddressSelectRequest.getLastUsedStreetAddressId();
    LOGGER.log(Level.INFO, "Set last used street address id: " + lastUsedStreetAddressId);
    configurationService.saveLastUsedStreetAddressId(lastUsedStreetAddressId);
    return ResponseEntity.ok(lastUsedStreetAddressId);
  }
}