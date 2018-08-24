package org.mindtree.practice.Hotel.Reviews.controller;

import java.util.List;

import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewBean;
import org.mindtree.practice.Hotel.Reviews.services.RestaurantReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	/*@ApiOperation(value="Getting all Reviews", response=RestaurantReviewBean.class)
	@GetMapping("/getReviews")
	public ResponseEntity<List<RestaurantReviewBean>> getReviews(@PathVariable Integer restaurantId) {
		beanList = service.getReviews(restaurantId);
		return new ResponseEntity<List<RestaurantReviewBean>>(beanList, HttpStatus.OK);
	}*/
	
	/*@GetMapping("/getReviewsPaginated")
	public ResponseEntity<Page<RestaurantReviewBean>> getReviewsPaginated(Pageable pageable) {
		beanPage = service.getReviewsPaginated(pageable);
		return new ResponseEntity<Page<RestaurantReviewBean>>(HttpStatus.OK);
	}*/
	
	@ApiOperation(value="Getting Reviews Paginated", response=RestaurantReviewBean.class)
	@GetMapping("/getReviewsById/{restaurantId}/Paginated")
	public Page<RestaurantReviewBean> getReviewsPaginated(@PathVariable Integer restaurantId, Pageable pageable) {
		System.out.println(restaurantId + "integer restaurant id ===============================");
		beanPage = service.getReviewsPaginated(pageable, restaurantId);
		return beanPage;
	}
	
	/*@PostMapping("/putReviews")
	public ResponseEntity<RestaurantReviewBean> putReviews(@PathVariable RestaurantReviewBean bean) {
		bean = service.putReviews(bean);
		return new ResponseEntity<RestaurantReviewBean>(bean, HttpStatus.OK);
	}*/
	
}
