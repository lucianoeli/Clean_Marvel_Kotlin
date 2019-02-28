package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.local.CharacterRealm
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character

class CharacterMapperLocal: BaseMapperRepository<Character, CharacterRealm> {

    override fun transform(type: Character): CharacterRealm {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transformToResponse(type: CharacterRealm): Character {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}