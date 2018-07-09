package com.ann

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class CustomPluginTest implements Plugin<Project> {

    @Override
    void apply(Project project) {
        //增加闭包名称，闭包为customPlugin，是 CustomPluginTestExtension类型，因此CustomPluginTestExtension类型中的JaveBean类型的属性可以任意设置
        project.extensions.add("customPlugin", CustomPluginTestExtension)
        project.task("showPersonInfo") << {
            println("姓名：" + project.customPlugin.name)
            println("年龄：" + project.customPlugin.age)
            println("地址：" + project.customPlugin.address)
        }

        project.task('showPersonInfo2') {
            group = "test-plugin2"
            doLast {
                println "Hello"
            }
        }
    }
}

class CustomPluginTestExtension {
    def name = "init"
    def age = "init"
    def address = "init"
}