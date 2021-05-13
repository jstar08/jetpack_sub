package com.example.jetpacksub1.data.local

import androidx.paging.DataSource
import com.example.jetpacksub1.data.local.entity.FilmModel
import com.example.jetpacksub1.data.local.entity.TvModel
import com.example.jetpacksub1.data.local.room.MovieDao

class LocalDataSource private constructor(private val mMoviesDao: MovieDao) {

    fun getAllFilm(): DataSource.Factory<Int, FilmModel> = mMoviesDao.getAllFilm()

    fun getAllTv(): DataSource.Factory<Int, TvModel> = mMoviesDao.getAllTv()

    fun insertFilm(film: List<FilmModel>) = mMoviesDao.insertFilm(film)

    fun insertTv(tv: List<TvModel>) = mMoviesDao.insertTv(tv)

    fun setFavoriteFilm(film: FilmModel, newState: Boolean) {
        film.favorite = newState
        mMoviesDao.updateFilm(film)
    }

    fun setFavoriteTv(tv: TvModel, newState: Boolean) {
        tv.favorite = newState
        mMoviesDao.updateTv(tv)
    }

    fun getFavoriteFilm(): DataSource.Factory<Int, FilmModel> = mMoviesDao.getFavoriteFilm()

    fun getFavoriteTv(): DataSource.Factory<Int, TvModel> = mMoviesDao.getFavoriteTv()

    fun getDetailFilm(id: Int) = mMoviesDao.getDetailFilm(id)

    fun getDetailTv(id: Int) = mMoviesDao.getDetailTv(id)


    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }
}