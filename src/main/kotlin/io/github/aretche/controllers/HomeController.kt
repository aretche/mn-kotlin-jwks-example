package io.github.aretche.controllers

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator
import org.slf4j.LoggerFactory
import java.security.Principal
import javax.inject.Inject

@Controller("/")
class HomeController(@Inject val jwtTokenGenerator: JwtTokenGenerator) {

    @Secured("isAuthenticated()")
    @Produces(MediaType.TEXT_PLAIN)
    @Get("/")
    fun index(principal: Principal): String {
        LOG.info("Ingresando a /")
        return principal.name
    }

    @Secured("isAnonymous()")
    @Produces(MediaType.TEXT_PLAIN)
    @Get("/custom")
    fun customJWT(): String {
        LOG.info("Ingresando a /custom")
        val customClaims = mapOf<String, Any>(
                "name" to "Jack",
                "email" to "jack@example.com",
                "twitter" to "@Jack"
        )
        val token = jwtTokenGenerator.generateToken(customClaims)
        return if (token.isPresent) {
            token.get()
        } else {
            "Token not generated"
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(HomeController::class.java)
    }
}
