package com.example.dsl.dsl.bean.workbean

import android.graphics.Paint
import android.graphics.Path
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.emun.WorkType

class CycleBean (
    override val type: WorkType, override val component: ComponentType,
    val r : Float = 0f,
    val paint: Paint,
    val positionX: Float = 0f,
    val positionY: Float = 0f,
    val height: Float? = null,
    val width: Float? = null
) : WorkBean(type, component) {
}