package com.example.bookinghotel.repository;

import com.example.bookinghotel.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUEmail(String email);
    Users findByUEmailAndUPassword(String email, String password);
    Users findByURole(String role);
}
