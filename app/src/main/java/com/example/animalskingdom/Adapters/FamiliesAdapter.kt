package com.example.animalskingdom.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animalskingdom.Models.Family
import com.example.animalskingdom.R
import com.example.animalskingdom.ViewHolderFactory

class FamiliesAdapter(private val families: MutableList<Family>,
                      private val OnClickListener: View.OnClickListener) :
    DynamicSearchAdapter<Family, ViewHolderFactory.FamilyViewHolder>(families) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFactory.FamilyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.family_item, parent, false)

        view.setOnClickListener(OnClickListener)

        return ViewHolderFactory.FamilyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolderFactory.FamilyViewHolder, position: Int) {
        return holder.bind(families[position])
    }

    override fun getItemCount(): Int {
        return families.size
    }
}