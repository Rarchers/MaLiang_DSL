package com.example.dsl.dsl.model.layout

import android.util.Log

class Column(val idDeque: ArrayDeque<Pair<String,String>>) {

    val TAG = "COLUMN class"
    fun addComponent(pathId : String,paintId:String){
        idDeque.add(Pair(pathId,paintId))
        Log.e(TAG, "addComponent: $pathId", )
    }



    operator fun invoke(block : Column.()->Unit){
        block()
    }
}