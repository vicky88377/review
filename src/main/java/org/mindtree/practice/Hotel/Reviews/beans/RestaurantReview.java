package org.mindtree.practice.Hotel.Reviews.beans;

public class RestaurantReview {
	private int restaurantId;
	private int restaurantRating;
	private String restaurantReview;

	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public int getRestaurantRating() {
		return restaurantRating;
	}
	public void setRestaurantRating(int restaurantRating) {
		this.restaurantRating = restaurantRating;
	}
	public String getRestaurantReview() {
		return restaurantReview;
	}
	public void setRestaurantReview(String restaurantReview) {
		this.restaurantReview = restaurantReview;
	}
}
