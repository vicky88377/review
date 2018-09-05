package org.mindtree.practice.Hotel.Reviews.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.mindtree.practice.Hotel.Reviews.beans.CustomerRestaurantReview;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantBean;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReview;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;

@Service
@PropertySource("classpath:reviewConfig.properties")
public class RestaurantReviewService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestaurantReviewRepository repository;
	
	@Value("${restauranturl1}")
    private String restauranturl1;
	
	private CustomerRestaurantReview bean;
	private List<CustomerRestaurantReview> beanList;
	private Page<CustomerRestaurantReview> beanPage;
	private Properties reviewFile;
	
	/*public List<CustomerRestaurantReview> getAllReviews() {
//		bean = repository.findById(restaurantId).get();
//		beanList = repository.findByReviewId(restaurantId);
		beanList = repository.findAll();
		return beanList;
	}
	
	public Page<CustomerRestaurantReview> getAllReviewsPaginated(Pageable pageable) {
//		bean = repository.findById(restaurantId).get();
//		beanList = (List<RestaurantReviewBean>) repository.findAll();
		beanPage = repository.findAll(pageable);
		return beanPage;
	}
	
	public List<CustomerRestaurantReview> getReviews(Integer restaurantId) {
//		bean = repository.findById(restaurantId).get();
		beanList = repository.findByRestaurantId(restaurantId);
		return beanList;
	}*/
	
	public Page<CustomerRestaurantReview> getReviewsPaginated(Pageable pageable, Integer restaurantId) {
//		bean = repository.findById(restaurantId).get();
//		beanPage = repository.findAll(pageable);
//		beanList = (List<RestaurantReviewBean>) repository.findAll();
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
		/*RestaurantBean restaurantBean;
		repository.doTheCronJob();
		beanList = repository.runCronJob();
		logger.info(beanList.toString());*/
		try {
			reviewFile = new Properties();
			reviewFile.load(new FileInputStream("reviewConfig.properties"));
			logger.info("--> " + reviewFile.getProperty("restauranturl1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<CustomerRestaurantReview> iterator = repository.runCronJob().iterator();
		while(iterator.hasNext()) {
			bean = iterator.next();
//			template.exchange("http://demojenkins3.southeastasia.cloudapp.azure.com:5665/" + bean.getRestaurantId() + "/reviews/" + bean.getRestaurantReview(), HttpMethod.PUT, null, RestaurantBean.class);
			ResponseEntity<RestaurantBean> restaurantEntityBean = template.exchange(restauranturl1+ bean.getRestaurantId() + "/reviews/" + bean.getReviewerRating(), HttpMethod.PUT, new HttpEntity<String>(new HttpHeaders()), RestaurantBean.class);
			logger.info("cron log : Restaurant ID " + bean.getRestaurantId() + " and average rating is " + bean.getReviewerRating() + " result status : " + restaurantEntityBean.getBody().getStatus() + " and status code : " + restaurantEntityBean.getBody().getStatus_code());
			/*RestaurantBean restaurantBean = template.getForObject("http://demojenkins3.southeastasia.cloudapp.azure.com:5665/restaurants/" + bean.getRestaurantId() + "/reviews/" + bean.getReviewerRating(), RestaurantBean.class);
			repository.findByRestaurantId(croneReview.getRestaurantId());
			bean.setRestaurantId(croneReview.getRestaurantId());
			bean.setReviewerRating(croneReview.getAverage());*/
			repository.save(bean);
			if(restaurantEntityBean.getBody().getStatus_code() != 200) {
				status = false;
			}
		}
		/*int restaurantId = 0;
		Iterator<RestaurantReviewBean> it = repository.findAll().iterator();
		while(it.hasNext()) {
			restaurantId = it.next().getRestaurantId();
			
		}
		beanList = repository.findByRestaurantId(restaurantId);*/
		logger.info("--> cron job stopped");
		return status;
	}

}
