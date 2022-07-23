package ru.example.rickmorty.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.example.rickmorty.model.entity.CharacterItem

@Parcelize
data class CharacterDto(
    val id: Int,
    val title: String,
    val status: String,
    val type: String,
    val imgUrl: String,
    val lastLocation: String,
    val firstSeen: String,
    val lastLocationUrl: String,
    val firstSeenUrl: String
) : Parcelable {

    constructor(characterItem: CharacterItem) : this(
        characterItem.id,
        characterItem.name,
        characterItem.status,
        characterItem.species,
        characterItem.image,
        characterItem.location.name,
        characterItem.origin.name,
        characterItem.location.url,
        characterItem.origin.url
    )


}


