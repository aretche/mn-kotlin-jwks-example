package io.github.aretche

import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import io.github.aretche.domain.RsaJwk
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession
import io.micronaut.spring.tx.annotation.Transactional
import java.util.*
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Singleton
open class RsaJwkRepositoryImpl(@param:CurrentSession
                                @field:PersistenceContext
                                private val entityManager: EntityManager) : RsaJwkRepository {

    @Transactional(readOnly = true)
    override fun findByKid(kid: String): RsaJwk? {
        val qlString = "SELECT k FROM RsaJwk as k WHERE k.kid = '$kid'"
        val query = entityManager.createQuery(qlString, RsaJwk::class.java)
        return query.singleResult
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<RsaJwk> {
        val qlString = "SELECT k FROM RsaJwk as k WHERE k.active = TRUE ORDER BY k.dateCreated DESC"
        val query = entityManager.createQuery(qlString, RsaJwk::class.java)
        return query.resultList
    }

    @Transactional(readOnly = true)
    override fun getLast(): RsaJwk? {
        val qlString = "SELECT k FROM RsaJwk as k WHERE k.active = TRUE ORDER BY k.dateCreated DESC"
        val query = entityManager.createQuery(qlString, RsaJwk::class.java)
        return if (query.resultList.isNotEmpty()) {
            query.resultList.first()
        } else {
            null
        }
    }

    @Transactional(readOnly = true)
    override fun getLastRSAKey(): RSAKey? {
        val rsaJwk = this.getLast()
        return if (null != rsaJwk) {
            RSAKey.parse(rsaJwk.privateJwk)
        } else {
            null
        }
    }

    @Transactional
    override fun createKey(): RsaJwk? {
        // Generate 2048-bit RSA key pair, attach some metadata
        val rsaJwk = RsaJwk(RSAKeyGenerator(2048)
                .keyUse(KeyUse.SIGNATURE) // indicate the intended use of the key
                .keyID(UUID.randomUUID().toString()) // give the key a unique ID
                .generate())
        entityManager.persist(rsaJwk)
        return rsaJwk
    }
}