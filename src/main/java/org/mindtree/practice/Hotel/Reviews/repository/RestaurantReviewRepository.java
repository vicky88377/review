package org.mindtree.practice.Hotel.Reviews.repository;

import java.util.List;

import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantReviewRepository extends JpaRepository<RestaurantReviewBean, Integer> {
	List<RestaurantReviewBean> findByReviewId(Integer restaurantId);
	Page<RestaurantReviewBean> findByReviewId(Pageable pageable, Integer restaurantId);
	
//	Page<RestaurantReviewBean> findByRestaurantReviewIdPaginated(Integer restaurantId);
}

/*@Repository
public interface RestaurantReviewRepository extends CrudRepository<RestaurantReviewBean, Integer> {
	List<RestaurantReviewBean> findByRestaurantReviewId(Integer restaurantId);
	
//	Page<RestaurantReviewBean> findByRestaurantReviewIdPaginated(Integer restaurantId);
}*/
