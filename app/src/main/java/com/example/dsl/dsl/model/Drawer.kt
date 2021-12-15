package com.example.dsl.dsl.model

import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import com.example.dsl.dsl.bean.componentbean.*
import com.example.dsl.dsl.bean.workbean.*
import com.example.dsl.dsl.emun.*
import com.example.dsl.dsl.model.layout.Column
import com.example.dsl.dsl.model.layout.Row
import com.example.dsl.dsl.utils.initTag
import java.lang.Exception
import kotlin.math.abs


class Drawer(

    val painterMap: HashMap<String, Paint>,
    val pathMap: HashMap<String, PathBean>,
    val workQueue: ArrayDeque<WorkBean>, val height: Int, val width: Int
) {

    val TAG = "Drawer"

    private val coreMap: HashMap<String, WorkBean> = HashMap()
    private val rowQueue = ArrayDeque<Pair<String,String>>()
    private val columnQueue = ArrayDeque<Pair<String,String>>()
    private val rowLayout = Row(rowQueue)
    private val columnLayout = Column(columnQueue)


    // LinearLayout


    //Row
    fun row(positionX: Float,positionY : Float,block: Row.() -> Unit){
        initTag(rowLayout,block)
        dealRowQueue(positionX,positionY)
    }

    private fun dealRowQueue(positionX: Float,positionY: Float){
        //1.创建一个1像素的定位点
        pathMap["!@#$%index%$#@!"] = CycleComponentBean(type = ComponentType.CIRCLE,1f).also{
            it.positionX = positionX
            it.positionY = positionY
        }

        "!@#$%index%$#@!".画在(位置.默认).使用画笔("default")
        //2.开始绘制
        var curStr = "!@#$%index%$#@!"
        while (rowQueue.isNotEmpty()){
            val str = rowQueue.removeFirst()
            str.first 画在 curStr 右方 0f 使用画笔 str.second
            curStr = str.first
        }
    }

    //column
    fun column(positionX : Float,positionY: Float,block : Column.() -> Unit){
        initTag(columnLayout,block)
        Log.e(TAG, "column: size ${columnQueue.size}", )
        dealColumnQueue(positionX,positionY)
    }


    private fun dealColumnQueue(positionX: Float,positionY: Float){
        //1.创建一个1像素的定位点
        pathMap["!@#$%index%$#@!"] = CycleComponentBean(type = ComponentType.CIRCLE,1f).also{
            it.positionX = positionX
            it.positionY = positionY
        }

        "!@#$%index%$#@!".画在(位置.默认).使用画笔("default")
        Log.e(TAG, "dealColumnQueue: column ${columnQueue.size}", )
        //2.开始绘制
        var curStr = "!@#$%index%$#@!"
        while (columnQueue.isNotEmpty()){
            val str = columnQueue.removeFirst()
            str.first 画在 curStr 下方 0f 使用画笔 str.second
            curStr = str.first
        }

    }


    /**
     *
     * relative Layout
     */
    infix fun String.画在(pathId: String): RelativeBean {
        if (!pathMap.containsKey(this) || !pathMap.containsKey(pathId)) {
            throw Exception("未找到相应路径id")
        }
        var relativeBean: RelativeBean? = null
        when (pathMap[this]!!.type) {
            ComponentType.TEXT -> {
                val forFontMetrics = painterMap["default"]!!.fontMetrics;
                val height = (forFontMetrics.descent - forFontMetrics.ascent);
                relativeBean = RelativeBean(WorkType.CHINESE, ComponentType.TEXT)
                relativeBean.pathStr = this
                relativeBean.BeanHeight = 0f
                relativeBean.BeanWidth = 0f
//                    painterMap["default"]!!.measureText((pathMap[this]!! as TextComponentBean).text)
            }
            ComponentType.PATH -> {
                relativeBean = RelativeBean(WorkType.CHINESE, ComponentType.PATH)
                relativeBean.pathStr = this
                relativeBean.BeanHeight = 0f
                relativeBean.BeanWidth = 0f

            }
            ComponentType.CIRCLE -> {
                relativeBean = RelativeBean(WorkType.CHINESE, ComponentType.CIRCLE)
                relativeBean.pathStr = this
                relativeBean.BeanWidth = (pathMap[this]!! as CycleComponentBean).r * 2
                relativeBean.BeanHeight = (pathMap[this]!! as CycleComponentBean).r * 2
            }
            ComponentType.PICTURE -> {
                relativeBean = RelativeBean(WorkType.CHINESE, ComponentType.PICTURE)
                relativeBean.pathStr = this
                relativeBean.BeanWidth = (pathMap[this]!! as PictureComponentBean).width
                relativeBean.BeanHeight = (pathMap[this]!! as PictureComponentBean).height
            }
        }
        relativeBean.relativeId = pathId

        return relativeBean
    }


    infix fun RelativeBean.左方(left: Float): RelativeBean {
        val work = coreMap[this.relativeId] ?: throw Exception("未找到相应Core,请检查Relative顺序")
        when (work.type) {
            WorkType.CHINESE -> {
                val core = (work as ChineseBean).core
                when (core?.component) {
                    ComponentType.PATH -> {
                        val bean = core as FreePathBean
                        if (this.BeanWidth == null) {
                            this.positionX = bean.positionX - left
                        } else {
                            this.positionX = bean.positionX - this.BeanWidth!! - left
                        }
                        this.positionY = bean.positionY
                    }

                    ComponentType.TEXT -> {
                        val bean = core as TextBean
                        if (this.BeanWidth == null) {
                            this.positionX = bean.textPositionX - left
                        } else {
                            when (this.component) {
                                ComponentType.TEXT, ComponentType.PICTURE -> {
                                    this.positionX =
                                        bean.textPositionX - bean.BeanWidth!! / 2  - left

                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.THIS_WIDTH
                                        it.scale = 0.5
                                    }
                                    this.positionXDeque.add(xdeque)


                                }
                                ComponentType.CIRCLE -> {
                                    this.positionX =
                                        bean.textPositionX - bean.BeanWidth!! / 2  - left

                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.THIS_WIDTH
                                        it.scale = 0.5
                                    }
                                    this.positionXDeque.add(xdeque)

                                }
                                else -> {
                                    this.positionX =
                                        bean.textPositionX - bean.BeanWidth!! / 2 - left

                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.THIS_WIDTH
                                    }
                                    this.positionXDeque.add(xdeque)

                                }
                            }
                        }
                        this.positionY = bean.textPositionY

                    }

                    ComponentType.CIRCLE -> {
                        val bean = core as CycleBean
                        if (this.BeanWidth == null) {
                            this.positionX = bean.positionX - left
                        } else {

                            when (this.component) {
                                ComponentType.TEXT, ComponentType.PICTURE -> {
                                    this.positionX =
                                        bean.positionX - this.BeanWidth!! - bean.r - left

                                }
                                ComponentType.CIRCLE -> {
                                    this.positionX = bean.positionX - this.BeanWidth!! - left

                                }
                                else -> {
                                    this.positionX =
                                        bean.positionX - this.BeanWidth!! - bean.r - left
                                }
                            }
                        }
                        this.positionY = bean.positionY
                    }

                    ComponentType.PICTURE -> {
                        val bean = core as PictureBean
                        if (this.BeanWidth == null) {
                            this.positionX = bean.positionX - left
                        } else {
                            when (this.component) {
                                ComponentType.TEXT, ComponentType.PICTURE -> {
                                    this.positionX = bean.positionX - this.BeanWidth!! - left
                                }
                                ComponentType.CIRCLE -> {
                                    this.positionX = bean.positionX - this.BeanWidth!! / 2 - left

                                }
                                else -> {
                                    this.positionX = bean.positionX - this.BeanWidth!! - left
                                }
                            }
                        }
                        this.positionY = bean.positionY
                    }
                }
            }
        }
        this.aligen = AligenType.左边
        return this
    }

    infix fun RelativeBean.上方(top: Float): RelativeBean {
        val work = coreMap[this.relativeId] ?: throw Exception("未找到相应Core,请检查Relative顺序")
        when (work.type) {
            WorkType.CHINESE -> {
                val core = (work as ChineseBean).core
                when (core?.component) {
                    ComponentType.PATH -> {
                        val bean = core as FreePathBean
                        if (this.BeanHeight == null) {
                            this.positionY = bean.positionY - top
                        } else {
                            this.positionY = bean.positionY - this.BeanHeight!! - top
                        }
                        this.positionX = bean.positionX
                    }

                    ComponentType.TEXT -> {
                        val bean = core as TextBean
                        val forFontMetrics = bean.paint.fontMetrics;
                        if (this.BeanHeight == null) {
                            this.positionY = bean.textPositionY - top
                        } else {
                            when (this.component) {
                                ComponentType.TEXT-> {
                                    this.positionY = bean.textPositionY - top - abs(forFontMetrics.ascent)


//                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
//                                        it.positive = 0
//                                        it.step = PositionTag.ASCENT
//                                    }
//                                    this.positionYDeque.add(xdeque)
                                }
                                ComponentType.CIRCLE -> {
                                    this.positionY = bean.textPositionY - top

                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.DESCENT
                                    }
                                    this.positionYDeque.add(xdeque)


                                    val xdeque1 =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.TOP
                                    }
                                    this.positionYDeque.add(xdeque1)

                                    val xdeque2 =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.THIS_HEIGHT
                                        it.scale = 0.5
                                    }
                                    this.positionYDeque.add(xdeque2)




                                }
                                ComponentType.PICTURE ->{
                                    this.positionY = bean.textPositionY + abs(forFontMetrics.descent) - top - abs(forFontMetrics.top) - this.BeanHeight!!


                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.DESCENT
                                    }
                                    this.positionYDeque.add(xdeque)

                                    val xdeque1 =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.TOP
                                    }
                                    this.positionYDeque.add(xdeque1)

                                    val xdeque2 =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.THIS_HEIGHT
                                    }
                                    this.positionYDeque.add(xdeque2)


                                }
                                else -> {
                                    this.positionY =
                                        bean.textPositionY - bean.BeanHeight!! / 2  - top


                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.THIS_HEIGHT
                                    }
                                    this.positionYDeque.add(xdeque)

                                }
                            }
                        }
                        this.positionX = bean.textPositionX

                    }

                    ComponentType.CIRCLE -> {
                        val bean = core as CycleBean
                        if (this.BeanHeight == null) {
                            this.positionY = bean.positionY - top
                        } else {

                            when (this.component) {
                                ComponentType.TEXT ->{
                                    val forFontMetrics = painterMap["default"]!!.fontMetrics;
                                    this.positionY =
                                        bean.positionY  - bean.r - top - (abs(forFontMetrics.descent - forFontMetrics.bottom))

                                }
                                ComponentType.CIRCLE -> {
                                    this.positionY = bean.positionY - this.BeanHeight!! - top

                                }
                                ComponentType.PICTURE -> {
                                    this.positionY = bean.positionY - this.BeanHeight!! - top - bean.r
                                }
                                else -> {
                                    this.positionY =
                                        bean.positionY - this.BeanHeight!! - bean.r - top
                                }
                            }
                        }
                        this.positionX = bean.positionX
                    }

                    ComponentType.PICTURE -> {
                        val bean = core as PictureBean
                        if (this.BeanHeight == null) {
                            this.positionY = bean.positionY - top
                        } else {
                            when (this.component) {
                                ComponentType.TEXT -> {
                                    val forFontMetrics = painterMap["default"]!!.fontMetrics;
                                    this.positionY = bean.positionY  - top - (abs(forFontMetrics.descent - forFontMetrics.bottom))
                                }
                                ComponentType.CIRCLE -> {
                                    this.positionY = bean.positionY - this.BeanHeight!! / 2 - top

                                }
                                ComponentType.PICTURE ->{
                                    this.positionY = bean.positionY - this.BeanHeight!! - top
                                }
                                else -> {
                                    this.positionY = bean.positionY - this.BeanHeight!! - top
                                }
                            }
                        }
                        this.positionX = bean.positionX
                    }
                }
            }
        }
        this.aligen = AligenType.上边
        return this
    }

    infix fun RelativeBean.下方(bottom: Float): RelativeBean {
        val work = coreMap[this.relativeId] ?: throw Exception("未找到相应Core,请检查Relative顺序")
        when (work.type) {
            WorkType.CHINESE -> {
                val core = (work as ChineseBean).core
                when (core?.component) {
                    ComponentType.PATH -> {
                        val bean = core as FreePathBean
                        if (this.BeanHeight == null) {
                            this.positionY = bean.positionY + bottom
                        } else {
                            this.positionY = bean.positionY + this.BeanHeight!! + bottom
                        }
                        this.positionX = bean.positionX
                        Log.e(TAG, "下方: A", )
                    }

                    ComponentType.TEXT -> {
                        val bean = core as TextBean
                        val forFontMetrics = bean.paint.fontMetrics;
                        if (this.BeanHeight == null) {
                            this.positionY = bean.textPositionY + bottom
                        } else {
                            when (this.component) {
                                ComponentType.TEXT-> {
                                    this.positionY = bean.textPositionY + bottom + abs(forFontMetrics.descent - forFontMetrics.bottom)
                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.ASCENT
                                    }
                                    this.positionYDeque.add(xdeque)

                                }
                                ComponentType.CIRCLE -> {
                                    this.positionY = bean.textPositionY + bottom

                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.THIS_HEIGHT
                                        it.scale = 0.5
                                    }
                                    this.positionYDeque.add(xdeque)

                                    val xdeque1 =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.DESCENT
                                        it.scale = 2.0
                                    }
                                    this.positionYDeque.add(xdeque1)

                                    val xdeque2 =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.BOTTOM
                                        it.scale = 2.0
                                    }
                                    this.positionYDeque.add(xdeque2)

                                }
                                ComponentType.PICTURE ->{
                                    this.positionY = bean.textPositionY + bottom + abs(forFontMetrics.descent - forFontMetrics.bottom) *2

                                    val xdeque1 =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.DESCENT
                                        it.scale = 2.0
                                    }
                                    this.positionYDeque.add(xdeque1)

                                    val xdeque2 =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.BOTTOM
                                        it.scale = 2.0
                                    }
                                    this.positionYDeque.add(xdeque2)

                                    val ydeque = PositionQueue(PositionTag.NUM).also {
                                        it.num = this.BeanWidth!!/2.0f
                                        it.positive = 0
                                    }
                                    this.positionXDeque.add(ydeque)


                                }
                                else -> {
                                    this.positionY =
                                        bean.textPositionY + bean.BeanHeight!! / 2  + bottom

                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.THIS_HEIGHT
                                    }
                                    this.positionYDeque.add(xdeque)

                                }
                            }
                        }
                        this.positionX = bean.textPositionX
                        Log.e(TAG, "下方: B", )

                    }

                    ComponentType.CIRCLE -> {
                        val bean = core as CycleBean
                        if (this.BeanHeight == null) {
                            this.positionY = bean.positionY + bottom
                        } else {

                            when (this.component) {
                                ComponentType.TEXT ->{

                                    this.positionY =
                                        bean.positionY  + bean.r + bottom
                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.ASCENT
                                    }
                                    this.positionYDeque.add(xdeque)

                                }
                                ComponentType.CIRCLE -> {
                                    this.positionY = bean.positionY + this.BeanHeight!! + bottom

                                }
                                ComponentType.PICTURE -> {
                                    this.positionY = bean.positionY  + bottom + bean.r
                                }
                                else -> {
                                    this.positionY =
                                        bean.positionY + this.BeanHeight!! + bean.r + bottom
                                }
                            }
                        }
                        this.positionX = bean.positionX
                        Log.e(TAG, "下方: C", )
                        
                    }

                    ComponentType.PICTURE -> {
                        val bean = core as PictureBean
                        if (this.BeanHeight == null) {
                            this.positionY = bean.positionY + bottom
                        } else {
                            when (this.component) {
                                ComponentType.TEXT -> {
                                    val forFontMetrics = painterMap["default"]!!.fontMetrics;
                                    this.positionY = bean.positionY  + bottom + (abs(forFontMetrics.ascent)) + bean.BeanHeight!!
                                }
                                ComponentType.CIRCLE -> {
                                    this.positionY = bean.positionY + bottom + bean.BeanHeight!! + this.BeanHeight!!/2

                                    val off = PositionQueue(PositionTag.NUM).also {
                                        it.positive = 1
                                        it.num = bean.BeanWidth!!/2
                                    }
                                    this.positionXDeque.add(off)

                                }
                                ComponentType.PICTURE ->{
                                    this.positionY = bean.positionY + this.BeanHeight!! + bottom
                                }
                                else -> {
                                    this.positionY = bean.positionY + this.BeanHeight!! + bottom
                                }
                            }
                        }
                        this.positionX = bean.positionX
                        Log.e(TAG, "下方: D", )
                    }
                }
            }
        }
        this.aligen = AligenType.下边
        return this
    }

    infix fun RelativeBean.右方(right: Float): RelativeBean {
        val work = coreMap[this.relativeId] ?: throw Exception("未找到相应Core,请检查Relative顺序")
        when (work.type) {
            WorkType.CHINESE -> {
                val core = (work as ChineseBean).core
                when (core?.component) {
                    ComponentType.PATH -> {
                        val bean = core as FreePathBean
                        if (this.BeanWidth == null) {
                            this.positionX = bean.positionX + right
                        } else {
                            this.positionX = bean.positionX + this.BeanWidth!! + right
                        }
                        Log.e(TAG, "右方: A" )
                        this.positionY = bean.positionY
                    }

                    ComponentType.TEXT -> { //基于 Text进行调整的数据
                        val bean = core as TextBean
                        if (this.BeanWidth == null) {
                            this.positionX = bean.textPositionX + right
                        } else {
                            when (this.component) {
                                ComponentType.TEXT -> {
                                    this.positionX = bean.textPositionX + bean.BeanWidth!!/2 + right
                                    Log.e(TAG, "右方: B1 bean:{position ${bean.textPositionX} width ${bean.BeanWidth}}", )
                                    
                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.THIS_WIDTH
                                        it.scale = 0.5
                                    }
                                    this.positionXDeque.add(xdeque)
                                    Log.e(TAG, "右方: B1", )
                                }
                                ComponentType.CIRCLE -> {
                                    this.positionX =
                                        bean.textPositionX  + right
                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.THIS_WIDTH
                                        it.scale = 0.5
                                    }
                                    this.positionXDeque.add(xdeque)
                                    Log.e(TAG, "右方: B2", )
                                }
                                ComponentType.PICTURE ->{
                                    this.positionX = bean.textPositionX + bean.BeanWidth!!/2  + right

                                    val xdeque =  PositionQueue(PositionTag.NUM).also {
                                        it.positive = 0
                                        it.num = this.BeanHeight!!
                                        Log.e(TAG, "右方: B3 height ${this.BeanHeight} width ${this.BeanWidth}", )
                                    }
                                    this.positionYDeque.add(xdeque)




                                    Log.e(TAG, "右方: B3", )
                                }
                                else -> {
                                    this.positionX =
                                        bean.textPositionX + bean.BeanWidth!! / 2  + right
                                }
                            }
                        }
                        Log.e(TAG, "右方: B" )
                        this.positionY = bean.textPositionY

                       /* val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                            it.positive = 0
                            it.step = PositionTag.THIS_HEIGHT
                            it.scale = 0.5
                        }
                        this.positionYDeque.add(xdeque)*/
                    }

                    ComponentType.CIRCLE -> {
                        val bean = core as CycleBean
                        if (this.BeanWidth == null) {
                            this.positionX = bean.positionX + right
                        } else {

                            when (this.component) {
                                ComponentType.TEXT -> {
                                    this.positionX = bean.positionX + bean.r + right
                                    Log.e(TAG, "右方: C1", )
                                    Log.e(TAG, "右方: C1 positionX : ${this.positionX}", )
                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 1
                                        it.step = PositionTag.THIS_WIDTH
                                        it.scale = 0.5
                                    }
                                    this.positionXDeque.add(xdeque)
                                }
                                ComponentType.CIRCLE -> {
                                    this.positionX = bean.positionX  + right
                                    val xdeque =  PositionQueue(PositionTag.MEASURE).also {
                                        it.positive = 0
                                        it.step = PositionTag.THIS_WIDTH
                                    }
                                    this.positionXDeque.add(xdeque)


                                    Log.e(TAG, "右方: C2", )

                                }
                                ComponentType.PICTURE ->{
                                    this.positionX = bean.positionX + bean.r + right
                                    Log.e(TAG, "右方: C3", )
                                }
                                else -> {
                                    this.positionX =
                                        bean.positionX + this.BeanWidth!! + bean.r + right
                                }
                            }
                        }
                        Log.e(TAG, "右方: C" )
                        this.positionY = bean.positionY
                    }

                    ComponentType.PICTURE -> {
                        val bean = core as PictureBean
                        if (this.BeanWidth == null) {
                            this.positionX = bean.positionX + right
                        } else {
                            when (this.component) {
                                ComponentType.TEXT -> {
                                    this.positionX = bean.positionX + right + bean.BeanWidth!!
                                }
                                ComponentType.CIRCLE -> {
                                    this.positionX = bean.positionX + this.BeanWidth!! / 2 + right + bean.BeanWidth!!
                                    val xdeque =  PositionQueue(PositionTag.NUM).also {
                                        it.positive = 1
                                        it.num = bean.BeanHeight!! - this.BeanWidth!!/2
                                    }
                                    this.positionYDeque.add(xdeque)




                                }
                                ComponentType.PICTURE -> {
                                    this.positionX = bean.positionX + this.BeanWidth!! + right
                                }
                                else -> {
                                    this.positionX = bean.positionX + this.BeanWidth!! + right
                                }
                            }
                        }
                        Log.e(TAG, "右方: D" )
                        this.positionY = bean.positionY



                    }
                }
            }
        }
        this.aligen = AligenType.右边
        return this
    }


    infix fun RelativeBean.上边距(top: Float) : RelativeBean{
        if (this.aligen == AligenType.上边 || this.aligen == AligenType.下边)
            return this
        else
            this.topEdge = top
        return this
    }

    infix fun RelativeBean.下边距(bottom: Float) : RelativeBean{
        if (this.aligen == AligenType.上边 || this.aligen == AligenType.下边)
            return this
        else
            this.bottomEdg = bottom
        return this
    }

    infix fun RelativeBean.左边距(left: Float) : RelativeBean{
        if (this.aligen == AligenType.左边 || this.aligen == AligenType.右边)
            return this
        else
            this.leftEdg = left
        return this
    }

    infix fun RelativeBean.右边距(right: Float) : RelativeBean{
        if (this.aligen == AligenType.左边 || this.aligen == AligenType.右边)
            return this
        else
            this.rightEdg = right
        return this
    }



    infix fun RelativeBean.使用画笔(paintId: String) {
        if (!painterMap.containsKey(paintId)) {
            throw Exception("未找到相应画笔id")
        }
        this.paint = painterMap[paintId]
        val chineseBean = ChineseBean(this.type, this.component)
        chineseBean.bottomEdg = this.bottomEdg
        chineseBean.topEdge = this.topEdge
        chineseBean.leftEdg = this.leftEdg
        chineseBean.rightEdg = this.rightEdg
        chineseBean.pathStr = this.pathStr
        chineseBean.place = this.place
        chineseBean.paint = this.paint




        when (pathMap[this.pathStr]!!.type) {
            ComponentType.TEXT -> {
                //文字绘制
                val bean = pathMap[this.pathStr]!! as TextComponentBean

                val forFontMetrics = painterMap[paintId]!!.fontMetrics
                val height = (forFontMetrics.descent - forFontMetrics.ascent)



                bean.let {
                    while (this.positionXDeque.isNotEmpty()){
                        val tag = this.positionXDeque.removeFirst()

                        val positive = if (tag.positive == 0) -1 else 1

                        if (tag.TAG == PositionTag.MEASURE){
                            when(tag.step){
                                PositionTag.THIS_HEIGHT ->{
                                    this.positionX += (height*tag.scale*positive).toFloat()
                                }
                                PositionTag.THIS_WIDTH ->{
                                    this.positionX += (painterMap[paintId]!!.measureText((pathMap[this.pathStr]!! as TextComponentBean).text)*tag.scale*positive).toFloat()
                                }
                                PositionTag.TOP ->{
                                    this.positionX += (abs(forFontMetrics.top)*tag.scale*positive).toFloat()
                                }
                                PositionTag.ASCENT ->{
                                    this.positionX += (abs(forFontMetrics.ascent*positive*tag.scale)).toFloat()
                                }
                                PositionTag.BOTTOM ->{
                                    this.positionX += (abs(forFontMetrics.bottom*positive*tag.scale)).toFloat()
                                }
                                PositionTag.DESCENT ->{
                                    this.positionX += (abs(forFontMetrics.descent*positive*tag.scale)).toFloat()
                                }

                            }
                        }else if (tag.TAG == PositionTag.NUM){
                            this.positionX += (tag.num * positive * tag.scale).toFloat()
                        }

                    }

                    while (this.positionYDeque.isNotEmpty()){
                        val tag = this.positionYDeque.removeFirst()

                        val positive = if (tag.positive == 0) -1 else 1

                        if (tag.TAG == PositionTag.MEASURE){
                            when(tag.step){
                                PositionTag.THIS_HEIGHT ->{
                                    this.positionY += (height*tag.scale*positive).toFloat()
                                }
                                PositionTag.THIS_WIDTH ->{
                                    this.positionY += (painterMap[paintId]!!.measureText((pathMap[this.pathStr]!! as TextComponentBean).text)*tag.scale*positive).toFloat()
                                }
                                PositionTag.TOP ->{
                                    this.positionY += (abs(forFontMetrics.top)*tag.scale*positive).toFloat()
                                }
                                PositionTag.ASCENT ->{
                                    this.positionY += (abs(forFontMetrics.ascent*positive*tag.scale)).toFloat()
                                }
                                PositionTag.BOTTOM ->{
                                    this.positionY += (abs(forFontMetrics.bottom*positive*tag.scale)).toFloat()
                                }
                                PositionTag.DESCENT ->{
                                    this.positionY += (abs(forFontMetrics.descent*positive*tag.scale)).toFloat()
                                }
                            }
                        }else if (tag.TAG == PositionTag.NUM){
                            this.positionY += (tag.num * positive * tag.scale).toFloat()
                        }











                    }


                    it.textPositionX = this.positionX
                    it.textPositionY = this.positionY
                    chineseBean.core = textPacket(WorkType.FREE, it, paintId)
                }
            }
            ComponentType.PATH -> {
                //自由绘图
                val bean = (pathMap[this.pathStr]!! as FreeComponentBean)
                bean.let {
                    it.positionX = this.positionX
                    it.positionY = this.positionY
                    chineseBean.core = pathPacket(WorkType.FREE, it, paintId)
                }

            }
            ComponentType.CIRCLE -> {
                val bean = (pathMap[this.pathStr]!! as CycleComponentBean)
                bean.let {
                    while (this.positionXDeque.isNotEmpty()){
                        val tag = this.positionXDeque.removeFirst()

                        val positive = if (tag.positive == 0) -1 else 1

                        if (tag.TAG == PositionTag.NUM){
                            this.positionX += (tag.num * positive * tag.scale).toFloat()
                        }

                    }

                    while (this.positionYDeque.isNotEmpty()){
                        val tag = this.positionYDeque.removeFirst()

                        val positive = if (tag.positive == 0) -1 else 1

                        if (tag.TAG == PositionTag.NUM){
                            this.positionY += (tag.num * positive * tag.scale).toFloat()
                        }


                    }
                    it.positionX = this.positionX
                    it.positionY = this.positionY
                    chineseBean.core = cyclePacket(WorkType.FREE, it, paintId)
                }
            }

            ComponentType.PICTURE -> {

                val bean = (pathMap[this.pathStr]!! as PictureComponentBean)
                bean.let {

                    while (this.positionXDeque.isNotEmpty()){
                        val tag = this.positionXDeque.removeFirst()

                        val positive = if (tag.positive == 0) -1 else 1

                        if (tag.TAG == PositionTag.NUM){
                            this.positionX += (tag.num * positive * tag.scale).toFloat()
                        }

                    }

                    while (this.positionYDeque.isNotEmpty()){
                        val tag = this.positionYDeque.removeFirst()

                        val positive = if (tag.positive == 0) -1 else 1

                        if (tag.TAG == PositionTag.NUM){
                            this.positionY += (tag.num * positive * tag.scale).toFloat()
                        }


                    }
                    it.positionX = this.positionX
                    it.positionY = this.positionY
                    chineseBean.core = picPacket(WorkType.FREE, it, paintId)
                }
            }
        }
        coreMap[this.pathStr] = chineseBean
        workQueue.add(chineseBean)

    }


    //Frame Layout

    infix fun String.画在(放置位置: 位置): ChineseBean {
        var chineseBean: ChineseBean? = null
        if (!pathMap.containsKey(this)) {
            throw Exception("未找到相应路径id")
        }
        when (pathMap[this]!!.type) {
            ComponentType.TEXT -> {
                chineseBean = ChineseBean(WorkType.CHINESE, ComponentType.TEXT)
                chineseBean.pathStr = this
            }
            ComponentType.PATH -> {
                chineseBean = ChineseBean(WorkType.CHINESE, ComponentType.PATH)
                chineseBean.pathStr = this

            }
            ComponentType.CIRCLE -> {
                chineseBean = ChineseBean(WorkType.CHINESE, ComponentType.CIRCLE)
                chineseBean.pathStr = this
            }
            ComponentType.PICTURE -> {
                chineseBean = ChineseBean(WorkType.CHINESE, ComponentType.PICTURE)
                chineseBean.pathStr = this
            }
        }
        when (放置位置) {
            位置.左上角 -> {
                chineseBean.place = 位置.左上角
            }
            位置.右上角 -> {
                chineseBean.place = 位置.右上角
            }
            位置.左下角 -> {
                chineseBean.place = 位置.左下角
            }

            位置.右下角 -> {
                chineseBean.place = 位置.右下角
            }
            位置.正中 -> {
                chineseBean.place = 位置.正中
            }
            位置.水平居中 -> {
                chineseBean.place = 位置.水平居中
            }
            位置.垂直居中 -> {
                chineseBean.place = 位置.垂直居中
            }
            位置.默认 -> {
                chineseBean.place = 位置.默认
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

    infix fun ChineseBean.上边距(top: Float): ChineseBean {
        if (this.place == 位置.左下角 || this.place == 位置.右下角 || this.place == 位置.正中 || this.place == 位置.垂直居中)
            return this
        else
            this.topEdge = top
        return this
    }

    infix fun ChineseBean.下边距(bottom: Float): ChineseBean {
        if (this.place == 位置.左上角 || this.place == 位置.右上角 || this.place == 位置.正中 || this.place == 位置.垂直居中)
            return this
        else
            this.bottomEdg = bottom
        return this
    }

    infix fun ChineseBean.左边距(left: Float): ChineseBean {
        if (this.place == 位置.右下角 || this.place == 位置.右上角 || this.place == 位置.正中 || this.place == 位置.水平居中)
            return this
        else
            this.leftEdg = left
        return this
    }

    infix fun ChineseBean.右边距(right: Float): ChineseBean {
        if (this.place == 位置.左上角 || this.place == 位置.左下角 || this.place == 位置.正中 || this.place == 位置.水平居中)
            return this
        else
            this.rightEdg = right
        return this
    }


    infix fun ChineseBean.使用画笔(paintId: String) {
        if (!painterMap.containsKey(paintId)) {
            throw Exception("未找到相应画笔id")
        }
        this.paint = painterMap[paintId]
        when (pathMap[this.pathStr]!!.type) {
            ComponentType.TEXT -> {
                //文字绘制
                painterMap[paintId]!!.textAlign = Paint.Align.CENTER
                val bean = pathMap[this.pathStr]!! as TextComponentBean
                val forFontMetrics = painterMap[paintId]!!.fontMetrics;
                val textWidth = painterMap[paintId]!!.measureText(bean.text)
                val bounds = Rect()
                painterMap[paintId]!!.getTextBounds(bean.text, 0, bean.text.length, bounds)
                bean.let {
                    when (this.place) {
                        位置.水平居中 -> {
                            it.textPositionX = (width) * 1f / 2
                            if (this.bottomEdg == 0f) {
                                if (this.topEdge == 0f) {
                                    it.textPositionY = 0f + abs(forFontMetrics.top)
                                } else {
                                    it.textPositionY = 0f + this.topEdge + abs(forFontMetrics.top)
                                }
                            } else {
                                if (this.bottomEdg == 1f) {
                                    if (this.topEdge == 0f) {
                                        it.textPositionY = height - abs(forFontMetrics.bottom)
                                    } else {
                                        it.textPositionY = height - abs(forFontMetrics.bottom)
                                    }
                                } else {
                                    if (this.topEdge == 0f) {
                                        it.textPositionY =
                                            height - this.bottomEdg - abs(forFontMetrics.bottom)
                                    } else {
                                        it.textPositionY =
                                            height - this.bottomEdg - abs(forFontMetrics.bottom)
                                    }
                                }


                            }
                        }
                        位置.垂直居中 -> {

                            if (this.leftEdg == 0f) {
                                if (this.rightEdg == 0f) {
                                    it.textPositionX = 0f + textWidth / 2
                                } else {
                                    if (rightEdg == 1f) {
                                        it.textPositionX = width - textWidth / 2
                                    } else {
                                        it.textPositionX = width - this.rightEdg - textWidth / 2
                                    }
                                }
                            } else {
                                if (this.rightEdg == 0f) {
                                    it.textPositionX = 0f + this.leftEdg + textWidth / 2
                                } else {
                                    it.textPositionX = 0f + this.leftEdg + textWidth / 2
                                }
                            }
                            it.textPositionY =
                                (height - ((forFontMetrics.top + forFontMetrics.bottom) / 2 - forFontMetrics.bottom)) * 1f / 2
                        }
                        位置.左上角 -> {
                            it.textPositionX = 0f + this.leftEdg + textWidth / 2
                            it.textPositionY = 0f + this.topEdge + abs(forFontMetrics.top)
                        }
                        位置.右上角 -> {
                            it.textPositionX = width - this.rightEdg - textWidth / 2
                            it.textPositionY = 0f + this.topEdge + abs(forFontMetrics.top)
                        }
                        位置.左下角 -> {
                            it.textPositionX = 0 + this.leftEdg + textWidth / 2
                            it.textPositionY = height - this.bottomEdg - forFontMetrics.bottom
                        }
                        位置.右下角 -> {
                            it.textPositionX = width - this.rightEdg - textWidth / 2
                            it.textPositionY = height - this.bottomEdg - forFontMetrics.bottom
                        }
                        位置.正中 -> {
                            it.textPositionX = (width) * 1f / 2
                            it.textPositionY =
                                (height - ((forFontMetrics.top + forFontMetrics.bottom) / 2 - forFontMetrics.bottom)) * 1f / 2
                        }
                    }
                    this.core = textPacket(WorkType.FREE, it, paintId)
                }
            }
            ComponentType.PATH -> {
                //自由绘图
                val bean = (pathMap[this.pathStr]!! as FreeComponentBean)
                bean.let {

                    when (this.place) {
                        位置.水平居中 -> {
                            it.positionX = width * 1f / 2
                            if (this.bottomEdg == 0f) {
                                if (this.topEdge == 0f) {
                                    it.positionY = 0f
                                } else {
                                    it.positionY = 0f + this.topEdge
                                }
                            } else {
                                if (this.topEdge == 0f) {
                                    it.positionY = height - this.bottomEdg
                                } else {
                                    it.positionY = height - this.bottomEdg
                                }
                            }
                        }

                        位置.垂直居中 -> {


                            if (this.leftEdg == 0f) {
                                if (this.rightEdg == 0f) {
                                    it.positionX = 0f
                                } else {
                                    it.positionX = width - this.rightEdg
                                }
                            } else {
                                if (this.rightEdg == 0f) {
                                    it.positionX = this.leftEdg
                                } else {
                                    it.positionX = this.leftEdg
                                }
                            }


                            it.positionY = height * 1f / 2
                        }
                        位置.左上角 -> {
                            it.positionX = 0f + this.leftEdg
                            it.positionY = 0f + this.topEdge
                        }
                        位置.右上角 -> {
                            it.positionX = width - this.rightEdg
                            it.positionY = 0f + this.topEdge
                        }
                        位置.左下角 -> {
                            it.positionX = 0 + this.leftEdg
                            it.positionY = height - this.bottomEdg
                        }

                        位置.右下角 -> {
                            it.positionX = width - this.rightEdg
                            it.positionY = height - this.bottomEdg
                        }

                        位置.正中 -> {
                            it.positionX = width * 1f / 2
                            it.positionY = height * 1f / 2
                        }

                    }

                    this.core = pathPacket(WorkType.FREE, it, paintId)
                }

            }
            ComponentType.CIRCLE -> {
                val bean = (pathMap[this.pathStr]!! as CycleComponentBean)
                bean.let {
                    when (this.place) {
                        位置.水平居中 -> {
                            it.positionX = width * 1f / 2
                            /* if (this.bottomEdg == 0f){
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
                             }*/
                            if (this.topEdge != 0f && this.topEdge != 1f) {
                                it.positionY = 0f + this.topEdge + it.r
                            } else {
                                if (this.bottomEdg != 0f && this.bottomEdg != 1f) {
                                    it.positionY = height - this.bottomEdg - it.r
                                } else {
                                    if (this.bottomEdg == 1f) {
                                        it.positionY = height - it.r
                                    } else {
                                        it.positionY = 0f + it.r
                                    }
                                }
                            }


                        }

                        位置.垂直居中 -> {
//                            if (this.leftEdg == 0f){
//                                if (this.rightEdg == 0f){
//                                    it.positionX = 0f
//                                }else{
//                                    it.positionX = width - this.rightEdg
//                                }
//                            }else{
//                                if (this.rightEdg == 0f){
//                                    it.positionX = this.leftEdg
//                                }else{
//                                    it.positionX = this.leftEdg
//                                }
//                            }

                            if (this.leftEdg != 0f && this.leftEdg != 1f) {
                                it.positionX = 0f + this.leftEdg + it.r
                            } else {
                                if (this.rightEdg != 0f && this.rightEdg != 1f) {
                                    it.positionX = width - this.rightEdg - it.r
                                } else {
                                    if (this.rightEdg == 1f) {
                                        it.positionX = width - it.r
                                    } else {
                                        it.positionX = 0f + it.r
                                    }
                                }
                            }

                            it.positionY = height * 1f / 2
                        }
                        位置.左上角 -> {
                            it.positionX = 0f + this.leftEdg + it.r
                            it.positionY = 0f + this.topEdge + it.r
                        }
                        位置.右上角 -> {
                            it.positionX = width - this.rightEdg - it.r
                            it.positionY = 0f + this.topEdge + it.r
                        }
                        位置.左下角 -> {
                            it.positionX = 0 + this.leftEdg + it.r
                            it.positionY = height - this.bottomEdg - it.r
                        }

                        位置.右下角 -> {
                            it.positionX = width - this.rightEdg - it.r
                            it.positionY = height - this.bottomEdg - it.r
                        }

                        位置.正中 -> {
                            it.positionX = width * 1f / 2
                            it.positionY = height * 1f / 2
                        }

                    }
                    this.core = cyclePacket(WorkType.FREE, it, paintId)
                }
            }
            ComponentType.PICTURE -> {
                val bean = (pathMap[this.pathStr]!! as PictureComponentBean)
                bean.let {
                    when (this.place) {
                        位置.水平居中 -> {
                            it.positionX = (width - it.width) * 1f / 2

                            /*if (this.bottomEdg == 0f){
                                if (this.topEdge == 0f){
                                    it.positionY = 0f
                                }else{
                                    it.positionY = 0f+this.topEdge
                                }
                            }else{
                                if (this.bottomEdg == 1f){
                                    if (this.topEdge == 0f){
                                        it.positionY = height - it.height
                                    }else{
                                        it.positionY = height - it.height
                                    }
                                }else{
                                    if (this.topEdge == 0f){
                                        it.positionY = height - this.bottomEdg  - it.height
                                    }else{
                                        it.positionY = height - this.bottomEdg - it.height
                                    }
                                }


                            }*/


                            //按照下边距为准
                            if (this.topEdge != 0f && this.topEdge != 1f) {
                                it.positionY = 0f + this.topEdge
                            } else {
                                if (this.bottomEdg != 0f && this.bottomEdg != 1f) {
                                    it.positionY = height - this.bottomEdg - it.height
                                } else {
                                    if (this.bottomEdg == 1f) {
                                        it.positionY = height - it.height
                                    } else {
                                        it.positionY = 0f
                                    }

                                }
                            }


                        }

                        位置.垂直居中 -> {
                            if (this.leftEdg == 0f) {
                                if (this.rightEdg == 0f) {
                                    it.positionX = 0f
                                } else {
                                    it.positionX = width - this.rightEdg - it.width
                                }
                            } else {
                                if (this.rightEdg == 0f) {
                                    it.positionX = this.leftEdg
                                } else {
                                    it.positionX = this.leftEdg
                                }
                            }
                            it.positionY = (height - it.height) * 1f / 2
                        }
                        位置.左上角 -> {
                            it.positionX = 0f + this.leftEdg
                            it.positionY = 0f + this.topEdge
                        }
                        位置.右上角 -> {
                            it.positionX = width - this.rightEdg - it.width
                            it.positionY = 0f + this.topEdge
                        }
                        位置.左下角 -> {
                            it.positionX = 0 + this.leftEdg
                            it.positionY = height - this.bottomEdg - it.height
                        }

                        位置.右下角 -> {
                            it.positionX = width - this.rightEdg - it.width
                            it.positionY = height - this.bottomEdg - it.height
                        }

                        位置.正中 -> {
                            it.positionX = (width - it.width) * 1f / 2
                            it.positionY = (height - it.height) * 1f / 2
                        }


                    }
                    this.core = picPacket(WorkType.FREE, it, paintId)
                }
            }

        }
        if (this.pathStr == "!@#$%index%$#@!"){
            coreMap[this.pathStr] = this //TODO
            workQueue.add(this)
        }else{
            coreMap[this.pathStr] = this
            workQueue.add(this)
        }


    }


    //传统方式进行调用

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


                else -> {
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


    private fun picPacket(type: WorkType, it: PictureComponentBean, idPaint: String): PictureBean {
        return PictureBean(
            type,
            ComponentType.PICTURE,
            it.height,
            it.width,
            painterMap[idPaint]!!,
            it.bitmap,
            it.positionX,
            it.positionY
        )
    }


    private fun cyclePacket(type: WorkType, it: CycleComponentBean, idPaint: String): CycleBean {
        return CycleBean(
            type,
            ComponentType.CIRCLE,
            it.r,
            painterMap[idPaint]!!,
            it.positionX,
            it.positionY,
            2 * it.r,
            2 * it.r
        )
    }


    private fun textPacket(type: WorkType, it: TextComponentBean, idPaint: String): TextBean {
        val forFontMetrics = painterMap[idPaint]!!.fontMetrics;
        val height = (forFontMetrics.top);
        return TextBean(
            type,
            ComponentType.TEXT,
            it.text,
            painterMap[idPaint]!!,
            it.textPath,
            it.textPositionY,
            it.textPositionX,
            BeanHeight = height,
            BeanWidth = painterMap[idPaint]!!.measureText(it.text)
        )
    }


    operator fun invoke(block: Drawer.() -> Unit) {
        block()
    }

}