package org.example.webstack.client.page

import org.example.webstack.client.FruitList
import org.webscene.client.dom.DomEditor
import org.webscene.client.dom.prependParentHtmlElement
import org.webscene.client.html.HtmlCreator
import kotlin.browser.document

class WelcomePage {
    private val fruitList = FruitList()

    init {
        fruitList.id = "fruit-list"
        document.body?.prependParentHtmlElement("div") {
            id = "main-layout"
            attributes["container"] = "true"
            htmlElement("span") { +"Hello World :)" }
            children.add(fruitList)
        }
        DomEditor.replaceElement {
            fruitList.children.add(HtmlCreator.element("li") {
                +"Pear"
            })
            fruitList
        }
    }
}