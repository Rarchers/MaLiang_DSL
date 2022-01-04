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






class DSLView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val backgroundHeight = 500
    private val backgroundWidth = 3 * backgroundHeight
    private val TAG = "DSLView"
    val paint = Paint().apply {
        this.color = Color.RED
        this.textSize = 30f
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
                        invokePaint.textSize = 30f
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
                    text("relativeCenter","相对布局中心")
                    text("columnLayout","纵向布局测试")
                    circle("center_circle",10f,positionX = width/2.0f,positionY = height/2.0f)
                    circle("positions",1f,0f,height/2*1f)
                }
                drawer {

                    //相对布局 测试组
                    "relativeCenter" 画在 位置.水平居中 下边距 200f 使用画笔 "invoke"
                    "relative" 画在 "relativeCenter" 上方 5f 右移 50f 使用画笔 "invoke"
                    "relative" 画在 "relativeCenter" 下方 5f 左移 50f 使用画笔 "invoke"
                    "relative" 画在 "relativeCenter" 右方 5f 下移 50f 使用画笔 "invoke"
                    "relative" 画在 "relativeCenter" 左方 5f 上移 50f 使用画笔 "invoke"


                    //横向布局 测试组
                    row(0f,height/2 *1f) {
                        addComponent("designed","rarcher")
                        addComponent("rowLayout","painters")
                        addComponent("picture","rarcher")
                        addComponent("center_circle","rarcher")
                    }

                    //纵向布局 测试组
                    column(width/2 *1f,0f) {
                        addComponent("designed","rarcher")
                        addComponent("columnLayout","painters")
                        addComponent("picture","rarcher")
                        addComponent("center_circle","rarcher")
                    }




                   //  "center_circle" 画在 位置.正中  使用画笔  "painters" // 中心定位点
                }
            }
        }
        invalidate()
    }








}