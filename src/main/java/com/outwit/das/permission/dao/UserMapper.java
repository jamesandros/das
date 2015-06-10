package com.outwit.das.permission.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.outwit.das.permission.model.User;

public interface UserMapper {
	void insertUser(User user)throws DataAccessException;
	void updateUser(User user)throws DataAccessException;
	void deleteUserById(String id)throws DataAccessException;
	void deleteUserByIds(List<String> ids)throws DataAccessException;
	List<User> selectUserList(User user)throws DataAccessException;
}
