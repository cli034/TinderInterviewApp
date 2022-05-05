package com.example.tinderinterviewapp.presentation.ui.giphy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tinderinterviewapp.databinding.ListItemGifsBinding
import com.example.tinderinterviewapp.domain.models.GifModel

class GifAdapter(
    private val onItemClicked: (GifModel) -> Unit
): ListAdapter<GifModel, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GifViewHolder(
            binding = ListItemGifsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            binding.root.setOnClickListener {
                onItemClicked(getItem(adapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is GifViewHolder) {
            holder.bind(item)
        }
    }

    class GifViewHolder(
        val binding: ListItemGifsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(gifModel: GifModel) {

            with(binding) {
                val downSampledUrl = gifModel.images.fixed_height_downsampled.url
                Glide.with(itemView.context)
                    .asGif()
                    .load(downSampledUrl)
                    .into(ivGif)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<GifModel>() {
        override fun areItemsTheSame(oldItem: GifModel, newItem: GifModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifModel, newItem: GifModel): Boolean {
            return oldItem == newItem
        }

    }
}