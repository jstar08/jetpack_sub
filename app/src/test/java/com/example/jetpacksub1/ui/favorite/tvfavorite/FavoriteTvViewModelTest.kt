package com.example.jetpacksub1.ui.favorite.tvfavorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.jetpacksub1.data.MovieRepository
import com.example.jetpacksub1.data.local.entity.TvModel
import junit.framework.Assert.assertNotNull
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvViewModelTest {

    private lateinit var viewModel: FavoriteTvViewModel

    @get:Rule
    var instantTaskExcecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvModel>>

    @Mock
    private lateinit var pagedList: PagedList<TvModel>

    @Before
    fun setUp() {
        viewModel = FavoriteTvViewModel(movieRepository)
    }

    @Test
    fun favoriteTv() {
        val dummyTv = pagedList
        `when`(dummyTv.size).thenReturn(5)
        val tv = MutableLiveData<PagedList<TvModel>>()
        tv.value = dummyTv

        `when`(movieRepository.getFavoriteTv()).thenReturn(tv)
        val tvEntities = viewModel.getFavoriteTv().value
        verify<MovieRepository>(movieRepository).getFavoriteTv()
        assertNotNull(tvEntities)
        assertEquals(5, tvEntities?.size)

        viewModel.getFavoriteTv().observeForever(observer)
        verify(observer).onChanged(tvEntities)
    }

}