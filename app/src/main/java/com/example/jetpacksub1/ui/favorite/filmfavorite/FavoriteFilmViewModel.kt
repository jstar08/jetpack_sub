package com.example.jetpacksub1.ui.favorite.filmfavorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.data.local.entity.FilmModel

class FavoriteFilmViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavoriteFilm(): LiveData<PagedList<FilmModel>> = movieRepository.getFavoriteFilm()
}