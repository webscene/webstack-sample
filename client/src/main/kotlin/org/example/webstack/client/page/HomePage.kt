package org.example.webstack.client.page

import org.example.webstack.client.HttpStatus
import org.w3c.xhr.XMLHttpRequest
import org.webscene.client.HttpMethod
import org.webscene.client.dom.DomEditor
import org.webscene.client.html.HtmlCreator
import org.webscene.client.html.ParentHtmlTag
import org.webscene.client.httpClient
import kotlin.browser.document

class HomePage {
    private val mainHeader = HtmlCreator.element("h1") {
        id = "main-header"
        +"Home Page"
    }
    private val mainLayout = HtmlCreator.parentElement("div") {
        id = "main-layout"
        attributes["container"] = "true"
        children.add(mainHeader)
    }

    init {
        document.body?.prepend(mainLayout.toDomElement())
        DomEditor.replaceElement {
            mainHeader.attributes["size"] = "large"
            mainHeader.txtContent = "Web stack home page."
            mainHeader
        }
        doHttpRequest(mainLayout)
    }

    private fun doHttpRequest(mainLayout: ParentHtmlTag) {
        val reqData = arrayOf<Pair<String, *>>("name" to "John")

        httpClient(url = "/api/greeting", method = HttpMethod.POST, reqData = reqData, sendNow = true) {
            onload = {
                if (status == HttpStatus.OK.num) processHttpResponse(this, mainLayout)
            }
        }
    }

    private fun processHttpResponse(client: XMLHttpRequest, mainLayout: ParentHtmlTag) {
        DomEditor.replaceElement {
            mainLayout.children.add(HtmlCreator.parentElement("p") {
                htmlElement("b") { +client.responseText }
            })
            mainLayout
        }
    }
}