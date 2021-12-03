package com.example.dsl.dsl.model

import com.example.dsl.dsl.bean.componentbean.CycleComponentBean
import com.example.dsl.dsl.bean.componentbean.PathBean
import com.example.dsl.dsl.bean.componentbean.TextComponentBean
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.utils.initTag
import dsl.DSLPreview

class PreviewLayer {

    private val pathMap = HashMap<String, PathBean>()
    private val component = Component(pathMap)
    private val workQueue : ArrayDeque<PathBean> = ArrayDeque()
    private val preview = PreviewNormal(pathMap,workQueue)



   fun component(init : Component.()->Unit){
       initTag(component,init)

    }

    fun preview(init: PreviewNormal.() -> Unit){
        initTag(preview,init)
        startPreview()
    }


    private fun startPreview(){
        while (workQueue.isNotEmpty()){
            val bean = workQueue.removeFirst()

            when(bean.type){
                ComponentType.TEXT ->{
                    val work = bean as TextComponentBean
                    DSLPreview().drawText(work.text)
                }
                ComponentType.PATH ->{

                }
                ComponentType.CIRCLE ->{
                    val work = bean as CycleComponentBean
                    DSLPreview().drawCircle(work.r)
                }
            }



        }
    }


}
