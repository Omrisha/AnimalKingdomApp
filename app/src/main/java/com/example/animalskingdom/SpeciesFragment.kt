package com.example.animalskingdom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animalskingdom.Adapters.FamiliesAdapter
import com.example.animalskingdom.Adapters.SpeciesAdapter
import com.example.animalskingdom.Models.Family
import com.example.animalskingdom.Models.FamilyClass
import com.example.animalskingdom.Models.Specie
import com.example.animalskingdom.Models.SpecieClass
import com.example.animalskingdom.Services.KingdomService
import com.example.animalskingdom.Services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * A simple [Fragment] subclass.
 * Use the [SpeciesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpeciesFragment : Fragment(), SearchView.OnQueryTextListener {
    private val args: SpeciesFragmentArgs by navArgs()

    private lateinit var species: RecyclerView
    private lateinit var speciesAdapter: SpeciesAdapter
    private val speciesDictionary: HashMap<String, Specie> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_species, container, false)

        val familyName = args.familyName
        activity?.title = "$familyName"
        species = view.findViewById(R.id.specie_list)
        
        val serachBox = view.findViewById<SearchView>(R.id.search_box_specie)
        serachBox.setOnQueryTextListener(this)
        
        if (speciesDictionary.isEmpty()) {
            loadFromCloud()
        } else {
            loadLocally()
        }


        return view
    }

    private fun loadFromCloud() {
        val destinationService = ServiceBuilder.buildService(KingdomService::class.java)
        val requestCall = destinationService.getSpecies(args.familyName)

        requestCall.enqueue(object : Callback<SpecieClass> {
            override fun onResponse(call: Call<SpecieClass>, response: Response<SpecieClass>) {
                if (response.isSuccessful) {
                    val familiesList = response.body()!!.Species

                    cacheSpeciesList(familiesList)
                    loadLocally()
                } else {
                    Toast.makeText(
                        context,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<SpecieClass>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadLocally() {
        val list = ArrayList(speciesDictionary.values)
        speciesAdapter = SpeciesAdapter(list as MutableList<Specie>, View.OnClickListener {

        })

        species.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = speciesAdapter
        }

    }

    private fun cacheSpeciesList(speciesList: List<Specie>) {
        for (specie in speciesList) {
            if (specie.AcceptedCommonName == null)
                specie.AcceptedCommonName = "Unknown"
            speciesDictionary[specie.ScientificName] = specie
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        search(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        search(newText)
        return true
    }

    private fun search(text: String?) {
        if (text != null) {
            speciesAdapter.search(text.lowercase(Locale.ROOT)) {
                Toast.makeText(context, "Nothing Found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}