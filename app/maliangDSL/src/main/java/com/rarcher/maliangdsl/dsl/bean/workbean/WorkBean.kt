package com.example.dsl.dsl.bean.workbean

import android.graphics.Paint
import android.graphics.Path
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.emun.WorkType


open class WorkBean(
    open val type : WorkType,
    open val component : ComponentType,
    open val BeanHeight: Float? = null,
    open val BeanWidth: Float? = null
)