package com.example.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

private const val TAG = "BoxDrawingView"
private val background_color = Color.rgb(248, 239, 224)
private val foreground_color = Color.argb(32, 255, 0, 0)
class BoxDrawingView(
    context: Context,
    attrs: AttributeSet? = null
): View(context, attrs) {
    private var currentBox: Box? = null
    private val boxes = mutableListOf<Box>()
    private val maxBoxCount = 3
//    private var shapeCount = 0

    private val boxPaint = Paint().apply {
//        color = 0x22ff0000.toInt()
        color = foreground_color
    }

    private val backgroundPaint = Paint().apply {
//        color = 0xfff8efe0.toInt()
        color = background_color
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
        boxes.forEach {box ->
            if (box.width > box.height) {
                canvas.drawRect(
                    box.left,
                    box.top,
                    box.left + min(box.width, box.height),
                    box.top + min(box.width, box.height),
                    boxPaint
                )
            } else {
                canvas.drawOval(
                    box.left,
                    box.top,
                    box.left + min(box.width, box.height),
                    box.top + min(box.width, box.height),
                    boxPaint
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                if (boxes.size < maxBoxCount) {
                    currentBox = Box(current).also {
                        boxes.add(it)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }
        Log.d(TAG, "$action at x=${current.x}, y=${current.y}")
        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }
}

