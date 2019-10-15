package io.github.aretche

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.RSAKey
import io.micronaut.security.token.jwt.signature.rsa.RSASignatureGeneratorConfiguration
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Named("generator")
class PersistentRsaSignatureConfiguration(private val rsaJwkRepository: RsaJwkRepositoryImpl) : RSASignatureGeneratorConfiguration {

    private var keyPair: RSAKey? = null

    @Synchronized
    private fun ensureKeyPair() {
        if (keyPair == null) {
            val rsaJwk = rsaJwkRepository.getLast()
            keyPair = if (rsaJwk == null) {
                RSAKey.parse(rsaJwkRepository.createKey()?.privateJwk)
            } else {
                RSAKey.parse(rsaJwk.privateJwk)
            }
        }
    }

    override fun getPublicKey(): RSAPublicKey {
        ensureKeyPair()
        return keyPair!!.toRSAPublicKey()
    }

    override fun getPrivateKey(): RSAPrivateKey {
        ensureKeyPair()
        return keyPair!!.toRSAPrivateKey()
    }

    override fun getJwsAlgorithm(): JWSAlgorithm {
        return JWSAlgorithm.RS256
    }
}