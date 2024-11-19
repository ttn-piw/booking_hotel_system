package com.example.bookinghotel.services;

import com.example.bookinghotel.models.wishlist;
import com.example.bookinghotel.models.wishlistDTO;
import com.example.bookinghotel.repository.WishlistsRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistsService {
    @Autowired
    private WishlistsRepository wishlistsRepository;

    public List<wishlistDTO> getWishlistByPID(String email) {
        return wishlistsRepository.findWishlistByPID(email);
    }
}
