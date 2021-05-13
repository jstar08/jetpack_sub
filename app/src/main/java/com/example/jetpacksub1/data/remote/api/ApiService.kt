package com.example.jetpacksub1.data.remote.api

import com.example.jetpacksub1.data.remote.response.FilmDetail
import com.example.jetpacksub1.data.remote.response.FilmResponse
import com.example.jetpacksub1.data.remote.response.TvDetail
import com.example.jetpacksub1.data.remote.response.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getFilmList(@Query("api_key") api_key: String): Call<FilmResponse>

    @GET("tv/popular")
    fun getTvList(@Query("api_key") api_key: String): Call<TvResponse>

    @GET("movie/{movie_id}")
    fun getFilmDetail(@Path("movie_id") id: Int, @Query("api_key") api_key: String)
            : Call<FilmDetail>

    @GET("tv/{tv_id}")
    fun getTvDetail(@Path("tv_id") id: Int, @Query("api_key") api_key: String)
            : Call<TvDetail>
}