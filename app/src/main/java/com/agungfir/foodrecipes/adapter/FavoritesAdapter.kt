package com.agungfir.foodrecipes.adapter

import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.agungfir.foodrecipes.R
import com.agungfir.foodrecipes.database.entities.FavoritesEntity
import com.agungfir.foodrecipes.ui.favorites.FavoriteRecipesFragmentDirections
import com.agungfir.foodrecipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.placeholder_row_layout.view.*

class FavoritesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>(), ActionMode.Callback {

    private var favoriteRecipes = emptyList<FavoritesEntity>()
    private var selectedRecipes = arrayListOf<FavoritesEntity>()
    private var multiSelection = false
    private lateinit var actionMode: ActionMode
    private val viewHolderFavorites = arrayListOf<ViewHolder>()
    private lateinit var rootView: View

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(favoriteEntity: FavoritesEntity) {
            itemView.findViewById<ImageView>(R.id.ivRecipe).load(favoriteEntity.foodRecipe.image)
            itemView.findViewById<TextView>(R.id.tvTitle).tvTitle.text =
                favoriteEntity.foodRecipe.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_recipe_row_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.viewHolderFavorites.add(holder)
        this.rootView = holder.itemView.rootView
        holder.bind(favoriteRecipes[position])
        /**
         * Single click listener
         * */
        holder.itemView.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, favoriteRecipes[position])
            } else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailActivity(
                        favoriteRecipes[position].foodRecipe
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        /**
         * Long click listener
         * */
        holder.itemView.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, favoriteRecipes[position])
                true
            } else {
                multiSelection = false
                false
            }
        }
    }

    override fun getItemCount(): Int = favoriteRecipes.size

    fun setData(newData: List<FavoritesEntity>) {
        val recipesDiffUtil = RecipesDiffUtil(this.favoriteRecipes, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        favoriteRecipes = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        this.actionMode = actionMode!!
        applyStatusBarColor(R.color.darker)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.deleteFavoriteRecipesMenu) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipe(it)
            }
            showSnackbar("${selectedRecipes.size} Recipe/s removed.")
            multiSelection = false
            selectedRecipes.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.colorPrimaryDark)
        this.viewHolderFavorites.forEach {
            changeRecipeStye(it, R.color.white, R.color.lightMediumGray)
        }
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun applySelection(holder: ViewHolder, currentRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStye(holder, R.color.white, R.color.lightMediumGray)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStye(holder, R.color.colorAccent, R.color.colorPrimaryDark)
            applyActionModeTitle()
        }
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> actionMode.finish()
            1 -> {
                actionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> actionMode.title = "${selectedRecipes.size} items selected"
        }
    }

    private fun changeRecipeStye(holder: ViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.rowCardView.setCardBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.itemView.rowCardView.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    private fun showSnackbar(message: String) {
        Snackbar
            .make(rootView, message, Snackbar.LENGTH_SHORT)
            .setAction("Okay") {

            }.show()
    }


    fun clearContextualActionMode() {
        if (this::actionMode.isInitialized) {
            actionMode.finish()
        }
    }
}