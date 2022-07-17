package ru.example.rickmorty.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.example.rickmorty.model.entity.CharacterBody
import ru.example.rickmorty.model.entity.CharacterItem
import ru.example.rickmorty.repo.api.RickMortyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepo @Inject constructor(
    private var rickMortyApi: RickMortyApi
) {

    private suspend fun getCharacters(pageIndex: Int) : CharacterBody = withContext(Dispatchers.IO) {
        rickMortyApi.getCharacters(pageIndex)
    }

    fun getPagedCharacters(): Flow<PagingData<CharacterItem>> {
        val loader: CharacterPageLoader = { pageIndex ->
            getCharacters(pageIndex)
        }

        return Pager(
            config = PagingConfig(
                pageSize = 20, //not used, api doesn't support size
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { CharacterPagingSource(loader) }
        ).flow
    }
}