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
    }
    val paint2 = Paint()
    val invokePaint = Paint()
    val addTicket = Paint()
    val path = Path()
    val background = Paint()
    val ball = Paint()
    val backgroundPath = Path()




    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        initPath()
        if (canvas != null) {

            layer(canvas = canvas,height,width){
                painters {
                    "addTicket"{
                        addTicket.color = Color.BLACK
                        addTicket.textSize = 25f
                        addTicket.textAlign = Paint.Align.CENTER
                        addTicket
                    }
                    "background"{
                        background.color = Color.WHITE
                        background
                    }
                    "ball"{
                        ball.color = Color.YELLOW
                        ball
                    }
                    "posPaint"{
                        paint2.color = Color.RED
                        paint2
                    }
                }
                component {
                    picture("title", BitmapFactory.decodeResource(this@DSLView.resources,R.drawable.title),width*1f,500f)
                    picture("item", BitmapFactory.decodeResource(this@DSLView.resources,R.drawable.item),width*1f,200f)
                    path("yellowBackground",path,width*1f/2-200f,height*1f-80f)
                    circle("balls",30f)
                    circle("pos",10f)
                    text("addgrab","添加抢票")
                }
                drawer {
                    "title" 画在 位置.水平居中 使用画笔 "background"
                    column(0f,500f){
                        addComponent("item","background")
                        addComponent("item","background")
                        addComponent("item","background")
                        addComponent("item","background")
                    }
                    drawComponent("ball","yellowBackground")
                    "pos" 画在 位置.水平居中 下边距 40f 使用画笔 "ball"
                    "balls" 画在 "pos" 左方 100f+30f 使用画笔 "ball"
                    "balls" 画在 "pos" 右方 200f 使用画笔 "ball"
                    "addgrab" 画在 位置.水平居中 下边距 38f 使用画笔 "addTicket"
                }
            }





        }
        invalidate()
    }




    fun initPath(){
        path.moveTo(0f,0f)
        path.lineTo(400f,0f)
        path.lineTo(400f,60f)
        path.lineTo(0f,60f)
        path.close()

    }




}