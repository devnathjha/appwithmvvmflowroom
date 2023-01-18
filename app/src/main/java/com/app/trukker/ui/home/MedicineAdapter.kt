package com.app.trukker.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.trukker.R
import com.app.trukker.data.model.AssociatedDrug
import com.app.trukker.databinding.MeditemLayoutBinding

class MedicineAdapter(
    private val drugs: ArrayList<AssociatedDrug>
) : RecyclerView.Adapter<MedicineAdapter.DataViewHolder>() {

    class DataViewHolder(meditemLayoutBinding: MeditemLayoutBinding) : RecyclerView.ViewHolder(meditemLayoutBinding.root) {
        private val binding = meditemLayoutBinding
        fun bind(drug: AssociatedDrug) {
            binding.tvName.text = drug.name
            binding.tvDose.text = drug.dose
            binding.tvstrength.text = drug.strength
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view: MeditemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.meditem_layout,
            parent,
            false
        )
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int = drugs.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(drugs[position])
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(drugDetail = drugs[position])
            holder.itemView.findNavController().navigate(action)
        }
    }
    fun addData(list: List<AssociatedDrug>) {
        drugs.addAll(list)
    }

}
