package com.example.booking_listview.model

data class Room(
    val ctgid: Int,
    val ctgname: String,
    val ctgimg: String,
    val ctgremain: Int,
    val ctgquantity: Int,
    val ctgstar: String,
    val ctgprice: String,
    val ctghid : Hotel,
    val ctgdes: String,
) {
    override fun toString(): String {
        return "Room(ctgid=$ctgid, ctgstar='$ctgstar', ctgprice='$ctgprice', ctgname='$ctgname', ctgimg='$ctgimg', ctgremain=$ctgremain, ctghid=$ctghid, ctgquantity=$ctgquantity, ctgdes=$ctgdes)"
    }
}
