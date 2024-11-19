package com.example.bookinghotel.repository;

import com.example.bookinghotel.models.compositeKey_WL;
import com.example.bookinghotel.models.wishlist;
import com.example.bookinghotel.models.wishlistDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishlistsRepository extends JpaRepository<wishlist, compositeKey_WL> {
    @Query("SELECT new com.example.bookinghotel.models.wishlistDTO(w.PID, w.CTGID, p.PName, r.CTGName, r.CTGImg, h.HName) " +
            "FROM wishlist w " +
            "JOIN w.persons p " +
            "JOIN w.rooms r " +
            "JOIN r.hotels h " +
            "WHERE p.users.UEmail = :email")
    List<wishlistDTO> findWishlistByEmail(@Param("email") String email);
}
