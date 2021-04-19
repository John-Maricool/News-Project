package com.maricoolsapps.mynewsproject.news

import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maricoolsapps.mynewsproject.databinding.NewsListCardBinding
import com.maricoolsapps.mynewsproject.news.models.Articles
import java.io.File

class NewsRecyclerAdapter(var listener: onItemClickListener) :
    PagingDataAdapter<Articles, NewsRecyclerAdapter.NewsViewHolder>(
        PHOTO_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            NewsListCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }

    }

    @Suppress("DEPRECATION")
    inner class NewsViewHolder(private val binding: NewsListCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init{
            binding.card.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null){
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(news: Articles) {
            binding.apply {
                Glide.with(itemView)
                    .load(news.urlToImage)
                    .centerCrop()
                    .into(imageView)

                textTitle.text = news.title
                textDescription.text = news.description
            }
        }
    }

    interface onItemClickListener{
        fun onItemClick(photo: Articles)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Articles>() {
            override fun areItemsTheSame(oldItem: Articles, newItem: Articles) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Articles, newItem: Articles) =
                oldItem == newItem


        }
    }


}