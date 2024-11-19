package com.example.booking_listview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.booking_listview.adapter.RvWishListAdapter
import com.example.booking_listview.model.Room
import com.example.booking_listview.model.Wishlist_model

class WishList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var wishlistAdapter: RvWishListAdapter
    private val roomList = mutableListOf<Room>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wish_list_activity)

        recyclerView = findViewById(R.id.recyclerViewWishlist)
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false)

        val sharedPreferences = getSharedPreferences("emailSession", MODE_PRIVATE)
        val emailSession = sharedPreferences.getString("session_email","No email found")
        fetchWishlists(emailSession.toString())
    }

    private fun fetchWishlists(emailSession: String) {
        val url = "http://10.0.2.2:8080/wishlist/api/personEmail?personEmail=$emailSession"
        val queue = Volley.newRequestQueue(this)

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val wishList = mutableListOf<Wishlist_model>()
                for (i in 0 until response.length()) {
                    val wishlistJson = response.getJSONObject(i)
                    val wishlist = Wishlist_model(
                        ctgid = wishlistJson.getInt("ctgid"),
                        pid = wishlistJson.getInt("pid"),
                        pname = wishlistJson.getString("pname"),
                        hname = wishlistJson.getString("hname"),
                        rname = wishlistJson.getString("rname"),
                        rimg = wishlistJson.getString("rimage")
                    )
                    wishList.add(wishlist)
                    Log.d("WL: ", wishlist.toString())
                }

                wishlistAdapter = RvWishListAdapter(
                    wishList,
                    removeListener = { wishlist ->
                        wishList.remove(wishlist)
                        wishlistAdapter.notifyDataSetChanged() // Update adapter
                    },
                    bookingListener = { wishlist ->
                        val intent = Intent(this, Hotel_detail::class.java)
                        intent.putExtra("HotelName", wishlist.hname)
                        intent.putExtra("RoomCategory", wishlist.rname)
                        startActivity(intent)
                    }
                )
                recyclerView.adapter = wishlistAdapter
            },
            { error ->
                Toast.makeText(this, "Failed to load hotels: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonArrayRequest)
    }

}
