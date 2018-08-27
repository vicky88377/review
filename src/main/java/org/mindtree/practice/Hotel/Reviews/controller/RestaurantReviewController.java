package org.mindtree.practice.Hotel.Reviews.controller;

import java.util.List;

/*import javax.ws.rs.core.MediaType;*/

import org.mindtree.practice.Hotel.Reviews.beans.ErrorDetails;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewBean;
import org.mindtree.practice.Hotel.Reviews.exceptions.InvalidRestaurantIdException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	RestaurantReviewService service;
	
	RestaurantReviewBean bean;
	List<RestaurantReviewBean> beanList;
	Page<RestaurantReviewBean> beanPage;
	
	@ApiResponses(value= {
			@ApiResponse(code=200, message="Successfully retrived"),
			@ApiResponse(code=403, message="Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code=404, message="The resource you were trying to reach is not found"),
		}
	)
	
	@ApiOperation(value="Getting all Reviews in a list", response=RestaurantReviewBean.class)
	@GetMapping("/getAllReviews")
	public ResponseEntity<List<RestaurantReviewBean>> getAllReviews() {
		beanList = service.getAllReviews();
		return new ResponseEntity<List<RestaurantReviewBean>>(beanList, HttpStatus.OK);
	}
	
	@ApiOperation(value="Getting all Reviews Paginated", response=RestaurantReviewBean.class)
	@GetMapping("/getAllReviews/Paginated")
	public Page<RestaurantReviewBean> getAllReviewsPaginated(Pageable pageable) {
		beanPage = service.getAllReviewsPaginated(pageable);
		return beanPage;
	}
	
	@ApiOperation(value="Getting Reviews of perticular Restaurant in a list", response=RestaurantReviewBean.class)
	@GetMapping("/getReviewsById/{restaurantId}")
	public ResponseEntity<List<RestaurantReviewBean>> getReviews(@PathVariable Integer restaurantId) {
		beanList = service.getReviews(restaurantId);
		return new ResponseEntity<List<RestaurantReviewBean>>(beanList, HttpStatus.OK);
	}
	
	@ApiOperation(value="Getting Reviews of perticular Restaurant Paginated", response=RestaurantReviewBean.class)
	@RequestMapping(value="/getReviewsById/{restaurantId}/Paginated", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
//	@GetMapping("/getReviewsById/{restaurantId}/Paginated", produces=MediaType.)
	public Page<RestaurantReviewBean> getReviewsPaginated(@PathVariable Integer restaurantId, Pageable pageable) throws IllegalArgumentException, InvalidRestaurantIdException {
		System.out.println(restaurantId + "integer restaurant id ===============================");
		beanPage = service.getReviewsPaginated(pageable, restaurantId);
		return beanPage;
//		return (Page<RestaurantReviewBean>) new ResponseEntity<Page<RestaurantReviewBean>>(beanPage, HttpStatus.OK);
	}
	
	@RequestMapping(value="/putReview", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantReviewBean> putReviews(@RequestBody RestaurantReviewBean bean) {
		bean = service.putReviews(bean);
		return new ResponseEntity<RestaurantReviewBean>(bean, HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateReview", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantReviewBean> updateReviews(@RequestBody RestaurantReviewBean bean) {
		bean = service.putReviews(bean);
		return new ResponseEntity<RestaurantReviewBean>(bean, HttpStatus.OK);
	}
	
}
