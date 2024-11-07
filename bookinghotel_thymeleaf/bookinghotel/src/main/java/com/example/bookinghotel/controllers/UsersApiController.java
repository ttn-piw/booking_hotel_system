package com.example.bookinghotel.controllers;

import com.example.bookinghotel.models.Users;
import com.example.bookinghotel.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path= "users")
public class UsersApiController {

    @Autowired
    private UsersService usersService;

    @GetMapping("")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/test")
    public String testGetEndpoint(){
        return "Test Get end point";
    }

    @GetMapping("/view")
    public String getAllUsersView(ModelMap model) {
        return "index";
    }
}
