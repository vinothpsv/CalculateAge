package com.vinoth.alivecor

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.Period
import java.util.*

class CalculateAgeViewModel : ViewModel() {

    private val _calculateAge = MutableLiveData(false)
    val calculateAge: LiveData<Boolean> = _calculateAge

    private val _displayDatePicker = MutableLiveData(false)
    val displayDatePicker: LiveData<Boolean> = _displayDatePicker

    fun calculateAge() {
        _calculateAge.postValue(true)
    }

    fun displayCalenderView() {
        _displayDatePicker.postValue(true)
    }

    lateinit var person: Person

    @SuppressLint("NewApi")
    fun computedAge(date: Long): String {
        val dob = Calendar.getInstance().apply {
            timeInMillis = date
        }
        val today = Calendar.getInstance()
        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (today[Calendar.DAY_OF_MONTH] < dob[Calendar.DAY_OF_MONTH]) {
            age--
        }

        val dobLocalDate = LocalDate.of(dob[Calendar.YEAR], dob[Calendar.MONTH], dob[Calendar.DAY_OF_MONTH])
        val todayLocalDate = LocalDate.of(today[Calendar.YEAR], today[Calendar.MONTH], today[Calendar.DAY_OF_MONTH])
        val period = Period.between(dobLocalDate, todayLocalDate)

        return "${period.years} years, ${period.months} month, ${period.days} days"
    }
}