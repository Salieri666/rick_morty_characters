package ru.example.rickmorty.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.example.rickmorty.model.entity.CharacterBody
import ru.example.rickmorty.model.entity.CharacterItem

typealias CharacterPageLoader = suspend (pageIndex: Int) -> CharacterBody

class CharacterPagingSource(
    private val loader: CharacterPageLoader
) : PagingSource<Int, CharacterItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterItem> {
        val pageIndex = params.key ?: 1

        return try {
            val body = loader.invoke(pageIndex)
            val characters = body.results

            return LoadResult.Page(
                data = characters,
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = if (body.info.next != null) pageIndex + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}