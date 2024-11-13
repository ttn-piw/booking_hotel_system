package com.example.booking_listview.model

data class Hotel(
    val hname: String,
    val himg: String,
    val hid: Int,
    val hphone: String,
    val hstar: String,
    val haddress: String,
    val hdes: String,
) {
    override fun toString(): String {
        return "Hotel(hname='$hname', hphone='$hphone', hstar='$hstar', himg='$himg', hid=$hid, haddress='$haddress', hdes='$hdes')"
    }
}
