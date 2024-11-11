package com.example.bookinghotel.services;

import com.example.bookinghotel.models.Users;
import com.example.bookinghotel.models.person;
import com.example.bookinghotel.repository.PersonRepository;
import com.example.bookinghotel.repository.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

        Users existingUser = usersRepository.findByUEmail(email);
        if (existingUser != null) {
            success = false;
        }
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

    public String loginUser(String email, String password) {
        Users checkLogin = usersRepository.findByUEmailAndUPassword(email, password);
        if (checkLogin != null) {
            System.out.println("ID: "+ checkLogin.getUID());
            System.out.println("Email: " + checkLogin.getUEmail());
            System.out.println("Password: " + checkLogin.getUPassword());
            System.out.println("Role: " + checkLogin.getURole());
            return checkLogin.getURole();
        } else {
            System.out.println("No user found");
            return null;
        }
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Integer getUserIdByEmail(String email) {
       String getUserID = "SELECT UID FROM users WHERE UEmail = '" + email + "'";
       entityManager.createNativeQuery(getUserID).getSingleResult();

       return (Integer) entityManager.createNativeQuery(getUserID).getSingleResult();
    }
}
