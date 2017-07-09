package com.lucas.dao.factory;

import java.util.HashMap;
import java.util.Map;

import com.lucas.dao.CustomerDAO;
import com.lucas.dao.impl.CustomerDAOJdbcImpl;
import com.lucas.dao.impl.CustomerDAOXMLImpl;

public class CustomerDAOFactory {
	private Map<String, CustomerDAO> map = new HashMap<String, CustomerDAO>();
	
	public CustomerDAOFactory() {
		map.put("xml", new CustomerDAOXMLImpl());
		map.put("jdbc", new CustomerDAOJdbcImpl());
	}
	
	private static CustomerDAOFactory instance = new CustomerDAOFactory();
	
	public static CustomerDAOFactory getInstance() {
		return instance;
	}
	
	private String type = null;
	
	public void setType(String type) {
		this.type = type;
	}
	
	public CustomerDAO getCustomerDAO() {
		return map.get(type);
	}
	
}
