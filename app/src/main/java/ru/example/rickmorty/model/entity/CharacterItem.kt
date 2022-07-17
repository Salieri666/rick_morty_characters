package ru.example.rickmorty.model.entity

data class CharacterItem(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val url: String,
    val created: String,
    val origin: AdditionalInfo,
    val location: AdditionalInfo,
    val image: String
)

data class AdditionalInfo(
    val name: String,
    val url: String
)
