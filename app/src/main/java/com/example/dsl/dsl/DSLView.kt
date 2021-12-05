package com.example.dsl.dsl

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.dsl.R
import com.example.dsl.dsl.emun.位置
import com.example.dsl.dsl.utils.layer



/**
*
* Relative Layout：
 * 1. 新增更多绘制描述
 * 2. 更多的放置测试，目前已经完成 圆的相对布局测试
*
*
* */




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




    @SuppressLint("DrawAllocation")
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

                    picture("picture", BitmapFactory.decodeResource(this@DSLView.resources,R.drawable.icon),100f,100f)

                    text("designed","by Rarcher")

                    circle("circle",10f)

                }
                drawer {

                  //  "hello" 画在 位置.正中 使用画笔 "invoke"

                    "designed" 画在 位置.右下角 右边距 300f 下边距 20f 使用画笔 "rarcher"

                    "circle" 画在 位置.正中  使用画笔  "painters"

                    "picture" 画在 位置.水平居中 上边距 500f 使用画笔 "invoke"



                //     "circle" 画在 "designed" 左边 300f 使用画笔 "rarcher"    //relative 实验性 当前API已完成


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