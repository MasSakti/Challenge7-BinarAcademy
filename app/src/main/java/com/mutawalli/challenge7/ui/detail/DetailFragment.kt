package com.mutawalli.challenge7.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.mutawalli.challenge7.R
import com.mutawalli.challenge7.data.local.favorite.MovieEntity
import com.mutawalli.challenge7.databinding.FragmentDetailBinding
import com.mutawalli.challenge7.ui.MainActivity
import com.mutawalli.challenge7.utils.urlImage
import com.google.android.material.snackbar.Snackbar
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).detailViewModel
        viewModel.getDetail(args.movieId)

        binding.ivBacktoList.setOnClickListener {
            it.findNavController().popBackStack()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingContainer.visibility = View.VISIBLE
            } else {
                binding.loadingContainer.visibility = View.GONE
            }
        }

        viewModel.detail.observe(viewLifecycleOwner) { movieDetail ->

            Glide.with(binding.ivBackDrop)
                .load(urlImage + movieDetail?.backdropPath)
                .error(R.drawable.ic_broken)
                .into(binding.ivBackDrop)

            Glide.with(binding.ivPosterDetail)
                .load(urlImage + movieDetail?.posterPath)
                .error(R.drawable.ic_broken)
                .into(binding.ivPosterDetail)

            binding.apply {
                tvTitleDetail.text = movieDetail?.title
                tvVoteDetail.text = "from " + movieDetail?.voteCount.toString() + " voters"
                tvOverviewDetail.text = movieDetail?.overview
                tvRatingDetail.text = "Rating " + movieDetail?.voteAverage.toString()
                tvPopularityDetail.text = "Popularity: " + movieDetail?.popularity.toString()

                if (movieDetail?.releaseDate != null && movieDetail.releaseDate.isNotBlank()) {
                    tvDateDetail.text = "Release: " + movieDetail.releaseDate
                } else {
                    tvDateDetail.visibility = View.GONE
                }
            }

            val movieEntity = MovieEntity(
                movieDetail?.id!!,
                movieDetail.backdropPath,
                movieDetail.overview,
                movieDetail.popularity,
                movieDetail.posterPath,
                movieDetail.releaseDate,
                movieDetail.title,
                movieDetail.voteAverage,
                movieDetail.voteCount
//                movieDetail.popularity
            )

            viewModel.showUserIsFavorite(movieEntity)

            binding.fabFavorite.setOnClickListener {
                viewModel.checkFavoriteUser(movieEntity)
            }

        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.ic_favorite_true
                    )
                )
            } else {
                binding.fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavorite.context,
                        R.drawable.ic_favorite_false
                    )
                )
            }
        }

        viewModel.errorStatus.observe(viewLifecycleOwner) { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShown()
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}