package com.example.bookinghotel.controllers;

import com.example.bookinghotel.models.review;
import com.example.bookinghotel.services.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/reviews")
public class ReviewsController {
    @Autowired
    ReviewsService reviewsService;

    @GetMapping("")
    @ResponseBody
    public List<review> getReviews() {
        return reviewsService.getReviewsService();
    }

    @GetMapping("/roomId")
    @ResponseBody
    public Map<String, Object> getReviewsByRoomID(@RequestParam("roomId") int roomId) {
        return reviewsService.getReviewsByRID(roomId);
    }

    @GetMapping("/bestReviews")
    @ResponseBody
    public List<review> getBestReviews() {
        return reviewsService.getBestReviewsService();
    }
}
