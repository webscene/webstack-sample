package org.example.webstack.server

import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.kotlin.core.http.HttpServerOptions

@Suppress("unused")
/**
 * Main Verticle that runs the web server.
 * @author Nick Apperley
 */
class Server : AbstractVerticle() {
    override fun start() {
        val router = Router.router(vertx)
//        val keyStoreOptions = JksOptions(path = "config/server-keystore.jks", password = "wibble")
//        val serverOptions = HttpServerOptions(port = 8080, host = "localhost", ssl = true,
//                keyStoreOptions = keyStoreOptions)
        val serverOptions = HttpServerOptions(port = 8080, host = "localhost")

        println("Starting server...")
        setupRoutes(router)
        // Must assign a request handler to the HTTP server otherwise an exception is thrown.
        vertx.createHttpServer(serverOptions).requestHandler { router.accept(it) }.listen()
        println("Server Access: ${serverOptions.protocol.toLowerCase()}://${serverOptions.host}:${serverOptions.port}")
    }

    override fun stop() {
        println("Stopping server...")
    }

    private fun setupRoutes(router: Router) {
        router.route("/public/*").handler(StaticHandler.create("public"))
        router.get("/").handler { routingCtx ->
            val resp = routingCtx.response()

            resp.statusCode = HttpStatus.OK.num
            resp.putHeader("content-type", ContentType.HTML.txt).end(createHomePage())
        }
        router.get("/welcome").handler { routingCtx ->
            val resp = routingCtx.response()

            resp.statusCode = HttpStatus.OK.num
            resp.putHeader("content-type", ContentType.HTML.txt).end(createWelcomePage())
        }
        setupApiRoutes(router)
    }

    private fun setupApiRoutes(router: Router) {
        router.get("/api/server-status").handler { routingCtx ->
            val resp = routingCtx.response()

            resp.statusCode = HttpStatus.OK.num
            resp.putHeader("content-type", ContentType.PLAIN_TXT.txt).end("Server Status: Up")
        }
        router.post("/api/greeting").handler(BodyHandler.create())
        router.post("/api/greeting").handler { routingCtx ->
            val resp = routingCtx.response()

            resp.statusCode = HttpStatus.OK.num
            resp.putHeader("content-type", ContentType.PLAIN_TXT.txt).end(
                    "Hello ${routingCtx.bodyAsJson.getString("name")}!")
        }
    }

    val HttpServerOptions.protocol
        get() = if (isSsl) "HTTPS" else "HTTP"
}