package com.example.mobileapplications_3rdclass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton

class FoodItemAdapter(private val foodList: List<FoodItem>) :
    RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder>() {

    class FoodItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodImage: ImageView = view.findViewById(R.id.foodImage)
        val foodTitle: TextView = view.findViewById(R.id.foodTitle)
        val foodDescription: TextView = view.findViewById(R.id.foodDescription)
        val likeButton: MaterialButton = view.findViewById(R.id.likeButton)
        val shareButton: MaterialButton = view.findViewById(R.id.shareButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        val foodItem = foodList[position]

        Glide.with(holder.itemView.context)
            .load(foodItem.imageUrl)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
            .into(holder.foodImage)

        holder.foodTitle.text = foodItem.name
        holder.foodDescription.text = foodItem.description

        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Clicked item: ${foodItem.id}",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.likeButton.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Like clicked for item: ${foodItem.id}",
                Toast.LENGTH_SHORT
            ).show()
        }

        holder.shareButton.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Share clicked for item: ${foodItem.id}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int = foodList.size
}