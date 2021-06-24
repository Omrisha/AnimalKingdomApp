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
import com.example.animalskingdom.Adapters.KingdomAdapter
import com.example.animalskingdom.Models.Family
import com.example.animalskingdom.Models.FamilyClass
import com.example.animalskingdom.Models.KingdomClass
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
 * Use the [FamilyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FamilyFragment : Fragment(), SearchView.OnQueryTextListener {
    private val args: FamilyFragmentArgs by navArgs()

    private lateinit var families: RecyclerView
    private lateinit var familiesAdapter: FamiliesAdapter
    private val familiesDictionary: HashMap<String, Family> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_family, container, false)

        val selectedFamily = args.familyName
        activity?.title = "$selectedFamily"
        families = view.findViewById(R.id.family_list)

        val searchBox = view.findViewById<SearchView>(R.id.search_box)
        searchBox.setOnQueryTextListener(this)

        if (familiesDictionary.isEmpty()) {
            loadFromCloud()
        } else {
            loadLocally()
        }

        return view
    }

    private fun loadFromCloud() {
        val destinationService = ServiceBuilder.buildService(KingdomService::class.java)
        val requestCall = destinationService.getFamilies(args.familyName)

        requestCall.enqueue(object : Callback<FamilyClass> {
            override fun onResponse(call: Call<FamilyClass>, response: Response<FamilyClass>) {
                if (response.isSuccessful) {
                    val familiesList = response.body()!!.Family

                    cacheFamiliesList(familiesList)
                    loadLocally()
                } else {
                    Toast.makeText(
                        context,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<FamilyClass>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadLocally() {
        val list = ArrayList(familiesDictionary.values)
        familiesAdapter = FamiliesAdapter(list as MutableList<Family>, View.OnClickListener {
            val familyName = it.findViewById<TextView>(R.id.family_name_text).text.toString()
            val action = FamilyFragmentDirections.actionFamilyFragmentToSpeciesFragment(familyName)
            view?.findNavController()?.navigate(action)
        })

        families.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = familiesAdapter
        }

    }

    private fun cacheFamiliesList(familiesList: List<Family>) {
        for (family in familiesList) {
            familiesDictionary[family.FamilyName] = family
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
            familiesAdapter.search(text.lowercase(Locale.ROOT)) {
                Toast.makeText(context, "Nothing Found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}