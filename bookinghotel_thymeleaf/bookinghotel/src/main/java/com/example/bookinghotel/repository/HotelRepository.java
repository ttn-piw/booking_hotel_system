package com.example.bookinghotel.repository;

import com.example.bookinghotel.models.hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<hotel,Integer> {
    hotel findByHID(int id);
}
