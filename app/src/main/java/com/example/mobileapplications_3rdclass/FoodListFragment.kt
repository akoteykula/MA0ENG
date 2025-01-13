package com.example.mobileapplications_3rdclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class FoodListFragment : Fragment() {

    private lateinit var foodRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private val viewModel: FoodListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_food_list, container, false).apply {
            foodRecyclerView = findViewById(R.id.foodList)
            searchView = findViewById(R.id.foodSearch)
        }

        val adapter = FoodItemAdapter(emptyList())
        foodRecyclerView.layoutManager = LinearLayoutManager(context)
        foodRecyclerView.adapter = adapter

        view.findViewById<View>(R.id.backArrow)?.setOnClickListener {
            activity?.onBackPressed()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchQuery(newText ?: "")
                return true
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredFoodItems.collect { foodItems ->
                    adapter.updateFoodList(foodItems)
                }
            }
        }

        return view
    }

    companion object {
        fun newInstance() = FoodListFragment()
    }
}