package org.mindtree.practice.Hotel.Reviews.controller;

import java.util.List;
import java.util.Map;

/*import javax.ws.rs.core.MediaType;*/

//import org.mindtree.practice.Hotel.Reviews.beans.ErrorDetails;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReview;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewUpdates;
import org.mindtree.practice.Hotel.Reviews.beans.CustomerRestaurantReview;
//import org.mindtree.practice.Hotel.Reviews.exceptions.InvalidRestaurantIdException;
import org.mindtree.practice.Hotel.Reviews.services.RestaurantReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.mindtree.practice.Hotel.Reviews.firebase.FireBaseAuthHelper;

/*import com.google.common.net.MediaType;*/

import ch.qos.logback.classic.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/ordermyfood")
@Api(value="Order My Food Application", description="Getting reviews : do mention page and size")
public class RestaurantReviewController {
	
	@Autowired
	private RestaurantReviewService service;
	
	private CustomerRestaurantReview bean;
	private RestTemplate template;
	
	private List<CustomerRestaurantReview> beanList;
	private Page<CustomerRestaurantReview> beanPage;
	
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Successfully retrived"),
			@ApiResponse(code=403, message="Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code=404, message="The resource you were trying to reach is not found"),
		}
	)
	
	/*@ApiOperation(value="Getting all Reviews in a list", response=CustomerRestaurantReview.class)
	@GetMapping("/getAllReviews")
	public ResponseEntity<List<CustomerRestaurantReview>> getAllReviews() {
		beanList = service.getAllReviews();
		return new ResponseEntity<List<CustomerRestaurantReview>>(beanList, HttpStatus.OK);
	}
	
	@ApiOperation(value="Getting all Reviews Paginated", response=CustomerRestaurantReview.class)
	@GetMapping("/getAllReviews/Paginated")
	public Page<CustomerRestaurantReview> getAllReviewsPaginated(Pageable pageable) {
		beanPage = service.getAllReviewsPaginated(pageable);
		return beanPage;
	}
	
	@ApiOperation(value="Getting Reviews of perticular Restaurant in a list", response=CustomerRestaurantReview.class)
	@GetMapping("/getReviewsById/{restaurantId}")
	public ResponseEntity<List<CustomerRestaurantReview>> getReviews(@PathVariable Integer restaurantId) {
		beanList = service.getReviews(restaurantId);
		return new ResponseEntity<List<CustomerRestaurantReview>>(beanList, HttpStatus.OK);
	}*/
	
	@ApiOperation(value="Getting Reviews of perticular Restaurant Paginated", response=CustomerRestaurantReview.class)
	@RequestMapping(value="/getReviewsById/{restaurantId}/Paginated", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
//	@GetMapping("/getReviewsById/{restaurantId}/Paginated", produces=MediaType.)
	public Page<CustomerRestaurantReview> getReviewsPaginated(@PathVariable Integer restaurantId, Pageable pageable) {
		System.out.println(restaurantId + "integer restaurant id ===============================");
		beanPage = service.getReviewsPaginated(pageable, restaurantId);
		return beanPage;
//		return (Page<RestaurantReviewBean>) new ResponseEntity<Page<RestaurantReviewBean>>(beanPage, HttpStatus.OK);
	}
	
	@RequestMapping(value="/putReview", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerRestaurantReview> putReviews(@RequestHeader(value="token") String firebaseAccessToken, @RequestBody RestaurantReview bean) {
		template = new RestTemplate();
		this.bean = new CustomerRestaurantReview();
//		String customerName = template.getForObject("/customer/" + customerEmailId, String.class);
		/*this.bean.setRestaurantId(bean.getRestaurantId());
		this.bean.setReviewerRating(bean.getRestaurantRating());
		this.bean.setRestaurantReview(bean.getRestaurantReview());
		this.bean = service.putReviews(this.bean);*/
		this.bean.seteMailId("shetashree1993@gmail.com");
		Map<String, String> userInfo;
		try {
			userInfo = FireBaseAuthHelper.getUserInfo(firebaseAccessToken);
			this.bean.seteMailId(userInfo.get("email"));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		http://demojenkins3.southeastasia.cloudapp.azure.com:5665/restaurants/{resId}/reviews/{rating}
//		String customerName = template.getForObject("https://172.23.22.1:9002/customers/customerName/" + this.bean.geteMailId(), String.class);
		this.bean.setReviewerName(template.getForObject("https://172.23.22.1:9002/customers/customerName/" + this.bean.geteMailId(), String.class));
		
		return new ResponseEntity<CustomerRestaurantReview>(this.bean, HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateReview/{reviewId}", method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerRestaurantReview> updateReviews(@PathVariable Integer reviewId, @RequestBody RestaurantReviewUpdates bean) {
		this.bean = service.updateReviews(bean, reviewId);
		return new ResponseEntity<CustomerRestaurantReview>(this.bean, HttpStatus.OK);
	}
	
}
