package com.mutawalli.challenge7.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutawalli.challenge7.databinding.FragmentFavoriteBinding
import com.mutawalli.challenge7.ui.MainActivity

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).favoriteViewModel

        viewModel.getUser()

        val adapter = FavoriteAdapter()
        binding.rvListFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListFavorite.adapter = adapter

        viewModel.getListFavorite.observe(viewLifecycleOwner) { favoriteList ->
            adapter.submitList(favoriteList)
        }

        binding.ivBacktoList3.setOnClickListener {
            it.findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}