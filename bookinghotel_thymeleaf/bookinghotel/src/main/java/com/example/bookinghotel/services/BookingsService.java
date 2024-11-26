package com.example.bookinghotel.services;

import com.example.bookinghotel.models.booking;
import com.example.bookinghotel.repository.BookingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
        String getBookingsByPID = "SELECT b.hotel.HName, b.room.CTGName, b.BMoney, b.BCheckIn,b.BCheckOut FROM booking b WHERE b.person.PID = "+id;
        return entityManager.createQuery(getBookingsByPID).getResultList();
    }

    @Transactional
    public List<booking> getBookingsByPIDWeb(Integer id){
        String getBookingsByPID = "SELECT b.person.PID,b.room.CTGID,b.hotel.HName,b.room.CTGImg, b.room.CTGName, b.BMoney, b.BCheckIn,b.BCheckOut FROM booking b WHERE b.person.PID = "+id;
        return entityManager.createQuery(getBookingsByPID).getResultList();
    }

    @Transactional
    public Boolean bookedRoom(Integer pid, Integer ctgid, Integer hid, String money, LocalDate checkInDate, LocalDate checkOutDate) {
        String booked = "INSERT INTO booking (pid, hid, ctgid, bdate, bmoney, bpay, bcheck_in, bcheck_out) " +
                "VALUES (:pid, :hid, :ctgid, :bdate, :money, 0, :bcheck_in, :bcheck_out)";
        try {
            entityManager.createNativeQuery(booked)
                    .setParameter("pid", pid)
                    .setParameter("hid", hid)
                    .setParameter("ctgid", ctgid)
                    .setParameter("bdate", LocalDateTime.now())
                    .setParameter("money", new BigDecimal(money))
                    .setParameter("bcheck_in", checkInDate)
                    .setParameter("bcheck_out", checkOutDate)
                    .executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }



}
