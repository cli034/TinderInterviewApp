package com.example.tinderinterviewapp.data.repositories

import com.example.tinderinterviewapp.BuildConfig
import com.example.tinderinterviewapp.data.api.GiphyService
import com.example.tinderinterviewapp.domain.models.GifModel
import com.example.tinderinterviewapp.domain.repositories.GiphyRepo
import javax.inject.Inject

class GiphyRepoImpl @Inject constructor(
    private val giphyService: GiphyService
): GiphyRepo {

    override suspend fun retrieveTrendingGifs(limit: Int?, offset: Int?): List<GifModel> {
        return giphyService.retrieveTrendingGifs(apiKey = API_KEY, limit = limit, offset = offset).data
    }

    companion object {
        private const val API_KEY = BuildConfig.api_key
    }
}