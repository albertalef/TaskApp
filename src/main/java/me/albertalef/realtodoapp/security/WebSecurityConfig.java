package me.albertalef.realtodoapp.security;

import lombok.RequiredArgsConstructor;
import me.albertalef.realtodoapp.security.filter.JwtAuthenticationFilter;
import me.albertalef.realtodoapp.security.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	public InMemoryUserDetailsManager userDetailsManager(){
		UserDetails user = User.builder()
				.passwordEncoder(passwordEncoder()::encode)
				.username("debug").password("hardpassword")
				.roles("USER").build();

		return new InMemoryUserDetailsManager(user);
	}
	private AuthenticationManager getAuthenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	@Bean
	public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception{
		http.csrf().disable();
		http.cors();
		http.authorizeRequests().antMatchers("/jwt/login").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
		http.httpBasic(Customizer.withDefaults());
		http.addFilter(new JwtAuthenticationFilter(getAuthenticationManager(), "/jwt/login"));
		http.addFilterBefore(new JwtAuthorizationFilter(getAuthenticationManager(), "/jwt/login"), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.stream(HttpMethod.values()).map(Enum::name).collect(Collectors.toList()));
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
