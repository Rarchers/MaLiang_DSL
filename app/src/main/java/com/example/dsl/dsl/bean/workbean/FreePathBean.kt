package com.example.dsl.dsl.bean.workbean

import android.graphics.Paint
import android.graphics.Path
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.emun.WorkType

data class FreePathBean(
    override val type: WorkType, override val component: ComponentType,
    val path: Path? = null,
    val paint: Paint,
    val positionX: Float = 0f,
    val positionY: Float = 0f,
    override val BeanHeight: Float? = null,
    override val BeanWidth: Float? = null
) : WorkBean(type, component) {
}