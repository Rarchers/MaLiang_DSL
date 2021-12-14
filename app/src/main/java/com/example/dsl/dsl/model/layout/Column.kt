package com.example.dsl.dsl.model.layout

class Column {




    operator fun invoke(block : Column.()->Unit){
        block()
    }
}