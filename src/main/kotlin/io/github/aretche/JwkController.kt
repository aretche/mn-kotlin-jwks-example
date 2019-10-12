package io.github.aretche

import io.github.aretche.domain.RsaJwk
import io.micronaut.http.annotation.*

@Controller("/jwt")
class JwkController (private val rsaJwkRepository: RsaJwkRepository){
    @Get("/jwks.json")
    fun show(): List<RsaJwk> {
        return rsaJwkRepository.findAll()
    }
}