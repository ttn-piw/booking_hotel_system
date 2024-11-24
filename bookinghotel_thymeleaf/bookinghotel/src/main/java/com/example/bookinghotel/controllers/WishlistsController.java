package com.example.bookinghotel.controllers;


import com.example.bookinghotel.models.wishlist;
import com.example.bookinghotel.models.wishlistDTO;
import com.example.bookinghotel.services.WishlistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistsController {
    @Autowired
    private WishlistsService wishlistsService;

    @GetMapping("/api/personEmail")
    @ResponseBody
    public List<wishlistDTO> getWishlistsByPersonMail(@RequestParam("personEmail") String personEmail) {
        return wishlistsService.getWishlistByEmail(personEmail);
    }

    @GetMapping("/wishlistPage")
    public String showWishlistsPage(Model model) {
        return "Website/wishlist.html";
    }
    
    @PostMapping("/addToWishlist")
    @ResponseBody
    public ResponseEntity<String> addToWishlist(@RequestParam("pid") int pid, @RequestParam("ctgid") int ctgid) {
        boolean added = wishlistsService.addToWishlist(pid, ctgid);
        if (added) {
            return ResponseEntity.ok("Added to wishlist successfully!!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Failed to add to wishlist!!");
        }
    }

    @PostMapping("/deleteFromWishList")
    @ResponseBody
    public ResponseEntity<String> deleteFromWishList(@RequestParam("pid") int pid, @RequestParam("ctgid") int ctgid) {
        boolean deleted = wishlistsService.deleteFromWishList(pid,ctgid);
        if (deleted) {
            return ResponseEntity.ok("Deleted from wishlist successfully!!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Failed to delete wishlist!!");
        }
    }

}
