package com.example.jetpacksub1.ui.home.film

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jetpacksub1.BuildConfig.IMAGE_URL
import com.example.jetpacksub1.R
import com.example.jetpacksub1.data.local.entity.FilmModel
import com.example.jetpacksub1.databinding.ListWatchBinding
import com.example.jetpacksub1.ui.detail.DetailActivity

class FilmAdapter(private val context: Context) :
    PagedListAdapter<FilmModel, FilmAdapter.FilmViewHolder>(DIFF_CALLBACK) {

    inner class FilmViewHolder(private val binding: ListWatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: FilmModel) {
            binding.apply {
                tvFilmtitleyear.text = film.title
                tvReleaseDate.text = film.releaseDate
                tvRate.text =
                    String.format(context.resources.getString(R.string.rating_format), film.score)
                tvDesc.text = film.overview

                Glide.with(root).load("$IMAGE_URL${film.moviePoster}").into(ivPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.FILM, film.id)
                    DetailActivity.isMovie = true
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    itemView.context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder =
        FilmViewHolder(
            ListWatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = getItem(position)
        if (film != null) {
            holder.bind(film)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FilmModel>() {
            override fun areItemsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}



