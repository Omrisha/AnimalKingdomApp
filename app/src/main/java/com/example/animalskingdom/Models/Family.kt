package com.example.animalskingdom.Models

import com.example.animalskingdom.Adapters.DynamicSearchAdapter
import java.io.Serializable
import java.util.*

data class Family(
    val KingdomName: String,
    val KingdomCommonName: String,
    val ClassName: String,
    val ClassCommonName: String,
    val FamilyName: String,
    val FamilyCommonName: String,
    val FamilyRank: Int,
    val SpeciesUrl: String
) : DynamicSearchAdapter.Searchable, Serializable {
    override fun getSearchCriteria(): String {
        return FamilyCommonName.lowercase(Locale.ROOT)
    }
}