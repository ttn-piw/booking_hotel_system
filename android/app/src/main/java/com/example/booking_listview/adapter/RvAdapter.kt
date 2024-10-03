package com.example.booking_listview.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_listview.Hotel_detail
import com.example.booking_listview.model.OutDataRecycleView
import com.example.booking_listview.R

class RvAdapter(
    val list_hotel: List<OutDataRecycleView>
) : RecyclerView.Adapter<RvAdapter.HotelViewHolder>() {

    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        // Inflate XML to view
        val viewHotel = LayoutInflater.from(parent.context).inflate(R.layout.activity_recycle_best_seller, parent, false)
        return HotelViewHolder(viewHotel)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.itemView.apply {
            val txtHotelName = findViewById<TextView>(R.id.txtHotelName)
            val txtStar = findViewById<TextView>(R.id.txtStar)
            val txtPrice = findViewById<TextView>(R.id.txtPrice)
            val imgHotel = findViewById<ImageView>(R.id.imgHotel)

            val hotel = list_hotel[position]

            txtHotelName.text = hotel.HotelName
            txtStar.text = hotel.HotelStar
            txtPrice.text = hotel.HotelPrice
            imgHotel.setImageResource(hotel.Image)

            imgHotel.setOnClickListener {
                val context = it.context
                val intent = Intent(context, Hotel_detail::class.java)
                intent.putExtra("HotelName", hotel.HotelName)
                intent.putExtra("HotelStar", hotel.HotelStar)
                intent.putExtra("HotelPrice", hotel.HotelPrice)
                intent.putExtra("HotelImage", hotel.Image)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list_hotel.size
    }
}
