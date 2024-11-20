package com.example.booking_listview.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.booking_listview.R
import com.example.booking_listview.RoomDetail
import com.example.booking_listview.model.Hotel
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

        holder.roomImage.setOnClickListener {
            val context = it.context
            val hid = wishlist.hid

            fetchRoomFromWishList(context, hid) { roomsList ->
                if (roomsList.isNotEmpty()) {
                    val room = roomsList[0]

                    val intent = Intent(context, RoomDetail::class.java)
                    intent.putExtra("RoomName", room.ctgname)
                    intent.putExtra("RoomStar", room.ctgstar)
                    intent.putExtra("RoomDes", room.ctgdes)
                    intent.putExtra("RoomPrice", room.ctgprice)
                    intent.putExtra("RHotelName", room.ctghid.hname)
                    intent.putExtra("RoomImage", room.ctgimg)
                    context.startActivity(intent)
                } else {
                    Toast.makeText(context, "No rooms available", Toast.LENGTH_SHORT).show()
                }
            }
        }



        holder.removeButton.setOnClickListener {
            val context = it.context
            removeListener(wishlist)

            val sharedPreferences = context.getSharedPreferences("userSession", Context.MODE_PRIVATE)
            val pidSession = sharedPreferences.getString("session_pid","No PID found")

            val ctgid_wishlist = wishlist.ctgid

            Log.d("CTGID_WL", ctgid_wishlist.toString())
            Log.d("PID_WL", pidSession.toString())

            val queue = Volley.newRequestQueue(context)
            val url = "http://10.0.2.2:8080/wishlist/deleteFromWishList"

            val stringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    Log.d("Wishlist_res", response)
                    Toast.makeText(context, "Remove from wishlist successfully!", Toast.LENGTH_SHORT).show()

                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    Toast.makeText(context, "Failed to delete from wishlist", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = mutableMapOf<String, String>()
                    params["pid"] = pidSession.toString()
                    params["ctgid"] = ctgid_wishlist.toString()
                    return params
                }
            }
            queue.add(stringRequest)
            removeListener(wishlist)
        }

        holder.bookingButton.setOnClickListener {
            bookingListener(wishlist)
        }
    }

    override fun getItemCount(): Int = wishlist.size

    private fun fetchRoomFromWishList(context: Context, hid: Int, callback: (List<Room>) -> Unit) {
        Log.d("HID IMG", hid.toString())

        val url = "http://10.0.2.2:8080/rooms/hotelId?hotel_id=$hid"
        val queue = Volley.newRequestQueue(context)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val roomsList = mutableListOf<Room>()
                for (i in 0 until response.length()) {
                    val roomJson = response.getJSONObject(i)
                    val hotelJson = roomJson.getJSONObject("hotels")

                    val hotel = Hotel(
                        hname = hotelJson.getString("hname"),
                        hphone = hotelJson.getString("hphone"),
                        hstar = hotelJson.getString("hstar"),
                        himg = hotelJson.getString("himg"),
                        hid = hotelJson.getInt("hid"),
                        haddress = hotelJson.getString("haddress"),
                        hdes = hotelJson.getString("hdescription")
                    )
                    val room = Room(
                        ctgid = roomJson.getInt("ctgid"),
                        ctgstar = roomJson.getString("ctgstar"),
                        ctgprice = roomJson.getString("ctgprice"),
                        ctgname = roomJson.getString("ctgname"),
                        ctgimg = roomJson.getString("ctgimg"),
                        ctgremain = roomJson.getInt("ctgremain"),
                        ctghid = hotel,
                        ctgquantity = roomJson.getInt("ctgquantity"),
                        ctgdes = roomJson.getString("ctgdescription")
                    )
                    roomsList.add(room)
                }
                // Trả kết quả qua callback
                callback(roomsList)
            },
            { error ->
                Toast.makeText(context, "Failed to load rooms: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonArrayRequest)
    }

}
