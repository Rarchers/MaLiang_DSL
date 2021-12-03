package com.example.dsl.dsl.model

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import com.example.dsl.dsl.bean.componentbean.CycleComponentBean
import com.example.dsl.dsl.bean.componentbean.FreeComponentBean
import com.example.dsl.dsl.bean.componentbean.PathBean
import com.example.dsl.dsl.bean.componentbean.TextComponentBean
import com.example.dsl.dsl.bean.workbean.*
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.emun.WorkType
import com.example.dsl.dsl.emun.位置
import java.lang.Exception


class Drawer(

    val painterMap: HashMap<String, Paint>,
    val pathMap: HashMap<String, PathBean>,
    val workQueue: ArrayDeque<WorkBean>,val height:Int,val width:Int
) {


    val TAG = "Drawer"

    fun row(height: Int, block: Drawer.() -> Unit) {
        block()
    }

    fun column(block: Drawer.() -> Unit) {
        block()
    }

    fun frame(block: Drawer.() -> Unit) {
        block()
    }




    infix fun String.画在(放置位置 : 位置) : ChineseBean {
        var chineseBean : ChineseBean? = null
        if (!pathMap.containsKey(this)) {
            throw Exception("未找到相应路径id")
        }
        when (pathMap[this]!!.type) {
            ComponentType.TEXT ->{
                chineseBean = ChineseBean(WorkType.CHINESE, ComponentType.TEXT)
                chineseBean.pathStr = this
            }
            ComponentType.PATH ->{
                chineseBean = ChineseBean(WorkType.CHINESE,ComponentType.PATH)
                chineseBean.pathStr = this

            }
            ComponentType.CIRCLE ->{
                chineseBean = ChineseBean(WorkType.CHINESE,ComponentType.CIRCLE)
                chineseBean.pathStr = this
            }
        }




        when(放置位置){
            位置.左上角 ->{
                chineseBean.place = 位置.左上角
            }
            位置.右上角 ->{
                chineseBean.place = 位置.右上角
            }
            位置.左下角 ->{
                chineseBean.place = 位置.左下角
            }

            位置.右下角 ->{
                chineseBean.place = 位置.右下角
            }
            位置.正中 ->{
                chineseBean.place = 位置.正中
            }
            位置.水平居中 ->{
                chineseBean.place = 位置.水平居中
            }
            位置.垂直居中 ->{
                chineseBean.place = 位置.垂直居中
            }
        }
        return chineseBean
    }


  /*

  TODO 暂时禁用 等待做一个移动序列



   infix fun ChineseBean.左移(left : Float) : ChineseBean{
        this.leftEdg = this.leftEdg-left
        return this
    }

    infix fun ChineseBean.右移(right : Float) : ChineseBean{
        this.leftEdg = this.leftEdg+right
        return this
    }

    infix fun ChineseBean.上移(top : Float) : ChineseBean{
        this.topEdge = this.topEdge-top
        return this
    }
    infix fun ChineseBean.下移(bottom : Float) : ChineseBean{
        this.topEdge = this.topEdge-bottom
        return this
    }*/

    infix fun ChineseBean.上边距(top : Float) : ChineseBean{
        if (this.place == 位置.左下角 || this.place == 位置.右下角 || this.place == 位置.正中||this.place == 位置.垂直居中)
            return this
        else
            this.topEdge = top
        return this
    }

    infix fun ChineseBean.下边距(bottom : Float) : ChineseBean{
        if (this.place == 位置.左上角 || this.place == 位置.右上角 || this.place == 位置.正中||this.place == 位置.垂直居中)
            return this
        else
            this.bottomEdg = bottom
        return this
    }

    infix fun ChineseBean.左边距(left : Float) : ChineseBean{
        if (this.place == 位置.右下角 || this.place == 位置.右上角 || this.place == 位置.正中||this.place == 位置.水平居中)
            return this
        else
            this.leftEdg = left
        return this
    }

    infix fun ChineseBean.右边距(right : Float) : ChineseBean{
        if (this.place == 位置.左上角 || this.place == 位置.左下角 || this.place == 位置.正中||this.place == 位置.水平居中)
            return this
        else
            this.rightEdg = right
        return this
    }


    infix fun ChineseBean.使用画笔(paintId : String){
        if (!painterMap.containsKey(paintId)) {
            throw Exception("未找到相应画笔id")
        }
        this.paint = painterMap[paintId]
        when (pathMap[this.pathStr]!!.type) {
            ComponentType.TEXT ->{
                //文字绘制
                val bean = pathMap[this.pathStr]!! as TextComponentBean
                bean.let {
                    when(this.place){

                        位置.水平居中 ->{
                            it.textPositionX = width*1f/2
                            if (this.bottomEdg == 0f){
                                if (this.topEdge == 0f){
                                    it.textPositionY = 0f
                                }else{
                                    it.textPositionY = 0f+this.topEdge
                                }
                            }else{
                                if (this.topEdge == 0f){
                                    it.textPositionY = height - this.bottomEdg
                                }else{
                                    it.textPositionY = height - this.bottomEdg
                                }
                            }
                        }

                        位置.垂直居中 ->{
                            it.textPositionX =  if (this.rightEdg != 0f) (width - this.rightEdg) else this.leftEdg



                            if (this.leftEdg == 0f){
                                if (this.rightEdg == 0f){
                                    it.textPositionX = 0f
                                }else{
                                    it.textPositionX = width - this.rightEdg
                                }
                            }else{
                                if (this.rightEdg == 0f){
                                    it.textPositionX = this.leftEdg
                                }else{
                                    it.textPositionX = this.leftEdg
                                }
                            }







                            it.textPositionY = height*1f/2
                        }

                        位置.左上角 ->{
                            it.textPositionX = 0f + this.leftEdg
                            it.textPositionY = 0f + this.topEdge
                        }
                        位置.右上角->{
                            it.textPositionX = width - this.rightEdg
                            it.textPositionY = 0f + this.topEdge
                        }
                        位置.左下角->{
                            it.textPositionX = 0 + this.leftEdg
                            it.textPositionY = height - this.bottomEdg
                        }

                        位置.右下角 ->{
                            it.textPositionX = width - this.rightEdg
                            it.textPositionY = height - this.bottomEdg
                        }

                        位置.正中 ->{
                            it.textPositionX = width*1f/2
                            it.textPositionY = height*1f/2
                        }

                    }
                    this.core =  textPacket(WorkType.FREE, it, paintId)
                }
            }
            ComponentType.PATH ->{
                //自由绘图
                val bean = (pathMap[this.pathStr]!! as FreeComponentBean)
                bean.let {

                    when(this.place){
                        位置.水平居中 ->{
                            it.positionX = width*1f/2
                            if (this.bottomEdg == 0f){
                                if (this.topEdge == 0f){
                                    it.positionY = 0f
                                }else{
                                    it.positionY = 0f+this.topEdge
                                }
                            }else{
                                if (this.topEdge == 0f){
                                    it.positionY = height - this.bottomEdg
                                }else{
                                    it.positionY = height - this.bottomEdg
                                }
                            }
                        }

                        位置.垂直居中 ->{


                            if (this.leftEdg == 0f){
                                if (this.rightEdg == 0f){
                                    it.positionX = 0f
                                }else{
                                    it.positionX = width - this.rightEdg
                                }
                            }else{
                                if (this.rightEdg == 0f){
                                    it.positionX = this.leftEdg
                                }else{
                                    it.positionX = this.leftEdg
                                }
                            }


                            it.positionY = height*1f/2
                        }
                        位置.左上角 ->{
                            it.positionX = 0f + this.leftEdg
                            it.positionY = 0f + this.topEdge
                        }
                        位置.右上角->{
                            it.positionX = width - this.rightEdg
                            it.positionY = 0f + this.topEdge
                        }
                        位置.左下角->{
                            it.positionX = 0 + this.leftEdg
                            it.positionY = height - this.bottomEdg
                        }

                        位置.右下角 ->{
                            it.positionX = width - this.rightEdg
                            it.positionY = height - this.bottomEdg
                        }

                        位置.正中 ->{
                            it.positionX = width*1f/2
                            it.positionY = height*1f/2
                        }

                    }

                    this.core = pathPacket(WorkType.FREE, it, paintId)
                }

            }
            ComponentType.CIRCLE ->{
                val bean = (pathMap[this.pathStr]!! as CycleComponentBean)
                bean.let {
                    when(this.place){
                        位置.水平居中 ->{
                            it.positionX = width*1f/2
                            if (this.bottomEdg == 0f){
                                if (this.topEdge == 0f){
                                    it.positionY = 0f
                                }else{
                                    it.positionY = 0f+this.topEdge
                                }
                            }else{
                                if (this.topEdge == 0f){
                                    it.positionY = height - this.bottomEdg
                                }else{
                                    it.positionY = height - this.bottomEdg
                                }
                            }
                        }

                        位置.垂直居中 ->{
                            if (this.leftEdg == 0f){
                                if (this.rightEdg == 0f){
                                    it.positionX = 0f
                                }else{
                                    it.positionX = width - this.rightEdg
                                }
                            }else{
                                if (this.rightEdg == 0f){
                                    it.positionX = this.leftEdg
                                }else{
                                    it.positionX = this.leftEdg
                                }
                            }
                            it.positionY = height*1f/2
                        }
                        位置.左上角 ->{
                            it.positionX = 0f + this.leftEdg
                            it.positionY = 0f + this.topEdge
                        }
                        位置.右上角->{
                            it.positionX = width - this.rightEdg
                            it.positionY = 0f + this.topEdge
                        }
                        位置.左下角->{
                            it.positionX = 0 + this.leftEdg
                            it.positionY = height - this.bottomEdg
                        }

                        位置.右下角 ->{
                            it.positionX = width - this.rightEdg
                            it.positionY = height - this.bottomEdg
                        }

                        位置.正中 ->{
                            it.positionX = width*1f/2
                            it.positionY = height*1f/2
                        }

                    }
                    this.core = cyclePacket(WorkType.FREE,it,paintId)
                }
            }
        }


        workQueue.add(this)
    }






    fun drawComponent(idPaint: String, idComponent: String) {
        if (!painterMap.containsKey(idPaint)) {
            throw Exception("未找到相应画笔id")
        } else if (!pathMap.containsKey(idComponent)) {
            throw Exception("未找到相应路径id")
        } else {
            when (pathMap[idComponent]!!.type) {
                ComponentType.PATH -> {
                    //自由绘图
                    val bean = (pathMap[idComponent]!! as FreeComponentBean)
                    bean.let {
                        workQueue.add(
                            pathPacket(WorkType.FREE, it, idPaint)
                        )
                    }
                }

                ComponentType.TEXT -> {
                    //文字绘制
                    val bean = pathMap[idComponent]!! as TextComponentBean
                    bean.let {
                        workQueue.add(
                            textPacket(WorkType.FREE, it, idPaint)
                        )
                    }
                }


            }
        }
    }


    private fun pathPacket(type: WorkType, bean: FreeComponentBean, idPaint: String): FreePathBean {
        return FreePathBean(
            type,
            ComponentType.PATH,
            bean.path,
            painterMap[idPaint]!!,
            bean.positionX,
            bean.positionY
        )
    }

    private fun cyclePacket(type: WorkType, it: CycleComponentBean, idPaint: String): CycleBean{
        return CycleBean(
            type,
            ComponentType.CIRCLE,
            it.r,
            painterMap[idPaint]!!,
            it.positionX,
            it.positionY
        )
    }


    private fun textPacket(type: WorkType, it: TextComponentBean, idPaint: String): TextBean {
        return TextBean(
            type,
            ComponentType.TEXT,
            it.text,
            painterMap[idPaint]!!,
            it.textPath,
            it.textPositionY,
            it.textPositionX
        )
    }


    operator fun invoke(block: Drawer.() -> Unit) {
        block()
    }

}