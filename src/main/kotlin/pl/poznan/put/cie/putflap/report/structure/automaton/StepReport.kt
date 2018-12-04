package pl.poznan.put.cie.putflap.report.structure.automaton

import jflap.automata.Configuration
import jflap.automata.fsa.FSAConfiguration
import jflap.automata.mealy.MealyConfiguration
import jflap.automata.pda.PDAConfiguration
import pl.poznan.put.cie.putflap.report.Report

class StepReport private constructor(
    val state: Int,
    val read: String,
    val toProcess: String,
    val currentOutput: String? = null,
    val stack: String? = null
) : Report() {
    companion object {
        fun generate(configuration: Configuration): StepReport {
            return when (configuration) {
                is FSAConfiguration -> generate(
                    configuration
                )
                is MealyConfiguration -> generate(
                    configuration
                )
                is PDAConfiguration -> generate(
                    configuration
                )
                else -> StepReport(-1, "", "")
            }
        }

        private fun generate(configuration: FSAConfiguration): StepReport {
            val read = configuration.input.subSequence(
                configuration.input.length - configuration.unprocessedInput.length - 1,
                configuration.input.length - configuration.unprocessedInput.length
            ).toString()

            return StepReport(
                configuration.currentState.id,
                read,
                configuration.unprocessedInput
            )
        }

        private fun generate(configuration: MealyConfiguration): StepReport {
            val read = configuration.input.subSequence(
                configuration.input.length - configuration.unprocessedInput.length - 1,
                configuration.input.length - configuration.unprocessedInput.length
            ).toString()

            return StepReport(
                configuration.currentState.id,
                read,
                configuration.unprocessedInput,
                configuration.output
            )
        }

        private fun generate(configuration: PDAConfiguration): StepReport {
            val read = configuration.input.subSequence(
                configuration.input.length - configuration.unprocessedInput.length - 1,
                configuration.input.length - configuration.unprocessedInput.length
            ).toString()

            return StepReport(
                configuration.currentState.id,
                read,
                configuration.unprocessedInput,
                stack = configuration.stack.toString()
            )
        }
    }

    override fun toInfoText(): String {
        return "state: $state, read: $read," +
                (if (currentOutput != null) " current output: $currentOutput," else "") +
                (if (stack != null )" stack: $stack," else "") +
                " to process: $toProcess"
    }

    override fun toString(): String {
        return "StepReport(state=$state, read='$read', toProcess='$toProcess', currentOutput=$currentOutput, stack=$stack)"
    }


}