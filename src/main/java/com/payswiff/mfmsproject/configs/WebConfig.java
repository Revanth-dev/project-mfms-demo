package com.payswiff.mfmsproject.configs; // Package declaration for configuration classes

import org.springframework.context.annotation.Configuration; // Importing Configuration annotation for Spring configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry; // Importing CorsRegistry for CORS configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // Importing WebMvcConfigurer to customize Spring MVC configuration

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) settings.
 * This class implements WebMvcConfigurer to customize the default Spring MVC settings.
 */
@Configuration // Annotation indicating that this class provides Spring configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Method to configure CORS mappings for the application.
     * This allows specific origins, HTTP methods, and headers for cross-origin requests.
     *
     * @param registry CORS registry to add mappings to
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all endpoints in the application
                .allowedOrigins("http://localhost:5173","http://192.168.2.4:5173") // Specify allowed origin (frontend URL)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                .allowedHeaders("*") // Allow all headers in requests
                .allowCredentials(true); // Allow credentials such as cookies or authorization headers
    }
}
