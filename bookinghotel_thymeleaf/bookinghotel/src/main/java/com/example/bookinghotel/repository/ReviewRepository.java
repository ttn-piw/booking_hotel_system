package com.example.bookinghotel.repository;

import com.example.bookinghotel.models.review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<review, Integer> {
}
