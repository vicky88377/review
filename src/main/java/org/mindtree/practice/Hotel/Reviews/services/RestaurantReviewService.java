package org.mindtree.practice.Hotel.Reviews.services;

import java.util.Iterator;
import java.util.List;

import org.mindtree.practice.Hotel.Reviews.beans.CustomerRestaurantReview;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReview;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewUpdates;
import org.mindtree.practice.Hotel.Reviews.exceptions.InvalidRestaurantIdException;
import org.mindtree.practice.Hotel.Reviews.repository.RestaurantReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestaurantReviewService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	RestaurantReviewRepository repository;
	
	CustomerRestaurantReview bean;
	List<CustomerRestaurantReview> beanList;
	Page<CustomerRestaurantReview> beanPage;
	
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
	
	@Scheduled(initialDelay=5000, fixedDelay=200000)
	public void cronJobAverageRating() {
		logger.info("<-- cron job running");
		RestTemplate template = new RestTemplate();
		/*repository.doTheCronJob();*/
		/*beanList = repository.runCronJob();
		logger.info(beanList.toString());*/
		Iterator<CustomerRestaurantReview> iterator = repository.runCronJob().iterator();
		while(iterator.hasNext()) {
			bean = iterator.next();
//			restaurantBean = template.getForObject("review/" + bean.getRestaurantId() + "/" + bean.getReviewerRating(), RestaurantBean.class);
			logger.info("crone processing next object with review ID{}" + bean.getRestaurantId() + " and average rating is " + bean.getReviewerRating());
			/*repository.findByRestaurantId(croneReview.getRestaurantId());
			bean.setRestaurantId(croneReview.getRestaurantId());
			bean.setReviewerRating(croneReview.getAverage());
			repository.save(bean);*/
		}
		
		/*int restaurantId = 0;
		Iterator<RestaurantReviewBean> it = repository.findAll().iterator();
		while(it.hasNext()) {
			restaurantId = it.next().getRestaurantId();
			
		}
		beanList = repository.findByRestaurantId(restaurantId);*/
		logger.info("--> cron job stopped");
	}

}
