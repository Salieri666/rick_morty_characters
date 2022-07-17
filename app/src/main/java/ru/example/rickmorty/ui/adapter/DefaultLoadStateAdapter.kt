package ru.example.rickmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.example.rickmorty.databinding.LoadingBottomBinding

class DefaultLoadStateAdapter(
    private val tryAgainListener: () -> Unit
) : LoadStateAdapter<DefaultLoadStateAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LoadingBottomBinding.inflate(inflater, parent, false)
        return Holder(binding, null, tryAgainListener)
    }

    class Holder(
        private val binding: LoadingBottomBinding,
        private val swipeRefreshLayout: SwipeRefreshLayout?,
        private val tryAgainListener: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tryAgainBtn.setOnClickListener { tryAgainListener() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            errorTextView.isVisible = loadState is LoadState.Error
            tryAgainBtn.isVisible = loadState is LoadState.Error
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.isRefreshing = loadState is LoadState.Loading
                progressBar.isVisible = false
            } else {
                progressBar.isVisible = loadState is LoadState.Loading
            }
        }
    }
}