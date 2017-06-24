package com.webserver.controllers;

import com.webserver.domain.UserDTO;
import com.webserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserDTO getUser(@PathVariable("id") int userId) {
	    return userService.findUserById(userId);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDTO login(@RequestBody UserDTO userDTO) {
        return userService.findUserByUsernamePassword(userDTO.getUsername(), userDTO.getPassword());
    }

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public int insertUser(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
	}

}

