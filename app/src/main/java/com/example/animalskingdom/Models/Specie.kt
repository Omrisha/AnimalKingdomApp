package com.example.animalskingdom.Models

import com.example.animalskingdom.Adapters.DynamicSearchAdapter
import java.io.Serializable
import java.util.*

data class Specie(
    var TaxonID: Int,
    var ScientificName: String  = "Unknown",
    var AcceptedCommonName: String  = "Unknown",
    var KingdomName: String,
    var KingdomCommonName: String,
    var ClassName: String,
    var ClassCommonName: String,
    var FamilyName: String,
    var FamilyRank: Int,
    var IsSuperseded: Boolean,
    var ConservationStatus: ConservationStatus,
    var Endemicity: String,
    var TypeReference: String,
    var Synonyms: Any,
    var PestStatus: String,
    var SpeciesEnvironment: String,
    var SpeciesProfileUrl: String,
) : DynamicSearchAdapter.Searchable, Serializable {
    override fun getSearchCriteria(): String {
        return AcceptedCommonName.lowercase(Locale.ROOT)
    }
}