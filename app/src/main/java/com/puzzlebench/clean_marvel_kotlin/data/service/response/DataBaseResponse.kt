package com.puzzlebench.clean_marvel_kotlin.data.service.response

import com.google.gson.annotations.SerializedName

class DataBaseResponse<T>(
        @SerializedName("results") val characters: List<CharacterResponse>,
        @SerializedName("characterData") val characterData: CharacterAditionalInfoResponse,
        val offset: Int,
        val limit: Int,
        val total: Int
)