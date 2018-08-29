package org.mindtree.practice.Hotel.Reviews.beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="restaurant_review")
public class CustomerRestaurantReview {
	
	@Id
	private int reviewId;
	
	private int restaurantId;
	private String reviewerName;
	private int reviewerRating;
	private String restaurantReview;
	private String eMailId;
	
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
	public String getRestaurantReview() {
		return restaurantReview;
	}
	public void setRestaurantReview(String restaurantReview) {
		this.restaurantReview = restaurantReview;
	}
	public String geteMailId() {
		return eMailId;
	}
	public void seteMailId(String eMailId) {
		this.eMailId = eMailId;
	}
		
}
