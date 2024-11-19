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

    public List<wishlistDTO> getWishlistByEmail(String email) {
        return wishlistsRepository.findWishlistByEmail(email);
    }

    public boolean addToWishlist(int pid, int ctgid){
        wishlist wishlist = new wishlist();
        wishlist.setPID(pid);
        wishlist.setCTGID(ctgid);

        wishlistsRepository.save(wishlist);
        return true;
    }
}
