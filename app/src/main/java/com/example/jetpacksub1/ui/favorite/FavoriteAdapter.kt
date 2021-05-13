package com.example.jetpacksub1.ui.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jetpacksub1.ui.favorite.filmfavorite.FavoriteFilmFragment
import com.example.jetpacksub1.ui.favorite.tvfavorite.FavoriteTvFragment

class FavoriteAdapter(private val fragment: FavoriteFragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteFilmFragment()
            1 -> FavoriteTvFragment()
            else -> FavoriteFilmFragment()
        }
    }


}