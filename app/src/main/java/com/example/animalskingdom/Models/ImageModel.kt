package com.example.animalskingdom.Models

import java.io.Serializable

data class ImageModel(
    var Type: String,
    var Format: String,
    var URL: String,
    var Reference: String,
    var Title: String
) : Serializable {
}