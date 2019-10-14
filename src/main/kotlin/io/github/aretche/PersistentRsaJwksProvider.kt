package io.github.aretche

import com.nimbusds.jose.jwk.JWK
import io.micronaut.security.token.jwt.endpoints.JwkProvider
import java.util.*
import javax.inject.Singleton

@Singleton
class PersistentRsaJwksProvider(private val rsaJwkRepository: RsaJwkRepository) : JwkProvider {

    override fun retrieveJsonWebKeys(): List<JWK> {
        val retval = ArrayList<JWK>()
        val keyPairs = rsaJwkRepository.findAll()
        for (keyPair in keyPairs) {
            val jwk = JWK.parse(keyPair.privateJwk)
            retval.add(jwk.toPublicJWK())
        }
        return retval
    }

}