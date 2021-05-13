package com.example.jetpacksub1.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.di.Injection
import com.example.jetpacksub1.ui.detail.DetailViewModel
import com.example.jetpacksub1.ui.favorite.filmfavorite.FavoriteFilmViewModel
import com.example.jetpacksub1.ui.favorite.tvfavorite.FavoriteTvViewModel
import com.example.jetpacksub1.ui.home.film.FilmViewModel
import com.example.jetpacksub1.ui.home.tv.TvViewModel

class ViewModelFactory private constructor(private val mContentRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(mContentRepository) as T
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                TvViewModel(mContentRepository) as T
            }
            modelClass.isAssignableFrom(FilmViewModel::class.java) -> {
                FilmViewModel(mContentRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteFilmViewModel::class.java) -> {
                FavoriteFilmViewModel(mContentRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTvViewModel::class.java) -> {
                FavoriteTvViewModel(mContentRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRespository(context))
            }
    }


}