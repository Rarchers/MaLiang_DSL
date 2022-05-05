# MaLiang_DSL

###请切换到layout分支使用

#如何使用
gradle：

在build.gradle内添加：
 maven { url 'https://jitpack.io' }


在dependency内使用 
implementation 'com.github.Rarchers:MaLiang_DSL:v1.0'



## 简介
  MaLiang DSL 是基于kotlin实现的安卓自定义View UI绘制框架DSL，它解决了传统自定义view中的代码复杂，开发测试排查bug难的问题，同时将传统的命令式UI转为当下流行的声明式UI，同时支持了传统自定义view所不支持的实时预览
  ___
## MaLiang 的优点
+ 实时的预览，（注意，需要添加DSL.jar包，后期会整合进模块内，无需自己引入）。可以快速预览当前所编写的组件内容以及展示的效果
+ 模块化编写， MaLiang 框架分为 layer，painters，component，drawer 四层，每个模块独立负责UI的一部分，使得代码更清爽，定位问题更清晰容易
+ 设计友好语言， MaLiang提供了描述性语言来放置UI组件，只需想日常写文章一样描述组件放置在布局的什么位置即可完成UI的布局工作

## MaLiang 框架结构

### UI绘制版本
``` kotlin
  layer(canvas = canvas,height,width) {
        painters {
     
        }

        component {

        }
        drawer {
        }

   }
```
layer： 进入MaLiang环境，接管canvas的绘制布局，内部共三个模块。
painters：画笔初始化
component： 组件定义，在这里完成每个独立小组件的路径绘制或者声明所需的文本
drawer： 布局区，在这里指定组件在布局中放置的位置，以及使用的画笔

### 预览版本
 注意：使用预览请独立创建一个main方法，并将代码放在main方法里，同时需要引入DSL.jar包
``` kotlin
   previewLayer {
        component {
        
        }

        preview {
          
        }
    }
 ```
 previewLayer ： 进入预览环境，无需传入canvas
 component ： 组件定义，为后续预览提供组件
 preview：选择需要进行预览的组件进行预览
 
 
 ## 使用样例
 
 ### UI绘制版本
 ``` kotlin
 
      layer(canvas = canvas,height,width) {
          painters {
              "invoke"{
                  invokePaint.color = Color.RED
                  invokePaint.textSize = 50f
                  invokePaint.textAlign = Paint.Align.CENTER;
                  invokePaint
              }
              "painters".painter {
                        paint2.color = Color.BLACK
                        paint2
              }
              addPaint("rarcher",paint)
          }

          component {
              text("hello","Hello World")
              text("designed","by Rarcher")
          }
          drawer {
              "hello" 画在 位置.正中 使用画笔 "invoke"
              "designed" 画在 位置.右下角 右边距 300f 下边距 20f 使用画笔 "rarcher"
          }
          
      }
 ```
 
 画笔初始化提供了三种方式 ：
 1. addPaint(id:String,paint: Paint)，添加一个已有的画笔，第一个参数为画笔的名称
 2. "painterName"{}  字符串为画笔名称，直接接上大括号，大括号内部完成画笔的初始化定义，最后一行为画笔的引用
 3. "painterName".painter{} 与2一致，只是多一个.painter方法，推荐使用2
 
 组件的定义方式：
 1.文本类： text(id:String,str:String,positionX:Float = 0f,positionY:Float = 0f) id为组件名称，str为文本内容，position 为指定文本位置，可为空（推荐）
 2.路径类：path(id:String, path:Path,positionX:Float,positionY:Float) id为组件名称，path为路径的内容position可为空（推荐）
 
 绘制的使用方法：
 1. 传统方法调用：drawComponent(idPaint: String, idComponent: String) 需要提供画笔id和组件id，（注意，如果组件未指定position，将会默认绘制在0，0坐标，即传统帧布局）
 2. DSL特有调用：  "designed" 画在 位置.右下角 右边距 300f 下边距 20f 使用画笔 "rarcher" 按照正常书写方式进行描述组件绘制的位置以及使用的画笔即可
 
 ### 预览版本
 
  ``` kotlin
    previewLayer {

        component {
            text("hello","DSL Preview Test")
            text("well","should not work")
            text("next","try mix!")

        }
        preview {
            "hello" 预览状态为 true
            "well" 预览状态为 false
            "next" 预览状态为 true
        }
    }
  ```
component:同UI版本
preview:  "hello" 预览状态为 true  依旧为正常书写逻辑进行设置预览状态即可
 
