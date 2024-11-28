package com.example.bookinghotel.controllers;

import com.example.bookinghotel.models.review;
import com.example.bookinghotel.services.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/")
    public String showReviewsAdmin(Model model) {
        model.addAttribute("reviews", reviewsService.getReviewsService());
        return "reviews/manageReviews.html";
    }

    @GetMapping("/delete")
    public String deleteReviewByID(@RequestParam int id){
        reviewsService.deleteReview(id);
        return "redirect:/api/reviews/";
    }

    @GetMapping("/roomId")
    @ResponseBody
    public Map<String, Object> getReviewsByRoomID(@RequestParam("roomId") int roomId) {
        return reviewsService.getReviewsByRID(roomId);
    }

    @GetMapping("/hotelId")
    @ResponseBody
    public List<review> getReviewsByHID(@RequestParam("hotelId") int hotelId) {
        return reviewsService.getReviewsByHID(hotelId);
    }

    @GetMapping("/bestReviews")
    @ResponseBody
    public List<review> getBestReviews() {
        return reviewsService.getBestReviewsService();
    }

    @PostMapping("/postReview")
    public ResponseEntity<String> postReview(@RequestParam("pid") Integer pid,
                                             @RequestParam("ctgid") Integer ctgid,
                                             @RequestParam("rating") Double rating,
                                             @RequestParam("rating_text") String rating_text) {
        Boolean reviewed = reviewsService.postReview(pid, ctgid, rating, rating_text);
        if (reviewed) {
            return ResponseEntity.ok("Post a new review!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
