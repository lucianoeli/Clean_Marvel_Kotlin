package com.puzzlebench.clean_marvel_kotlin.domain.model

class CharacterAditionalInfo(
        //TODO fix this, use this class instead of character when requesting aditional info.
        val character: Character,
        val comicsUrl: String,
        val seriesUrl: String,
        val storiesUrl: String,
        val eventsUrl: String
)
