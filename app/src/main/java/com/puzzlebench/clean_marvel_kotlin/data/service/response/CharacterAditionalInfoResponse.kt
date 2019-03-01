package com.puzzlebench.clean_marvel_kotlin.data.service.response

class CharacterAditionalInfoResponse(
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: ThumbnailResponse,
        val comics: String,
        val series: String,
        val stories: String,
        val events: String
)