package com.example.dsl.dsl.bean.workbean

import android.graphics.Paint
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.emun.WorkType
import com.example.dsl.dsl.emun.位置

class ChineseBean(type: WorkType, component: ComponentType) : WorkBean(type, component) {
    var place : 位置 = 位置.左上角
    var pathStr : String = ""
    var paint : Paint? = null
    var topEdge : Float = 0f
    var leftEdg : Float = 0f
    var rightEdg : Float = 0f
    var bottomEdg : Float = 0f
    var core : WorkBean? = null
}