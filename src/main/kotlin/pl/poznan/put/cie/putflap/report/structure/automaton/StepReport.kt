package pl.poznan.put.cie.putflap.report.structure.automaton

import jflap.automata.Automaton
import jflap.automata.Configuration
import jflap.automata.fsa.FSAConfiguration
import jflap.automata.mealy.MealyConfiguration
import jflap.automata.mealy.MooreMachine
import jflap.automata.pda.PDAConfiguration
import jflap.automata.pda.PushdownAutomaton
import pl.poznan.put.cie.putflap.exception.IncompatibleAutomatonException
import pl.poznan.put.cie.putflap.report.Report

data class StepReport (
    val state: Int,
    val read: String,
    val toProcess: String,
    val currentOutput: String? = null,
    val stack: String? = null
) : Report() {
    companion object {
        fun generate(configuration: Configuration): StepReport {
            return when (configuration) {
                is FSAConfiguration -> StepReport(configuration)
                is MealyConfiguration -> StepReport(configuration)
                is PDAConfiguration -> StepReport(configuration)
                else -> throw IncompatibleAutomatonException("Step report supports only FSA, Mealy and PDA steps")
            }
        }

        fun initial(automaton: Automaton, input: String): StepReport {
            return StepReport(
                automaton.initialState.id,
                "",
                input,
                when (automaton) {
                    is MooreMachine -> automaton.getOutput(automaton.initialState)
                    else -> null
                },
                if (automaton is PushdownAutomaton) "Z" else null
            )
        }

        private fun getRead(configuration: FSAConfiguration): String {
            val parent = (configuration.parent as FSAConfiguration)
            return parent.unprocessedInput.substring(
                0,
                parent.unprocessedInput.length - configuration.unprocessedInput.length
            )
        }

        private fun getRead(configuration: MealyConfiguration): String {
            val parent = (configuration.parent as MealyConfiguration)
            return parent.unprocessedInput.substring(
                0,
                parent.unprocessedInput.length - configuration.unprocessedInput.length
            )
        }

        private fun getRead(configuration: PDAConfiguration): String {
            val parent = (configuration.parent as PDAConfiguration)
            return parent.unprocessedInput.substring(
                0,
                parent.unprocessedInput.length - configuration.unprocessedInput.length
            )
        }
    }

    constructor (configuration: FSAConfiguration): this(
        configuration.currentState.id,
        getRead(configuration),
        configuration.unprocessedInput
    )

    constructor (configuration: MealyConfiguration): this(
            configuration.currentState.id,
            getRead(configuration),
            configuration.unprocessedInput,
            configuration.output
        )

    constructor (configuration: PDAConfiguration): this(
            configuration.currentState.id,
            getRead(configuration),
            configuration.unprocessedInput,
            stack = configuration.stack.toString()
        )
}