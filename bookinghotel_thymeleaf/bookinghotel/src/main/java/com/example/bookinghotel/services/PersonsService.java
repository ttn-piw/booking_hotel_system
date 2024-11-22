package com.example.bookinghotel.services;

import com.example.bookinghotel.models.Users;
import com.example.bookinghotel.models.person;
import com.example.bookinghotel.repository.PersonRepository;
import com.example.bookinghotel.repository.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonsService {
    @Autowired
    private PersonRepository personRepository;

    public List<person> getAllPersons() {
        return personRepository.findAll();
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deletePerson(Integer personID) {
        String deletePersonInUsers = "DELETE u, p " +
                "FROM users u " +
                "JOIN person p ON u.UID = p.UID " +
                "WHERE p.PID = " + personID;
        entityManager.createNativeQuery(deletePersonInUsers).executeUpdate();
    }

    @Transactional
    public List<person> getPIDFromEmail(String email) {
        String getPID = "SELECT p.PID, p.PName, p.PAddress FROM person p JOIN Users u ON u.UID = p.users.UID WHERE u.UEmail = :email";
            return entityManager.createQuery(getPID)
                    .setParameter("email", email)
                    .getResultList();

    }

}
