package io.github.aretche

import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import org.slf4j.LoggerFactory
import javax.inject.Singleton


@Singleton
class BootStrap(private val rsaJwkRepository: RsaJwkRepositoryImpl) : ApplicationEventListener<ServerStartupEvent> {

    private val log = LoggerFactory.getLogger(BootStrap::class.java)

    override fun onApplicationEvent(event: ServerStartupEvent?) {
        if (rsaJwkRepository.getLast() == null) {
            log.info("Creando clave")
            rsaJwkRepository.createKey()
        }
    }
}