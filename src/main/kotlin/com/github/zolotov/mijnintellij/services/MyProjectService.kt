package com.github.zolotov.mijnintellij.services

import com.github.zolotov.mijnintellij.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
