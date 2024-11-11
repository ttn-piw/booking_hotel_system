package com.example.bookinghotel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class HandleLoginSession {

    @GetMapping("")
    public String login() {
        return "redirect:/index.html";
    }
}
