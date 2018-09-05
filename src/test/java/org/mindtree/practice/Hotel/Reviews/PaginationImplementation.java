/*package org.mindtree.practice.Hotel.Reviews;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationImplementation implements Pageable {

	private Page page;
	private int limit;
	private long offset;
	private Sort sort;

	public PaginationImplementation(Page page, int limit) {
		super();
		this.page = page;
		this.limit = limit;
	}
	
	public PaginationImplementation(Page page, int limit, int offset) {
		super();
		this.page = page;
		this.limit = limit;
		this.offset = offset;
	}

	public PaginationImplementation(Page page, int limit, Sort sort) {
		super();
		this.page = page;
		this.limit = limit;
		this.sort = sort;
	}

	public PaginationImplementation(Page page, int limit, long offset, Sort sort) {
		super();
		this.page = page;
		this.limit = limit;
		this.offset = offset;
		this.sort = sort;
	}

	@Override
	public Pageable first() {
		// TODO Auto-generated method stub
		while(!page.isFirst()) {
			page.previousPageable();
		}
		return page.getPageable();
	}

	@Override
	public long getOffset() {
		// TODO Auto-generated method stub
		return offset;
	}

	@Override
	public int getPageNumber() {
		// TODO Auto-generated method stub
		return page.getNumber();
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return limit;
	}

	@Override
	public Sort getSort() {
		// TODO Auto-generated method stub
		return sort;
	}

	@Override
	public boolean hasPrevious() {
		// TODO Auto-generated method stub
		return page.hasPrevious();
	}

	@Override
	public Pageable next() {
		// TODO Auto-generated method stub
		return page.nextPageable();
	}

	@Override
	public Pageable previousOrFirst() {
		// TODO Auto-generated method stub
		return page.previousPageable();
	}

}
*/