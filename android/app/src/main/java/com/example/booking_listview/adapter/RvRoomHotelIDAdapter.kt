package com.example.booking_listview.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_listview.Hotel_detail
import com.example.booking_listview.R
import com.example.booking_listview.RoomDetail
import com.example.booking_listview.model.Hotel
import com.example.booking_listview.model.OutDataRecycleView_Rooms
import com.example.booking_listview.model.Room

class RvRoomHotelIDAdapter(
    val list_room: List<Room>
) : RecyclerView.Adapter<RvRoomHotelIDAdapter.RoomViewHolder>() {

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RoomViewHolder {
        val viewRoomAvailable = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_rooms_hotel, parent, false)
        return RoomViewHolder(viewRoomAvailable)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.itemView.apply {

            val txtRoomName = findViewById<TextView>(R.id.txtRHCategory)
            val txtStar = findViewById<TextView>(R.id.txtRHStar)
            val imgRoom = findViewById<ImageView>(R.id.imgRoomHotel)

            val room = list_room[position]
            txtRoomName.text = room.ctgname

            val starRating = room.ctgstar.toDouble()
            txtStar.text = "$starRating/5"

            val imageResId = resources.getIdentifier(room.ctgimg.split(".")[0], "drawable", context.packageName)

            if (imageResId != 0) {
                imgRoom.setImageResource(imageResId)
            } else {
                imgRoom.setImageResource(R.drawable.not_found)
            }

            imgRoom.setOnClickListener {
                val context = it.context
                val intent = Intent(context, RoomDetail::class.java)
                intent.putExtra("RoomName", room.ctgname)
                intent.putExtra("RoomStar", room.ctgstar)
                intent.putExtra("RoomDes",room.ctgdes)
                intent.putExtra("RoomPrice",room.ctgprice)
                intent.putExtra("RHotelName", room.ctghid.hname)
                intent.putExtra("RoomImage", imageResId)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = list_room.size
}
