package org.example.webstack.server

import java.io.InputStream
import org.webscene.server.WebScene as ws

// Provides the web pages for the web app.
// Author - Nick Apperley

private val scriptSources = fetchScriptSources()

fun createHomePage(): String {
    val template = ws.pageTemplate(title = "Kotlin Web Stack", pageId = "home")

    scriptSources.forEach { template.scripts.add(createJSScript(it)) }
    return template.content?.createText() ?: ""
}

fun createWelcomePage(): String {
    val template = ws.pageTemplate(title = "Kotlin Web Stack - Welcome", pageId = "welcome")

    scriptSources.forEach { template.scripts.add(createJSScript(it)) }
    return template.content?.createText() ?: ""
}

private fun createJSScript(src: String) = ws.parentHtmlElement("script") {
    attributes["src"] = src
    attributes["type"] = "application/javascript"
}

private fun fetchScriptSources(): Array<String> {
    val inputStream: InputStream? = "".javaClass.getResourceAsStream("/config/scripts.txt")
    val tmp = mutableListOf<String>()

    inputStream?.use { input ->
        input.bufferedReader().forEachLine { line ->
            if (line != "\n") tmp.add(line)
        }
    }
    return tmp.toTypedArray()
}