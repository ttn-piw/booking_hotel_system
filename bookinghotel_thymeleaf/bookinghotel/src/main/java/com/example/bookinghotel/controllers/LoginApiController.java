package com.example.bookinghotel.controllers;

import com.example.bookinghotel.models.Users;
import com.example.bookinghotel.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class LoginApiController {
    @Autowired
    UsersService usersService;

    @PostMapping("users/login")
    public ResponseEntity loginUser(@RequestParam("param_email")String email,
                                    @RequestParam("param_password")String password) {
        usersService.loginUser(email, password);

        return new ResponseEntity<>("User Register Sucessfully!", HttpStatus.OK);
    }
}
