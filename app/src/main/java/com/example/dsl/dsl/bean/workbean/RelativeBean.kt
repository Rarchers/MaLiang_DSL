package com.example.dsl.dsl.bean.workbean

import android.graphics.Paint
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.emun.WorkType
import com.example.dsl.dsl.emun.位置

class RelativeBean(type: WorkType, component: ComponentType) : WorkBean(type, component) {
    var relativeId : String = ""
    var place : 位置 = 位置.默认
    var pathStr : String = ""
    var paint : Paint? = null
    var topEdge : Float = 0f
    var leftEdg : Float = 0f
    var rightEdg : Float = 0f
    var bottomEdg : Float = 0f
    var positionX : Float = 0f
    var positionY : Float = 0f
    var core : WorkBean? = null
    override var BeanHeight: Float? = null
    override var BeanWidth: Float? = null
}