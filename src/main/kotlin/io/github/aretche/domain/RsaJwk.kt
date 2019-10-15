package io.github.aretche.domain

import com.nimbusds.jose.jwk.JWK
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rsa_jwk")
class RsaJwk(
        @Id
        @Column(name = "kid", nullable = false)
        val kid: String,

        @Column(name = "private_jwk", length = 10000, nullable = false)
        val privateJwk: String,

        @Column(name = "active", nullable = false)
        val active: Boolean,

        @Column(name = "date_created", nullable = false)
        val dateCreated: Date
){
        constructor(jwk: JWK): this(kid = jwk.keyID, privateJwk = jwk.toJSONString(), active = true, dateCreated = Date())
}