package com.example.dsl.dsl.model

import android.graphics.Paint
import android.util.Log

class PainterCustom(private val painterMap :HashMap<String, Paint>) {

    val TAG = "PainterCustom"
    fun addPaint(id:String,paint: Paint){
        painterMap[id] = paint.also { it.textAlign = Paint.Align.CENTER}
    }

    fun addPaint(id:String){
        val paint = Paint()
        paint.textAlign = Paint.Align.CENTER
        painterMap[id] = paint
    }

    operator fun invoke(block : PainterCustom.()->Unit){
        block()
    }


    fun String.painter(block : ()->Paint){
        painterMap[this] = block().also { it.textAlign = Paint.Align.CENTER }

    }


    operator fun String.invoke(block : ()->Paint){
        painterMap[this] = block().also { it.textAlign = Paint.Align.CENTER }
      //  Log.e(TAG, "invoke: ${painterMap[this]!!.textAlign}", )
    }




}
