package com.example.bookinghotel.controllers;


import com.example.bookinghotel.models.person;
import com.example.bookinghotel.services.PersonsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/persons")
public class PersonsController {

    @Autowired
    private PersonsService personsService;

    @GetMapping("")
    public String showPersons(Model model) {
        model.addAttribute("persons", personsService.getAllPersons());
        return "persons/managePersons.html";
    }


    @GetMapping("/delete")
    public String deletePersonByID(@RequestParam int id){
        personsService.deletePerson(id);
        return "redirect:/persons";
    }

    @GetMapping("/getPID/personEmail")
    @ResponseBody
    public List<person> getPID(@RequestParam("personEmail") String email){
        return personsService.getPIDFromEmail(email);
    }

    @GetMapping("/update")
    public String updatePerson(HttpSession session, Model model){
        String userEmail = (String) session.getAttribute("userEmail");
        model.addAttribute("userEmail", userEmail);
        return "Website/update_customer.html";
    }

    @PutMapping("/api/update")
    public ResponseEntity<String> updateUser(
            @RequestParam Integer param_id,
            @RequestParam String param_name,
            @RequestParam Boolean param_sex,
            @RequestParam String param_address) {
        try {
            personsService.updateUser(param_id, param_name, param_sex, param_address);
            return ResponseEntity.ok("User information updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update user: " + e.getMessage());
        }
    }

}
