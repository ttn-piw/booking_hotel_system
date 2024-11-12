package com.example.booking_listview.model

data class Room(
    val ctgid: Int,
    val ctgname: String,
    val ctgimg: String,
    val ctgremain: Int,
    val ctgquantity: Int,
    val ctgstar: String,
    val ctgprice: String,
    val ctghid : Hotel
)
