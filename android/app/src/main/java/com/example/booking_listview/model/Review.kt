package com.example.booking_listview.model

import android.app.Person

data class Review(
    val review_id: Int,
    val ctgid : Room,
    val pid: Person,
    val rating: String,
    val rating_text: String,
)
