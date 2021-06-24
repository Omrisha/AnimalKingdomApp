package com.example.animalskingdom.Models

data class ConservationStatus(
    var ConservationSignificant: Boolean,
    var BIOStatus: String,
    var BIOStatusCode: String,
    var NCAStatus: String,
    var NCAStatusCode: String,
    var BOTStatus: String,
    var BOTStatusCode: String
) {

}