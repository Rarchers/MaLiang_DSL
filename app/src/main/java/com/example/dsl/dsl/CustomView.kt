package com.example.dsl.dsl

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.dsl.R

class CustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    val addTicket = Paint()
    val path = Path()
    val background = Paint()
    val ball = Paint()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val title = BitmapFactory.decodeResource(this.resources, R.drawable.title)
        val item = BitmapFactory.decodeResource(this.resources, R.drawable.item)
        canvas?.drawBitmap(title,0f,0f,background)
        canvas?.drawBitmap(item,0f,450f,background)
        canvas?.drawBitmap(item,0f,650f,background)
        canvas?.drawBitmap(item,0f,850f,background)
        canvas?.drawBitmap(item,0f,1050f,background)

        val background = Path().also {
            it.moveTo(width/2-200f,height-80f)
            it.lineTo(width/2+200f,height-80f)
            it.lineTo(width/2+200f,height-20f)
            it.lineTo(width/2-200f,height-20f)
            it.close()
        }
        val paint = Paint()
        paint.color = Color.YELLOW
        canvas?.drawPath(background,paint)

        canvas?.drawCircle(width/2-200f,height-20f-30f,30f,paint)
        canvas?.drawCircle(width/2+200f,height-20f-30f,30f,paint)

        val textPaint = Paint()
        textPaint.color = Color.BLACK
        textPaint.textSize = 23f
        textPaint.textAlign = Paint.Align.CENTER
        canvas?.drawText("添加抢票",width/2*1f,height-20f-30f,textPaint)


    }
}