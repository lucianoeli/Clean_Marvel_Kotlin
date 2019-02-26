package com.puzzlebench.clean_marvel_kotlin.data.service

import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperService
import com.puzzlebench.clean_marvel_kotlin.data.service.api.MarvelApi
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.CharacterAditionalInfo
import io.reactivex.Observable

class CharacterServicesImpl(private val api: MarvelResquestGenerator = MarvelResquestGenerator(), private val mapper: CharacterMapperService = CharacterMapperService()) {

    fun getCaracters(): Observable<List<Character>> {
        return Observable.create { subscriber ->
            val callResponse = api.createService(MarvelApi::class.java).getCharacter()
            val response = callResponse.execute()
            Log.d("GET_CHARACTERS",response.toString())
            if (response.isSuccessful) {
                subscriber.onNext(mapper.transform(response.body()!!.data!!.characters))
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    fun getCharacterById(id: Int): Observable<CharacterAditionalInfo>{
        return Observable.create { subscriber ->
            val callResponse = api.createService(MarvelApi::class.java).getChatacterById(id)
            val response = callResponse.execute()
            Log.d("GET_CHARACTERS_BY_ID",response.toString())

            if (response.isSuccessful) {
                //subscriber.onNext(mapper.transform(response.body()!!.data!!.characters))
                //subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}