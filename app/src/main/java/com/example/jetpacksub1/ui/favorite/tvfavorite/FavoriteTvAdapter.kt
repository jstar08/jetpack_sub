package com.example.jetpacksub1.ui.favorite.tvfavorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jetpacksub1.BuildConfig
import com.example.jetpacksub1.R
import com.example.jetpacksub1.data.local.entity.TvModel
import com.example.jetpacksub1.databinding.ListWatchBinding
import com.example.jetpacksub1.ui.detail.DetailActivity

class FavoriteTvAdapter(private val context: Context) :
    PagedListAdapter<TvModel, FavoriteTvAdapter.FavoriteTvViewHolder>(DIFF_CALLBACK) {

    inner class FavoriteTvViewHolder(private val binding: ListWatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: TvModel) {
            binding.apply {
                tvFilmtitleyear.text = film.title
                tvReleaseDate.text = film.releaseDate
                tvRate.text =
                    String.format(context.resources.getString(R.string.rating_format), film.score)
                tvDesc.text = film.overview

                Glide.with(root).load("${BuildConfig.IMAGE_URL}${film.seriesPoster}").into(ivPoster)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.FILM, film.id)
                    DetailActivity.isMovie = false
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    itemView.context.startActivity(intent)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTvViewHolder =
        FavoriteTvViewHolder(
            ListWatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: FavoriteTvViewHolder, position: Int) {
        val tv = getItem(position)
        if (tv != null) {
            holder.bind(tv)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvModel>() {
            override fun areItemsTheSame(oldItem: TvModel, newItem: TvModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvModel, newItem: TvModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
