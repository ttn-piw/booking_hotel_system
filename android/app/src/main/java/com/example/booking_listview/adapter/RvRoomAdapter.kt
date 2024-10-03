package com.example.booking_listview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_listview.R
import com.example.booking_listview.model.OutDataRecycleView_Rooms

class RvRoomAdapter(
    val list_rooms: List<OutDataRecycleView_Rooms>,
    val onImageClick: (OutDataRecycleView_Rooms) -> Unit // Pass a click listener
) : RecyclerView.Adapter<RvRoomAdapter.RoomsViewHolder>() {

    inner class RoomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val viewRooms = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_rooms, parent, false)
        return RoomsViewHolder(viewRooms)
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        holder.itemView.apply {
            val txtRHotelName = findViewById<TextView>(R.id.txtRHotelName)
            val txtRStar = findViewById<TextView>(R.id.txtRStar)
            val txtRPrice = findViewById<TextView>(R.id.txtRPrice)
            val imgRoom = findViewById<ImageView>(R.id.imgRoom)

            val room = list_rooms[position]
            txtRHotelName.text = room.HotelName
            txtRStar.text = room.RoomStar
            txtRPrice.text = room.RoomPrice
            imgRoom.setImageResource(room.RoomImage)

            // Handle image click
            imgRoom.setOnClickListener {
                onImageClick(room) // Call the click listener with room data
            }
        }
    }

    override fun getItemCount(): Int {
        return list_rooms.size
    }
}
