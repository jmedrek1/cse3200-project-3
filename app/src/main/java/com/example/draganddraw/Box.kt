package com.example.draganddraw

import android.graphics.PointF
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Box(val start: PointF) {

    var end: PointF = start
    val left: Float
        get() = min(start.x, end.x)

    val right: Float
        get() = max(start.x, end.x)

    val top: Float
        get() = min(start.y, end.y)

    val bottom: Float
        get() = max(start.y, end.y)

    val width: Float
        get() = abs(end.x - start.x)

    val height: Float
        get() = abs(end.y - start.y)

}
