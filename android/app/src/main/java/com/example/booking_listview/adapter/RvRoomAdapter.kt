package com.example.booking_listview.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.booking_listview.Hotel_detail
import com.example.booking_listview.R
import com.example.booking_listview.RoomDetail
import com.example.booking_listview.WishList
import com.example.booking_listview.model.Hotel
import com.example.booking_listview.model.OutDataRecycleView_Rooms
import com.example.booking_listview.model.Room

class RvRoomAdapter(
    val list_room: List<Room>
) : RecyclerView.Adapter<RvRoomAdapter.RoomViewHolder>() {

    inner class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RoomViewHolder {
        val viewHotel = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_rooms, parent, false)
        return RoomViewHolder(viewHotel)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.itemView.apply {
            val txtRHotelName = findViewById<TextView>(R.id.txtRHotelName)
            val txtRoomName = findViewById<TextView>(R.id.txtRCategory)
            val txtRoomPrice = findViewById<TextView>(R.id.txtRPrice)
            val txtStar = findViewById<TextView>(R.id.txtRStar)
            val imgRoom = findViewById<ImageView>(R.id.imgRoom)
            val btnAddToWishList = findViewById<ImageView>(R.id.btnAddToWishList)

            val room = list_room[position]

            txtRHotelName.text = room.ctghid.hname
            txtRoomName.text = "${room.ctgname} room"

            val priceMax = room.ctgprice.toInt() + 500;
            txtRoomPrice.text = "${room.ctgprice} $ - ${priceMax.toString()}$"

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
                intent.putExtra("RHotelName", txtRHotelName.text.toString())
                intent.putExtra("RoomImage", imageResId)
                context.startActivity(intent)
            }

            val room_ctgid = room.ctgid;
            val sharedPreferences = context.getSharedPreferences("userSession", Context.MODE_PRIVATE)
            val pidSession = sharedPreferences.getString("session_pid","No PID found")

            btnAddToWishList.setOnClickListener {
                val context = it.context
                val intent = Intent(context, WishList::class.java)
                Log.d("SESSION_ROOM: ",pidSession.toString())
                Log.d("CTG_ID: ",room_ctgid.toInt().toString())

                val pidSession = pidSession.toString()
                val roomCtgid = room_ctgid.toString()

                val queue = Volley.newRequestQueue(context)
                val url = "http://10.0.2.2:8080/wishlist/addToWishlist"

                val stringRequest = object : StringRequest(Method.POST, url,
                    Response.Listener { response ->
                        Log.d("Wishlist_res", response)
                        Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show()

                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        Toast.makeText(context, "Failed to add to Wishlist", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = mutableMapOf<String, String>()
                        params["pid"] = pidSession
                        params["ctgid"] = roomCtgid
                        return params
                    }
                }

                queue.add(stringRequest)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = list_room.size
}
