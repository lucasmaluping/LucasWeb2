package com.lucas.dao;

import java.util.List;

import com.lucas.domain.User;

public interface UserDao {
	public List<User> getAll();
	public long getCountWithName(String name);
	public int save(User user);
}
