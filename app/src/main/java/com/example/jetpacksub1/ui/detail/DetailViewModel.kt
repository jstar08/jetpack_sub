package com.example.jetpacksub1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.data.local.entity.FilmModel
import com.example.jetpacksub1.data.local.entity.TvModel
import com.example.jetpacksub1.vo.Resource

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val id = MutableLiveData<Int>()

    fun setSelectedMovie(id: Int) {
        this.id.value = id
    }

    fun getFilmById(id: Int): LiveData<Resource<FilmModel>> {
        return movieRepository.getFilmWithId(id)
    }

    fun getTvById(id: Int): LiveData<Resource<TvModel>> {
        return movieRepository.getTvWithId(id)
    }


    fun setFilmFavorite(data: FilmModel) {
        val newState = !data.favorite
        movieRepository.setFavoriteFilm(data, newState)
    }

    fun setTvFavorite(data: TvModel) {
        val newState = !data.favorite
        movieRepository.setFavoriteTv(data, newState)
    }
}