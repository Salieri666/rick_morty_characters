package ru.example.rickmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import ru.example.rickmorty.databinding.CharacterItemBinding
import ru.example.rickmorty.model.CharacterDto

class RecyclerCharacterAdapter : PagingDataAdapter<CharacterDto, RecyclerCharacterAdapter.CharacterItemHolder>(
    DiffCallback()
) {
    companion object {
        const val LOADING_ITEM = 0
        const val CHARACTER_ITEM = 1
    }


    var itemClickListener: ( (Int) -> Unit )? = null

    private class DiffCallback : DiffUtil.ItemCallback<CharacterDto>() {

        override fun areItemsTheSame(oldDto: CharacterDto, newDto: CharacterDto): Boolean =
            oldDto.id == newDto.id

        override fun areContentsTheSame(oldDto: CharacterDto, newDto: CharacterDto): Boolean =
            oldDto == newDto

    }

    inner class CharacterItemHolder(binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val view = itemView
        val title: TextView = binding.characterName
        val imgView: ImageView = binding.episodeIcon
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemBinding.inflate(inflater, parent, false)

        return CharacterItemHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterItemHolder, position: Int) {
        val dto: CharacterDto? = getItem(position)

        holder.title.text = dto?.title

        itemClickListener?.let { itemListener ->
            holder.view.setOnClickListener {
                itemListener(position)
            }
        }

        Glide.with(holder.imgView.context)
                .asBitmap()
                .format(DecodeFormat.PREFER_RGB_565)
                .centerCrop()
                .load(dto?.imgUrl)
                .into(holder.imgView)

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) CHARACTER_ITEM else LOADING_ITEM
    }
}