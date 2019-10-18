package com.vesystem;

import net.unicon.cas.client.configuration.CasClientConfigurerAdapter;
import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EnableCasClient
@SpringBootApplication(scanBasePackages = "com.vesystem")
public class CasClientApplication extends CasClientConfigurerAdapter{

	@Value("${cas-service-redirect-url}")
	private String serviceRedirectUrl;

	@Value("${cas-ignore-pattern}")
	private String ignorePatter;

	public static void main(String[] args) {
		SpringApplication.run(CasClientApplication.class, args);
	}

	@Override
	public void configureValidationFilter(FilterRegistrationBean validationFilter) {
		/*List<String> urlPatterns = new ArrayList<>();
		urlPatterns.add("/cas/login");
		urlPatterns.add("/index");
		validationFilter.setUrlPatterns(urlPatterns);*/
		Map<String, String> map = validationFilter.getInitParameters();
		map.remove("serverName");
		map.put("service",serviceRedirectUrl);
	}

	@Override
	public void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
		/*List<String> urlPatterns = new ArrayList<>();
		urlPatterns.add("/cas*//**//*");
		authenticationFilter.setUrlPatterns(urlPatterns);*/
		Map<String, String> map = authenticationFilter.getInitParameters();
		map.remove("serverName");
		map.put("service",serviceRedirectUrl);
		//map.put("ignorePattern",ignorePatter);
	}
}
