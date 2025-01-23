package com.example.mobileapplications_3rdclass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FoodListViewModel : ViewModel() {

    private val _foodItems = MutableStateFlow(DummyFoodData.getFoodItems())
    val foodItems: StateFlow<List<FoodItem>> = _foodItems

    private val _searchQuery = MutableStateFlow("")

    val filteredFoodItems: StateFlow<List<FoodItem>> = _searchQuery
        .map { query -> filterFoodItems(query) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _foodItems.value
        )

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun filterFoodItems(query: String): List<FoodItem> {
        return if (query.length < 3) {
            _foodItems.value
        } else {
            _foodItems.value.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
    }
}