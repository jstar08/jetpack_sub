package com.example.jetpacksub1.di

import android.content.Context
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.data.local.LocalDataSource
import com.example.jetpacksub1.data.local.room.MovieDatabase
import com.example.jetpacksub1.data.remote.RemoteDataSource
import com.example.jetpacksub1.utils.AppExecutors

object Injection {
    fun provideRespository(context: Context): MovieRepository {

        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}