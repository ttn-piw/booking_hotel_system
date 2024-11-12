package com.example.bookinghotel.repository;

import com.example.bookinghotel.models.compositeKey_WL;
import com.example.bookinghotel.models.wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistsRepository extends JpaRepository<wishlist, compositeKey_WL> {
    List<wishlist> findByPID(Integer pid);
}
