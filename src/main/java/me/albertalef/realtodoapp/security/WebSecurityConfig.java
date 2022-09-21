package me.albertalef.realtodoapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {



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


	@Bean
	public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception{
		http.csrf().disable();
		http.authorizeRequests().anyRequest().authenticated();
		http.httpBasic(Customizer.withDefaults());
		http.formLogin();
		http.logout();

		return http.build();

	}
}
