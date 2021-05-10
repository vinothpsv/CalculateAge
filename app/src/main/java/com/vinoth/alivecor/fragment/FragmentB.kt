package com.vinoth.alivecor.fragment

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.vinoth.alivecor.CalculateAgeViewModel
import com.vinoth.alivecor.R
import com.vinoth.alivecor.databinding.FragmentBBinding

class FragmentB : Fragment() {

    private lateinit var binding: FragmentBBinding
    private val calculateAgeViewModel: CalculateAgeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_b, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.calculateAgeViewModel = calculateAgeViewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.ic_back_arrow)
                setDisplayShowTitleEnabled(false)
                setDisplayShowHomeEnabled(true)
            }
        }
    }
}

@BindingAdapter("firstName")
fun setFirstName(view: TextView?, firstName: String?) {
    view?.text = "First Name : \"${firstName}\""
}

@BindingAdapter("lastName")
fun setLastName(view: TextView?, lastName: String?) {
    view?.text = "Last Name : \"${lastName}\""
}

@BindingAdapter("age")
fun setAge(view: TextView?, age: String?) {
    view?.text = "Age : ${age}"
}