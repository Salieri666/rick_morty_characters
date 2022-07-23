package ru.example.rickmorty.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.example.rickmorty.R
import ru.example.rickmorty.databinding.FragmentSelectedCharacterBinding
import ru.example.rickmorty.model.CharacterDto
import kotlin.math.abs


@AndroidEntryPoint
class SelectedCharacterFragment : Fragment() {
    private var character: CharacterDto? = null

    private var _binding: FragmentSelectedCharacterBinding? = null
    private val binding: FragmentSelectedCharacterBinding
        get() = if (_binding != null) _binding!! else throw NullPointerException("FragmentSelectedCharacterBinding is null!")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            character = it.getParcelable(SELECTED_CHARACTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectedCharacterBinding.inflate(inflater, container, false)
        fillData()

        setTitleBar()
        setupMenu()
        setupVisibility()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupIconActions()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.selected_character_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_mark_bookmark -> {
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setTitleBar() {
        val activityCombat = (requireActivity() as AppCompatActivity?)
        activityCombat?.setSupportActionBar(binding.toolbar)
        activityCombat?.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun fillData() {
        character?.let { character ->
            Glide.with(requireContext())
                .asBitmap()
                .format(DecodeFormat.PREFER_RGB_565)
                .centerCrop()
                .load(character.imgUrl)
                .into(binding.characterImage)

            with(binding) {
                characterNameFloat.text = character.title
                innerContent.lastLocation.text = character.lastLocation
                innerContent.firstSeen.text = character.firstSeen
                innerContent.status.text = character.status
                innerContent.type.text = character.type
            }
        }
    }

    private fun menuVisibility(visibility: Boolean) {
        val menu = binding.toolbar.menu
        menu?.let {
            val item = it.findItem(R.id.menu_mark_bookmark)
            item.isVisible = visibility
        }
    }

    private fun toolbarTitleVisibility(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (abs(verticalOffset) == appBarLayout.totalScrollRange) { //toolbar mode
            binding.collapsToolbar.title = character?.title
        } else if (abs(verticalOffset) != appBarLayout.totalScrollRange) {
            binding.collapsToolbar.title = " "
        }
    }

    private fun setupVisibility() {
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            when {
                verticalOffset == 0 -> { //total expanded, menu hidden
                    menuVisibility(false)
                }
                abs(verticalOffset) <= (appBarLayout.totalScrollRange * 0.7).toInt() -> { //menu should be hidden
                    menuVisibility(false)

                    binding.bookmarkFab.visibility = View.VISIBLE
                    binding.characterNameFloat.visibility = View.VISIBLE
                }
                abs(verticalOffset) > (appBarLayout.totalScrollRange * 0.7).toInt() -> {//menu should be visible
                    menuVisibility(true)

                    binding.bookmarkFab.visibility = View.INVISIBLE
                    binding.characterNameFloat.visibility = View.INVISIBLE
                }
            }

            toolbarTitleVisibility(appBarLayout, verticalOffset)
        })
    }

    private fun setupIconActions() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    companion object {
        private const val SELECTED_CHARACTER = "SELECTED_CHARACTER"

        @JvmStatic
        fun newInstance(character: CharacterDto) =
            SelectedCharacterFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SELECTED_CHARACTER, character)
                }
            }
    }
}