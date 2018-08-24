package org.mindtree.practice.Hotel.Reviews.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RestaurantReviewBean {
	
	@Id
	private int reviewId;
	
	private int restaurantId;
	private String reviewerName;
	private int reviewerRating;
	private String restaurantReview;
	
	
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public int getReviewerRating() {
		return reviewerRating;
	}
	public void setReviewerRating(int reviewerRating) {
		this.reviewerRating = reviewerRating;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getReview() {
		return restaurantReview;
	}
	public void setReview(String review) {
		this.restaurantReview = review;
	}
	
}
