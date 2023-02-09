package com.agungfir.foodrecipes.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.agungfir.foodrecipes.models.FoodRecipe
import com.agungfir.foodrecipes.utils.Constants

@Entity(tableName = Constants.FAVORITES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var foodRecipe: FoodRecipe
)