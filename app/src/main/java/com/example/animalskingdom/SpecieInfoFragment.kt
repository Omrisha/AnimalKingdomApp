package com.example.animalskingdom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs

/**
 * A simple [Fragment] subclass.
 * Use the [SpecieInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpecieInfoFragment : Fragment() {
    private val args: SpecieInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_specie_info, container, false)

        val speice = args.specieInfo

        val scientific_name_text_field = view.findViewById<TextView>(R.id.scientific_name_text_field)
        scientific_name_text_field.text = speice.ScientificName

        val accepted_common_name_text_field = view.findViewById<TextView>(R.id.accepted_common_name_text_field)
        accepted_common_name_text_field.text = speice.AcceptedCommonName

        return view
    }
}