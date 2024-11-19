package com.example.bookinghotel.controllers;


import com.example.bookinghotel.models.wishlist;
import com.example.bookinghotel.models.wishlistDTO;
import com.example.bookinghotel.services.WishlistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistsController {
    @Autowired
    private WishlistsService wishlistsService;

    @GetMapping("/api/personEmail")
    @ResponseBody
    public List<wishlistDTO> getWishlistsByPersonId(@RequestParam("personEmail") String personEmail) {
        return wishlistsService.getWishlistByPID(personEmail);
    }
}
