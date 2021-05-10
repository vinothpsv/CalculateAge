package com.vinoth.alivecor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.vinoth.alivecor.CalculateAgeViewModel
import com.vinoth.alivecor.Person
import com.vinoth.alivecor.R
import com.vinoth.alivecor.databinding.FragmentABinding
import java.text.SimpleDateFormat
import java.util.*

class FragmentA : Fragment() {

    private lateinit var binding: FragmentABinding
    private val calculateAgeViewModel: CalculateAgeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_a, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.calculateAgeViewModel = calculateAgeViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calculateAgeViewModel.calculateAge.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (binding.firstName.text?.isEmpty() == true && binding.lastName.text?.isEmpty() == true && binding.dateOfBirth.text?.isEmpty() == true) {
                    Toast.makeText(
                        requireActivity(),
                        "All fields are mandatory, Please fill in.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    requireActivity().supportFragmentManager.commit {
                        replace<FragmentB>(R.id.mainActivityFragment)
                        addToBackStack("FragmentB")
                    }
                }
            }
        })

        calculateAgeViewModel.displayDatePicker.observe(viewLifecycleOwner, Observer {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                val constraintsBuilder = CalendarConstraints.Builder()
                val calendar = Calendar.getInstance()
                constraintsBuilder.setEnd(calendar.timeInMillis)

                val picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(constraintsBuilder.build())
                    .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                    .build()

                picker.show(
                    requireActivity().supportFragmentManager,
                    FragmentA.javaClass.simpleName
                )
                picker.addOnPositiveButtonClickListener { result ->
                    binding.dateOfBirth.setText(outputDateFormat.format(result))
                    calculateAgeViewModel.person = Person(
                        firstName = binding.firstName.text.toString(),
                        lastname = binding.lastName.text.toString(),
                        age = calculateAgeViewModel.computedAge(result)
                    )
                }
            }
        })
    }

    private val outputDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    companion object {
        const val DATE_FORMAT = "MM-dd-yyyy"
    }
}