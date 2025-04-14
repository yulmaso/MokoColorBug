package org.example.project

object HtmlProvider {
    private var counter = 0

    fun getHtmlString(): String {
        val backgroundColor = ColorProvider.getColorInt(SharedRes.colors.background)
        return """
            <HTML>
            <HEAD>
            <META http-equiv="Content-Type" content_main="text/html; charset=utf-8">
            <style>
            body { background-color: $backgroundColor; font-size: 60px; }
            </style>
            </HEAD>
            <BODY>
            <a href="url">CLICK TO REFRESH</a>
            ${++counter}
            </BODY>
        """.trimIndent()
    }
}