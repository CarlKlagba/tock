/*
 * Copyright (C) 2017/2019 e-voyageurs technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.tock.bot.admin.story

import ai.tock.bot.admin.answer.AnswerConfiguration
import ai.tock.bot.admin.answer.AnswerConfigurationType
import ai.tock.bot.admin.answer.ScriptAnswerConfiguration
import ai.tock.bot.admin.bot.BotVersion
import ai.tock.bot.definition.StoryDefinition

/**
 * Contains list of [AnswerConfiguration].
 */
internal interface StoryDefinitionAnswersContainer {

    /**
     * The answers available.
     */
    val answers: List<AnswerConfiguration>
    /**
     * The type of answer configuration.
     */
    val currentType: AnswerConfigurationType

    fun findCurrentAnswer(): AnswerConfiguration? =
        findAnswer(currentType)

    /**
     * Is there a current answer ?
     */
    fun hasCurrentAnwser(): Boolean = findCurrentAnswer()?.hasAnswer() == true

    fun findAnswer(type: AnswerConfigurationType): AnswerConfiguration? =
        answers.firstOrNull { it.answerType == type }

    fun storyDefinition(botId: String): StoryDefinition? =
        (findCurrentAnswer() as? ScriptAnswerConfiguration)
            ?.findBestVersion(BotVersion.getCurrentBotVersion(botId))
            ?.storyDefinition

    fun findNextSteps(story: StoryDefinitionConfiguration): List<String> = emptyList()
}