package ru.example.rickmorty.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.example.rickmorty.model.entity.CharacterItem

@Parcelize
data class CharacterDto(
    val id: Int,
    val title: String,
    val imgUrl: String
) : Parcelable {

    constructor(characterItem: CharacterItem) : this(
        characterItem.id, characterItem.name, characterItem.image
    )


}


