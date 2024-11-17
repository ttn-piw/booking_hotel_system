package com.example.bookinghotel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "reviews")
public class review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int review_id;

    @ManyToOne
    @JoinColumn(name = "CTGID",nullable = false)
    private room room;

    @ManyToOne
    @JoinColumn(name = "PID",nullable = false)
    private person person;

    @Column(name = "rating", nullable = false)
    @NotNull(message = "Rating is required")
    private float rating;

    @Column(name = "rating_text", nullable = false)
    @NotNull(message = "Description is required")
    private String rating_text;

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public com.example.bookinghotel.models.room getRoom() {
        return room;
    }

    public void setRoom(com.example.bookinghotel.models.room room) {
        this.room = room;
    }

    public com.example.bookinghotel.models.person getPerson() {
        return person;
    }

    public void setPerson(com.example.bookinghotel.models.person person) {
        this.person = person;
    }

    @NotNull(message = "Rating is required")
    public float getRating() {
        return rating;
    }

    public void setRating(@NotNull(message = "Rating is required") float rating) {
        this.rating = rating;
    }

    public @NotNull(message = "Description is required") String getRating_text() {
        return rating_text;
    }

    public void setRating_text(@NotNull(message = "Description is required") String rating_text) {
        this.rating_text = rating_text;
    }
}
