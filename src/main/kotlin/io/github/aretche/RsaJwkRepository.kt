package io.github.aretche

import com.nimbusds.jose.jwk.RSAKey
import io.github.aretche.domain.RsaJwk

interface RsaJwkRepository {
    fun getLast(): RsaJwk?
    fun getLastRSAKey(): RSAKey?
    fun findByKid(kid: String): RsaJwk?
    fun findAll(): List<RsaJwk>
    fun createKey(): RsaJwk?
}