package ru.example.rickmorty.model.entity

data class CharacterBody(
    val info: Info,
    val results: List<CharacterItem>
)
