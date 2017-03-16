package org.example.webstack.client

import org.webscene.client.html.ParentHtmlElement

/**
 * Custom parent HTML element.
 * @author Nick Apperley
 */
class FruitList : ParentHtmlElement() {
    override var tagName
        get() = "ol"
        set(value) {}

    init {
        val fruit = arrayOf("Orange", "Kiwifruit", "Mango", "Apple")

        fruit.forEach { htmlElement("li") { +it } }
    }
}