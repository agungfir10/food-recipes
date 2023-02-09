package com.agungfir.foodrecipes.models

import com.google.gson.annotations.SerializedName

data class FoodRecipeResponse(
    @SerializedName("number")
    val number: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<FoodRecipe>,
    @SerializedName("totalResults")
    val totalResults: Int
)