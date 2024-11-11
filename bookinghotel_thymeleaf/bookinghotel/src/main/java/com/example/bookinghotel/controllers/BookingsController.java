package com.example.bookinghotel.controllers;

import com.example.bookinghotel.models.booking;
import com.example.bookinghotel.services.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingsController {

    @Autowired
    private BookingsService bookingsService;

    @GetMapping("")
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingsService.getAllBookings());
        return "bookings/bookingsList.html";
    }


    @PostMapping("/update-payment-status/{id}")
    public ResponseEntity<?> updatePaymentStatus(@PathVariable("id") int bookingId) {
//        System.out.println("booking ID: " + bookingId);
        boolean updated = bookingsService.updatePaymentStatus(bookingId);
        if (updated) {
            System.out.println("Payment status updated successfully for booking ID: " + bookingId);
            return ResponseEntity.ok().build();
        } else {
            System.out.println("Booking not found with ID: " + bookingId);
            return ResponseEntity.notFound().build();
        }
    }

}
