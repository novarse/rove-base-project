package edu.vtac.roveBaseProject.config;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.vtac.roveBaseProject.util.BuildInfo;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

	@Autowired
	private BuildInfo buildInfo;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .apiInfo(createApiInfo())
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build();                                           
    }

	private ApiInfo createApiInfo() {

		String dateBuild = "";
		try {
			dateBuild = Instant.parse(buildInfo.getBuildTime())
			.atZone(ZoneId.of("Australia/Sydney"))
			.format(
				DateTimeFormatter
					.ofLocalizedDateTime( FormatStyle.FULL )
                	.withLocale(Locale.ENGLISH ) 
			);			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
			format.setTimeZone(TimeZone.getTimeZone("Australia/Melbourne"));
		} catch (Exception e) {
		} finally {
			
		}
		String version = "<strong>Build Time:</strong> " + dateBuild + " <br><strong>Branch:</strong> " + buildInfo.getGitBranch() + " <br><strong>Id:</strong> " + buildInfo.getCommitId() + "<br>";
		ApiInfo apiInfo = new ApiInfoBuilder()
				.description(version + "CourseSearch allows you to perform a keyword search on course titles, groups, qualifications, institutions and course subjects")
				.title("Course Search API")
				.build();
		return apiInfo;
	}

}
