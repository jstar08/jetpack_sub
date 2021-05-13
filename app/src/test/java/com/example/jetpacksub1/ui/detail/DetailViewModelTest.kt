package com.example.jetpacksub1.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.data.local.entity.FilmModel
import com.example.jetpacksub1.data.local.entity.TvModel
import com.example.jetpacksub1.utils.DummyObject
import com.example.jetpacksub1.vo.Resource
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var filmViewModel: DetailViewModel
    private lateinit var tvViewModel: DetailViewModel
    private val dummyFilm = DummyObject.generateMovies()[0]
    private val dummyTv = DummyObject.generateTv()[0]

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<FilmModel>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<TvModel>>

    @Before
    fun setUp() {
        filmViewModel = DetailViewModel(movieRepository)
        tvViewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun getFilm() {
        val moviesId = dummyFilm.id
        val movies = MutableLiveData<Resource<FilmModel>>()
        movies.value = Resource.success(dummyFilm)

        `when`(movieRepository.getFilmWithId(moviesId)).thenReturn(movies)

        val movieEntity = Resource.success(filmViewModel.getFilmById(moviesId).value)

        assertNotNull(movieEntity)
        assertEquals(dummyFilm.id, movieEntity.data?.data?.id)
        assertEquals(dummyFilm.title, movieEntity.data?.data?.title)
        assertEquals(dummyFilm.duration, movieEntity.data?.data?.duration)
        assertEquals(dummyFilm.releaseDate, movieEntity.data?.data?.releaseDate)
        assertEquals(dummyFilm.genre, movieEntity.data?.data?.genre)
        assertEquals(dummyFilm.score, movieEntity.data?.data?.score)
        assertEquals(dummyFilm.overview, movieEntity.data?.data?.overview)
        assertEquals(dummyFilm.moviePoster, movieEntity.data?.data?.moviePoster)

        filmViewModel.getFilmById(moviesId).observeForever(movieObserver)

        verify(movieObserver).onChanged(Resource.success(dummyFilm))
    }

    @Test
    fun getTv() {
        val tvId = dummyTv.id
        val tv = MutableLiveData<Resource<TvModel>>()
        tv.value = Resource.success(dummyTv)

        `when`(movieRepository.getTvWithId(tvId)).thenReturn(tv)

        Resource.success(tvViewModel.setSelectedMovie(tvId))
        val tvEntity = Resource.success(tvViewModel.getTvById(tvId).value)

        assertNotNull(tvEntity)
        assertEquals(dummyTv.id, tvEntity.data?.data?.id)
        assertEquals(dummyTv.title, tvEntity.data?.data?.title)
        assertEquals(dummyTv.duration, tvEntity.data?.data?.duration)
        assertEquals(dummyTv.releaseDate, tvEntity.data?.data?.releaseDate)
        assertEquals(dummyTv.genre, tvEntity.data?.data?.genre)
        assertEquals(dummyTv.score, tvEntity.data?.data?.score)
        assertEquals(dummyTv.overview, tvEntity.data?.data?.overview)
        assertEquals(dummyTv.seriesPoster, tvEntity.data?.data?.seriesPoster)

        tvViewModel.getTvById(tvId).observeForever(tvObserver)
        verify(tvObserver).onChanged(Resource.success(dummyTv))
    }

    @Test
    fun setFavoriteMovie() {
        val filmId = dummyFilm.id
        val filmDummy = MutableLiveData<Resource<FilmModel>>()
        filmDummy.value = Resource.success(dummyFilm)

        `when`(movieRepository.getFilmWithId(filmId)).thenReturn(filmDummy)
        val film = Resource.success(filmViewModel.getFilmById(filmId).value)
        filmViewModel.setFilmFavorite(film.data?.data!!)
        verify(movieRepository).setFavoriteFilm(film.data!!.data!!, true)
        verifyNoMoreInteractions(movieObserver)
        assertEquals(false, film.data?.data?.favorite)
    }

    @Test
    fun setFavoriteTv() {
        val tvId = dummyTv.id
        val tvDummy = MutableLiveData<Resource<TvModel>>()
        tvDummy.value = Resource.success(dummyTv)

        `when`(movieRepository.getTvWithId(tvId)).thenReturn(tvDummy)
        val tv = Resource.success(filmViewModel.getTvById(tvId).value)
        filmViewModel.setTvFavorite(tv.data?.data!!)
        verify(movieRepository).setFavoriteTv(tv.data!!.data!!, true)
        verifyNoMoreInteractions(movieObserver)
        assertEquals(false, tv.data?.data?.favorite)
    }
}