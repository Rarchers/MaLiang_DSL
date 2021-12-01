package com.example.dsl.dsl.model

import com.example.dsl.dsl.bean.componentbean.PathBean
import com.example.dsl.dsl.bean.componentbean.TextComponentBean
import com.example.dsl.dsl.bean.workbean.TextBean
import com.example.dsl.dsl.bean.workbean.WorkBean
import com.example.dsl.dsl.emun.ComponentType
import com.example.dsl.dsl.emun.WorkType
import java.lang.Exception

class PreviewNormal(
    private val pathMap: HashMap<String, PathBean>,
    private val workQueue: ArrayDeque<PathBean>
) {


    infix fun String.预览状态为(flag: Boolean) {
        if (!flag) {
            return
        }
        if (!pathMap.containsKey(this)) {
            throw Exception("未找到相应路径id")
        } else {
            val bean = pathMap[this]
            if (bean != null) {
                workQueue.add(bean)
            }
        }
    }


}


