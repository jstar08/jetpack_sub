package com.example.jetpacksub1.ui.home.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.data.local.entity.FilmModel
import com.example.jetpacksub1.vo.Resource

class FilmViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFilm(): LiveData<Resource<PagedList<FilmModel>>> = movieRepository.getAllFilm()
}


















