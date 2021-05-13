package com.example.jetpacksub1.ui.favorite.tvfavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.data.local.entity.TvModel

class FavoriteTvViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavoriteTv(): LiveData<PagedList<TvModel>> = movieRepository.getFavoriteTv()
}