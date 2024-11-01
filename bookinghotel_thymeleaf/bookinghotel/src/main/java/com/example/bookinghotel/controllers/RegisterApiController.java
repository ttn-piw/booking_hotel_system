package com.example.bookinghotel.controllers;

import com.example.bookinghotel.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RegisterApiController {

    @Autowired
    UsersService usersService;

    @PostMapping("/users/register")
    public ResponseEntity registerNewUser(@RequestParam("param_email")String email,
                                          @RequestParam("param_password")String password,
                                          @RequestParam("param_role")String role,
                                          @RequestParam("param_name")String name,
                                          @RequestParam("param_address")String address,
                                          @RequestParam("param_sex")Boolean sex) {

        if (!usersService.registerUserAndPerson(email, password, role,name,address,sex))
            return new ResponseEntity("Email has been exist",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("User Register Sucessfully!", HttpStatus.OK);
    }
}
