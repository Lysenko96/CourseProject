package edu.lysenko.catalog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("edu.lysenko.catalog")
@PropertySource("classpath:config.properties")
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setPrefix("/WEB-INF/");
		bean.setSuffix(".jsp");
		return bean;
	}

	@Value("${db.url}")
	private String url;
	@Value("${db.user}")
	private String user;
	@Value("${db.passwd}")
	private String passwd;
	@Value("${db.driver}")
	private String driver;

	@Bean
	public void createSchema() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, passwd);
		dataSource.setDriverClassName(driver);
		DatabasePopulatorUtils.execute(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")), dataSource);
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
