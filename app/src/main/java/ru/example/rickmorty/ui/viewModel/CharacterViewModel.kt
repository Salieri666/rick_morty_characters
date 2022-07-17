package ru.example.rickmorty.ui.viewModel

import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import ru.example.rickmorty.model.CharacterDto
import ru.example.rickmorty.useCase.RickMortyUseCase
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private var rickMortyUseCase: RickMortyUseCase
) : BaseViewModel() {


    fun getCharacters() : Flow<PagingData<CharacterDto>> {
        return rickMortyUseCase
            .getCharacters()
            .cachedIn(viewModelScope)
    }
}