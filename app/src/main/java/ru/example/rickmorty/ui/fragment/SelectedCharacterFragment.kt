package ru.example.rickmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import ru.example.rickmorty.databinding.FragmentSelectedCharacterBinding
import ru.example.rickmorty.model.CharacterDto


@AndroidEntryPoint
class SelectedCharacterFragment : Fragment() {
    private var character: CharacterDto? = null

    private var binding: FragmentSelectedCharacterBinding? = null

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
        binding = FragmentSelectedCharacterBinding.inflate(inflater, container, false)
        fillData()
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun fillData() {
        binding?.namePerson?.text = character?.title
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