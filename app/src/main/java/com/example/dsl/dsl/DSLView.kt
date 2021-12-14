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


//TODO : 因为预设定Text的画笔，导致错误计算了positionX和positionY 需要进行修改
//TODO ：修正方法： 在 左边 后边等前置计算position的时候，使用标记来记录positionX和positionY的偏移，后期进行二次测量后再代入值


class DSLView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val backgroundHeight = 500
    private val backgroundWidth = 3 * backgroundHeight
    private val TAG = "DSLView"
    val paint = Paint().apply {
        this.color = Color.RED
        this.textSize = 50f
       // this.textAlign = Paint.Align.CENTER
        //this.textAlign = Paint.Align.CENTER;
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
                       // invokePaint.textAlign = Paint.Align.CENTER
                        invokePaint
                    }
                    "painters".painter {
                        paint2.color = Color.BLACK
                        paint2.textAlign = Paint.Align.CENTER
                        paint2
                    }
                    addPaint("rarcher",paint)
                }

                component {

                    text("relative","相对布局测试")
                    picture("picture", BitmapFactory.decodeResource(this@DSLView.resources,R.drawable.icon),100f,100f)
                    text("designed","文字基准测试")
                    text("rowLayout","横向布局测试")
                    circle("center_circle",10f)
                    circle("positions",1f,0f,height/2*1f)
                }
                drawer {

                  //  "relative" 画在 位置.正中 使用画笔 "invoke"
//                    "relative" 画在 位置.左上角 使用画笔 "invoke"
//                    "relative" 画在 "relative" 右方 0f 使用画笔 "invoke"
//                    "rowLayout" 画在 "relative" 右方 0f 使用画笔 "invoke"


                  //  "!@#$%index%$#@!".画在(位置.默认).使用画笔("default")
//                    "positions" 画在 位置.垂直居中 使用画笔 "default"
//                    "designed" 画在 "positions" 右方 0f 使用画笔 "rarcher"
//                    "rowLayout" 画在 "designed" 右方 0f 使用画笔 "painters"
//                    "picture" 画在 "rowLayout" 右方 0f 使用画笔 "rarcher"



                    row(positionY = height/2 *1f) {
                        addComponent("designed","rarcher")
                        addComponent("rowLayout","painters")
                        addComponent("picture","rarcher")
                        addComponent("center_circle","rarcher")
                    }



                   //  "center_circle" 画在 位置.正中  使用画笔  "painters" // 中心定位点
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