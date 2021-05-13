package com.example.jetpacksub1.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpacksub1.BuildConfig.API_KEY
import com.example.jetpacksub1.data.remote.api.ApiConfig
import com.example.jetpacksub1.data.remote.response.*
import com.example.jetpacksub1.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource {

    fun getListFilm(): LiveData<ApiResponse<List<FilmList>>> {
        EspressoIdlingResource.increment()

        val resultFilm = MutableLiveData<ApiResponse<List<FilmList>>>()
        ApiConfig.getApiService().getFilmList(API_KEY).enqueue(object : Callback<FilmResponse> {
            override fun onResponse(
                call: Call<FilmResponse>,
                response: Response<FilmResponse>
            ) {
                response.body()?.result?.let { listFilm ->
                    resultFilm.value = ApiResponse.success(listFilm)
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })

        return resultFilm
    }


    fun getListTv(): LiveData<ApiResponse<List<TvList>>> {
        EspressoIdlingResource.increment()

        val resultTv = MutableLiveData<ApiResponse<List<TvList>>>()
        ApiConfig.getApiService().getTvList(API_KEY).enqueue(object : Callback<TvResponse> {
            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                response.body()?.result?.let { listTv ->
                    resultTv.value = ApiResponse.success(listTv)
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })
        return resultTv
    }

    fun getFilmDetail(id: Int): LiveData<ApiResponse<FilmDetail>> {
        EspressoIdlingResource.increment()
        val resultFilm = MutableLiveData<ApiResponse<FilmDetail>>()
        ApiConfig.getApiService().getFilmDetail(id, API_KEY).enqueue(object : Callback<FilmDetail> {
            override fun onResponse(call: Call<FilmDetail>, response: Response<FilmDetail>) {
                resultFilm.value = ApiResponse.success(response.body()!!)
                EspressoIdlingResource.decrement()
            }


            override fun onFailure(call: Call<FilmDetail>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })

        return resultFilm
    }

    fun getTvDetail(id: Int): LiveData<ApiResponse<TvDetail>> {
        EspressoIdlingResource.increment()
        val resultTv = MutableLiveData<ApiResponse<TvDetail>>()
        ApiConfig.getApiService().getTvDetail(id, API_KEY).enqueue(object : Callback<TvDetail> {
            override fun onResponse(call: Call<TvDetail>, response: Response<TvDetail>) {
                resultTv.value = ApiResponse.success(response.body()!!)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvDetail>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })

        return resultTv
    }

    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }


}