package com.example.bookinghotel.services;

import com.example.bookinghotel.models.booking;
import com.example.bookinghotel.repository.BookingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingsService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public boolean updatePaymentStatus(int bookingId) {
        Optional<booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            booking booking = bookingOptional.get();
            booking.setBPay(true);
            bookingRepository.save(booking);
            return true;
        }
        return false;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<booking> getBookingsByPID(Integer id){
        String getBookingsByPID = "SELECT b FROM booking b WHERE b.person.PID = "+id;
        return entityManager.createQuery(getBookingsByPID).getResultList();
    }

    @Transactional
    public Boolean bookedRoom(Integer pid, Integer ctgid, Integer hid, String money) {
        String booked = "INSERT INTO booking (pid, ctgid, hid, bdate, bmoney, bpay) VALUES (:pid, :ctgid, :hid, :bdate, :money, 0)";
        try {
            entityManager.createNativeQuery(booked)
                    .setParameter("pid", pid)
                    .setParameter("ctgid", ctgid)
                    .setParameter("hid", hid)
                    .setParameter("bdate", LocalDateTime.now())
                    .setParameter("money", money)
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
