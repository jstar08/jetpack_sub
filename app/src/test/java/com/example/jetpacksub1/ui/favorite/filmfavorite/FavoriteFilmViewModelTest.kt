package com.example.jetpacksub1.ui.favorite.filmfavorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.data.local.entity.FilmModel
import junit.framework.Assert
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteFilmViewModelTest {

    private lateinit var viewModel: FavoriteFilmViewModel

    @get:Rule
    var instantTaskExcecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<FilmModel>>

    @Mock
    private lateinit var pagedList: PagedList<FilmModel>

    @Before
    fun setUp() {
        viewModel = FavoriteFilmViewModel(movieRepository)
    }

    @Test
    fun favoriteFilm() {
        val dummyFilm = pagedList
        Mockito.`when`(dummyFilm.size).thenReturn(5)
        val film = MutableLiveData<PagedList<FilmModel>>()
        film.value = dummyFilm

        Mockito.`when`(movieRepository.getFavoriteFilm()).thenReturn(film)
        val filmEntities = viewModel.getFavoriteFilm().value
        Mockito.verify<MovieRepository>(movieRepository).getFavoriteFilm()
        Assert.assertNotNull(filmEntities)
        TestCase.assertEquals(5, filmEntities?.size)

        viewModel.getFavoriteFilm().observeForever(observer)
        Mockito.verify(observer).onChanged(filmEntities)
    }

}