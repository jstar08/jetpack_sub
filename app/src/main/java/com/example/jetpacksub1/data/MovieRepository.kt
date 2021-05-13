package com.example.jetpacksub1.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jetpacksub1.data.local.LocalDataSource
import com.example.jetpacksub1.data.local.entity.FilmModel
import com.example.jetpacksub1.data.local.entity.TvModel
import com.example.jetpacksub1.data.remote.ApiResponse
import com.example.jetpacksub1.data.remote.RemoteDataSource
import com.example.jetpacksub1.data.remote.response.FilmDetail
import com.example.jetpacksub1.data.remote.response.FilmList
import com.example.jetpacksub1.data.remote.response.TvDetail
import com.example.jetpacksub1.data.remote.response.TvList
import com.example.jetpacksub1.utils.AppExecutors
import com.example.jetpacksub1.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    override fun getAllFilm(): LiveData<Resource<PagedList<FilmModel>>> {
        return object : NetworkBoundResource<PagedList<FilmModel>, List<FilmList>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<FilmModel>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllFilm(), config).build()
            }

            override fun shouldFetch(data: PagedList<FilmModel>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<FilmList>>> =
                remoteDataSource.getListFilm()

            override fun saveCallResult(data: List<FilmList>) {
                val filmList = ArrayList<FilmModel>()
                for (response in data) {
                    val movie = FilmModel(
                        id = response.id ?: 0,
                        title = response.title ?: "",
                        overview = response.overview ?: "",
                        genre = "",
                        duration = 0,
                        releaseDate = response.releaseDate ?: "",
                        score = response.score ?: 0.0,
                        moviePoster = response.moviePoster ?: "",
                    )
                    filmList.add(movie)
                }
                localDataSource.insertFilm(filmList)
            }
        }.asLiveData()
    }

    override fun getAllTv(): LiveData<Resource<PagedList<TvModel>>> {

        return object : NetworkBoundResource<PagedList<TvModel>, List<TvList>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvModel>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTv(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvModel>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvList>>> =
                remoteDataSource.getListTv()

            override fun saveCallResult(data: List<TvList>) {
                val tvList = ArrayList<TvModel>()
                for (response in data) {
                    val tv = TvModel(
                        id = response.id ?: 0,
                        title = response.title ?: "",
                        overview = response.overview ?: "",
                        genre = "",
                        duration = 0,
                        releaseDate = response.releaseDate ?: "",
                        score = response.score ?: 0.0,
                        seriesPoster = response.tvPoster ?: "",
                    )
                    tvList.add(tv)
                }
                localDataSource.insertTv(tvList)
            }
        }.asLiveData()
    }

    override fun getFilmWithId(filmId: Int): LiveData<Resource<FilmModel>> {

        return object : NetworkBoundResource<FilmModel, FilmDetail>(appExecutors) {
            override fun loadFromDB(): LiveData<FilmModel> =
                localDataSource.getDetailFilm(filmId)

            override fun shouldFetch(data: FilmModel?): Boolean =
                data?.genre == "" || data?.duration == 0

            override fun createCall(): LiveData<ApiResponse<FilmDetail>> =
                remoteDataSource.getFilmDetail(filmId)

            override fun saveCallResult(data: FilmDetail) {
                val filmList = ArrayList<FilmModel>()
                var genre = ""
                for (i in 0 until data.genres.size) {
                    if (i < 2) genre += data.genres[i].genreName
                    if (i < 1) genre += " | "
                }
                val film = FilmModel(
                    id = data.id ?: 0,
                    title = data.title ?: "",
                    overview = data.overview ?: "",
                    genre = genre,
                    duration = data.duration ?: 0,
                    releaseDate = data.releaseDate ?: "",
                    score = data.score ?: 0.0,
                    moviePoster = data.moviePoster ?: ""
                )
                filmList.add(film)

                localDataSource.insertFilm(filmList)
            }
        }.asLiveData()
    }

    override fun getTvWithId(tvId: Int): LiveData<Resource<TvModel>> {

        return object : NetworkBoundResource<TvModel, TvDetail>(appExecutors) {
            override fun loadFromDB(): LiveData<TvModel> =
                localDataSource.getDetailTv(tvId)

            override fun shouldFetch(data: TvModel?): Boolean =
                data?.genre == "" || data?.duration == 0

            override fun createCall(): LiveData<ApiResponse<TvDetail>> =
                remoteDataSource.getTvDetail(tvId)

            override fun saveCallResult(data: TvDetail) {
                val tvList = ArrayList<TvModel>()
                var genre = ""
                for (i in 0 until data.genres.size) {
                    if (i < 2) genre += data.genres[i].genreName
                    if (i < 1) genre += " | "
                }
                val tv = TvModel(
                    id = data.id ?: 0,
                    title = data.title ?: "",
                    overview = data.overview ?: "",
                    genre = genre,
                    duration = data.duration ?: 0,
                    releaseDate = data.releaseDate ?: "",
                    score = data.score ?: 0.0,
                    seriesPoster = data.tvPoster ?: "",
                )
                tvList.add(tv)
                localDataSource.insertTv(tvList)
            }

        }.asLiveData()
    }

    override fun setFavoriteFilm(film: FilmModel, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteFilm(film, state) }
    }

    override fun setFavoriteTv(tv: TvModel, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteTv(tv, state) }
    }

    override fun getFavoriteFilm(): LiveData<PagedList<FilmModel>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteFilm(), config).build()
    }

    override fun getFavoriteTv(): LiveData<PagedList<TvModel>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTv(), config).build()
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors).apply {
                    instance = this
                }
            }
    }
}