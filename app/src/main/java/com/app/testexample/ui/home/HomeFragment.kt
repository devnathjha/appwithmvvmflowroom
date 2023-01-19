package com.app.testexample.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.testexample.R
import com.app.testexample.data.model.AssociatedDrug
import com.app.testexample.databinding.FragmentHomeBinding
import com.app.testexample.db.entity.Drug
import com.app.testexample.utils.State
import com.app.testexample.utils.UtilFunctions
import com.app.testexample.utils.hideView
import com.app.testexample.utils.showView
import com.app.testexample.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var drugAdapter: MedicineAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    var drugList: MutableList<AssociatedDrug> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        drugAdapter = MedicineAdapter(arrayListOf())
        binding.recyclerView.apply {
            addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerView.context,
                    (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = drugAdapter
        }
    }

    private fun setupObserver() {
        drugList.clear()
        homeViewModel.drugList.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                // updates the list.
                list.map {
                    AssociatedDrug(it.dose, it.name, it.strength)
                }
                if(drugList.isNotEmpty()) {
                    Log.e("Devnath",drugList.toString())
                    renderList(drugList)
                }
                else {
                    drugList.clear()
                    onlineDataobserver()
                }

            }
        })

    }

    private fun onlineDataobserver(){
        drugList.clear()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.medicine1.collect {
                    try {
                        when (it) {
                            is State.DataState -> {
                                // (activity as MainActivity).updateProgressView(it)
                                binding.shimmerFrameLayout.apply {
                                    stopShimmer()
                                    hideView()
                                }
                                drugList.clear()

                                // println("kjsakfjf"+it.data.problems[0].Diabetes[0].medications[0].medicationsClasses[0].className[0].associatedDrug2)
                                homeViewModel.clearDruglist()
                                Log.e("HomeFrag","Data cleared")
                                it.data.problems.forEach { problem ->

                                    problem.Diabetes[0].medications[0].medicationsClasses.forEach { medicationsClasse ->
                                        if (medicationsClasse.className[0].associatedDrug != null) {
                                            medicationsClasse.className[0].associatedDrug.forEach { item ->
                                                // Log.d("TAG", "getIndex = " + index + "  Name   ${item.name} Dose ${item.dose} strength ${item.strength}")
                                                drugList.add(item)
                                            }
                                        }
                                        if (medicationsClasse.className[0].associatedDrug2 != null) {
                                            medicationsClasse.className[0].associatedDrug2.forEach { item ->
                                                // Log.d("TAG", "getIndex = " + index + "  Name   ${item.name} Dose ${item.dose} strength ${item.strength}")
                                                drugList.add(item)
                                            }
                                        }
                                        if (medicationsClasse.className2[0].associatedDrug != null) {
                                            medicationsClasse.className2[0].associatedDrug.forEach { item ->
                                                // Log.d("TAG", "getIndex = " + index + "  Name   ${item.name} Dose ${item.dose} strength ${item.strength}")
                                                drugList.add(item)
                                            }
                                        }
                                        if (medicationsClasse.className2[0].associatedDrug2 != null) {
                                            medicationsClasse.className2[0].associatedDrug2.forEach { item ->
                                                // Log.d("TAG", "getIndex = " + index + "  Name   ${item.name} Dose ${item.dose} strength ${item.strength}")
                                                drugList.add(item)
                                            }
                                        }
                                    }

                                }
                                Log.d("TAG", drugList.toString())
                                var index =0
                                var drugs:MutableList<Drug> = arrayListOf()
                                for((index, value) in drugList.withIndex())
                                {
                                    drugs.add(Drug(null,value.name,value.dose,value.strength))
                                }


                                homeViewModel.insertDruglist(drugs)
                                Log.e("HomeFrag","Inserted Data")
                                renderList(drugList)
                                binding.recyclerView.showView()
                            }
                            is State.LoadingState -> {
                                // (activity as MainActivity).updateProgressView(it)
                                binding.shimmerFrameLayout.showView()
                                binding.recyclerView.hideView()
                            }
                            is State.ErrorState -> {
                                // (activity as MainActivity).updateProgressView(it)
                                binding.shimmerFrameLayout.apply {
                                    stopShimmer()
                                    hideView()
                                }
                                UtilFunctions.showToastLong(it.exception.message, requireContext())
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }



    private fun renderList(drugs: List<AssociatedDrug>) {
        /* homeAdapter.addData(users)
         homeAdapter.notifyDataSetChanged()*/
        drugAdapter.addData(drugs)
        drugAdapter.notifyDataSetChanged()

    }
}
