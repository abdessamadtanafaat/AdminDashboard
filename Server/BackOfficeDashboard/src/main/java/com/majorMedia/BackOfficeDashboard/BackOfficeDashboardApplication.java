package com.majorMedia.BackOfficeDashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.BindException;
import java.util.*;


import java.security.Key;

@SpringBootApplication
public class BackOfficeDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackOfficeDashboardApplication.class, args);

		}
	}



