package com.example.bookinghotel.controllers;


import com.example.bookinghotel.models.wishlist;
import com.example.bookinghotel.services.WishlistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistsController {
    @Autowired
    private WishlistsService wishlistsService;

    @GetMapping("/api/all")
    @ResponseBody
    public List<wishlist> getAllWishlists() {
        return wishlistsService.getAllWishlists();
    }

    @GetMapping("/api/personId")
    @ResponseBody
    public List<wishlist> getWishlistsByPersonId(@RequestParam("personId") int personId) {
        return wishlistsService.getWishlistByPID(personId);
    }
}
