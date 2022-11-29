package edu.vtac.roveBaseProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(1)
public class SwaggerSecurityConfig extends WebSecurityConfigurerAdapter {

	


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  auth.inMemoryAuthentication()
	        .passwordEncoder(passwordEncoder())
	        .withUser("vtac-dev").password(passwordEncoder().encode("password")).roles("SWAGGGER");		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().disable();
		http.csrf().disable();
		http.antMatcher("/swagger-ui.html")
			.antMatcher("/v2/api-docs")
			.authorizeRequests()
			.antMatchers("/swagger-ui.html", "/v2/api-docs")
			.hasRole("SWAGGGER")
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {    
	    web.ignoring().antMatchers("/swagger-resources/**");
	    web.ignoring().antMatchers("/webjars/**");
	}
}
