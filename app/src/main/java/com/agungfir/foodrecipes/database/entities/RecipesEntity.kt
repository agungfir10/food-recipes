package com.agungfir.foodrecipes.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agungfir.foodrecipes.models.FoodRecipeResponse
import com.agungfir.foodrecipes.utils.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipesResponse: FoodRecipeResponse) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}