package com.github.zolotov.mijnintellij.listeners

import com.intellij.execution.JavaRunConfigurationBase
import com.intellij.execution.RunnerAndConfigurationSettings
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.RunContextAction
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.junit.JUnitConfiguration
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.rt.execution.junit.RepeatCount

class RunUntilStoppedAction() : RunContextAction(DefaultRunExecutor.getRunExecutorInstance()) {
    override fun perform(configuration: RunnerAndConfigurationSettings, context: ConfigurationContext) {
        val junitConfiguration = configuration.configuration as? JUnitConfiguration
        if (junitConfiguration != null) {
            junitConfiguration.repeatMode = RepeatCount.UNLIMITED
        }
        super.perform(configuration, context)
    }

    override fun isEnabledFor(configuration: RunConfiguration): Boolean {
        return super.isEnabledFor(configuration) && configuration is JUnitConfiguration
    }

    override fun updatePresentation(presentation: Presentation, actionText: String, context: ConfigurationContext) {
        super.updatePresentation(presentation, actionText, context)
        presentation.text = "${presentation.text} and repeat until stopped"
    }
}
