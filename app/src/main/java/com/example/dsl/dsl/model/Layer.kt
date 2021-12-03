package com.example.dsl.dsl.model

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.widget.Toast
import com.example.dsl.dsl.bean.componentbean.PathBean
import com.example.dsl.dsl.bean.workbean.*
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.emun.WorkType

import com.example.dsl.dsl.utils.initTag
import java.lang.Exception
import kotlin.collections.ArrayDeque
import kotlin.collections.HashMap

class Layer(val canvas: Canvas,val height:Int,val width:Int){

    private val painterMap = HashMap<String,Paint>()
    private val pathMap = HashMap<String, PathBean>()
    private val workQueue : ArrayDeque<WorkBean> = ArrayDeque()
    private val painter = PainterCustom(painterMap)
    private val component = Component(pathMap)
    private val drawer = Drawer(painterMap,pathMap,workQueue,height,width)
    private var curHeight = 0
    private var curWidth = 0
    private val TAG = "Layer"




    fun painters(init : PainterCustom.()->Unit) {
        initTag(painter,init)
    }

    fun component(init : Component.()->Unit){

        initTag(component,init)
    }


    fun drawer(init : Drawer.()->Unit) {
        initTag(drawer,init)
        drawAll()
    }


    private fun drawAll(){
        while (workQueue.isNotEmpty()){
            val work = workQueue.removeFirst()
            when(work.type){
                WorkType.FREE ->{


                    when(work.component){
                        ComponentType.PATH ->{
                            val bean = work as FreePathBean
                            if (bean.path != null){
                                canvas.save()
                                canvas.translate(bean.positionX,bean.positionY)
                                canvas.drawPath(bean.path,bean.paint)
                                canvas.restore()
                            }
                        }

                        ComponentType.TEXT ->{
                            val bean = work as TextBean
                            if (bean.textPath == null){
                                canvas.save()
                                canvas.translate(bean.textPositionX,bean.textPositionY)
                                canvas.drawText(bean.text,0f,0f,bean.paint)
                                canvas.restore()
                            }else{
                                canvas.save()
                                canvas.translate(bean.textPositionX,bean.textPositionY)
                                canvas.drawTextOnPath(bean.text,bean.textPath,0f,0f,bean.paint)
                                canvas.restore()
                            }
                        }

                        ComponentType.CIRCLE ->{
                            val bean = work as CycleBean
                            if (bean.r != 0f){
                                canvas.save()
                                canvas.translate(bean.positionX,bean.positionY)
                                canvas.drawCircle(0f,0f,bean.r,bean.paint)
                                canvas.restore()
                            }
                        }


                    }
                }

                WorkType.CHINESE->{

                    val core = (work as ChineseBean).core

                    when(core?.component){
                        ComponentType.PATH ->{
                            val bean = core as FreePathBean
                            if (bean.path != null){
                                canvas.save()
                                canvas.translate(bean.positionX,bean.positionY)
                                canvas.drawPath(bean.path,bean.paint)
                                canvas.restore()
                            }
                        }

                        ComponentType.TEXT ->{
                            val bean = core as TextBean
                            if (bean.textPath == null){
                                canvas.save()
                                canvas.translate(bean.textPositionX,bean.textPositionY)
                                canvas.drawText(bean.text,0f,0f,bean.paint)
                                Log.e(TAG, "drawAll: ${bean.toString()}")
                                canvas.restore()
                            }else{
                                canvas.save()
                                canvas.translate(bean.textPositionX,bean.textPositionY)
                                Log.e(TAG, "drawAll: ${bean.toString()}")
                                canvas.drawTextOnPath(bean.text,bean.textPath,0f,0f,bean.paint)
                                canvas.restore()
                            }
                        }

                        ComponentType.CIRCLE ->{
                            val bean = core as CycleBean
                            if (bean.r != 0f){
                                canvas.save()
                                canvas.translate(bean.positionX,bean.positionY)
                                canvas.drawCircle(0f,0f,bean.r,bean.paint)
                                canvas.restore()
                            }
                        }
                    }




                }













                WorkType.COLUMN ->{

                }
                WorkType.ROW ->{

                }
                WorkType.FRAME ->{

                }
            }
        }
    }

}





