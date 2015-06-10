package com.outwit.das.permission.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.outwit.das.exception.BusinessException;
import com.outwit.das.permission.model.User;
import com.outwit.das.permission.service.UserService;

@Controller
@RequestMapping({"/user"})
public class UserController {
	@Autowired
	private UserService userService;
	
	//@RequestMapping(value="permission/login.do",method=RequestMethod.GET)
	//@ResponseBody
	@RequestMapping()
	@ResponseBody//如果添加这个。返回就是json 否则返回jsp
	public Map<String,String> login(HttpServletRequest request){
		Map<String,String> map = new HashMap<>();
		User u  = new User();
		u.setUsername("1234");
		u.setSign("sadfsdafsd");
		u.setAge("18");
		u.setMobilenum("12341234");
		u.setPassword("123412");
		//u.setId("2");
		try {
			userService.add(u);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("u", "456");
		System.out.println("1234");
		
		return map;
	}
}
