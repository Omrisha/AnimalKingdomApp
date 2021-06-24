package com.example.animalskingdom.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animalskingdom.Models.Kingdom
import com.example.animalskingdom.R

class KingdomAdapter(private val kingdoms: List<Kingdom>, private val OnClickListener: View.OnClickListener) :
    RecyclerView.Adapter<KingdomAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var className = itemView.findViewById<TextView>(R.id.class_name_text)
        var commonClassName = itemView.findViewById<TextView>(R.id.common_class_name_text)

        fun bind(kingdom: Kingdom) {
            className.text = kingdom.ClassName
            commonClassName.text = kingdom.ClassCommonName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kingdom_item, parent, false)

        view.setOnClickListener(OnClickListener)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(kingdoms[position])
    }

    override fun getItemCount(): Int {
        return kingdoms.size
    }
}