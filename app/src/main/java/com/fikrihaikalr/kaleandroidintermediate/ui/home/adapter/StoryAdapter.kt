package com.fikrihaikalr.kaleandroidintermediate.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fikrihaikalr.kaleandroidintermediate.data.remote.model.story.ListStoryItem
import com.fikrihaikalr.kaleandroidintermediate.databinding.ItemListHomeBinding
import com.fikrihaikalr.kaleandroidintermediate.ui.home.HomeFragmentDirections

class StoryAdapter : RecyclerView.Adapter<StoryAdapter.ListViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<ListStoryItem>() {
        override fun areItemsTheSame(
            oldItem: ListStoryItem,
            newItem: ListStoryItem
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ListStoryItem,
            newItem: ListStoryItem
        ): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder =
        ListViewHolder(
            ItemListHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListViewHolder(private val binding: ItemListHomeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem){
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.photoUrl).into(ivPhoto)
                tvName.text = item.name
                itemView.setOnClickListener {
                    val directions = HomeFragmentDirections.actionHomeFragmentToDetailStoryFragment(item.id.toString())
                    it.findNavController().navigate(directions)
                }
            }
        }

    }
}