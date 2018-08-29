package org.mindtree.practice.Hotel.Reviews;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.mindtree.practice.Hotel.Reviews.beans.CustomerRestaurantReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageImplementation implements Page<CustomerRestaurantReview> {

	private List<CustomerRestaurantReview> bean;
	private int pageNumber;
	
	
	@Override
	public List<CustomerRestaurantReview> getContent() {
		// TODO Auto-generated method stub
		return bean;
	}

	@Override
	public int getNumber() {
		// TODO Auto-generated method stub
		return pageNumber;
	}

	@Override
	public int getNumberOfElements() {
		// TODO Auto-generated method stub
		return bean.size();
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return bean.size();
	}

	@Override
	public Sort getSort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasContent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFirst() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLast() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pageable nextPageable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pageable previousPageable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<CustomerRestaurantReview> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalElements() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalPages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <U> Page<U> map(Function<? super CustomerRestaurantReview, ? extends U> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
