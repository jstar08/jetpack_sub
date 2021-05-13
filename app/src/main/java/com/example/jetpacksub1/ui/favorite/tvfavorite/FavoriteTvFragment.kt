package com.example.jetpacksub1.ui.favorite.tvfavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jetpacksub1.databinding.FragmentFavoriteTvBinding
import com.example.jetpacksub1.utils.ViewModelFactory

class FavoriteTvFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteTvBinding
    private lateinit var viewModel: FavoriteTvViewModel
    private lateinit var adapter: FavoriteTvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteTvBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())

        viewModel = ViewModelProvider(this, factory)[FavoriteTvViewModel::class.java]

        adapter = FavoriteTvAdapter(requireContext())

        viewModel.getFavoriteTv().observe(viewLifecycleOwner, { tv ->
            adapter.submitList(tv)
            adapter.notifyDataSetChanged()
        })

        binding.rvFavTv.adapter = adapter
    }
}