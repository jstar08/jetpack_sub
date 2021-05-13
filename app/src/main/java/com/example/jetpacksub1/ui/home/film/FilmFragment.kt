package com.example.jetpacksub1.ui.home.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jetpacksub1.databinding.FragmentFilmBinding
import com.example.jetpacksub1.utils.ViewModelFactory
import com.example.jetpacksub1.vo.Status

class FilmFragment : Fragment() {

    private lateinit var binding: FragmentFilmBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            val viewModel = ViewModelProvider(
                this,
                factory
            )[FilmViewModel::class.java]
            val filmAdapter = FilmAdapter(requireContext())


            viewModel.getFilm().observe(viewLifecycleOwner, { film ->
                if (film != null) {
                    when (film.status) {
                        Status.LOADING -> showLoading(true)
                        Status.SUCCESS -> {
                            showLoading(false)
                            filmAdapter.submitList(film.data!!)
                            filmAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            showLoading(false)
                            Toast.makeText(context, "Data Loading Failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            })
            binding.rvMovie.adapter = filmAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvMovie.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvMovie.visibility = View.VISIBLE
        }
    }

}