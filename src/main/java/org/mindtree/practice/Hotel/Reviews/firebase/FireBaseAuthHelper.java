package org.mindtree.practice.Hotel.Reviews.firebase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	
	public static Map<String,String> getUserInfo( String firebaseAccessToken ) throws InterruptedException {
		
		Map userMap = new HashMap();
		Task<FirebaseToken> decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseAccessToken);
		Thread.sleep(5000l);
		userMap.put("email", decodedToken.getResult().getEmail());
		userMap.put("name", decodedToken.getResult().getName());

		return userMap;
	}
	
	public static boolean isValidToken( String firebaseAccessToken ) {
			
		try {
	 		Task<FirebaseToken> decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseAccessToken);
		}catch(Exception exec) {
			System.out.println("====invalid token====");

			return false;
		}		
		System.out.println("====valid token====");
			return true;
		}
}
