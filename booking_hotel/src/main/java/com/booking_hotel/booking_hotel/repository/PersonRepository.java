package com.booking_hotel.booking_hotel.repository;

import com.booking_hotel.booking_hotel.models.person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<person, Integer> {

}
