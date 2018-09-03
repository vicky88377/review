package org.mindtree.practice.Hotel.Reviews;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
@EnableScheduling
public class HotelReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelReviewsApplication.class, args);
	}
	

	}
