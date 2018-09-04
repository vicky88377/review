/*package org.mindtree.practice.Hotel.Reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindtree.practice.Hotel.Reviews.beans.CustomerRestaurantReview;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReview;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewUpdates;
import org.mindtree.practice.Hotel.Reviews.controller.RestaurantReviewController;
import org.mindtree.practice.Hotel.Reviews.services.RestaurantReviewService;
import org.mockito.Mockito;
//import org.mindtree.practice.Hotel.Reviews.exceptions.InvalidRestaurantIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class HotelReviewsApplicationTests {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Pageable actualPageable;
	private Pageable expectedPageable;
	private Page actualPage;
	private Page expectedPage;
	private RestaurantReview restaurantReviewbean;
	private RestaurantReviewUpdates restaurantReviewUpdates;
	private CustomerRestaurantReview expectedCustomerRestaurantReview;
	private List<CustomerRestaurantReview> expectedCustomerRestaurantReviewList;
	private Iterator iterator;
	
	@MockBean
	private RestaurantReviewService service;
	
	@Autowired
	private RestaurantReviewController controller;
		
	@Before
	public void initializeTestResources() {
		int i = 1;
		expectedCustomerRestaurantReviewList = new ArrayList<CustomerRestaurantReview>();
		while(i<4) {
			expectedCustomerRestaurantReview = new CustomerRestaurantReview();
			expectedCustomerRestaurantReview.setReviewId(i);
			expectedCustomerRestaurantReview.setRestaurantId(1);
			expectedCustomerRestaurantReview.setReviewerName("abc");
			expectedCustomerRestaurantReview.setReviewerRating(5);
			expectedCustomerRestaurantReview.setRestaurantReview("review " + i);
			expectedCustomerRestaurantReview.seteMailId("abc@abc.com");
			expectedCustomerRestaurantReviewList.add(expectedCustomerRestaurantReview);
			i++;
		}
		
		restaurantReviewbean = new RestaurantReview();
		restaurantReviewbean.setRestaurantId(1);
		restaurantReviewbean.setRestaurantRating(3);
		restaurantReviewbean.setRestaurantReview("review 3");
		
		restaurantReviewUpdates = new RestaurantReviewUpdates();
		restaurantReviewUpdates.setRestaurantRating(4);
		restaurantReviewUpdates.setRestaurantReview("review 1");
		
		expectedCustomerRestaurantReview = new CustomerRestaurantReview();
		expectedCustomerRestaurantReview.setReviewId(1);
		expectedCustomerRestaurantReview.setRestaurantId(1);
		expectedCustomerRestaurantReview.setReviewerName("abc");
		expectedCustomerRestaurantReview.setReviewerRating(3);
		expectedCustomerRestaurantReview.setRestaurantReview("review 3");
		
		expectedPageable = PageRequest.of(0, 3);
		expectedPage = new PageImpl<>(expectedCustomerRestaurantReviewList, expectedPageable, expectedCustomerRestaurantReviewList.size());
	}
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testPositiveGetReviewsPaginated() {
		actualPage = controller.getReviewsPaginated(1, expectedPageable);
		assertTrue(actualPage.getContent().iterator().next().getClass().isInstance(expectedCustomerRestaurantReview));
		
//		logger.info(actualPage.getContent().containsAll(expectedCustomerRestaurantReview) + "logging assert ======= ");
		int pageNumber = (int) expectedCustomerRestaurantReviewList.size()/3;
		pageNumber++;
		int i = 0;
		while(i<pageNumber) {
			logger.info("  ======================= page " + i);
			expectedPageable = PageRequest.of(i, 3);
			expectedPage = new PageImpl<>(expectedCustomerRestaurantReviewList, expectedPageable, expectedCustomerRestaurantReviewList.size());
			actualPage = controller.getReviewsPaginated(1, expectedPageable);
			logger.info(expectedPage.getContent().size() + "with testing id " + actualPage.getContent().containsAll(expectedCustomerRestaurantReviewList));
			Iterator it1 = expectedPage.getContent().iterator();
			Iterator it2 = actualPage.getContent().iterator();
			
			while(it1.hasNext()) {
				CustomerRestaurantReview expected = (CustomerRestaurantReview) it1.next();
				CustomerRestaurantReview actual = (CustomerRestaurantReview) it2.next();
				logger.info(expected.getRestaurantId() + expected.getRestaurantReview() + " with testing id " + actual.getRestaurantId() + actual.getRestaurantReview());
				assertSame(actual.getRestaurantId(), expected.getRestaurantId());
				assertEquals(actual.getRestaurantReview(), expected.getRestaurantReview());
				assertEquals(actual.getReviewerName(), expected.getReviewerName());
				assertSame(actual.getReviewerRating(), expected.getReviewerRating());
				assertSame(actual.getReviewId(), expected.getReviewId());
				assertEquals(actual.geteMailId(), expected.geteMailId());
			}
			i++;
		}
		
		logger.info("this is comparing objs " + actual.equals(expected));
		assertEquals(actual, expected);
		assertSame(expectedPage, actualPage);
	}
	
	@Test
	public void testNegativeGetReviewsPaginated() {
		beanPage = service.getReviewsPaginated(pageable);
		logger.info("size of pageable bean got : " + beanPage.getSize());
		Assert.isInstanceOf(Page.class, beanPage, "Got Null in reviews with pagination function");
		Assert.notNull(beanPage, "Got Null in reviews wit pagination function");
	}
	
	@Test
	public void testCronJob() {
		Mockito.doReturn(null).when(service).cronJobAverageRating();
		assertEquals(null, null);
	}
	
	@Test
	public void testCreateReviews() throws ExecutionException {
		expectedCustomerRestaurantReview = new CustomerRestaurantReview();
		expectedCustomerRestaurantReview.setReviewId(1);
		expectedCustomerRestaurantReview.setRestaurantId(1);
		expectedCustomerRestaurantReview.setReviewerName("abc");
		expectedCustomerRestaurantReview.setReviewerRating(3);
		expectedCustomerRestaurantReview.setRestaurantReview("review 3");
		ResponseEntity<CustomerRestaurantReview> actualCustomerRestaurantReview = controller.putReviews("eyJhbGciOiJSUzI1NiIsImtpZCI6ImEwY2ViNDY3NDJhNjNlMTk2NDIxNjNhNzI4NmRjZDQyZjc0MzYzNjYifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbXlvYXV0aHByajEyMyIsIm5hbWUiOiJLcnVwYWthciAuVCIsInBpY3R1cmUiOiJodHRwczovL2xoNi5nb29nbGV1c2VyY29udGVudC5jb20vLVBqTGNkM0JZcVJnL0FBQUFBQUFBQUFJL0FBQUFBQUFBQ2FNL1JnUE1PVlBtTm5rL3Bob3RvLmpwZyIsImF1ZCI6Im15b2F1dGhwcmoxMjMiLCJhdXRoX3RpbWUiOjE1MzU5NzA2MDAsInVzZXJfaWQiOiJhQWFFMnlxTExPUGoya05ldzcwb2xEeXk5UW4yIiwic3ViIjoiYUFhRTJ5cUxMT1BqMmtOZXc3MG9sRHl5OVFuMiIsImlhdCI6MTUzNTk3MzUyMSwiZXhwIjoxNTM1OTc3MTIxLCJlbWFpbCI6ImtydXBha2FyLmJsckBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJnb29nbGUuY29tIjpbIjExNzcxNzU4OTAyMDg0MjkwODQ0NyJdLCJlbWFpbCI6WyJrcnVwYWthci5ibHJAZ21haWwuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoiZ29vZ2xlLmNvbSJ9fQ.CpS4Rudnk2lsn309-ucP8wy32xcBvL3hz6xtBBCU-xusaza2s1UrWErojf4XvoEvH9d9el-Vk-nqfz6kNCpZJU9jJq7rMvcmU3vYlikuVNYHK6STXO2Ifl0S0401M4xJbCJ6jngJUKCyinhIW5zAXMZWyCG4A55-K__kfkVL-A2cn_28y-GMUxJjAWYtNVbOMpOYAI3QA4wUUGWFlDc5tVSoxSGeXWiCDV1tF5rvujf7_kj_seV0Gd-Im7N1eD7jlxlx9H-PkBjXaoXM9la4r_7TexCjxlV6SRDcOQL199GFcDktrCaTTDTWz7DvN2vLRbYnEXPeUiO8toq80HpRNw", restaurantReviewbean);
		assertTrue(actualCustomerRestaurantReview.getBody().getClass().isInstance(expectedCustomerRestaurantReview));
	}
	
	@Test
	public void testUpdateReview() {
		expectedCustomerRestaurantReview = new CustomerRestaurantReview();
		expectedCustomerRestaurantReview.setReviewId(1);
		expectedCustomerRestaurantReview.setRestaurantId(1);
		expectedCustomerRestaurantReview.setReviewerName("abc");
		expectedCustomerRestaurantReview.setReviewerRating(3);
		expectedCustomerRestaurantReview.setRestaurantReview("review 3");
		ResponseEntity<CustomerRestaurantReview> actualCustomerRestaurantReview = controller.updateReviews(new Integer(1), restaurantReviewUpdates);
		assertTrue(actualCustomerRestaurantReview.getBody().getClass().isInstance(expectedCustomerRestaurantReview));
	}

}
*/