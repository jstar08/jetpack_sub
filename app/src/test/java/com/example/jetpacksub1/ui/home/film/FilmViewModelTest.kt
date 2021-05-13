package com.example.jetpacksub1.ui.home.film

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.data.local.entity.FilmModel
import com.example.jetpacksub1.vo.Resource
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
class FilmViewModelTest {

    private lateinit var viewModel: FilmViewModel

    @get:Rule
    var instantTaskExcecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<FilmModel>>>

    @Mock
    private lateinit var pagedList: PagedList<FilmModel>

    @Before
    fun setUp() {
        viewModel = FilmViewModel(movieRepository)
    }

    @Test
    fun testGetFilm() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data?.size).thenReturn(5)
        val movies = MutableLiveData<Resource<PagedList<FilmModel>>>()
        movies.value = dummyMovies

        `when`(movieRepository.getAllFilm()).thenReturn(movies)
        val movieEntities = viewModel.getFilm().value?.data
        verify<MovieRepository>(movieRepository).getAllFilm()
        assertNotNull(movieEntities)
        assertEquals(5, movieEntities?.size)

        viewModel.getFilm().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}