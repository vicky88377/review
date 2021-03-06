package org.mindtree.practice.Hotel.Reviews.controller;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.mindtree.practice.Hotel.Reviews.beans.CustomerInfo;
import org.mindtree.practice.Hotel.Reviews.beans.CustomerRestaurantReview;

/*import javax.ws.rs.core.MediaType;*/

//import org.mindtree.practice.Hotel.Reviews.beans.ErrorDetails;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReview;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewUpdates;
import org.mindtree.practice.Hotel.Reviews.firebase.FireBaseAuthHelper;
//import org.mindtree.practice.Hotel.Reviews.exceptions.InvalidRestaurantIdException;
import org.mindtree.practice.Hotel.Reviews.services.RestaurantReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/*import com.google.common.net.MediaType;*/


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@PropertySource("classpath:reviewConfig.properties")

@Api(value="Order My Food Application", description="Getting reviews : do mention page and size")
public class RestaurantReviewController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestaurantReviewService service;
	
	private CustomerRestaurantReview bean;
	private RestTemplate template;
	private Properties reviewFile;
	
    @Value("${customerurl}")
    private String customerurl;

    @Value("${restauranturl1}")
    private String restauranturl1;

    @Value("${restauranturl2}")
    private String restauranturl2;

	
	
	private List<CustomerRestaurantReview> beanList;
	private Page<CustomerRestaurantReview> beanPage;
	private Map<String, String> userInfo;
	
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Successfully retrived"),
			@ApiResponse(code=403, message="Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code=404, message="The resource you were trying to reach is not found"),
		}
	)
	
		
	@ApiOperation(value="Getting Reviews of perticular Restaurant Paginated", response=CustomerRestaurantReview.class)
	@RequestMapping(value="/reviews/{restaurantId}/", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public Page<CustomerRestaurantReview> getReviewsPaginated(@PathVariable Integer restaurantId, Pageable pageable) {
		System.out.println(restaurantId + "integer restaurant id ===============================");
		beanPage = service.getReviewsPaginated(pageable, restaurantId);
		return beanPage;
	}
	
	@RequestMapping(value="/review", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerRestaurantReview> putReviews(@RequestHeader(value="Authorization") String firebaseAccessToken, @RequestBody RestaurantReview bean) {
		logger.info("token is : " + firebaseAccessToken);
		FireBaseAuthHelper instance = FireBaseAuthHelper.getInstace();
		firebaseAccessToken.replaceAll("Bearer ", "");
		if(instance.isValidToken(firebaseAccessToken)){
			template = new RestTemplate();
			this.bean = new CustomerRestaurantReview();
			this.bean.setRestaurantId(bean.getRestaurantId());
			this.bean.setReviewerRating(bean.getRestaurantRating());
			this.bean.setRestaurantReview(bean.getRestaurantReview());
			this.bean.seteMailId("shwetashree1993@gmail.com");
			try {
				userInfo = instance.getUserInfo1(firebaseAccessToken);
				this.bean.seteMailId(userInfo.get("email"));
				HttpHeaders headers = new HttpHeaders();
				headers.add("Authorization", firebaseAccessToken);
				HttpEntity<String> entity = new HttpEntity<String>(headers);
				logger.info("customer url : " + customerurl);
				logger.info("restaurant url : " + restauranturl1);
				ResponseEntity<CustomerInfo> responseEntity = template.exchange(customerurl, HttpMethod.GET, entity, CustomerInfo.class);
				this.bean.seteMailId(responseEntity.getBody().getEmail_id());
				this.bean.setReviewerName(responseEntity.getBody().getCustomer_name());
				logger.info("got response from customer module :");
				this.bean = service.putReviews(this.bean);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<CustomerRestaurantReview>(this.bean, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<CustomerRestaurantReview>(this.bean, HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="/review/{reviewId}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerRestaurantReview> updateReviews(@PathVariable Integer reviewId, @RequestHeader(value="Authorization") String firebaseAccessToken, @RequestBody RestaurantReviewUpdates bean) {
		FireBaseAuthHelper instance = FireBaseAuthHelper.getInstace();
		firebaseAccessToken.replaceAll("Bearer ", "");
		if(instance.isValidToken(firebaseAccessToken)){
			try {
				userInfo = instance.getUserInfo1(firebaseAccessToken);
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<CustomerRestaurantReview>(this.bean, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			logger.info(" ================================ after auth ================================ ");
			logger.info(service.getReviewEMailId(reviewId));
			logger.info(userInfo.get("email"));
			if(service.getReviewEMailId(reviewId).equals(userInfo.get("email"))) {
				logger.info(" ================================ after auth condition ================================ ");
				this.bean = service.updateReviews(bean, reviewId);
				return new ResponseEntity<CustomerRestaurantReview>(this.bean, HttpStatus.OK);
			} else {
				return new ResponseEntity<CustomerRestaurantReview>(this.bean, HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<CustomerRestaurantReview>(this.bean, HttpStatus.FORBIDDEN);
		}
	}
	
//	@RequestMapping(value="/review/cron", method=RequestMethod.GET)
	@Scheduled(initialDelay=5000, fixedDelay=100000)
	public boolean cronJobAverageRating() {
	return service.cronJobAverageRating();
	}
}
