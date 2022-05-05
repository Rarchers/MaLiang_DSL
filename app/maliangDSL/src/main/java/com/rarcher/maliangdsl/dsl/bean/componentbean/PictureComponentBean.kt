package com.example.dsl.dsl.bean.componentbean

import android.graphics.Bitmap
import com.example.dsl.dsl.emun.ComponentType

class PictureComponentBean(
    override val type: ComponentType,
    val bitmap:Bitmap,
    val height : Float,
    val width : Float


) : PathBean(type){
    var positionX: Float = 0f
    var positionY: Float = 0f
}