package com.example.viewvolume.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ComboSpinner(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paintMainCircle = Paint()
    private val paintBorderCircle = Paint()
    private val paintKnobCircle = Paint()

    private var startX: Float = 0f
    private var startY: Float = 0f
    private var diffX: Float = 0f
    private var diffY: Float = 0f


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                //event.x - startX
                //startY + event.y
                val distanceTraveled = startX 
                diffX = distanceTraveled + width/2f

            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        val centerX = width/2f
        val centerY = height/2f
        val mainRadius = width/2.2f
        val borderRadius = mainRadius * .9f

        canvas?.rotate(diffX, centerX, centerY)

        //Main black circle
        canvas?.drawCircle(centerX, centerY, mainRadius, paintMainCircle)

        //Green hollow circle
        paintBorderCircle.style = Paint.Style.STROKE
        paintBorderCircle.color = Color.GREEN
        canvas?.drawCircle(centerX, centerY, borderRadius, paintBorderCircle)

        //Volume circle
        paintKnobCircle.color = Color.BLUE
        canvas?.drawCircle(centerX - borderRadius * .5f, centerY + borderRadius *.6f, borderRadius *.15f, paintKnobCircle)


        super.onDraw(canvas)
    }
}