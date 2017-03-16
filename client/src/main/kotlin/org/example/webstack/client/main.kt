package org.example.webstack.client

import org.webscene.client.WebScene.htmlElement
import org.webscene.client.comms.HttpMethod
import org.webscene.client.dom.prependParentHtmlElement
import org.webscene.client.html.ParentHtmlTag
import kotlin.browser.document
import kotlin.browser.window
import org.webscene.client.WebScene as ws

// Bootstrap for the web client.
// Author - Nick Apperley

fun main(args: Array<String>) {
    window.onload = {
        if (ws.DomQuery.pageId() == "home") setupHomePage()
        else if (ws.DomQuery.pageId() == "welcome") setupWelcomePage()
    }
}

private fun setupHomePage() {
    val mainHeader = ws.htmlElement("h1") {
        id = "main-header"
        +"Home Page"
    }
    val mainLayout = ws.parentHtmlElement("div") {
        id = "main-layout"
        attributes["container"] = "true"
        children.add(mainHeader)
    }

    document.body?.prepend(mainLayout.toDomElement())
    ws.updateDomElementById {
        mainHeader.attributes["size"] = "large"
        mainHeader.txtContent = "Web stack home page."
        mainHeader
    }
    doHomePageHttpRequest(mainLayout)
}

private fun doHomePageHttpRequest(mainLayout: ParentHtmlTag) {
    val reqData = arrayOf<Pair<String, *>>("name" to "John")

    ws.httpClient(url = "/api/greeting", method = HttpMethod.POST, reqData = reqData, sendNow = true) {
        onload = {
            if (status == HttpStatus.OK.num) {
                ws.updateDomElementById {
                    mainLayout.children.add(ws.parentHtmlElement("p") {
                        htmlElement("b") { +responseText }
                    })
                    mainLayout
                }
            }
        }
    }
}

private fun setupWelcomePage() {
    val fruitList = FruitList()

    fruitList.id = "fruit-list"
    document.body?.prependParentHtmlElement("div") {
        id = "main-layout"
        attributes["container"] = "true"
        htmlElement("span") { +"Hello World :)" }
        children.add(fruitList)
    }
    ws.updateDomElementById {
        fruitList.children.add(htmlElement("li") {
            +"Pear"
        })
        fruitList
    }
}