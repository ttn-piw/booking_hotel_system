package com.example.booking_listview.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_listview.R
import com.example.booking_listview.model.Room
import com.example.booking_listview.model.Wishlist_model

class RvWishListAdapter(
    private val wishlist: MutableList<Wishlist_model>,
    private val removeListener: (Wishlist_model) -> Unit, // Listener for remove action
    private val bookingListener: (Wishlist_model) -> Unit // Listener for booking action
) : RecyclerView.Adapter<RvWishListAdapter.WishlistViewHolder>() {

    inner class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roomImage: ImageView = itemView.findViewById(R.id.imgRoom)
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
        val wishlist = wishlist[position]

        holder.hotelName.text = wishlist.hname
        holder.roomCategory.text = wishlist.rname

        val imageResId = holder.itemView.context.resources.getIdentifier(wishlist.rimg.substringBeforeLast("."), "drawable", holder.itemView.context.packageName)
        Log.d("Image Res ID", "Resource ID for image: $imageResId")
        if (imageResId != 0) {
            holder.roomImage.setImageResource(imageResId)
        } else {
            Log.d("Image Load Error", "Invalid image resource: ${wishlist.rimg}")
        }


        holder.removeButton.setOnClickListener {
            removeListener(wishlist)
        }

        holder.bookingButton.setOnClickListener {
            bookingListener(wishlist)
        }
    }

    override fun getItemCount(): Int = wishlist.size
}
