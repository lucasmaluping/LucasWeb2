package com.lucas.dao.impl;

import java.util.List;

import com.lucas.dao.DAO;
import com.lucas.dao.UserDao;
import com.lucas.domain.User;


public class UserDaoJdbcImpl extends DAO<User> implements UserDao{

	@Override
	public List<User> getAll() {
		String sql = "select * from user";
		return getForList(sql);
	}

	@Override
	public long getCountWithName(String name) {
		String sql = "select count(id) from user where name=?";
		return getForValue(sql, name);
	}

	@Override
	public int save(User user) {
		int result = 0;
		String sql = "insert into user values (null,?,?,?,?,?)";
		result = update(sql, user.getName(), user.getAge(), user.getPassword(), user.getGender(), user.getAvator());
		return result;
	}

}
