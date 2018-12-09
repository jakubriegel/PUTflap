package pl.poznan.put.cie.putflap.report.structure.automaton

import jflap.automata.Configuration
import jflap.automata.fsa.FSAConfiguration
import jflap.automata.mealy.MealyConfiguration
import jflap.automata.pda.PDAConfiguration
import pl.poznan.put.cie.putflap.exception.IncompatibleAutomatonException
import pl.poznan.put.cie.putflap.report.Report

data class StepReport internal constructor(
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
    }

    constructor (configuration: FSAConfiguration): this(
        configuration.currentState.id,
        configuration.input.subSequence(
            configuration.input.length - configuration.unprocessedInput.length - 1,
            configuration.input.length - configuration.unprocessedInput.length
        ).toString(),
        configuration.unprocessedInput
    )

    constructor (configuration: MealyConfiguration): this(
            configuration.currentState.id,
            configuration.input.subSequence(
                configuration.input.length - configuration.unprocessedInput.length - 1,
                configuration.input.length - configuration.unprocessedInput.length
            ).toString(),
            configuration.unprocessedInput,
            configuration.output
        )

    constructor (configuration: PDAConfiguration): this(
            configuration.currentState.id,
            configuration.input.subSequence(
                configuration.input.length - configuration.unprocessedInput.length - 1,
                configuration.input.length - configuration.unprocessedInput.length
            ).toString(),
            configuration.unprocessedInput,
            stack = configuration.stack.toString()
        )
}