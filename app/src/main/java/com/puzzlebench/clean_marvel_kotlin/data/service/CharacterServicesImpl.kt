package com.puzzlebench.clean_marvel_kotlin.data.service

import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperService
import com.puzzlebench.clean_marvel_kotlin.data.service.api.MarvelApi
import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterResponse
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.reactivex.Observable

class CharacterServicesImpl(
        private val api: MarvelResquestGenerator = MarvelResquestGenerator(),
        private val mapper: CharacterMapperService = CharacterMapperService()

) {

    fun getCaracters(): Observable<List<Character>> {
        return Observable.create { subscriber ->
            val callResponse = api.createService(MarvelApi::class.java).getCharacter()
            val response = callResponse.execute()
            if (response.isSuccessful) {
                subscriber.onNext(mapper.transform(response.body()?.data?.characters?: emptyList()))
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun getCharacterById(id: Int): Observable<List<Character>> {
        return Observable.create { subscriber ->
            val callResponse = api.createService(MarvelApi::class.java).getChatacterById(id)
            val response = callResponse.execute()
            if (response.isSuccessful) {
                subscriber.onNext(mapper.transform(response.body()?.data?.characters?: emptyList()))
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}