package com.booking_hotel.booking_hotel.controllers;

import com.booking_hotel.booking_hotel.models.Users;
import com.booking_hotel.booking_hotel.services.UsersSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UsersApiController {
    @Autowired
    private UsersSerive usersService;


    @GetMapping("/test")
    public String testGetEndpoint(){
        return "Test Get end point";
    }

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }
}
