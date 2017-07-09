package com.lucas.dao.impl;

import java.util.List;

import com.lucas.dao.CustomerDAO;
import com.lucas.domain.CriteriaCustomer;
import com.lucas.domain.Customer;

public class CustomerDAOXMLImpl implements CustomerDAO{

	@Override
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc) {
		System.out.println("getForListWithcriteriaCustomer");
		return null;
	}

	@Override
	public List<Customer> getAll() {
		System.out.println("getAll");
		return null;
	}

	@Override
	public void save(Customer customer) {
		System.out.println("save");
	}

	@Override
	public Customer get(Integer id) {
		System.out.println("get");
		return null;
	}

	@Override
	public void delete(Integer id) {
		System.out.println("delete");
		
	}

	@Override
	public void update(Customer customer) {
		System.out.println("update");
	}

	@Override
	public long getCountWithName(String name) {
		System.out.println("getCountWithName");
		return 0;
	}

}
