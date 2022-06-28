package com.mutawalli.challenge7.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mutawalli.challenge7.data.api.Movies
import com.mutawalli.challenge7.utils.urlImage
import com.bumptech.glide.Glide
import com.mutawalli.challenge7.R
import com.mutawalli.challenge7.databinding.ListItemMovieBinding

class HomeAdapter : ListAdapter<Movies, HomeAdapter.ViewHolder>(DiffCallBack()) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemMovieBinding.bind(view)

        fun bind(dataPopular: Movies) {
            binding.apply {
                binding.tvTitleMovie.text = dataPopular.title
                binding.tvDateMovie.text = "Release: " + dataPopular.releaseDate
                binding.tvRatingMovie.text = "Popularity: " + dataPopular.popularity.toString()
                Glide.with(binding.root).load(urlImage + dataPopular.posterPath)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(binding.ivImageMovie)
                root.setOnClickListener {
                    val id =
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(dataPopular.id!!)
                    it.findNavController().navigate(id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Movies>() {
    override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem == newItem
    }

}