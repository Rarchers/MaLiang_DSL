package com.example.dsl.dsl.bean.componentbean

import android.graphics.Path
import com.example.dsl.dsl.emun.ComponentType

class TextComponentBean(
    override val type: ComponentType,  //文字绘制
    val text: String,
    val textPath: Path? = null,
    ) : PathBean(type) {
    var textPositionX: Float = 0f
    var textPositionY: Float = 0f
}