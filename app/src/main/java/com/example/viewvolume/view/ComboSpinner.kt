package com.example.viewvolume.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.storage.StorageVolume
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class ComboSpinner(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paintMainCircle = Paint()
    private val paintBorderCircle = Paint()
    private val paintKnobCircle = Paint()

    private var startX: Float = 0f
    private var distanceTraveled: Float = 0f
    private var lastLocation: Float = 0f


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                println(distanceTraveled)
            }
            MotionEvent.ACTION_MOVE -> {
                distanceTraveled = event.x - startX + lastLocation
            }

            MotionEvent.ACTION_UP -> {
                limitRange()
                Toast.makeText(context, "${(distanceTraveled/280f) * 100}%", Toast.LENGTH_LONG).show()
                lastLocation = distanceTraveled
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

        limitRange()
        println(distanceTraveled)
        canvas?.rotate(distanceTraveled, centerX, centerY)

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

    private fun limitRange(){
        when{
            distanceTraveled <= 0 -> distanceTraveled = 0f
            distanceTraveled >= 280f -> distanceTraveled = 280f
        }
    }

    fun getVolume(): Float {
        return lastLocation
    }
    fun setVolume(volume: Float){
        lastLocation = volume
    }

}