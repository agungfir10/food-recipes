package com.agungfir.foodrecipes.database

import androidx.room.TypeConverter
import com.agungfir.foodrecipes.models.FoodRecipe
import com.agungfir.foodrecipes.models.FoodRecipeResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipesToString(foodRecipeResponse: FoodRecipeResponse): String {
        return gson.toJson(foodRecipeResponse)
    }

    @TypeConverter
    fun stringToFoodRecipes(data: String): FoodRecipeResponse {
        val listType = object : TypeToken<FoodRecipeResponse>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String = gson.toJson(foodRecipe)

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe = gson.fromJson(data, FoodRecipe::class.java)
}