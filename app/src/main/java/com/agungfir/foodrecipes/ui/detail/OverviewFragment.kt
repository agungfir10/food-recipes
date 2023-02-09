package com.agungfir.foodrecipes.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.agungfir.foodrecipes.R
import com.agungfir.foodrecipes.models.FoodRecipe
import com.agungfir.foodrecipes.utils.Constants.Companion.RECIPES_BUNDLE
import kotlinx.android.synthetic.main.fragment_overview.*
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myBundle = arguments?.getParcelable<FoodRecipe>(RECIPES_BUNDLE)
        ivFood?.load(myBundle?.image.toString())
        tvTitle?.text = myBundle?.title
        tvLikes?.text = myBundle?.aggregateLikes.toString()
        tvMinutes?.text = myBundle?.cookingMinutes.toString()

        val jsoup = Jsoup.parse(myBundle?.summary.toString())
        tvSummary?.text = jsoup.text()

        if (myBundle?.vegetarian == true) {
            ivVegetarian?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            tvVegetarian?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.glutenFree == true) {
            ivGluttenFree?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            tvGluttenFree?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.veryHealthy == true) {
            ivHealthy?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            tvHealthy?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.vegan == true) {
            ivVegan?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            tvVegan?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.dairyFree == true) {
            ivDairyFree?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            tvDairyFree?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.cheap == true) {
            ivCheap?.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            tvCheap?.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

}