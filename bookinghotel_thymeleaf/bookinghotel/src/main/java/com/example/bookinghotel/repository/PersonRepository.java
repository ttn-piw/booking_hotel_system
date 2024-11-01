package com.example.bookinghotel.repository;

import com.example.bookinghotel.models.person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<person, Integer> {

}
