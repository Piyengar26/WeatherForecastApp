package com.Piyengar26.WeatherForecastApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true); // Allow credentials like cookies or authorization headers
    config.addAllowedOriginPattern("*"); // Allow all origins; use specific ones in production
    config.addAllowedHeader("*"); // Allow all headers
    config.addAllowedMethod("*"); // Allow all HTTP methods
    source.registerCorsConfiguration("/**", config); // Apply to all endpoints
    return new CorsFilter(source);
  }
}
