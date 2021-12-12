package com.github.procrastinationfighter.ideaannotator.services

import com.intellij.openapi.project.Project
import com.github.procrastinationfighter.ideaannotator.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
