package io.github.aretche

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("io.github.aretche")
                .mainClass(Application.javaClass)
                .start()
    }
}