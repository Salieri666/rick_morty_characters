package ru.example.rickmorty.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.example.rickmorty.R
import ru.example.rickmorty.databinding.FragmentBottomBarBinding

@AndroidEntryPoint
class BottomBarFragment : Fragment() {

    private var binding: FragmentBottomBarBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomBarBinding.inflate(inflater, container, false)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.bottom_nav_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding?.bottomNavigationView?.setupWithNavController(navController)

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}