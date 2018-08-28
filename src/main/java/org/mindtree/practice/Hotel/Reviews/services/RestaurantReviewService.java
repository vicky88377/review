package org.mindtree.practice.Hotel.Reviews.services;

import java.util.Iterator;
import java.util.List;

import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReview;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantBean;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewBean;
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
	
	RestaurantReviewBean bean;
	List<RestaurantReviewBean> beanList;
	Page<RestaurantReviewBean> beanPage;
	RestaurantReview cronReview;
	List<RestaurantReview> reviewList;

	private RestaurantBean restaurantBean;
	
	public List<RestaurantReviewBean> getAllReviews() {
//		bean = repository.findById(restaurantId).get();
//		beanList = repository.findByReviewId(restaurantId);
		beanList = repository.findAll();
		return beanList;
	}
	
	public Page<RestaurantReviewBean> getAllReviewsPaginated(Pageable pageable) {
//		bean = repository.findById(restaurantId).get();
//		beanList = (List<RestaurantReviewBean>) repository.findAll();
		beanPage = repository.findAll(pageable);
		return beanPage;
	}
	
	public List<RestaurantReviewBean> getReviews(Integer restaurantId) {
//		bean = repository.findById(restaurantId).get();
		beanList = repository.findByRestaurantId(restaurantId);
		return beanList;
	}
	
	public Page<RestaurantReviewBean> getReviewsPaginated(Pageable pageable, Integer restaurantId) throws InvalidRestaurantIdException {
//		bean = repository.findById(restaurantId).get();
//		beanPage = repository.findAll(pageable);
//		beanList = (List<RestaurantReviewBean>) repository.findAll();
		try {
			beanPage = (Page<RestaurantReviewBean>) repository.findByRestaurantId(pageable, restaurantId);
		}catch (Exception e) {
			throw new InvalidRestaurantIdException();
		}
		return beanPage;
	}
	
	public RestaurantReviewBean putReviews(RestaurantReviewBean bean) {
		this.bean = repository.save(bean);
		return this.bean;
	}
	
	@Scheduled(initialDelay=5000, fixedDelay=200000)
	public void cronJobAverageRating() {
		logger.info("<-- cron job running");
		RestTemplate template = new RestTemplate();
		/*repository.doTheCronJob();*/
		beanList = repository.doTheCronJob();
		logger.info(beanList.toString());
		Iterator<RestaurantReviewBean> iterator = beanList.iterator();
		while(iterator.hasNext()) {
			bean = iterator.next();
//			restaurantBean = template.getForObject("review/" + cronReview.getRestaurantId() + "/" + cronReview.getAverage(), RestaurantBean.class);
			logger.info("crone processing next object with review ID{}" + bean.getRestaurantId() + " " + bean.getReviewId() + " " + bean.getReviewerName() + " " + bean.getReviewerRating());
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
