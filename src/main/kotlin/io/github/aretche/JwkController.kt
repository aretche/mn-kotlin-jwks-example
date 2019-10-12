package io.github.aretche

import io.micronaut.http.annotation.*

@Controller("/jwt")
class JwkController (private val rsaJwkRepository: RsaJwkRepository){
    @Get("/jwks.json")
    fun show(): Jwks {
        return Jwks(keys = rsaJwkRepository.findAll())
    }
}