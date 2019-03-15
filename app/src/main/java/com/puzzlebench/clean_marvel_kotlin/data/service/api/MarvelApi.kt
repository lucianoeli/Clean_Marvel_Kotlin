package com.puzzlebench.clean_marvel_kotlin.data.service.api

import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterAditionalInfoResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.DataBaseResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.MarvelBaseResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {
    @GET("/v1/public/characters")
    //fun getCharacter(): Observable<MarvelBaseResponse<DataBaseResponse<ArrayList<CharacterResponse>>>>
    fun getCharacter(): Call<MarvelBaseResponse<DataBaseResponse<ArrayList<CharacterResponse>>>>

    @GET("/v1/public/characters/{characterId}")
    //fun getCharacterById(@Path("characterId")id: Int): Obse<MarvelBaseResponse<DataBaseResponse<ArrayList<CharacterAditionalInfoResponse>>>>
    fun getCharacterById(@Path("characterId")id: Int): Call<MarvelBaseResponse<DataBaseResponse<ArrayList<CharacterAditionalInfoResponse>>>>
}
