package io.github.aretche.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rsa_jwk")
class RsaJwk(
        @Id
        @GeneratedValue
        val id: Long,

        @Column(name = "kid", unique=true, nullable = false)
        val kid: String,

        @Column(name = "private_jwk", length = 5000, nullable = false)
        val privateJwk: String,

        @Column(name = "public_jwk", length = 1000, nullable = false)
        val publicJwk: String,

        @Column(name = "active", nullable = false)
        val active: Boolean,

        @Column(name = "date_created", nullable = false)
        val dateCreated: Date
)