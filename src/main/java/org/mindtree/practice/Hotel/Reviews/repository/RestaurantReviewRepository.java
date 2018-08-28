package org.mindtree.practice.Hotel.Reviews.repository;

import java.util.List;

import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReview;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantReviewRepository extends JpaRepository<RestaurantReviewBean, Integer> {
	List<RestaurantReviewBean> findByRestaurantId(Integer restaurantId);
	Page<RestaurantReviewBean> findByRestaurantId(Pageable pageable, Integer restaurantId);
	
	/*@Query(value="select restaurant_Id, avg(reviewer_rating) from customers.restaurant_review group by restaurant_id;", nativeQuery = true)
	List<RestaurantReviewBean> doTheCronJob();*/
	/*void doTheCronJob();*/
	
	@Query(value="select restaurant_Id, review_id, reviewer_name, reviewer_rating from customers.restaurant_review group by restaurant_id", nativeQuery = true)
	List<RestaurantReviewBean> doTheCronJob();
	
	
//	List<RestaurantReviewBean> findCroneJob(Integer restaurantId);
//	Page<RestaurantReviewBean> findByReviewId(Pageable pageable, Integer restaurantId);	
//	Page<RestaurantReviewBean> findByRestaurantReviewIdPaginated(Integer restaurantId);
}

/*@Repository
public interface RestaurantReviewRepository extends CrudRepository<RestaurantReviewBean, Integer> {
	List<RestaurantReviewBean> findByRestaurantReviewId(Integer restaurantId);
	
//	Page<RestaurantReviewBean> findByRestaurantReviewIdPaginated(Integer restaurantId);
}*/
