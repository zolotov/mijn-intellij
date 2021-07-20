package com.github.zolotov.mijnintellij.actions

import com.intellij.execution.Executor
import com.intellij.execution.RunnerAndConfigurationSettings
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.RunContextAction
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.executors.DefaultDebugExecutor
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.junit.JUnitConfiguration
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.rt.execution.junit.RepeatCount

abstract class RunDebugUntilStoppedAction(private val repeatMode: String, executor: Executor) : RunContextAction(executor) {
    override fun perform(configuration: RunnerAndConfigurationSettings, context: ConfigurationContext) {
        val junitConfiguration = configuration.configuration as? JUnitConfiguration
        if (junitConfiguration != null) {
            junitConfiguration.repeatMode = repeatMode
        }
        super.perform(configuration, context)
    }

    override fun isEnabledFor(configuration: RunConfiguration): Boolean {
        return super.isEnabledFor(configuration) && configuration is JUnitConfiguration && configuration.repeatMode != repeatMode
    }

    override fun updatePresentation(presentation: Presentation, actionText: String, context: ConfigurationContext) {
        super.updatePresentation(presentation, actionText, context)
        presentation.text = "${presentation.text} and repeat until stopped"
    }
}


class RunUntilStoppedAction : RunDebugUntilStoppedAction(RepeatCount.UNLIMITED, DefaultRunExecutor.getRunExecutorInstance())

class DebugUntilStoppedAction : RunDebugUntilStoppedAction(RepeatCount.UNLIMITED, DefaultDebugExecutor.getDebugExecutorInstance())

class RunOnceAction : RunDebugUntilStoppedAction(RepeatCount.ONCE, DefaultRunExecutor.getRunExecutorInstance())

class DebugOnceAction : RunDebugUntilStoppedAction(RepeatCount.ONCE, DefaultDebugExecutor.getDebugExecutorInstance())
