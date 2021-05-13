package com.example.jetpacksub1.ui.favorite.filmfavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jetpacksub1.databinding.FragmentFavoriteFilmBinding
import com.example.jetpacksub1.utils.ViewModelFactory


class FavoriteFilmFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteFilmBinding
    private lateinit var viewModel: FavoriteFilmViewModel
    private lateinit var adapter: FavoriteFilmAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteFilmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())

        viewModel = ViewModelProvider(this, factory)[FavoriteFilmViewModel::class.java]

        adapter = FavoriteFilmAdapter(requireContext())

        viewModel.getFavoriteFilm().observe(viewLifecycleOwner, { film ->
            adapter.submitList(film)
            adapter.notifyDataSetChanged()
        })

        binding.rvFavMovie.adapter = adapter

    }

}