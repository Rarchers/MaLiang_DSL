package com.example.dsl.dsl.bean.workbean

import android.graphics.Paint
import android.graphics.Path
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.emun.WorkType

class TextBean(
    override val type: WorkType, override val component: ComponentType,//自由绘制
    val text: String,
    val paint: Paint,
    val textPath: Path? = null,
    val textPositionY: Float = 0f,
    val textPositionX: Float = 0f,
    override val BeanHeight: Float? = null,
    override val BeanWidth: Float? = null
) : WorkBean(type, component) {
    override fun toString(): String {
        return "text $text path $textPath paint $paint x $textPositionX y $textPositionY"
    }
}