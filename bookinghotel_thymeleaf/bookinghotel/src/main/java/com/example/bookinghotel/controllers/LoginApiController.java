package com.example.bookinghotel.controllers;

import com.example.bookinghotel.models.Users;
import com.example.bookinghotel.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/v1")
public class LoginApiController {
    @Autowired
    UsersService usersService;

    @PostMapping("users/login")
    public ResponseEntity<String> loginUser(@RequestParam("param_email") String email,
                                            @RequestParam("param_password") String password, HttpSession httpSession) {
        String role = usersService.loginUser(email, password);

        if (role == null) {
            return new ResponseEntity<>("Invalid login", HttpStatus.UNAUTHORIZED);
        } else {
            Integer userId = usersService.getUserIdByEmail(email);
            System.out.println("USER_ID: "+userId);

            httpSession.setAttribute("userRole", role);
            httpSession.setAttribute("isLoggedIn", true);
            httpSession.setAttribute("userId", userId); // Lưu userId vào session

            if (role.equals("admin")) {
                return new ResponseEntity<>("Go to admin page", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Redirect user page", HttpStatus.OK);
            }
        }
    }

    @PostMapping("users/logout")
    public ResponseEntity<String> logoutUser(HttpSession httpSession) {
        Integer userId = (Integer) httpSession.getAttribute("userId");
        System.out.println("USER_ID before log_out: "+userId);
        httpSession.invalidate();
        System.out.println("USER_ID after destroy session: "+userId);
        return new ResponseEntity<>("Log out successfully!", HttpStatus.OK);
    }
}
