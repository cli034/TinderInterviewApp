package com.example.tinderinterviewapp.data.servermodels

import com.example.tinderinterviewapp.domain.models.GifModel
import com.example.tinderinterviewapp.domain.models.MetaModel
import com.example.tinderinterviewapp.domain.models.PaginationModel

data class GiphyResponse(
    val data: List<GifModel>,
    val pagination: PaginationModel,
    val meta: MetaModel
)
