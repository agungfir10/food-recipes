package com.agungfir.foodrecipes.network

import com.agungfir.foodrecipes.models.FoodJoke
import com.agungfir.foodrecipes.models.FoodRecipeResponse
import com.agungfir.foodrecipes.network.api.FoodRecipesApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipeResponse> =
        foodRecipesApi.getRecipes(queries)

    suspend fun searchRecipes(queries: Map<String, String>): Response<FoodRecipeResponse> =
        foodRecipesApi.searchRecipes(queries)

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> =
        foodRecipesApi.getFoodJoke(apiKey)
}