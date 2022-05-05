package com.example.tinderinterviewapp.presentation.ui.giphy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinderinterviewapp.domain.models.GifModel
import com.example.tinderinterviewapp.domain.usecases.RetrieveTrendingGifsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val retrieveTrendingGifsUseCase: RetrieveTrendingGifsUseCase
): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var currentOffset = 0

    private val _trendingGifs: MutableLiveData<List<GifModel>> by lazy {
        MutableLiveData<List<GifModel>>().also {
            getTrendingGifs()
        }
    }
    val trendingGifs: LiveData<List<GifModel>> by lazy { _trendingGifs }

    fun getTrendingGifs() {
        showProgressBar()
        viewModelScope.launch(errorHandler()) {
            val trendingGifs = retrieveTrendingGifsUseCase(
                limit = TRENDING_GIF_LIMIT,
                offset = currentOffset
            )
            _trendingGifs.value = processPagination(trendingGifs)
            currentOffset += TRENDING_GIF_LIMIT
            hideProgressBar()
        }
    }

    private fun processPagination(responseList: List<GifModel>): List<GifModel> {
        return _trendingGifs.value?.let { gifModels ->
            mutableListOf<GifModel>().apply {
                addAll(gifModels)
                addAll(responseList)
            }
        } ?: responseList
    }

    private fun errorHandler() = CoroutineExceptionHandler { _, throwable ->
        hideProgressBar()
        Log.d("error_msg", throwable.toString())
    }

    private fun showProgressBar() {
        _isLoading.value = true
    }

    private fun hideProgressBar() {
        _isLoading.value = false
    }

    companion object {

        const val TRENDING_GIF_LIMIT = 25
    }
}