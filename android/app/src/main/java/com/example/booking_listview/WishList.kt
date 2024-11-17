package com.example.booking_listview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_listview.adapter.RvWishListAdapter
import com.example.booking_listview.model.Hotel
import com.example.booking_listview.model.Room

class WishList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var wishlistAdapter: RvWishListAdapter
    private val roomList = mutableListOf<Room>() // Danh sách phòng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wish_list_activity)

        recyclerView = findViewById(R.id.recyclerViewWishlist)

        // Tạo dữ liệu mẫu cho khách sạn và phòng
        val hotel = Hotel("Hotel ABC", "hotel_image", 1, "2313123131", "4.5", "Can Tho", "Description of the hotel")
        val room = Room(1, "Standard", "room_image", 5, 10, "5 stars", "100 USD", hotel, "Room description")

        roomList.add(room) // Thêm phòng vào danh sách phòng

        // Cài đặt Adapter
        wishlistAdapter = RvWishListAdapter(
            roomList,
            removeListener = { room ->
                // Xử lý khi nhấn "Remove"
                roomList.remove(room)
                wishlistAdapter.notifyDataSetChanged() // Cập nhật lại adapter sau khi xóa
            },
            bookingListener = { room ->
                // Xử lý khi nhấn "Booking"
                val intent = Intent(this, Hotel_detail::class.java)
                intent.putExtra("HotelName", room.ctghid.hname) // Truyền tên khách sạn
                intent.putExtra("RoomCategory", room.ctgname) // Truyền loại phòng
                startActivity(intent)
            }
        )

        // Thiết lập RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = wishlistAdapter
    }
}
