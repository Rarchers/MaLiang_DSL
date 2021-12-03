package com.example.dsl.dsl

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.dsl.dsl.emun.位置
import com.example.dsl.dsl.utils.layer


class DSLView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val backgroundHeight = 500
    private val backgroundWidth = 3 * backgroundHeight
    private val TAG = "DSLView"
    val paint = Paint().apply {
        this.color = Color.RED
        this.textSize = 50f
    }
    val paint2 = Paint()
    val invokePaint = Paint()
    val warringPath = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {


            layer(canvas = canvas,height,width) {

                painters {
                    "invoke"{
                        invokePaint.color = Color.RED
                        invokePaint.textSize = 50f
                        invokePaint.textAlign = Paint.Align.CENTER;
                        invokePaint
                    }
                    "painters".painter {
                        paint2.color = Color.BLACK
                        paint2
                    }
                    addPaint("rarcher",paint)
                }

                component {
                    initPath()
                    text("hello","Hello World")



                    text("designed","by Rarcher")

                    circle("circle",50f)

                }
                drawer {

                    "hello" 画在 位置.正中 使用画笔 "invoke"

                    "designed" 画在 位置.右下角 右边距 300f 下边距 20f 使用画笔 "rarcher"

                    "circle" 画在 位置.垂直居中 左边距 200f 使用画笔  "painters"

                    /*
                    *
                    * "hello" 画在 "designed" 右边 左边距 50f 上边距 200f  使用画笔 "rarcher"    //relative
                    *
                    * */

                }

            }




        }
        invalidate()

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    fun initPath(){
        warringPath.moveTo(
          0f,0f
        )
        warringPath.lineTo(
            (backgroundHeight / 2 - backgroundHeight / 18).toFloat(),
            (backgroundHeight * 7 / 12 - backgroundHeight / 18).toFloat()
        )
        warringPath.lineTo(
            (backgroundHeight / 2 + backgroundHeight / 18).toFloat(),
            (backgroundHeight * 7 / 12 - backgroundHeight / 18).toFloat()
        )
        warringPath.lineTo(
            (backgroundHeight / 2 + backgroundHeight / 12).toFloat(),
            (backgroundHeight / 3 - backgroundHeight / 12).toFloat()
        )
        warringPath.lineTo(
            (backgroundHeight / 2 - backgroundHeight / 12).toFloat(),
            (backgroundHeight / 3 - backgroundHeight / 12).toFloat()
        )
    }
}