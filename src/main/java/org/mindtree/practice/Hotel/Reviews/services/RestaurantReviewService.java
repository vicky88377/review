package org.mindtree.practice.Hotel.Reviews.services;

import java.util.List;

import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewBean;
import org.mindtree.practice.Hotel.Reviews.repository.RestaurantReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantReviewService {
	
	@Autowired
	RestaurantReviewRepository repository;
	
	RestaurantReviewBean bean;
	List<RestaurantReviewBean> beanList;
	Page<RestaurantReviewBean> beanPage;
	
	public List<RestaurantReviewBean> getAllReviews(Integer restaurantId) {
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
		beanList = repository.findByReviewId(restaurantId);
		return beanList;
	}
	
	public Page<RestaurantReviewBean> getReviewsPaginated(Pageable pageable, Integer restaurantId) {
//		bean = repository.findById(restaurantId).get();
//		beanPage = repository.findAll(pageable);
//		beanList = (List<RestaurantReviewBean>) repository.findAll();
		beanPage = (Page<RestaurantReviewBean>) repository.findByReviewId(pageable, restaurantId);
		return beanPage;
	}
	
	public RestaurantReviewBean putReviews(RestaurantReviewBean bean) {
		bean = repository.save(bean);
		return bean;
	}

}
