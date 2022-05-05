package com.example.dsl.dsl.bean.workbean

import com.example.dsl.dsl.emun.PositionTag

class PositionQueue(val TAG : PositionTag) {
    var num:Float = 0f
    var positive = 0 // 0 - ;  1 +
    var step : PositionTag = PositionTag.NUM
    var scale : Double = 1.0
}