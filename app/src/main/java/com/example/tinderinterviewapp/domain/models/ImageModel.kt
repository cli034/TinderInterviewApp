package com.example.tinderinterviewapp.domain.models

data class ImageModel(
    val original: ImageDetailModel,
    val downsized: ImageDetailModel,
    val fixed_height: ImageDetailModel,
    val fixed_height_downsampled: ImageDetailModel
)
