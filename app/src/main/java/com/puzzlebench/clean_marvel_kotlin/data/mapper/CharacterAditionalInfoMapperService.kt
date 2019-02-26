package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterAditionalInfoResponse
import com.puzzlebench.clean_marvel_kotlin.domain.model.CharacterAditionalInfo

open class CharacterAditionalInfoMapperService: BaseMapperRepository<CharacterAditionalInfoResponse, CharacterAditionalInfo>{

    override fun transform(characterFiResponse: CharacterAditionalInfoResponse): CharacterAditionalInfo = CharacterAditionalInfo(
            characterFiResponse.comics,
            characterFiResponse.series,
            characterFiResponse.stories,
            characterFiResponse.events
    )

    override fun transformToResponse(type: CharacterAditionalInfo): CharacterAditionalInfoResponse {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}