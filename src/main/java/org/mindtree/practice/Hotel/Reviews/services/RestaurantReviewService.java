package org.mindtree.practice.Hotel.Reviews.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.mindtree.practice.Hotel.Reviews.beans.CustomerRestaurantReview;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantBean;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewUpdates;
//import org.mindtree.practice.Hotel.Reviews.exceptions.InvalidRestaurantIdException;
import org.mindtree.practice.Hotel.Reviews.repository.RestaurantReviewRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:reviewConfig.properties")
public class RestaurantReviewService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestaurantReviewRepository repository;
	
	@Value("${restauranturl1}")
    private String restauranturl1;
	
	private CustomerRestaurantReview bean;
	private Page<CustomerRestaurantReview> beanPage;
	
	
	public Page<CustomerRestaurantReview> getReviewsPaginated(Pageable pageable, Integer restaurantId) {
		beanPage = (Page<CustomerRestaurantReview>) repository.findByRestaurantId(pageable, restaurantId);
		return beanPage;
	}
	
	public CustomerRestaurantReview putReviews(CustomerRestaurantReview bean) {
		logger.info("Review created with restaurantID " + bean.getRestaurantId());
		this.bean = repository.save(bean);
		return this.bean;
	}
	
	public CustomerRestaurantReview updateReviews(RestaurantReviewUpdates updateBean, Integer reviewId) {		
		CustomerRestaurantReview updatedBean;
		logger.info("reviewer ====================================== " + reviewId);
		updatedBean = repository.findById(reviewId).get();
		logger.info(updatedBean + "   reviewer update bean ====================================== " + reviewId);
		updatedBean.setReviewerRating(updateBean.getRestaurantRating());
		updatedBean.setRestaurantReview(updateBean.getRestaurantReview());
		this.bean = repository.save(updatedBean);
		logger.info("bean1 ====================================== " + this.bean);
		return this.bean;
	} 
	
	public String getReviewEMailId(Integer reviewId) {
		return repository.findById(reviewId).get().geteMailId();
	}
	
	public boolean cronJobAverageRating() {
		boolean status = true;
		logger.info("--> cron job running");
		RestTemplate template = new RestTemplate();
		Iterator<CustomerRestaurantReview> iterator = repository.runCronJob().iterator();
		while(iterator.hasNext()) {
			bean = iterator.next();
			ResponseEntity<RestaurantBean> restaurantEntityBean = template.exchange(restauranturl1+ bean.getRestaurantId() + "/reviews/" + bean.getReviewerRating(), HttpMethod.PUT, new HttpEntity<String>(new HttpHeaders()), RestaurantBean.class);
			logger.info("cron log : Restaurant ID " + bean.getRestaurantId() + " and average rating is " + bean.getReviewerRating() + " result status : " + restaurantEntityBean.getBody().getStatus() + " and status code : " + restaurantEntityBean.getBody().getStatus_code());
			repository.save(bean);
			if(restaurantEntityBean.getBody().getStatus_code() != 200) {
				status = false;
			}
		}
		logger.info("--> cron job stopped");
		return status;
	}

}
