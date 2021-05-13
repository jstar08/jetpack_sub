package com.example.jetpacksub1.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.jetpacksub1.R
import com.example.jetpacksub1.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadFilm() {
        onView(withId(R.id.rv_movie)).check((matches(isDisplayed())))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                19
            )
        )
    }

    @Test
    fun loadDetailFilm() {
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.tv_titleDetail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_duration)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_ratingDetail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_overviewDetail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_releaseDate)).check(matches(isDisplayed()))

        onView(withId(R.id.cv_coverDetail)).check(matches(isDisplayed()))

        onView(withId(R.id.iv_actionBack)).perform(click())
    }

    @Test
    fun TestAddAndDeleteFavoriteFilm() {
        onView(withId(R.id.rv_movie)).check((matches(isDisplayed())))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                5
            )
        )
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                5,
                click()
            )
        )

        onView(withId(R.id.fb_addFav)).perform(click())

        pressBack()

        onView(withId(R.id.nav_favorite)).perform(click())
        onView(withText(R.string.favorite_movie)).perform(click())

        onView(withId(R.id.rv_fav_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )

        onView(withId(R.id.fb_addFav)).perform(click())
        pressBack()
        pressBack()
    }

    @Test
    fun loadTv() {
        onView(withId(R.id.nav_tv)).perform(click())
        onView(withId(R.id.rv_movie)).check((matches(isDisplayed())))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                19
            )
        )
    }

    @Test
    fun loadDetailTv() {
        onView(withId(R.id.nav_tv)).perform(click())
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.tv_titleDetail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_duration)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_ratingDetail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_overviewDetail)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_releaseDate)).check(matches(isDisplayed()))

        onView(withId(R.id.cv_coverDetail)).check(matches(isDisplayed()))

        onView(withId(R.id.iv_actionBack)).perform(click())
    }

    @Test
    fun TestAddAndDeleteFavoriteTv() {
        onView(withId(R.id.nav_tv)).perform(click())
        onView(withId(R.id.rv_movie)).check((matches(isDisplayed())))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                5
            )
        )
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                5,
                click()
            )
        )

        onView(withId(R.id.fb_addFav)).perform(click())

        pressBack()

        onView(withId(R.id.nav_favorite)).perform(click())
        onView(withText(R.string.favorite_tv)).perform(click())

        onView(withId(R.id.rv_fav_tv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()
            )
        )

        onView(withId(R.id.fb_addFav)).perform(click())
        pressBack()
        pressBack()
    }
}