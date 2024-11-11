package com.booking_hotel.booking_hotel.repository;

import com.booking_hotel.booking_hotel.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUEmail(String email);
    Users findByUEmailAndUPassword(String email, String password);
}
