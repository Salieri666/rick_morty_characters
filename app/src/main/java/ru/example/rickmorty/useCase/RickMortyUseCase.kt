package ru.example.rickmorty.useCase

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.example.rickmorty.model.CharacterDto
import ru.example.rickmorty.repo.NetworkRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RickMortyUseCase @Inject constructor(
    private var networkRepo: NetworkRepo
) {

    fun getCharacters() : Flow<PagingData<CharacterDto>> = networkRepo.getPagedCharacters()
        .map { pagingData ->
            pagingData.map { item -> CharacterDto(item) }
        }
}