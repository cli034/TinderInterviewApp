package com.example.tinderinterviewapp.presentation.ui.giphy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.tinderinterviewapp.R
import com.example.tinderinterviewapp.databinding.FragmentGifDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifDetailFragment : Fragment() {

    private val safeArgs: GifDetailFragmentArgs by navArgs()

    private var _binding: FragmentGifDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGifDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {

        with(binding) {

            activity?.let { activity ->

                Glide.with(activity)
                    .asGif()
                    .load(safeArgs.gifUrl)
                    .into(ivGif)
            }
        }
    }
}