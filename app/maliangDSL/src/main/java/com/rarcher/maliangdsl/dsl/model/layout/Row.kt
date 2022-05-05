package com.example.dsl.dsl.model.layout

import android.graphics.Paint

class Row(
    val idDeque: ArrayDeque<Pair<String,String>>
) {




    fun addComponent(pathId : String,paintId:String){
        idDeque.add(Pair(pathId,paintId))
    }



    operator fun invoke(block : Row.()-> Unit){
        block()
    }
}