package com.example.bookinghotel.services;

import com.example.bookinghotel.models.Users;
import com.example.bookinghotel.models.hotel;
import com.example.bookinghotel.models.hotelDTO;
import com.example.bookinghotel.repository.HotelRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelsService {

    @Autowired
    private HotelRepository hotelRepository;

    public List<hotel> getAllHotels() {
        List<hotel> hotels = hotelRepository.findAll();
        return hotels;
    }


    public void createNewHotel(hotelDTO hotelDTO, String image){
        hotel newHotel = new hotel();
        newHotel.setHName(hotelDTO.getHName());
        newHotel.setHAddress(hotelDTO.getHAddress());
        newHotel.setHPhone(hotelDTO.getHPhone());
        newHotel.setHStar(hotelDTO.getHStar());
        newHotel.setHImg(image);
        hotelRepository.save(newHotel);
    }
    public hotel getHotelByHID(Integer hotelID) {
        return hotelRepository.findById(hotelID).get();
    }

    public void updateHotel(hotel hotel) {
        hotel.setHStar(hotel.getHStar());
        hotel.setHPhone(hotel.getHPhone());
        hotel.setHAddress(hotel.getHAddress());
        hotel.setHName(hotel.getHName());
        hotel.setHImg(hotel.getHImg());
        hotelRepository.save(hotel);
    }

    public void deleteHotel(Integer hotelID) {
        hotelRepository.deleteById(hotelID);
    }

    //-----------------------------JSON------------------------------//
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<hotel> getBestHotels() {
        String getBestListHotels = "SELECT h FROM hotel h ORDER BY h.HStar DESC";
        List<hotel> resultList = entityManager.createQuery(getBestListHotels, hotel.class)
                .setMaxResults(5)
                .getResultList();
        return resultList;
    }

    @Transactional
    public List<hotel> showSearchHotels(String location) {
        String query = "SELECT h FROM hotel h WHERE LOWER(h.HAddress) LIKE :location";
        List<hotel> resultList = entityManager.createQuery(query, hotel.class)
                .setParameter("location", "%" + location.toLowerCase() + "%")
                .getResultList();
        return resultList;
    }
}
