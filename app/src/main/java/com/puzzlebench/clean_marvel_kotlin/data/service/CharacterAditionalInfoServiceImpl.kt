package com.puzzlebench.clean_marvel_kotlin.data.service

import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterAditionalInfoMapperService

import com.puzzlebench.clean_marvel_kotlin.data.service.api.MarvelApi
import com.puzzlebench.clean_marvel_kotlin.domain.model.CharacterAditionalInfo
import io.reactivex.Observable

class CharacterAditionalInfoServiceImpl(
        private val api: MarvelResquestGenerator = MarvelResquestGenerator(),
        private val mapper: CharacterAditionalInfoMapperService = CharacterAditionalInfoMapperService(),
        private val id: Int
) {

    fun getCharacterById(id: Int): Observable<CharacterAditionalInfo> {
        return Observable.create { subscriber ->
            val callResponse = api.createService(MarvelApi::class.java).getChatacterById(id)
            val response = callResponse.execute()
            Log.d("GetCharacByID",response.toString())
            if (response.isSuccessful) {
                subscriber.onNext(mapper.transform(response.body()!!.data!!.characterData))
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }

        }
    }
}