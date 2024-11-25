package com.example.bookinghotel.controllers;

import com.example.bookinghotel.models.booking;
import com.example.bookinghotel.services.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/api/personId")
    @ResponseBody
    public List<booking> getBookingsByPersonId(@RequestParam("personId") int personId) {
        return bookingsService.getBookingsByPID(personId);
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

    @PostMapping("/booked")
    public ResponseEntity<String> bookedRoom( @RequestParam("pid") Integer pid,  // Thay đổi từ int sang Integer
                                              @RequestParam("ctgid") Integer ctgid,
                                              @RequestParam("hid") Integer hid,
                                              @RequestParam("money") String money,
                                              @RequestParam("checkInDate") @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate checkInDate,
                                              @RequestParam("checkOutDate") @DateTimeFormat(pattern = "yyyy/MM/dd") LocalDate checkOutDate) {
        Boolean booked = bookingsService.bookedRoom(pid, ctgid, hid, money, checkInDate, checkOutDate);
        if (booked) {
            return ResponseEntity.ok("Booking successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
