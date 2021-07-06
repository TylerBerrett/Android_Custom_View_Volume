package com.example.viewvolume.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.viewvolume.R

class ComboSpinner(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

   var mainCircleColor: Int? = Color.BLACK
   var borderCircleColor: Int? = Color.BLACK
   var knobCircleColor: Int? = Color.BLACK

    init {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.ComboSpinner)
        val mainCircleColor2 = typedArray?.getColor(R.styleable.ComboSpinner_colorCircle, Color.BLACK)
        val borderCircleColor2 = typedArray?.getColor(R.styleable.ComboSpinner_colorBorder, Color.GREEN)
        val knobCircleColor2 = typedArray?.getColor(R.styleable.ComboSpinner_colorKnob, Color.BLUE)
        mainCircleColor = mainCircleColor2
        borderCircleColor = borderCircleColor2
        knobCircleColor = knobCircleColor2
        typedArray?.recycle()
    }

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
        canvas?.rotate(distanceTraveled, centerX, centerY)

        //Main black circle
        paintMainCircle.color = mainCircleColor!!
        canvas?.drawCircle(centerX, centerY, mainRadius, paintMainCircle)

        //Green hollow circle
        paintBorderCircle.style = Paint.Style.STROKE
        paintBorderCircle.color = borderCircleColor!!
        canvas?.drawCircle(centerX, centerY, borderRadius, paintBorderCircle)

        //Volume circle
        paintKnobCircle.color = knobCircleColor!!
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