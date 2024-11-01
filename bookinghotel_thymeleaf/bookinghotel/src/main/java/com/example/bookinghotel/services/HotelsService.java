package com.example.bookinghotel.services;

import com.example.bookinghotel.models.Users;
import com.example.bookinghotel.models.hotel;
import com.example.bookinghotel.models.hotelDTO;
import com.example.bookinghotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
