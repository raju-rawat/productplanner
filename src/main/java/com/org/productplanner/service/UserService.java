package com.org.productplanner.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.org.productplanner.beans.User;
import com.org.productplanner.repository.UserRepository;

@Service
public class UserService extends CommonService{

	@Autowired
	private UserRepository userRepository;
	
	public boolean validateUser(Map<String,String> loginDetails)
    {
    	String password="";
		System.out.println("Login Data: "+loginDetails);
		try
		{
			password=userRepository.getPassword(loginDetails.get("username"));
		}
		catch(EmptyResultDataAccessException ex)
		{
			return false;
		}
		
		return password.equals(loginDetails.get("password"));//Password is case sensitive
    }
	
    public List<User> getAllUser()
    {
    	return userRepository.getUsers();
    }

    public void addUser(User user)
    {
    	user.setObjid(getNEXTObjId("TABLE_USER"));
    	userRepository.addUser(user);
    	
    }

	@SuppressWarnings("unchecked")
	public void updateUsers(Map<String, Object> userMap) {
		List<String> listOfUserIds=(List<String>) userMap.get("deletedUsers");
    	List<User> listOfUsers=(List<User>) userMap.get("updatedUsers");
    	if(listOfUserIds!=null && !listOfUserIds.isEmpty())
    	{
    		userRepository.delete(formatString(listOfUserIds));
    	}
    	if(listOfUsers!=null && !listOfUsers.isEmpty())
    	{
    		userRepository.update(listOfUsers);
    	}
		
	}
    
}
