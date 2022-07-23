package ru.example.rickmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.example.rickmorty.R
import ru.example.rickmorty.databinding.FragmentCharactersBinding
import ru.example.rickmorty.ui.adapter.DefaultLoadStateAdapter
import ru.example.rickmorty.ui.adapter.RecyclerCharacterAdapter
import ru.example.rickmorty.ui.viewModel.CharacterViewModel


@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private val characterViewModel : CharacterViewModel by viewModels()

    private var charactersAdapter: RecyclerCharacterAdapter? = null
    //private var mainLoadStateHolder: DefaultLoadStateAdapter.Holder? = null
    private var binding : FragmentCharactersBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        setupCharacterList()
        setupSwipeToRefresh()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setupLoadingIcon()
        observeCharacters(charactersAdapter!!)
        observeLoadState(charactersAdapter!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        charactersAdapter = null
    }

    private fun setupCharacterList() {
        charactersAdapter = RecyclerCharacterAdapter()
        val footerAdapter = DefaultLoadStateAdapter { charactersAdapter?.refresh() }
        val adapterWithLoadState = charactersAdapter?.withLoadStateFooter(footerAdapter)

        charactersAdapter?.itemClickListener = ::onCharacterClick

        val gridLayoutManager = GridLayoutManager(context,  2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (charactersAdapter?.getItemViewType(position) == RecyclerCharacterAdapter.LOADING_ITEM) 1 else 2
            }
        }

        binding?.let{
            it.recyclerCharacters.apply {
                layoutManager = gridLayoutManager
                adapter = adapterWithLoadState
            }

            /*mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
                it.loadingCharacter,
                it.swipeRefresh
            ) { charactersAdapter?.refresh() }*/
        }



    }

    /*private fun setupLoadingIcon() {
        characterViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding?.loadingNewsIcon?.visibility = View.VISIBLE
            } else {
                binding?.loadingNewsIcon?.visibility = View.GONE
            }
        }
    }*/

    private fun setupSwipeToRefresh() {
        binding?.swipeRefresh?.setOnRefreshListener {
            charactersAdapter?.refresh()
        }
    }

    private fun observeCharacters(adapter: RecyclerCharacterAdapter) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            characterViewModel.characters.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: RecyclerCharacterAdapter) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collectLatest { state ->
                binding?.swipeRefresh?.isRefreshing = state.refresh is LoadState.Loading
                //mainLoadStateHolder?.bind(state.refresh)
            }
        }
    }

    private fun onCharacterClick(position: Int) {
        val selectedCharacter = charactersAdapter?.snapshot()?.items?.get(position)
        val bundle = bundleOf("SELECTED_CHARACTER" to selectedCharacter)

        Navigation.findNavController(requireActivity(), R.id.main_nav_fragment).navigate(R.id.action_bottomBar_to_selectedCharacterFragment, bundle)
    }

    /*private fun setupLoadingCharacterList() {
        characterViewModel.allCharacters
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> charactersAdapter?.submitList(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        //characterViewModel.loadCharacters()
    }*/
 }