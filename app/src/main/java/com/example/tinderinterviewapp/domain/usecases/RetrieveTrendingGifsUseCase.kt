package com.example.tinderinterviewapp.domain.usecases

import com.example.tinderinterviewapp.domain.models.GifModel
import com.example.tinderinterviewapp.domain.repositories.GiphyRepo
import javax.inject.Inject

class RetrieveTrendingGifsUseCase @Inject constructor(
    private val giphyRepo: GiphyRepo
) {

    suspend operator fun invoke(
        limit: Int? = null,
        offset: Int? = null
    ): List<GifModel> {
        return giphyRepo.retrieveTrendingGifs(
            limit = limit,
            offset = offset
        )
    }
}