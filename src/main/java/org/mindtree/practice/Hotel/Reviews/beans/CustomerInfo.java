package org.mindtree.practice.Hotel.Reviews.beans;

public class CustomerInfo {
	
	private int customer_id;
	private String customer_name;
	private String email_id;
	private int customer_pincode; 

	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public int getCustomer_pincode() {
		return customer_pincode;
	}
	public void setCustomer_pincode(int customer_pincode) {
		this.customer_pincode = customer_pincode;
	}
}
