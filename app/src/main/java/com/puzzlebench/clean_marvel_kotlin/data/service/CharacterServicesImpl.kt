package com.puzzlebench.clean_marvel_kotlin.data.service

import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperService
import com.puzzlebench.clean_marvel_kotlin.data.service.api.MarvelApi
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.ZERO
import io.reactivex.Observable

class CharacterServicesImpl(
        private val api: MarvelResquestGenerator = MarvelResquestGenerator(),
        private val mapper: CharacterMapperService = CharacterMapperService()
)
{
    fun getCharacters(): Observable<List<Character>> {
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

    fun getCharacterById(id: Int): Observable<com.puzzlebench.clean_marvel_kotlin.domain.model.Character> {
        return Observable.create { subscriber ->
            val callResponse = api.createService(MarvelApi::class.java).getCharacterById(id)
            val response = callResponse.execute()
            if (response.isSuccessful) {
                response.body()?.data?.characters?.get(ZERO)?.let { mapper.transform(it) }?.let { subscriber.onNext(it) }
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}