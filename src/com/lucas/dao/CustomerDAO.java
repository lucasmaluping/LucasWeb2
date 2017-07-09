package com.lucas.dao;

import java.util.List;

import com.lucas.domain.CriteriaCustomer;
import com.lucas.domain.Customer;

public interface CustomerDAO {
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc);
	public List<Customer> getAll();
	public void save(Customer customer);
	public Customer get(Integer id);
	public void delete(Integer id);
	public void update(Customer customer);
	/**
	 * ���غ�name��ȵļ�¼��
	 * @param name
	 * @return
	 */
	public long getCountWithName(String name);
}
