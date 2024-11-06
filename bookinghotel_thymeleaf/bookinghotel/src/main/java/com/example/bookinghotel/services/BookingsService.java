package com.example.bookinghotel.services;

import com.example.bookinghotel.models.booking;
import com.example.bookinghotel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingsService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public boolean updatePaymentStatus(int bookingId) {
        Optional<booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            booking booking = bookingOptional.get();
            booking.setBPay(true);
            bookingRepository.save(booking);
            return true;
        }
        return false;
    }

}
