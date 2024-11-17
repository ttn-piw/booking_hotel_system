package com.example.bookinghotel.services;

import com.example.bookinghotel.models.review;
import com.example.bookinghotel.repository.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewsService {
    @Autowired
    ReviewRepository reviewRepository;

    public List<review> getReviewsService() {
        return reviewRepository.findAll();
    }

    @PersistenceContext
    private EntityManager entityManager;

    public Map<String, Object> getReviewsByRID(Integer rid) {
        String query = "SELECT r.review_id, r.room.CTGName, r.person.PName, r.rating, r.rating_text " +
                "FROM review r WHERE r.room.CTGID = :rid";
        List<Object[]> results = entityManager.createQuery(query)
                .setParameter("rid", rid)
                .getResultList();

        List<String> columnNames = List.of("review_id", "CTGName", "PName", "rating", "rating_text");

        List<Map<String, Object>> data = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> rowMap = new HashMap<>();
            for (int i = 0; i < columnNames.size(); i++) {
                rowMap.put(columnNames.get(i), row[i]);
            }
            data.add(rowMap);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("columns", columnNames);
        response.put("data", data);

        return response;
    }

}
