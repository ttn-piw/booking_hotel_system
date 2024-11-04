package com.example.bookinghotel.services;

import com.example.bookinghotel.models.hotel;
import com.example.bookinghotel.models.room;
import com.example.bookinghotel.models.roomDTO;
import com.example.bookinghotel.repository.HotelRepository;
import com.example.bookinghotel.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomsService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<room> getAllRooms(){
        List<room> rooms =  roomRepository.findAll();
        return rooms;
    }
    public room getRoomByCTGID(Integer roomID) {
        return roomRepository.findById(roomID).get();
    }

    public void createNewRoom(roomDTO roomDTO, String imagePath) {
        // Find the hotel entity by HID
        hotel associatedHotel = hotelRepository.findById(roomDTO.getHID())
                .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + roomDTO.getHID()));

        room newRoom = new room();
        newRoom.setCTGName(roomDTO.getCTGName());
        newRoom.setCTGQuantity(roomDTO.getCTGQuantity());
        newRoom.setCTGRemain(roomDTO.getCTGQuantity());
        newRoom.setCTGStar(roomDTO.getCTGStar());
        newRoom.setCTGImg(imagePath);
        newRoom.setHotels(associatedHotel);

        roomRepository.save(newRoom);
    }

    @Transactional
    public Integer takeHIDFromRoom(Integer CTGID) {
        String query = "SELECT HID FROM category WHERE CTGID = :CTGID";
        return (Integer) entityManager.createNativeQuery(query)
                .setParameter("CTGID", CTGID)
                .getSingleResult();
    }
    public void updateRoom(room updatedRoom, Integer id) {
        room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with ID: " + id));

        existingRoom.setCTGName(updatedRoom.getCTGName());
        existingRoom.setCTGQuantity(updatedRoom.getCTGQuantity());
        existingRoom.setCTGRemain(updatedRoom.getCTGRemain());
        existingRoom.setCTGStar(updatedRoom.getCTGStar());

        // Check if a new image is provided; if so, update it
        if (updatedRoom.getCTGImg() != null && !updatedRoom.getCTGImg().isEmpty()) {
            existingRoom.setCTGImg(updatedRoom.getCTGImg());
        }

        Integer hotelId = takeHIDFromRoom(id);
        hotel associatedHotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + hotelId));
        existingRoom.setHotels(associatedHotel);

        roomRepository.save(existingRoom);
    }

    public void deleteRoom(Integer roomID) {
        roomRepository.deleteById(roomID);
    }

}
