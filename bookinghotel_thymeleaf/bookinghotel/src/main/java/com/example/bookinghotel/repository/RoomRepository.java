package com.example.bookinghotel.repository;

import com.example.bookinghotel.models.room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<room,Integer> {
    room findByCTGID(int id);
}
