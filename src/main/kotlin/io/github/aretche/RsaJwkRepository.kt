package io.github.aretche

import io.github.aretche.domain.RsaJwk

interface RsaJwkRepository {
    fun getLast(): RsaJwk?
    fun findByKid(kid: String): RsaJwk?
    fun findAll(): List<RsaJwk>
    fun createKey(): RsaJwk?
}