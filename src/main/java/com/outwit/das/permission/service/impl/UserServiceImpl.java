package com.outwit.das.permission.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.caucho.hessian.server.HessianServlet;
import com.outwit.das.exception.BusinessException;
import com.outwit.das.page.MyPage;
import com.outwit.das.page.Page;
import com.outwit.das.page.PageHelper;
import com.outwit.das.permission.dao.UserMapper;
import com.outwit.das.permission.model.User;
import com.outwit.das.permission.service.UserService;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;

	public void add(User user) throws BusinessException {
		userMapper.insertUser(user);
	}

	public void modify(User user) throws BusinessException {
		userMapper.updateUser(user);
		
	}

	public void removeById(String id) throws BusinessException {
		userMapper.deleteUserById(id);
	}

	public void removeByIds(List<String> ids) throws BusinessException {
		userMapper.deleteUserByIds(ids);
	}

	public List<User> queryList(User user) throws BusinessException {
		return userMapper.selectUserList(user);
	}
	
	public MyPage<User> queryListByPage(User user,MyPage<User> myPage) throws BusinessException {
		PageHelper.startPage(myPage.getPageNum(), myPage.getPageSize());
		try {
			Page<User> page = (Page<User>)userMapper.selectUserList(user);
			return myPage.convertToMyPage(page);
		} catch (DataAccessException e) {
			throw new BusinessException(e);
		}
	}

	

	
	
	
}
