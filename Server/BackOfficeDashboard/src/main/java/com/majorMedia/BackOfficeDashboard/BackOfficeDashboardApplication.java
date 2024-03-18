package com.majorMedia.BackOfficeDashboard;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.BindException;
import java.util.*;


import java.security.Key;

@SpringBootApplication
public class BackOfficeDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackOfficeDashboardApplication.class, args);
	}


}
