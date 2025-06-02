package io.vopenia

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    runBlocking {
        embeddedServer(
            Netty,
            port = 8081,
            host = "0.0.0.0"
        ) {
            configureRouting()
        }.start(wait = true)

        println("finished")
    }
}
