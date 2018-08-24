package org.mindtree.practice.Hotel.Reviews;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindtree.practice.Hotel.Reviews.beans.RestaurantReviewBean;
import org.mindtree.practice.Hotel.Reviews.services.RestaurantReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelReviewsApplicationTests {
	
	@Autowired
	RestaurantReviewService service;
	
	private Pageable pageable;
	private Page<RestaurantReviewBean> beanPage;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testPositiveGetReviewsPaginated() {
		beanPage = service.getReviewsPaginated(pageable);
		logger.info("size of pageable bean got : " + beanPage.getSize());
		Assert.notNull(beanPage, "Got Null in reviews wit pagination function");
	}
	
	@Test
	public void testNegativeGetReviewsPaginated() {
		beanPage = service.getReviewsPaginated(pageable);
		logger.info("size of pageable bean got : " + beanPage.getSize());
		Assert.isInstanceOf(Page.class, beanPage, "Got Null in reviews with pagination function");
		Assert.notNull(beanPage, "Got Null in reviews wit pagination function");
	}

}
