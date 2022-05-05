package com.example.tinderinterviewapp.presentation.ui.giphy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tinderinterviewapp.R
import com.example.tinderinterviewapp.databinding.FragmentGiphyBinding
import com.example.tinderinterviewapp.domain.models.GifModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GiphyFragment : Fragment() {

    private val mGiphyViewModel by viewModels<GiphyViewModel>()

    private lateinit var mGifAdapter: GifAdapter

    private var _binding: FragmentGiphyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGiphyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel() {

        with(mGiphyViewModel) {

            trendingGifs.observe(viewLifecycleOwner, trendingGifsObserver)
            isLoading.observe(viewLifecycleOwner, loadingObserver)
        }
    }

    private fun initViews() {

        with(binding) {

            mGifAdapter = GifAdapter { gifModel ->
                val gifUrl = gifModel.images.original.url
                val navAction = GiphyFragmentDirections.actionGiphyFragmentToGifDetailFragment(
                    gifUrl = gifUrl
                )
                findNavController().navigate(navAction)
            }

            with(rvGifs) {
                adapter = mGifAdapter
                addOnScrollListener(onScrollListener)
            }
        }
    }

    private val trendingGifsObserver = Observer<List<GifModel>> { trendingGifs ->
        mGifAdapter.submitList(trendingGifs)
    }

    private val loadingObserver = Observer<Boolean> { showLoading ->
        with(binding) {
            if (showLoading) {
                pbarLoading.visibility = View.VISIBLE
            } else {
                pbarLoading.visibility = View.GONE
            }
        }
    }

    private val onScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            with(binding) {

                (rvGifs.layoutManager as? GridLayoutManager)?.let {
                    if (it.findLastCompletelyVisibleItemPosition() == it.itemCount - 1
                        && dy > 0) {
                        mGiphyViewModel.getTrendingGifs()
                    }
                }
            }
        }
    }
}