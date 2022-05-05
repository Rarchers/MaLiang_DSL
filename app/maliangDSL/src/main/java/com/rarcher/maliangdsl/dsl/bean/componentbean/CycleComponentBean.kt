package com.example.dsl.dsl.bean.componentbean

import android.graphics.Path
import com.example.dsl.dsl.emun.ComponentType

class CycleComponentBean (
    override val type: ComponentType,

    val r : Float

    ) : PathBean(type){
    var positionX: Float = 0f
    var positionY: Float = 0f
}