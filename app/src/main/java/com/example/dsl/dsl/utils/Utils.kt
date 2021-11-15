package com.example.dsl.dsl.utils

import android.graphics.Canvas
import com.example.dsl.dsl.model.Layer


fun <T>initTag(tag:T,init:T.()->Unit):T{
    tag.init()
    return tag
}

fun layer(canvas: Canvas,height : Int, width : Int, init : Layer.()-> Unit){
    val layer = Layer(canvas,height, width)
    layer.init()
}
