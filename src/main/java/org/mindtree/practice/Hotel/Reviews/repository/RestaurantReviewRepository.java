package org.mindtree.practice.Hotel.Reviews.repository;

import java.util.List;

import org.mindtree.practice.Hotel.Reviews.beans.CustomerRestaurantReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantReviewRepository extends JpaRepository<CustomerRestaurantReview, Integer> {
	List<CustomerRestaurantReview> findByRestaurantId(Integer restaurantId);
	Page<CustomerRestaurantReview> findByRestaurantId(Pageable pageable, Integer restaurantId);

	
	@Query(value="select restaurant_Id, review_id, reviewer_name, restaurant_review, e_mail_id, avg(reviewer_rating) as reviewer_rating from customers.restaurant_review group by restaurant_id;", nativeQuery = true)
	List<CustomerRestaurantReview> runCronJob();
	/*void doTheCronJob();*/
	
	/*@Query(value="select * from customers.restaurant_review group by restaurant_id;", nativeQuery = true)
	List<RestaurantReviewBean> doTheCronJob();*/
	
//	CustomerRestaurantReview findByEMailIdId(Integer reviewId);	
//	List<RestaurantReviewBean> findCroneJob(Integer restaurantId);
//	Page<RestaurantReviewBean> findByReviewId(Pageable pageable, Integer restaurantId);	
//	Page<RestaurantReviewBean> findByRestaurantReviewIdPaginated(Integer restaurantId);
}

/*@Repository
public interface RestaurantReviewRepository extends CrudRepository<RestaurantReviewBean, Integer> {
	List<RestaurantReviewBean> findByRestaurantReviewId(Integer restaurantId);
	
//	Page<RestaurantReviewBean> findByRestaurantReviewIdPaginated(Integer restaurantId);
}*/
