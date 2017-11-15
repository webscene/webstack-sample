package org.example.webstack.server

import org.webscene.server.html.HtmlCreator.pageTemplate
import org.webscene.server.html.HtmlCreator as html
import java.io.InputStream

// Provides the web pages for the web app.
// Author - Nick Apperley

private val scriptSources = fetchScriptSources()

fun createHomePage(): String {
    val template = pageTemplate(title = "Kotlin Web Stack", pageId = "home")

    scriptSources.forEach { template.scripts.add(createJsScript(it)) }
    return template.content?.createText() ?: ""
}

fun createWelcomePage(): String {
    val template = pageTemplate(title = "Kotlin Web Stack - Welcome", pageId = "welcome")

    scriptSources.forEach { template.scripts.add(createJsScript(it)) }
    return template.content?.createText() ?: ""
}

private fun createJsScript(src: String) = html.parentHtmlElement("script") {
    attributes["src"] = src
    attributes["type"] = "application/javascript"
}

private fun fetchScriptSources(): Array<String> {
    val inputStream: InputStream? = "".javaClass.getResourceAsStream("/config/scripts.txt")
    val tmp = mutableListOf<String>()

    inputStream?.use { input ->
        input.bufferedReader().forEachLine { line ->
            if (line != "\n") tmp += line
        }
    }
    return tmp.toTypedArray()
}