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
import com.example.booking_listview.model.Hotel
import com.example.booking_listview.model.OutDataRecycleView

class RvAdapter(
    val list_hotel: List<Hotel>
) : RecyclerView.Adapter<RvAdapter.HotelViewHolder>() {

    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
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
            txtHotelName.text = hotel.hname
            txtPrice.text = hotel.hphone

            val starRating = hotel.hstar.toDouble()
            txtStar.text = "$starRating/5"

            val imageResId = resources.getIdentifier(hotel.himg.split(".")[0], "drawable", context.packageName)

            if (imageResId != 0) {
                imgHotel.setImageResource(imageResId)
            } else {
                imgHotel.setImageResource(R.drawable.not_found)
            }

            imgHotel.setOnClickListener {
                val context = it.context
                val intent = Intent(context, Hotel_detail::class.java)
                intent.putExtra("HotelName", hotel.hname)
                intent.putExtra("HotelStar", hotel.hstar)
                intent.putExtra("HotelPrice", hotel.hphone)
                intent.putExtra("HotelImage", hotel.himg)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = list_hotel.size
}


