package com.example.booking_listview.adapter


import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_listview.databinding.RecycleviewBookedHotelBinding
import com.example.booking_listview.model.Booking



class BookingAdapter(
    val list_booking: List<Booking>
) : RecyclerView.Adapter<BookingAdapter.bookingViewHolder>() {

    inner class bookingViewHolder(val binding: RecycleviewBookedHotelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookingViewHolder {
        val binding = RecycleviewBookedHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return bookingViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: bookingViewHolder, position: Int) {
        val booking = list_booking[position]
        holder.binding.apply {
            Log.d("BOOKINGS_ADAP", booking.toString())
            txtCheckIn.text = "Check-in: ${booking.check_in}"
            txtCheckOut.text = "Check-out: ${booking.check_out}"
            txtRoomName.text = "Type: ${booking.ctgname}"
            txtHotelName.text = booking.hname
            txtTotalMoney.text = "${booking.total_money}$"
        }
    }

    override fun getItemCount(): Int = list_booking.size
}
