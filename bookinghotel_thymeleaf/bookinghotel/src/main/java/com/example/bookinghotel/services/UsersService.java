package com.example.bookinghotel.services;

import com.example.bookinghotel.models.Users;
import com.example.bookinghotel.models.person;
import com.example.bookinghotel.repository.PersonRepository;
import com.example.bookinghotel.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PersonRepository personRepository;

    public List<Users> getAllUsers() {
        return (List<Users>) usersRepository.findAll();
    }

    @Transactional
    public boolean registerUserAndPerson(String email, String password, String role,
                                      String personName, String personAddress, Boolean personSex) {

        Boolean success = true;

        //Check Email exist
        Users existingUser = usersRepository.findByUEmail(email);
        if (existingUser != null) {
            success = false;
        }
        // Insert user
        Users newUser = new Users();
        newUser.setUEmail(email);
        newUser.setUPassword(password);
        newUser.setURole(role);
        Users savedUsers = usersRepository.save(newUser);

        person newPerson = new person();
        newPerson.setPName(personName);
        newPerson.setPAddress(personAddress);
        newPerson.setPSex(personSex);
        newPerson.setUsers(savedUsers);  // Foreign key reference to User
        personRepository.save(newPerson);

        return success;
    }

    public void loginUser(String email, String password) {
        Users checkLogin = usersRepository.findByUEmailAndUPassword(email, password);
        if (checkLogin != null) {
            System.out.println(checkLogin.getUEmail());
            System.out.println(checkLogin.getUPassword());
        } else {
            System.out.println("No user found");
        }
    }
}
