package com.example.animalskingdom

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animalskingdom.Models.Family
import com.example.animalskingdom.Models.Specie

object ViewHolderFactory {
    fun create(view: View, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.family_item -> FamilyViewHolder(view)
            R.layout.specie_item -> SpecieViewHolder(view)
            else -> {
                FamilyViewHolder(view)
            }
        }
    }

    class FamilyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var familyName: TextView
        var commonFamilyName: TextView
        var familyRank: TextView

        init {
            familyName = itemView.findViewById<TextView>(R.id.family_name_text)
            commonFamilyName = itemView.findViewById<TextView>(R.id.common_family_name_text)
            familyRank = itemView.findViewById<TextView>(R.id.family_rank_text)
        }
        fun bind(family: Family) {
            familyName.text = family.FamilyName
            commonFamilyName.text = family.FamilyCommonName
            familyRank.text = "Rank: ${family.FamilyRank}"
        }
    }

    class SpecieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var acceptedName: TextView
        var scientificName: TextView

        init {
            acceptedName = itemView.findViewById<TextView>(R.id.accepted_common_name_text)
            scientificName = itemView.findViewById<TextView>(R.id.scientific_name_text)
        }
        fun bind(specie: Specie) {
            acceptedName.text = specie.AcceptedCommonName
            scientificName.text = specie.ScientificName
        }
    }
}