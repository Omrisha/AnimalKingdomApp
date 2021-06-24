package com.example.animalskingdom.Services

import com.example.animalskingdom.Models.Family
import com.example.animalskingdom.Models.FamilyClass
import com.example.animalskingdom.Models.KingdomClass
import com.example.animalskingdom.Models.SpecieClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface KingdomService {
    @GET("?op=getclassnames&kingdom=animals")
    fun getKingdoms() : Call<KingdomClass>

    @GET("?op=getfamilynames&kingdom=animals")
    fun getFamilies(@Query("class") familyName: String) : Call<FamilyClass>

    @GET("?op=getspecies")
    fun getSpecies(@Query("family") familyName: String) : Call<SpecieClass>
}