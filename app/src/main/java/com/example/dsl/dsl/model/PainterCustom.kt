package com.example.dsl.dsl.model

import android.graphics.Paint

class PainterCustom(private val painterMap :HashMap<String, Paint>) {

    fun addPaint(id:String,paint: Paint){
        painterMap[id] = paint
    }

    fun addPaint(id:String){
        val paint = Paint()
        painterMap[id] = paint
    }

    operator fun invoke(block : PainterCustom.()->Unit){
        block()
    }


    fun String.painter(block : ()->Paint){
        painterMap[this] = block()
    }


    operator fun String.invoke(block : ()->Paint){
        painterMap[this] = block()
    }




}
