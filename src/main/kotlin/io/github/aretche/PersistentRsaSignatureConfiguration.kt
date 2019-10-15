package io.github.aretche

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.RSAKey
import io.micronaut.runtime.context.scope.Refreshable
import io.micronaut.security.token.jwt.signature.rsa.RSASignatureGeneratorConfiguration
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import javax.inject.Named
import javax.inject.Singleton

@Refreshable // Component is refreshable via a refresh event
@Singleton
@Named("generator")
class PersistentRsaSignatureConfiguration(private val rsaJwkRepository: RsaJwkRepositoryImpl) : RSASignatureGeneratorConfiguration {

    private var keyPair: RSAKey? = null

    @Synchronized private fun ensureKeyPair() {
        val lastKey = rsaJwkRepository.getLastRSAKey()
        if(null == lastKey){
            rsaJwkRepository.createKey()
            keyPair = rsaJwkRepository.getLastRSAKey()
        } else if (keyPair != lastKey){
            keyPair = lastKey
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