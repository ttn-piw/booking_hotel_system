package com.example.booking_listview.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.booking_listview.databinding.RecycleviewReviewsRoomsBinding
import com.example.booking_listview.model.Review


class RvReviewAdapter(
    val list_review: List<Review>
) : RecyclerView.Adapter<RvReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(val binding: RecycleviewReviewsRoomsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = RecycleviewReviewsRoomsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = list_review[position]
        holder.binding.apply {
            txtUNameRV.text = review.pname
            txtRatingScoreRV.text = "Rating: ${review.rating}"
            txtRatingTextRV.text = review.rating_text
        }
    }

    override fun getItemCount(): Int = list_review.size
}
