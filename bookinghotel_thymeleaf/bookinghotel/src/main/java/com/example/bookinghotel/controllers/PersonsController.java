package com.example.bookinghotel.controllers;


import com.example.bookinghotel.models.person;
import com.example.bookinghotel.services.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
