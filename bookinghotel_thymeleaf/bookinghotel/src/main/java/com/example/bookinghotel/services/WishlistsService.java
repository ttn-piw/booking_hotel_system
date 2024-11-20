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

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public boolean deleteFromWishList(int pid, int ctgid){
        String deleteQuery = "DELETE FROM wishlist WHERE pid = :pid AND ctgid = :ctgid";
        try {
            entityManager.createNativeQuery(deleteQuery)
                    .setParameter("pid", pid)
                    .setParameter("ctgid", ctgid)
                    .executeUpdate();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
