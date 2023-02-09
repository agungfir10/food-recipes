package com.agungfir.foodrecipes.ui.recipes.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.agungfir.foodrecipes.R
import com.agungfir.foodrecipes.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.agungfir.foodrecipes.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.agungfir.foodrecipes.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.recipes_bottom_sheet.*
import java.util.*


class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesViewModel.readMealAndDietType.asLiveData().observe(requireActivity()) { value ->
            mealTypeChip = value.selectedMealType
            mealTypeChipId = value.selectedMealTypeId
            dietTypeChip = value.selectedDietType
            dietTypeChipId = value.selectedDietTypeId

            Log.i(this::class.java.simpleName, value.selectedMealTypeId.toString())
            Log.i(this::class.java.simpleName, value.selectedDietTypeId.toString())
            udpateMealChip(mealTypeChipId)
            udpateDietChip(dietTypeChipId)
        }
        mealTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            Log.i("CHIP ID", selectedChipId.toString())
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId
        }

        dietTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId
        }

        applyFilterBtn.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )
            val action =
                RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment()
            action.backFromBottomSheet = true
            findNavController().navigate(action)
        }
    }

    private fun udpateDietChip(selectedDietTypeId: Int) {
        if (selectedDietTypeId != 0) {
            try {
                dietTypeChipGroup.findViewById<Chip>(selectedDietTypeId).isChecked = true
            } catch (e: Exception) {
                Log.d(this::class.java.simpleName, e.message.toString())
            }
        }
    }

    private fun udpateMealChip(selectedMealTypeId: Int) {
        if (selectedMealTypeId != 0) {
            try {
                mealTypeChipGroup.findViewById<Chip>(selectedMealTypeId).isChecked = true
            } catch (e: Exception) {
                Log.d(this::class.java.simpleName, e.message.toString())
            }
        }
    }

    @Suppress("UNREACHABLE_CODE")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)

        this.dialog?.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet as View).state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }
}