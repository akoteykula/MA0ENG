package com.example.mobileapplications_3rdclass

object DummyFoodData {
    fun getFoodItems(): List<FoodItem> {
        return listOf(
            FoodItem(
                1,
                "Plov",
                "Uzbekistan's national dish, a flavorful rice pilaf cooked with meat (usually lamb or beef), onions, carrots, and spices.",
                "https://dummyimage.com/720x540/"
            ),
            FoodItem(
                2,
                "Shurpa",
                "Hearty and aromatic soup with chunks of meat (lamb or beef), potatoes, carrots, onions, and herbs.",
                "https://dummyimage.com/720x540/"
            ),
            FoodItem(
                3,
                "Manti",
                "Steamed dumplings filled with seasoned ground meat (usually lamb or beef) and onions, often served with a dollop of yogurt or sour cream.",
                "https://dummyimage.com/720x540/"
            ),
            FoodItem(
                4,
                "Lagman",
                "Hand-pulled noodles served in a savory broth with meat, vegetables, and a blend of spices.",
                "https://dummyimage.com/720x540/"
            ),
            FoodItem(
                5,
                "Samsa",
                "Savory baked pastries filled with meat, onions, and spices, similar to a samosa but with a distinct Uzbek flavor.",
                "https://dummyimage.com/720x540/"
            ),
            FoodItem(
                6,
                "Shashlik",
                "Skewered and grilled meat (lamb, beef, or chicken) marinated in a blend of spices and onions, often served with flatbread and pickled onions.",
                "https://dummyimage.com/720x540/"
            )
        )
    }
}