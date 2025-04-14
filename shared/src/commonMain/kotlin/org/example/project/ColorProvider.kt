package org.example.project

import dev.icerock.moko.resources.ColorResource

internal expect object ColorProvider {
    fun getColorInt(resource: ColorResource): Int
}