package com.lucas.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.lucas.dao.CustomerDAO;
import com.lucas.dao.impl.CustomerDAOJdbcImpl;
import com.lucas.domain.CriteriaCustomer;
import com.lucas.domain.Customer;

public class CustomerDAOJdbcImplTest {
	private CustomerDAO customerDAO = new CustomerDAOJdbcImpl();
	
	@Test
	public void testGetForListWithCriteriaCustomer() {
		CriteriaCustomer cc = new CriteriaCustomer("n",null,null);
		List<Customer> customers = customerDAO.getForListWithCriteriaCustomer(cc);
		System.out.println(customers);
	}

	@Test
	public void testGetAll() {
		List<Customer> all = customerDAO.getAll();
		System.out.println(all);
	}

	@Test
	public void testSave() {
		Customer customer = new Customer();
		customer.setAddress("shanghai");
		customer.setName("nimoo");
		customer.setPhone("15565896522");
		customerDAO.save(customer);
	}

	@Test
	public void testGetInteger() {
		Customer customer = customerDAO.get(1);
		System.out.println(customer);
	}

	@Test
	public void testDelete() {
		customerDAO.delete(1);
	}

	@Test
	public void testGetCountWithName() {
		long countWithName = customerDAO.getCountWithName("nimoo");
		System.out.println(countWithName);
	}

}
