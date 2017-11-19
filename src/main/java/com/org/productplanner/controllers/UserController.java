package com.org.productplanner.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.org.productplanner.beans.User;
import com.org.productplanner.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/validate",method = RequestMethod.POST)
	public @ResponseBody boolean login(@RequestBody Map<String,String> loginDetails)
	{		
		return userService.validateUser(loginDetails);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<User> getAllUser()
	{
		return userService.getAllUser();		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody void AddUser(@RequestBody User user)
	{
		userService.addUser(user);		
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public @ResponseBody void UpdateUser(@RequestBody Map<String,Object> userMap)
	{
		userService.updateUsers(userMap);	
	}
}
