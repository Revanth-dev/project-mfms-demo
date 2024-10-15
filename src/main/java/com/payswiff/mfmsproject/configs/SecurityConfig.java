package com.payswiff.mfmsproject.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.payswiff.mfmsproject.security.JwtAuthenticationEntryPoint;
import com.payswiff.mfmsproject.security.JwtAuthenticationFilter;
import com.payswiff.mfmsproject.security.JwtTokenProvider;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	    httpSecurity
	        .csrf(csrf -> csrf.disable()) // Disable CSRF protection
	        .authorizeHttpRequests((authorize) -> authorize
	        		// Allow access to all employees without authentication
	                .requestMatchers(HttpMethod.POST, "/api/authentication/login").permitAll()
	                .requestMatchers(HttpMethod.POST, "/api/authentication/forgotpassword").permitAll()
	                .requestMatchers(HttpMethod.POST, "/api/employees/create").permitAll()
	                // Allow access to admin-only endpoints
	                .requestMatchers(HttpMethod.POST, "/api/devices/create").hasRole("admin")
	                .requestMatchers(HttpMethod.POST, "/api/merchants/create").hasRole("admin")
	                .requestMatchers(HttpMethod.POST, "/api/MerchantDeviceAssociation/assign").hasRole("admin")
	                .requestMatchers(HttpMethod.POST, "/api/questions/create").hasRole("admin")
	                .requestMatchers(HttpMethod.GET, "/api/MerchantDeviceAssociation/get/merchantdeviceslist").hasRole("admin")
	                .requestMatchers(HttpMethod.GET, "/api/MerchantDeviceAssociation/check/merchant-device").hasRole("admin")
	                // Access to employee endpoints with authentication
	                .requestMatchers(HttpMethod.POST, "/api/feedback/create").hasRole("employee")
	                // Access to endpoints requiring either admin or employee authentication
	                .requestMatchers(HttpMethod.GET, "/api/devices/get").authenticated()
	                .requestMatchers(HttpMethod.GET, "/api/devices/all").authenticated()
	                .requestMatchers(HttpMethod.GET, "/api/employees/get").authenticated()
	                .requestMatchers(HttpMethod.GET, "/api/feedback/getallfeedbacks").authenticated()
	                .requestMatchers(HttpMethod.GET, "/api/merchants/get").authenticated()
	                .requestMatchers(HttpMethod.GET, "/api/merchants/all").authenticated()
	                .requestMatchers(HttpMethod.GET, "/api/questions/get").authenticated()
	                .requestMatchers(HttpMethod.GET, "/api/questions/getbydesc").authenticated()
	                .requestMatchers(HttpMethod.GET, "/api/questions/all").authenticated()
	                // Permit all other requests
	                .anyRequest().permitAll()
	        )
	        .httpBasic(Customizer.withDefaults())
	        .exceptionHandling(exception->exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
	        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	    
	    httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
// Enable basic authentication
	    
	    return httpSecurity.build();
	}

	
}
