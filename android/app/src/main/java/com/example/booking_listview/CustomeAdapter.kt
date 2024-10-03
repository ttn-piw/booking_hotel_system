package com.example.booking_listview

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

//Adapter take data and show to View
class CustomeAdapter(val activity:Activity, val list:List<OutData>): ArrayAdapter<OutData>(activity,R.layout.booking_view){
    var selectedHotel = -1

    override fun getCount(): Int {
        return list.size;
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertXML = activity.layoutInflater
        val rowView = convertXML.inflate(R.layout.booking_view,parent,false)

        val images = rowView.findViewById<ImageView>(R.id.HotelImg)
        val name = rowView.findViewById<TextView>(R.id.HotelName)
        val province = rowView.findViewById<TextView>(R.id.HotelProvince)

        name.text = list[position].Hotel_Name
        province.text = list[position].Hotel_Provice
        images.setImageResource(list[position].Hotel_Img)

        //convert OutData to View
//        if (position == selectedHotel) {
//            list[selectedHotel].toView  .setBackgroundColor(Color.parseColor("#FFDD00"))
//        } else {
//            // Đặt màu nền mặc định
//            rowView.setBackgroundColor(Color.parseColor("#FFFFFF")) // Màu trắng
//        }
        return rowView
    }
}