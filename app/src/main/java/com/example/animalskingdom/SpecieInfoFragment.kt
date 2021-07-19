package com.example.animalskingdom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.animalskingdom.Models.PhotoResponse
import com.example.animalskingdom.Models.PhotoSearchResponse
import com.example.animalskingdom.Services.ImageServiceBuilder
import com.example.animalskingdom.utilities.CircleTransform
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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



        val shareButton = view.findViewById<ImageView>(R.id.share_button)
        shareButton.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "I learned a lot about ${speice.AcceptedCommonName} from AnimalKingdom App, Download it now on Google Play Store!");
            startActivity(Intent.createChooser(shareIntent, "Share"))
        }

        val searchResponse = ImageServiceBuilder.client.fetchImages("${speice.AcceptedCommonName} animal")

        searchResponse.enqueue(object : Callback<PhotoSearchResponse> {
            override fun onResponse(call: Call<PhotoSearchResponse>, response: Response<PhotoSearchResponse>) {
                if (response.isSuccessful) {
                    val photo = response.body()!!.photos.photo.firstOrNull()

                    if (photo != null) {
                        Picasso.get().load(getUrl(photo)).transform(CircleTransform()).placeholder(R.drawable.image_person).error(R.drawable.image_person).into(animal_image)
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<PhotoSearchResponse>, t: Throwable) {
                Toast.makeText(context, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }

        })

        return view
    }

    private fun getUrl(photo: PhotoResponse): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
    }
}