package com.app.testexample.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.testexample.R
import com.app.testexample.data.model.AssociatedDrug
import com.app.testexample.databinding.FragmentDetailsBinding
import com.app.testexample.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModels()
    //private lateinit var userDetails: User
    private  var drugDetail: AssociatedDrug?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            drugDetail = it.getParcelable(USER_DETAILS)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drugDetail?.let {
            binding.detailsTxt.text =
                "${it.name}  ${it.dose} ${it.strength}"
        }
    }

    companion object {
        const val USER_DETAILS = "drugDetail"
    }
}
