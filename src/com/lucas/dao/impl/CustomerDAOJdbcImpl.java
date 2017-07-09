package com.lucas.dao.impl;

import java.util.List;

import com.lucas.dao.CustomerDAO;
import com.lucas.dao.DAO;
import com.lucas.domain.CriteriaCustomer;
import com.lucas.domain.Customer;

public class CustomerDAOJdbcImpl extends DAO<Customer> implements CustomerDAO{

	@Override
	public List<Customer> getAll() {
		String sql = "select * from customer";
		return getForList(sql);
	}

	@Override
	public void save(Customer customer) {
		String sql = "insert into customer values (null,?,?,?)";
		update(sql, customer.getName(), customer.getAddress(),customer.getPhone());
	}

	@Override
	public Customer get(Integer id) {
		String sql = "select id, name,address,phone from customer where id=?";
		
		return get(sql, id);
	}

	@Override
	public void delete(Integer id) {
		String sql = "delete from customer where id = ?";
		update(sql, id);
	}

	@Override
	public long getCountWithName(String name) {
		String sql = "select count(id) from customer where name = ?";
		return getForValue(sql, name);
	}

	@Override
	public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc) {
		String sql = "select * from customer where name LIKE ? AND address LIKE ? AND phone LIKE ?";
		return getForList(sql,cc.getName(),cc.getAddress(), cc.getPhone());
	}

	@Override
	public void update(Customer customer) {
		String sql = "update customer set name = ?, address = ?, phone = ? where id = ?";
		update(sql, customer.getName(), customer.getAddress(), customer.getPhone(), customer.getId());
	}

}
















