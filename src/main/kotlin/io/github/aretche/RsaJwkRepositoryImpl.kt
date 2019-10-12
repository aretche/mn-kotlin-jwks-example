package io.github.aretche

import io.github.aretche.domain.RsaJwk
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession
import io.micronaut.spring.tx.annotation.Transactional
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Singleton
open class RsaJwkRepositoryImpl(@param:CurrentSession
@field:PersistenceContext
private val entityManager: EntityManager) : RsaJwkRepository {
    @Transactional(readOnly = true)
    override fun findById(id: Long): RsaJwk? {
        return entityManager.find(RsaJwk::class.java, id)
    }

    @Transactional(readOnly = true)
    override fun findByKid(kid: String): RsaJwk? {
        //return entityManager.find(RsaJwk::class.java, kid)
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
}