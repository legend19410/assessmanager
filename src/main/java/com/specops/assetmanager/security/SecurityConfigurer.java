package com.specops.assetmanager.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.specops.assetmanager.officers.OfficerRepository;
import com.specops.assetmanager.officers.OfficerService;



@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private OfficerService officerService;
	
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(officerService);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
		customAuthenticationFilter.setFilterProcessesUrl("/api/login");
		http.csrf().disable();
		http.cors().and();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		http.authorizeRequests().antMatchers("api/officer/refresh-token").permitAll();
//		http.authorizeRequests().antMatchers("/api/login", "api/officer/refresh-token").permitAll();
//		http.authorizeRequests().antMatchers("/api/lvl-3/**").hasAnyAuthority("ALPHA_ADMIN_LVL1","ALPHA_ADMIN_LVL2","ALPHA_ADMIN_LVL3");
//		http.authorizeRequests().antMatchers("/api/lvl-1/**").hasAnyAuthority("ALPHA_ADMIN_LVL1");
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(customAuthenticationFilter);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	
	}
	

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT", "PATCH"));
		configuration.addAllowedHeader("Authorization");
		configuration.addAllowedHeader("Formations");
		configuration.addAllowedHeader("Content-Type");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Override 
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
//	@Bean
//	PasswordEncoder passwordEncoder(BCryptPasswordEncoder p){
//		return p;
//	}
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
}
