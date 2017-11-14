package com.org.productplanner.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.org.productplanner.beans.User;
import com.org.productplanner.queries.Query;

@Service
public class UserService extends CommonService{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public boolean validateUser(Map<String,String> loginDetails)
    {
    	boolean isLogin=false;
    	String password="";
		try{
			System.out.println("Login Data: "+loginDetails);
			password=jdbcTemplate.queryForObject(Query.GET_PASSWORD, new Object[]{loginDetails.get("username")}, String.class);
			if(loginDetails.get("password").equalsIgnoreCase(password))
			{
				isLogin=true;
			}
		}catch(Exception ex)
		{
			System.out.println("Error Occured: "+ex);
		}
		return isLogin;
    }
	
    public List<User> getAllUser()
    {
    	List<User> userList=jdbcTemplate.query(
                "SELECT * FROM TABLE_USER",
                (rs, rowNum) -> new User(rs.getInt("OBJID"),
                        rs.getString("USER_ID"), 
                        rs.getString("USER_NAME"),
                        rs.getString("PASSWORD"),
                        rs.getString("ADDRESS"),
                        rs.getString("PHONE"),
                        rs.getString("STATUS"),
                        rs.getString("USER_TYPE"), 
                        rs.getDate("CREATE_DATE"))
        );
    	return userList;
    }

    
    /**
     * 
     * @param User
     * @return true if user is added successfully
     * else false
     */
    public boolean addUser(User user)
    {
    	user.setObjid(getNEXTObjId("TABLE_USER"));
    	int output=jdbcTemplate.update(Query.ADD_USER, new Object[]
        			{
        					user.getObjid(),
        					user.getUserID(),
        					user.getUserName(),
        					user.getPassword(),
        					user.getAddress(),
        					user.getPhone(),
        					user.getStatus(),
        					user.getUserType(),
        					user.getCreateDate()
            			});
    	
    	return output==1?true:false;
    }
    
}
