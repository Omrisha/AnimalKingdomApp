package com.example.animalskingdom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass.
 * Use the [SpecieInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpecieInfoFragment : Fragment() {
    private val args: SpecieInfoFragmentArgs by navArgs()
    private lateinit var scientific_name_text_field: TextView
    private lateinit var accepted_common_name_text_field: TextView
    private lateinit var family_name_text_field: TextView
    private lateinit var family_rank_text_field: TextView
    private lateinit var bio_status_text_field: TextView
    private lateinit var bio_code_text_field: TextView
    private lateinit var endemicity_text_field: TextView
    private lateinit var pest_status_text_field: TextView
    private lateinit var animal_image: ImageView

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

        activity?.title = speice.AcceptedCommonName
        scientific_name_text_field = view.findViewById<TextView>(R.id.scientific_name_text_field)
        scientific_name_text_field.text = speice.ScientificName

        accepted_common_name_text_field = view.findViewById<TextView>(R.id.accepted_common_name_text_field)
        accepted_common_name_text_field.text = speice.AcceptedCommonName

        family_name_text_field = view.findViewById<TextView>(R.id.family_name_text_field)
        family_name_text_field.text = speice.FamilyName

        family_rank_text_field = view.findViewById<TextView>(R.id.family_rank_text_field)
        family_rank_text_field.text = speice.FamilyRank.toString()

        bio_status_text_field = view.findViewById<TextView>(R.id.bio_status_text_field)
        bio_status_text_field.text = speice.ConservationStatus.BIOStatus

        bio_code_text_field = view.findViewById<TextView>(R.id.bio_code_text_field)
        bio_code_text_field.text = speice.ConservationStatus.BIOStatusCode

        endemicity_text_field = view.findViewById<TextView>(R.id.endemicity_text_field)
        endemicity_text_field.text = speice.Endemicity

        pest_status_text_field = view.findViewById<TextView>(R.id.pest_status_text_field)
        pest_status_text_field.text = speice.PestStatus

        animal_image = view.findViewById(R.id.animal_image)

        Glide.with(this).load(speice.Image.URL).into(animal_image)

        return view
    }
}