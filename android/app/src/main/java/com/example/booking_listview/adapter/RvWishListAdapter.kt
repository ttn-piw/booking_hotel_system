package com.example.booking_listview.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_listview.R
import com.example.booking_listview.model.Room

class RvWishListAdapter(
    private val roomList: MutableList<Room>,
    private val removeListener: (Room) -> Unit, // Listener cho hành động xóa
    private val bookingListener: (Room) -> Unit // Listener cho hành động đặt phòng
) : RecyclerView.Adapter<RvWishListAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hotelImage: ImageView = itemView.findViewById(R.id.imgHotel)
        val hotelName: TextView = itemView.findViewById(R.id.txtHotelName)
        val roomCategory: TextView = itemView.findViewById(R.id.txtRCategory)
        val removeButton: Button = itemView.findViewById(R.id.btnRemoveFromWishlist)
        val bookingButton: Button = itemView.findViewById(R.id.btnBooking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_wish_list, parent, false)
        return WishlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val room = roomList[position]
        val hotel = room.ctghid

        holder.hotelName.text = hotel.hname
        holder.roomCategory.text = room.ctgname
        val imageResId = holder.itemView.context.resources.getIdentifier(hotel.himg, "drawable", holder.itemView.context.packageName)
        holder.hotelImage.setImageResource(imageResId)

        holder.removeButton.setOnClickListener {
            removeListener(room)
        }

        holder.bookingButton.setOnClickListener {
            bookingListener(room)
        }
    }

    override fun getItemCount(): Int = roomList.size
}
