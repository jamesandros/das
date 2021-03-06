package com.outwit.das.permission.service;

import java.util.List;

import com.outwit.das.annotation.Hessian;
import com.outwit.das.common.exception.BusinessException;
import com.outwit.das.permission.model.User;

@Hessian("/zenmepo")
public interface UserService {
	public void add(User model) throws BusinessException;

	public void modify(User model) throws BusinessException;

	public void removeById(String id) throws BusinessException;

	public void removeByIds(List<String> ids) throws BusinessException;

	public List<User> queryList(User model) throws BusinessException;
	
}
