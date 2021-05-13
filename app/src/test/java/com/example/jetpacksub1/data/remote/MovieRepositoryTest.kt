package com.example.jetpacksub1.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.jetpacksub1.data.local.LocalDataSource
import com.example.jetpacksub1.data.local.entity.FilmModel
import com.example.jetpacksub1.data.local.entity.TvModel
import com.example.jetpacksub1.util.LiveDataTestUtil
import com.example.jetpacksub1.util.PagedListUtil
import com.example.jetpacksub1.util.TestExecutor
import com.example.jetpacksub1.utils.AppExecutors
import com.example.jetpacksub1.utils.DummyObject
import com.example.jetpacksub1.vo.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutor = Mockito.mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(remote, local, appExecutor)
    private val testExecutor = AppExecutors(TestExecutor(), TestExecutor(), TestExecutor())

    private val filmResponse = DummyObject.generateRemoteMovies()
    private val tvResponse = DummyObject.generateRemoteTv()
    private val filmList = DummyObject.generateMovies()
    private val tvList = DummyObject.generateTv()
    private val filmId = filmList[0].id
    private val tvId = tvList[0].id

    @Test
    fun getAllFilm() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FilmModel>
        `when`(local.getAllFilm()).thenReturn(dataSourceFactory)
        movieRepository.getAllFilm()

        val filmEntities =
            Resource.success(PagedListUtil.mockPagedList(DummyObject.generateMovies()))
        verify(local).getAllFilm()
        assertNotNull(filmEntities.data)
        assertEquals(filmResponse.size.toLong(), filmEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTv() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvModel>
        `when`(local.getAllTv()).thenReturn(dataSourceFactory)
        movieRepository.getAllTv()

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(DummyObject.generateTv()))
        verify(local).getAllTv()
        assertNotNull(tvEntities.data)
        assertEquals(tvResponse.size.toLong(), tvEntities.data?.size?.toLong())
    }

    @Test
    fun getFilmWithId() {
        val filmDummy = MutableLiveData<FilmModel>()
        filmDummy.value = filmList[0]
        `when`(local.getDetailFilm(filmId!!)).thenReturn(filmDummy)

        val filmEntities = LiveDataTestUtil.getValue(movieRepository.getFilmWithId(filmList[0].id))

        verify(local).getDetailFilm(filmId)
        assertNotNull(filmEntities.data)
        assertEquals(filmId, filmEntities.data?.id)
    }

    @Test
    fun getTvWithId() {
        val tvDummy = MutableLiveData<TvModel>()
        tvDummy.value = tvList[0]
        `when`(local.getDetailTv(tvId!!)).thenReturn(tvDummy)

        val tvEntities = LiveDataTestUtil.getValue(movieRepository.getTvWithId(tvList[0].id))
        verify(local).getDetailTv(tvId)
        assertNotNull(tvEntities.data)
        assertEquals(tvId, tvEntities.data?.id)
    }

    @Test
    fun getFavoriteFilm() {
        val dataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FilmModel>
        `when`(local.getFavoriteFilm()).thenReturn(dataSource)
        movieRepository.getFavoriteFilm()

        val filmEntities =
            Resource.success(PagedListUtil.mockPagedList(DummyObject.generateMovies()))
        verify(local).getFavoriteFilm()
        assertNotNull(filmEntities)
        assertEquals(filmResponse.size.toLong(), filmEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTv() {
        val dataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvModel>
        `when`(local.getFavoriteTv()).thenReturn(dataSource)
        movieRepository.getFavoriteTv()

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(DummyObject.generateTv()))
        verify(local).getFavoriteTv()
        assertNotNull(tvEntities)
        assertEquals(tvResponse.size.toLong(), tvEntities.data?.size?.toLong())
    }

    @Test
    fun setFavoriteFilm() {
        `when`(appExecutor.diskIO()).thenReturn(testExecutor.diskIO())
        doNothing().`when`(local).setFavoriteFilm(filmList[0], true)
        movieRepository.setFavoriteFilm(filmList[0], true)
        verify(local, times(1)).setFavoriteFilm(filmList[0], true)
        assertEquals(false, filmList[0].favorite)
    }

    @Test
    fun setFavoriteTv() {
        `when`(appExecutor.diskIO()).thenReturn(testExecutor.diskIO())
        doNothing().`when`(local).setFavoriteTv(tvList[0], true)
        movieRepository.setFavoriteTv(tvList[0], true)
        verify(local, times(1)).setFavoriteTv(tvList[0], true)
        assertEquals(false, tvList[0].favorite)
    }

}