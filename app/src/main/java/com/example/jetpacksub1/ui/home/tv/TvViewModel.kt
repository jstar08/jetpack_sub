package com.example.jetpacksub1.ui.home.tv


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.data.local.entity.TvModel
import com.example.jetpacksub1.vo.Resource

class TvViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getTv(): LiveData<Resource<PagedList<TvModel>>> = movieRepository.getAllTv()
}