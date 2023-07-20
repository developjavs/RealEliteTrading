package com.realelitetrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication(scanBasePackages={"com.realelitetrading.*"})
public class RealelitetradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealelitetradingApplication.class, args);
	}
	
	@Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://127.0.0.1:5500"); // Reemplaza con tu origen permitido
        corsConfig.addAllowedOrigin("http://www.realelitetrading.com/");
        corsConfig.addAllowedMethod("*"); // Permitir todos los métodos (GET, POST, PUT, etc.)
        corsConfig.addAllowedHeader("*"); // Permitir todos los encabezados

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsFilter(source);
    }

}
