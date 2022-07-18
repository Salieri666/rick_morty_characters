package ru.example.rickmorty.ui.viewModel

import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.example.rickmorty.model.CharacterDto
import ru.example.rickmorty.useCase.RickMortyUseCase
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private var rickMortyUseCase: RickMortyUseCase
) : BaseViewModel() {

    val characters : Flow<PagingData<CharacterDto>> = rickMortyUseCase
        .getCharacters()
        .cachedIn(viewModelScope)

}