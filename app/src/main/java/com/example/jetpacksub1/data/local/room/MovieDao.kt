package com.example.jetpacksub1.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.jetpacksub1.data.local.entity.FilmModel
import com.example.jetpacksub1.data.local.entity.TvModel

@Dao
interface MovieDao {

    @Query("SELECT * FROM filmEntities")
    fun getAllFilm(): DataSource.Factory<Int, FilmModel>

    @Query("SELECT * FROM tvEntities")
    fun getAllTv(): DataSource.Factory<Int, TvModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: List<FilmModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(tv: List<TvModel>)

    @Update
    fun updateFilm(film: FilmModel)

    @Update
    fun updateTv(tv: TvModel)

    @Query("SELECT * FROM filmEntities where id = :id")
    fun getDetailFilm(id: Int): LiveData<FilmModel>

    @Query("SELECT * FROM tvEntities where id = :id")
    fun getDetailTv(id: Int): LiveData<TvModel>

    @Query("SELECT * FROM filmEntities where favorite = 1")
    fun getFavoriteFilm(): DataSource.Factory<Int, FilmModel>

    @Query("SELECT * FROM tvEntities where favorite = 1")
    fun getFavoriteTv(): DataSource.Factory<Int, TvModel>


}