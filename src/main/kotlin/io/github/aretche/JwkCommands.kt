package io.github.aretche

import com.fasterxml.jackson.annotation.JsonInclude
import io.github.aretche.domain.RsaJwk

@JsonInclude(JsonInclude.Include.ALWAYS)
data class Jwks(
        val keys: List<RsaJwk>
)