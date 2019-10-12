package com.vesystem;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCasClient
@SpringBootApplication(scanBasePackages = "com.vesystem")
public class CasClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasClientApplication.class, args);
	}

}
