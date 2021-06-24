package com.example.animalskingdom.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.animalskingdom.Models.Specie
import com.example.animalskingdom.R
import com.example.animalskingdom.ViewHolderFactory

class SpeciesAdapter(private val species: MutableList<Specie>,
                     private val OnClickListener: View.OnClickListener) :
    DynamicSearchAdapter<Specie, ViewHolderFactory.SpecieViewHolder>(species){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFactory.SpecieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.specie_item, parent, false)

        view.setOnClickListener(OnClickListener)

        return ViewHolderFactory.SpecieViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolderFactory.SpecieViewHolder, position: Int) {
        return holder.bind(species[position])
    }

    override fun getItemCount(): Int {
        return species.size
    }
}