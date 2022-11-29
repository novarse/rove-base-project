package edu.vtac.roveBaseProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@Order(2)
public class CourseSearchAdminSecurityConfig extends WebSecurityConfigurerAdapter  {

	public static final String PASSWORD = "password";
	public static final String CSADMIN = "csadmin";

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  auth.inMemoryAuthentication()
	        .passwordEncoder(passwordEncoder())
	        .withUser(CSADMIN).password(passwordEncoder().encode(PASSWORD)).roles("CSADMIN");		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .csrf().disable()
        .antMatcher("/admin/api/**")
        .authorizeRequests()
        .antMatchers("/admin/api/**")
        .hasRole("CSADMIN")
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .authenticationEntryPoint(createBasicAuthenticationEntryPoint());
	}

	private AuthenticationEntryPoint createBasicAuthenticationEntryPoint() {
		BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
		basicAuthenticationEntryPoint.setRealmName("CSAPI");
		return basicAuthenticationEntryPoint;
	}
}
