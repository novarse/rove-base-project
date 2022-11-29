package edu.vtac.roveBaseProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = {"edu.vtac.roveBaseProject.repo"})
@ComponentScan(basePackages = {"edu.vtac.roveBaseProject"})
@EnableCaching
@PropertySources({
	@PropertySource("classpath:git.properties"),
	@PropertySource("classpath:META-INF/build-info.properties")
})
@EnableAsync
@EnableScheduling
public class RoveBaseProject extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RoveBaseProject.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(RoveBaseProject.class);
	}

}
