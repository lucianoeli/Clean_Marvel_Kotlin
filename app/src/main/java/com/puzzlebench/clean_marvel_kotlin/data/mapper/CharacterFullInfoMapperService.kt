package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterFullInfoResponse
import com.puzzlebench.clean_marvel_kotlin.domain.model.CharacterFullInfo

open class CharacterFullInfoMapperService: BaseMapperRepository<CharacterFullInfoResponse, CharacterFullInfo>{

    override fun transform(characterFiResponse: CharacterFullInfoResponse): CharacterFullInfo = CharacterFullInfo(
            characterFiResponse.comics,
            characterFiResponse.series,
            characterFiResponse.stories,
            characterFiResponse.events
    )

    override fun transformToResponse(type: CharacterFullInfo): CharacterFullInfoResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}