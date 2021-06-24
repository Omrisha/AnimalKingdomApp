package com.example.animalskingdom.Adapters

import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animalskingdom.Models.Family
import com.example.animalskingdom.R

abstract class DynamicSearchAdapter<T : DynamicSearchAdapter.Searchable, T1 : RecyclerView.ViewHolder>(private val searchableList: MutableList<T>) : RecyclerView.Adapter<T1>(), Filterable{
    private val originalList = ArrayList(searchableList)

    private var onNothingFound: (() -> Unit)? = null

    fun search(str: String?, onNothingFound: (() -> Unit)?) {
        this.onNothingFound = onNothingFound
        filter.filter(str)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                searchableList.clear()
                if (constraint.isNullOrBlank()) {
                    searchableList.addAll(originalList)
                } else {
                    val searchResults = originalList.filter { it.getSearchCriteria().contains(constraint) }
                    searchableList.addAll(searchResults)
                }

                return filterResults.also {
                    it.values = searchableList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (searchableList.isNullOrEmpty())
                    onNothingFound?.invoke()

                notifyDataSetChanged()
            }
        }
    }

    interface Searchable {
        fun getSearchCriteria() : String
    }
}