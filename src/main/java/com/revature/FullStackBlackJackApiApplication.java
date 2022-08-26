package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FullStackBlackJackApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullStackBlackJackApiApplication.class, args);
		System.out.println(org.hibernate.Version.getVersionString());
		System.out.println("Full-Stack-Black-Jack Application is Ready to Go!!!");
	}

}
