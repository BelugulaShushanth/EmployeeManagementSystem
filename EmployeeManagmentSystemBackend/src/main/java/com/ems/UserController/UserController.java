package com.ems.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ems.UserBean.UserBean;
import com.ems.UserService.UserService;

@CrossOrigin
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/validateUser",  method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> verifyUser(@RequestBody UserBean userBean){
		try {
			Long userId = userService.validateUser(userBean);
			if(userId != null) {
				return new ResponseEntity<String>(""+userId, HttpStatus.FOUND);
			}
			else {
				return new ResponseEntity<String>("User is invalid", HttpStatus.NOT_FOUND);
			}
		
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<String>("Network error! unable to validate user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
