package com.example.animalskingdom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animalskingdom.Adapters.KingdomAdapter
import com.example.animalskingdom.Models.Kingdom
import com.example.animalskingdom.Models.KingdomClass
import com.example.animalskingdom.Services.KingdomService
import com.example.animalskingdom.Services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [KingdomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KingdomFragment : Fragment() {
    private lateinit var kingdoms: RecyclerView
    private lateinit var kingdomsAdapter: KingdomAdapter
    private val kingdomDictionary: HashMap<String, Kingdom> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_kingdom, container, false)

        activity?.title = "Animals Kingdom"
        kingdoms = view.findViewById(R.id.kingdom_list)

        if (kingdomDictionary.isEmpty()) {
            loadFromCloud()
        } else {
            loadLocally()
        }

        return view
    }

    private fun loadFromCloud() {
        val destinationService = ServiceBuilder.buildService(KingdomService::class.java)
        val requestCall = destinationService.getKingdoms()

        requestCall.enqueue(object : Callback<KingdomClass> {
            override fun onResponse(call: Call<KingdomClass>, response: Response<KingdomClass>) {
                if (response.isSuccessful) {
                    val kingdomsList = response.body()!!.Class

                    cacheKingdomList(kingdomsList)
                    loadLocally()
                } else {
                    Toast.makeText(
                        context,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<KingdomClass>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadLocally() {
        val kingdomList = ArrayList(kingdomDictionary.values)
        kingdomsAdapter = KingdomAdapter(kingdomList, View.OnClickListener {
            val kingdomClassName = it.findViewById<TextView>(R.id.class_name_text).text.toString()
            val action = KingdomFragmentDirections.actionKingdomFragmentToFamilyFragment(kingdomClassName)
            view?.findNavController()?.navigate(action)
        })

        kingdoms.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = kingdomsAdapter
        }

    }

    private fun cacheKingdomList(kingdomList: List<Kingdom>) {
        for (kingdom in kingdomList) {
            kingdomDictionary[kingdom.ClassName] = kingdom
        }
    }
}