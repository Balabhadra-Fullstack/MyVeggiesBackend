package com.fresh.veggies.Configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@Configuration
public class SeurityConfig {
   /* @Bean
public CorsConfigurationSource corsConfigurationSource(){
    CorsConfiguration cros = new CorsConfiguration();
    cros.setAllowedOriginPatterns(List.of("*"));
    cros.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
    cros.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    cros.setAllowCredentials(true);
    cros.setExposedHeaders(List.of("Authorization"));
    cros.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cros);

    return source;
}*/
    
}
