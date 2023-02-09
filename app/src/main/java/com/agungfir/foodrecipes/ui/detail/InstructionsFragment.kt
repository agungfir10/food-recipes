package com.agungfir.foodrecipes.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.agungfir.foodrecipes.R
import com.agungfir.foodrecipes.models.FoodRecipe
import com.agungfir.foodrecipes.utils.Constants
import kotlinx.android.synthetic.main.fragment_instructions.*

class InstructionsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_instructions, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myBundle = arguments?.getParcelable<FoodRecipe>(Constants.RECIPES_BUNDLE)

        wvInsructions.webViewClient = object : WebViewClient() {}
        val websiteUrl: String = myBundle?.sourceUrl!!
        wvInsructions.loadUrl(websiteUrl)

    }
}