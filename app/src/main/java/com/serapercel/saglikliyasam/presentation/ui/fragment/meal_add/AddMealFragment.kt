package com.serapercel.saglikliyasam.presentation.ui.fragment.meal_add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.serapercel.saglikliyasam.databinding.FragmentAddMealBinding
import com.serapercel.saglikliyasam.model.Meal
import com.serapercel.saglikliyasam.util.createDailyWorkRequest

class AddMealFragment : Fragment() {

    private lateinit var viewModel: MealAddViewModel


    private var _binding: FragmentAddMealBinding? = null
    private val binding get() = _binding!!

    private var chosenHour = 0
    private var chosenMin = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val descriptionText = binding.editTextMeal
        val button = binding.addButton
        val timePicker = binding.timePicker

        viewModel = ViewModelProvider(requireActivity()).get(MealAddViewModel::class.java)

        timePicker.setOnTimeChangedListener { _, hour, minute ->
            chosenHour = hour
            chosenMin = minute
        }

        button.setOnClickListener {

            createDailyWorkRequest(
                chosenHour, chosenMin,
                descriptionText.text.toString(), requireContext()
            )

            viewModel.storeInSQLite(
                Meal(
                    descriptionText.text.toString(),
                    "$chosenHour:$chosenMin"
                )
            )

            Toast.makeText(requireContext(), "Öğün Hatırlatıcı Oluşturuldu!", Toast.LENGTH_SHORT)
                .show()

            val action =
                AddMealFragmentDirections.actionAddMealFragmentToMealsFragment()
            findNavController().navigate(action)
        }
    }

}