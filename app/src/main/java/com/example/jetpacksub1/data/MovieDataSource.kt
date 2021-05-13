package com.example.jetpacksub1.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.jetpacksub1.data.local.entity.FilmModel
import com.example.jetpacksub1.data.local.entity.TvModel
import com.example.jetpacksub1.vo.Resource

interface MovieDataSource {

    fun getAllFilm(): LiveData<Resource<PagedList<FilmModel>>>

    fun getAllTv(): LiveData<Resource<PagedList<TvModel>>>

    fun getFilmWithId(filmId: Int): LiveData<Resource<FilmModel>>

    fun getTvWithId(tvId: Int): LiveData<Resource<TvModel>>

    fun setFavoriteFilm(film: FilmModel, state: Boolean)

    fun setFavoriteTv(tv: TvModel, state: Boolean)

    fun getFavoriteFilm(): LiveData<PagedList<FilmModel>>

    fun getFavoriteTv(): LiveData<PagedList<TvModel>>
}