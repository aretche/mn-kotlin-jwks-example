package io.github.aretche

import com.nimbusds.jose.jwk.RSAKey
import io.micronaut.security.token.jwt.endpoints.JwkProvider
import java.util.*
import javax.inject.Singleton

@Singleton
class PersistentRsaJwksProvider(private val rsaJwkRepository: RsaJwkRepository) : JwkProvider {

    override fun retrieveJsonWebKeys(): List<RSAKey> {
        val retval = ArrayList<RSAKey>()
        val keyPairs = rsaJwkRepository.findAll()
        for (keyPair in keyPairs) {
            val jwk = RSAKey.parse(keyPair.privateJwk)
            retval.add(jwk.toPublicJWK())
        }
        return retval
    }

}