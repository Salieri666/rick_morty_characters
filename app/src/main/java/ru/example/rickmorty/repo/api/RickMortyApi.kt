package ru.example.rickmorty.repo.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.example.rickmorty.model.entity.CharacterBody


interface RickMortyApi {

    @GET("character?")
    suspend fun getCharacters(
        @Query("page") page: Int
    ) : CharacterBody
}