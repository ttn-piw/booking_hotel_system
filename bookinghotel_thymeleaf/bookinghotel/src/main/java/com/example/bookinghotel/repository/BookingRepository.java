package com.example.bookinghotel.repository;

import com.example.bookinghotel.models.booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<booking,Integer> {
    booking findByBID(Integer id);
}
