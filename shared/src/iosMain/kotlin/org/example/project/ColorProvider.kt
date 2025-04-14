package org.example.project

import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.getUIColor
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.CoreGraphics.CGFloatVar
import platform.UIKit.UIColor

internal actual object ColorProvider {
    actual fun getColorInt(resource: ColorResource): Int {
        return resource.getUIColor().toInt()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun UIColor.toInt(): Int {
        return memScoped {
            val red : CGFloatVar = alloc()
            val green : CGFloatVar = alloc()
            val blue : CGFloatVar = alloc()
            val alpha : CGFloatVar = alloc()

            getRed(red.ptr, green.ptr, blue.ptr, null)

            val redInt = (red.value * 255 + 0.5).toInt()
            val greenInt = (green.value * 255 + 0.5).toInt()
            val blueInt = (blue.value * 255 + 0.5).toInt()
            val alphaInt = (alpha.value * 255 + 0.5).toInt()

            (alphaInt shl 24) or (redInt shl 16) or (greenInt shl 8) or blueInt
        }
    }
}