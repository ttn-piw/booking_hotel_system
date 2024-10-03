package com.booking_hotel.booking_hotel.services;

import com.booking_hotel.booking_hotel.models.Users;
import com.booking_hotel.booking_hotel.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersSerive {
    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getAllUsers() {
        return (List<Users>) usersRepository.findAll();
    }

}
