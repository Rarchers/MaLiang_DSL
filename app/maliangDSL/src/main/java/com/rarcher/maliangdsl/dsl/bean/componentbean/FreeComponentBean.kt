package com.example.dsl.dsl.bean.componentbean

import android.graphics.Path
import com.example.dsl.dsl.emun.ComponentType

data class FreeComponentBean(
    override val type: ComponentType,
    //自由绘制
    val path: Path,

) : PathBean(type){
    var positionX: Float = 0f
    var positionY: Float = 0f
}