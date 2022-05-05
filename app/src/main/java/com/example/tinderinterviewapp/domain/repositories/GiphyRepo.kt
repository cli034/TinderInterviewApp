package com.example.tinderinterviewapp.domain.repositories

import com.example.tinderinterviewapp.domain.models.GifModel

interface GiphyRepo {

    suspend fun retrieveTrendingGifs(limit: Int?, offset: Int?): List<GifModel>
}