package org.example.webstack.client

import org.example.webstack.client.page.HomePage
import org.example.webstack.client.page.WelcomePage
import org.webscene.client.dom.DomQuery
import kotlin.browser.window

// Bootstrap for the web client.
// Author - Nick Apperley

fun main(args: Array<String>) {
    window.onload = {
        if (DomQuery.pageId() == "home") HomePage()
        else if (DomQuery.pageId() == "welcome") WelcomePage()
        HttpStatus.OK.num
    }
}