package com.example.mobileapplications_3rdclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FoodListFragment : Fragment() {

    private lateinit var foodRecyclerView: RecyclerView
    private lateinit var searchInput: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_food_list, container, false)

        foodRecyclerView = view.findViewById(R.id.foodList)
        searchInput = view.findViewById(R.id.searchInput)

        val foodItems = DummyFoodData.getFoodItems() // Fetch the dummy data
        val adapter = FoodItemAdapter(foodItems)

        foodRecyclerView.layoutManager = LinearLayoutManager(context)
        foodRecyclerView.adapter = adapter

        view.findViewById<View>(R.id.backArrow)?.setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }

    companion object {
        fun newInstance() = FoodListFragment()
    }
}