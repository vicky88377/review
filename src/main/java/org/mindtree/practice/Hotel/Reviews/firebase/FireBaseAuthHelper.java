package org.mindtree.practice.Hotel.Reviews.firebase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;

public class FireBaseAuthHelper {

	static String FB_BASE_URL="";

	static {
		FirebaseOptions options = null;
		try {
			options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("/firebase-authentication.json").getInputStream()))
					  .setDatabaseUrl(FB_BASE_URL)
					  .build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				FirebaseApp.initializeApp(options);		
	}
	
	public static Map<String,String> getUserInfo1( String firebaseAccessToken ) throws InterruptedException, ExecutionException {
		
		Map userMap = new HashMap();
		FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(firebaseAccessToken).get();
		userMap.put("email", decodedToken.getEmail());
		userMap.put("name", decodedToken.getName());

		return userMap;
	}
	
	public static boolean isValidToken( String firebaseAccessToken ) {
			
		try {
			FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(firebaseAccessToken).get();
		}catch(Exception exec) {
			System.out.println("====invalid token====");

			return false;
		}		
		System.out.println("====valid token====");
			return true;
		}
}
