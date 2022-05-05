package com.example.dsl.dsl.bean.workbean

import android.graphics.Bitmap
import android.graphics.Paint
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.emun.WorkType

class PictureBean(
    override val type: WorkType, override val component: ComponentType,//自由绘制
    override val BeanHeight: Float? = null,
    override val BeanWidth: Float? = null,
    val paint: Paint,
    val bitmap: Bitmap,
    val positionX: Float = 0f,
    val positionY: Float = 0f,
    )  : WorkBean(type, component) {
}