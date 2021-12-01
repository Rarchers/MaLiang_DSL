package com.example.dsl.dsl.preview


import com.example.dsl.dsl.utils.previewLayer
import dsl.DSLPreview


fun main() {


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


}


