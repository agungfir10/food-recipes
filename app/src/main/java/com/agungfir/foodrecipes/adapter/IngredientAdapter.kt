package com.agungfir.foodrecipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.agungfir.foodrecipes.R
import com.agungfir.foodrecipes.models.ExtendedIngredient
import com.agungfir.foodrecipes.utils.Constants.Companion.BASE_IMAGE_URL
import kotlinx.android.synthetic.main.ingredient_row_layout.view.*

class IngredientAdapter :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    private var ingredients: List<ExtendedIngredient> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(ingredient: ExtendedIngredient) {
            itemView.ivIngredient.load(BASE_IMAGE_URL + ingredient.image)
            itemView.tvIngredientName.text = ingredient.name
            itemView.tvAmount.text = ingredient.amount.toString()
            itemView.tvUnit.text = ingredient.unit.toString()
            itemView.tvConsistency.text = ingredient.consistency
            itemView.tvOriginal.text = ingredient.original
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientAdapter.ViewHolder {
        return IngredientAdapter.ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredient_row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IngredientAdapter.ViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount(): Int = ingredients.size

    fun setData(ingredients: List<ExtendedIngredient>) {
        val recipesDiffUtil = RecipesDiffUtil(this.ingredients, ingredients)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        this.ingredients = ingredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}