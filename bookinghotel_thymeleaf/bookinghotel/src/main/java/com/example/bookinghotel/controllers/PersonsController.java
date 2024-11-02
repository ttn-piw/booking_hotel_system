package com.example.bookinghotel.controllers;


import com.example.bookinghotel.services.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/persons")
public class PersonsController {

    @Autowired
    private PersonsService personsService;

    @GetMapping("/")
    public String showPersons(Model model) {
        model.addAttribute("persons", personsService.getAllPersons());
        return "persons/managePersons.html";
    }

    @GetMapping("/delete")
    public String deletePersonByID(@RequestParam int id){
        personsService.deletePerson(id);
        return "redirect:/persons/";
    }
}
