package com.example.booking_listview

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.booking_listview.R.*
import com.example.booking_listview.R.id.imgRoom

class MainActivity : AppCompatActivity() {
    lateinit var customeAdapter_var: CustomeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(layout.activity_main)

        addevents()
        val btnSignup = findViewById<Button>(id.btnSignup)
        btnSignup.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
        val btnLogin = findViewById<Button>(id.btnLogin)
        btnLogin.setOnClickListener{
            val intent = Intent(this,Login::class.java )
            startActivity(intent)
        }

        val btnMainpage = findViewById<Button>(id.btnMainpage)
        btnMainpage.setOnClickListener{
            Log.d("MAINPAGE", "PRESSS BUTTON MainPage ")
            val intent = Intent(this,Mainpage::class.java)
            startActivity(intent)
        }

        val btnTestRoom = findViewById<Button>(id.btnTestRoom)
        btnTestRoom.setOnClickListener{
           val intent = Intent(this,RoomDetail::class.java)
            startActivity(intent)
        }
    }

    public fun addevents() {
        show_list_hotel()
        press_verify()
    }
    var list_hotel = mutableListOf<OutData>()
    fun add_data(pHotel_img:Int,pHotel_name:String,pHotel_province:String){

        list_hotel.add(OutData(pHotel_img,pHotel_name,pHotel_province))
    }
    fun show_list_hotel(){
        val list_ac_main = findViewById<ListView>(id.list_ac_main)

        add_data(drawable.hotel1,"Hotel 1","Ha Noi")
        add_data(drawable.hotel2,"Hotel 2","Hai Phong")
        add_data(drawable.hotel3,"Hotel 3","Can Tho")
        add_data(drawable.hotel4,"Hotel 4","Bac Lieu")
        add_data(drawable.hotel5,"Hotel 5","Ho Chi Minh")
        add_data(drawable.hotel1,"Hotel 1","Ha Noi")
        add_data(drawable.hotel2,"Hotel 2","Hai Phong")
        add_data(drawable.hotel3,"Hotel 3","Can Tho")
        add_data(drawable.hotel4,"Hotel 4","Bac Lieu")
        add_data(drawable.hotel5,"Hotel 5","Ho Chi Minh")

        customeAdapter_var = CustomeAdapter(this,list_hotel)
        list_ac_main.adapter = customeAdapter_var
        list_ac_main.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this,"Choose "+list_hotel[i].Hotel_Name,Toast.LENGTH_SHORT).show()
            customeAdapter_var.selectedHotel = i

            Log.d("HOTEL", i.toString())
            customeAdapter_var.notifyDataSetChanged()
        }
    }
    fun press_verify() {
        val btnverify = findViewById<Button>(id.btnverify)
        btnverify.setOnClickListener {
            Log.d("DEBUG", "Setting OnClickListener for btnverify")
            Toast.makeText(this, "Click button", Toast.LENGTH_SHORT).show()

            // Sử dụng Handler để trì hoãn việc gọi finish
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 1000) // Độ trễ 1000 milliseconds (1 giây)
        }
    }


}