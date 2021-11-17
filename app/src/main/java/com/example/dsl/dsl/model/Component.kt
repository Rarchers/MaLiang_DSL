package com.example.dsl.dsl.model

import android.graphics.Path
import com.example.dsl.dsl.bean.componentbean.CycleComponentBean
import com.example.dsl.dsl.bean.componentbean.FreeComponentBean
import com.example.dsl.dsl.bean.componentbean.PathBean
import com.example.dsl.dsl.bean.componentbean.TextComponentBean
import com.example.dsl.dsl.emun.ComponentType

class Component(private val pathMap : HashMap<String, PathBean>){

    fun path(id:String, path:Path,positionX:Float,positionY:Float){
        pathMap[id] = FreeComponentBean(type = ComponentType.PATH,path = path, ).also {
            it.positionX = positionX
            it.positionY = positionY
        }
    }

    fun text(id:String,str:String,positionX:Float = 0f,positionY:Float = 0f){
        pathMap[id] = TextComponentBean(type = ComponentType.TEXT,str,null).also {
            it.textPositionX = positionX
            it.textPositionY = positionY
        }

    }

    fun text(id:String,str:String,path : Path,positionX:Float = 0f,positionY:Float = 0f) {
        pathMap[id] = TextComponentBean(type = ComponentType.TEXT,str,path).also{
            it.textPositionX = positionX
            it.textPositionY = positionY
        }
    }


    fun cycle(id:String,r:Float,positionX:Float = 0f,positionY:Float = 0f){
        pathMap[id] = CycleComponentBean(type = ComponentType.CYCLE,r).also{
            it.positionX = positionX
            it.positionY = positionY
        }
    }

    operator fun invoke(block : Component.()->Unit){
        block()
    }




}